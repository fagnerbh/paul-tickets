package br.fagner.paultickets.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fagner.paultickets.component.SemaphorCache;
import br.fagner.paultickets.component.TakenSeatsCache;
import br.fagner.paultickets.dao.EventDao;
import br.fagner.paultickets.dao.EventSeatDao;
import br.fagner.paultickets.dao.OrderDao;
import br.fagner.paultickets.dao.UserDao;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.exception.SectorNotFoundException;
import br.fagner.paultickets.model.Event;
import br.fagner.paultickets.model.EventOrder;
import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.EventSeatId;
import br.fagner.paultickets.model.OrderStatus;
import br.fagner.paultickets.model.Seat;
import br.fagner.paultickets.model.User;
import br.fagner.paultickets.value.OrderStatusEnum;
import lombok.extern.log4j.Log4j2;

/**
 * Manage events' seat reservation
 * @author fagner
 *
 */
@Service
@Transactional(rollbackFor = { ReservationException.class })
@Log4j2
public class SempaphorEventOrder implements EventOrderService {

	@Autowired
	private EventSeatDao eventSeatDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	@Qualifier("singleSemaphor")
	private SemaphorCache edventSeatSemaphorCache;

	@Autowired
	private TakenSeatsCache<String> takenSeatsCache;

	@Override
	public List<EventSeat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException {
		List<EventSeat> reservedSeats = new ArrayList<>();

		if (numSeats == null || numSeats.isEmpty()) {
			return reservedSeats;
		}

		Semaphore secSemaphor = null;
		boolean semaphorAquired = true;
		try {
			secSemaphor = edventSeatSemaphorCache.getSectorSemaphor(sectorId);
			semaphorAquired = secSemaphor.tryAcquire(2, TimeUnit.SECONDS);

			if (!semaphorAquired) {
				log.info("Sempaphor timeout");
				throw new ReservationException("Timeout.");
			}

			User user = userDao.findById(userId).orElseThrow(() -> new ReservationException("user not found"));
			Event targetEvent = eventDao.findById(eventId).orElseThrow(() -> new ReservationException("Event not found"));
			//retrieves seats for update (locking the given seats registers)
			List<Seat> seatList = eventSeatDao.findNumSeatsForEventAvailable(eventId, sectorId, numSeats);

			// if the number of seats retrieved from the database doesn't match the total request, the reserve is failed.
			if (seatList.size() != numSeats.size()) {
				log.info("Seats already taken");
				throw new ReservationException("Could not reserve all seats");
			}

			AtomicReference<String>[] arraySeats = concurrentControll(userId, eventId, sectorId, seatList);

			EventOrder reserveOrder = new EventOrder();

            // creates a order with reserved status for later confirmation on check-out
			reserveOrder.setUser(user);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			reserveOrder.setOrdDate(LocalDateTime.parse(format.format(Calendar.getInstance().getTime())));
			reserveOrder.setOrderStatus(new OrderStatus(OrderStatusEnum.RESERVED.getStatus()));

			for(Seat seat: seatList)  {
				EventSeat eventSeat = new EventSeat();
				eventSeat.setOrder(reserveOrder);

				EventSeatId eventSeatId = new EventSeatId();
				eventSeatId.setEvtId(targetEvent.getId());
				eventSeatId.setSeaId(seat.getId());

				eventSeat.setId(eventSeatId);
				eventSeat.setEvent(targetEvent);
				eventSeat.setSeat(seat);

				String atmUser = takenSeatsCache.getTakenEventSeat(eventId + sectorId + String.valueOf(seat.getSeaNum())).get();

				for (AtomicReference<String> controllCache : arraySeats) {
					if (!controllCache.get().equals(atmUser)) {
						throw new ReservationException("Seat already taken");
					}
				}

				reserveOrder = orderDao.save(reserveOrder);
				eventSeatDao.save(eventSeat);
				reservedSeats.add(eventSeat);
			}

		} catch (SectorNotFoundException e) {
			throw new ReservationException("Sector not found.");
		} catch (InterruptedException e) {
			throw new ReservationException("Timeout.");
		} finally {
			if (secSemaphor != null && semaphorAquired) {
				secSemaphor.release();
			}
		}

		return reservedSeats;
	}

	private AtomicReference<String>[] concurrentControll(String userId, String eventId, String sectorId, List<Seat> seatList) {
		AtomicReference<String>[] arrayAtmReferences = new AtomicReference[seatList.size()];

		int count = 0;
		for (Seat seat: seatList) {
			arrayAtmReferences[count++] = takenSeatsCache.setTakenEventSeat(eventId + sectorId + String.valueOf(seat.getSeaNum()), userId);
		}

		return arrayAtmReferences;
	}

}
