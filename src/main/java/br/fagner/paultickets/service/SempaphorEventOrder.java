package br.fagner.paultickets.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fagner.paultickets.dao.EventDao;
import br.fagner.paultickets.dao.EventSeatDao;
import br.fagner.paultickets.dao.OrderDao;
import br.fagner.paultickets.dao.UserDao;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.Order;
import br.fagner.paultickets.model.OrderStatus;
import br.fagner.paultickets.model.Seat;
import br.fagner.paultickets.value.OrderStatusEnum;
import lombok.AllArgsConstructor;

/**
 * Manage events' seat reservation
 * @author fagner
 *
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = { ReservationException.class })
public class SempaphorEventOrder implements EventOrder {
	private EventSeatDao eventSeatDao;
	
	private UserDao userDao;
	
	private OrderDao orderDao;
	
	private EventDao eventDao;

	@Override
	public List<Seat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException {
		List<Seat> reservedSeats = new ArrayList<>();
		
		if (numSeats == null || numSeats.isEmpty()) {
			return reservedSeats;
		}
		
		//retrieves seats for update (locking)
		reservedSeats = eventSeatDao.findNumSeatsForEventAvailable(eventId, sectorId, numSeats);

		for(Seat seat: reservedSeats)  {
			Order reserveOrder = new Order();

			reserveOrder.setUser(userDao.findById(userId).orElseThrow(() -> new ReservationException()));
			reserveOrder.setOrdDate(LocalDateTime.now());
			reserveOrder.setOrderStatus(new OrderStatus(OrderStatusEnum.RESERVED.getStatus()));
			
			reserveOrder = orderDao.save(reserveOrder);
			
			EventSeat eventSeat = new EventSeat(reserveOrder, eventDao.findById(eventId).orElseThrow(() -> new ReservationException()), seat);
			eventSeatDao.save(eventSeat);
		}
		
		return reservedSeats;
	}

}
