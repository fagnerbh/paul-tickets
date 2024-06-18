package br.fagner.paultickets.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.fagner.paultickets.component.externalcache.SelectSeatsCache;
import br.fagner.paultickets.dao.EventSeatDao;
import br.fagner.paultickets.dao.OrderDao;
import br.fagner.paultickets.dao.SeatDao;
import br.fagner.paultickets.dao.UserDao;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.Event;
import br.fagner.paultickets.model.EventOrder;
import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.EventSeatId;
import br.fagner.paultickets.model.OrderStatus;
import br.fagner.paultickets.model.Seat;
import br.fagner.paultickets.model.Sector;
import br.fagner.paultickets.model.User;
import br.fagner.paultickets.value.OrderStatusEnum;
import lombok.RequiredArgsConstructor;

/**
 * Searches in an external previously loaded event seats cache for rooms available before
 * regestering a pre order
 */
@Service
@RequiredArgsConstructor
@Qualifier("externalCacheEventOrderService")
public class ExternalCacheEventOrderService implements EventOrderService {

    private final SelectSeatsCache selectSeatsCache;

    private final UserDao userDao;

    private final SeatDao seatDao;

    private final OrderDao orderDao;

    private final EventSeatDao eventSeatDao;

    @Override
    public List<EventSeat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException {
        List<EventSeat> reservedSeats = new ArrayList<>();

        if (!selectSeatsCache.reserveSeats(eventId, sectorId, numSeats.toArray(new Integer[0]))) {
            return reservedSeats;
        }

        User user = userDao.findById(userId).orElseThrow(() -> new ReservationException("user not found"));

        Sector sector = new Sector();
        sector.setId(sectorId);

        final Iterator<Integer> seatIt = numSeats.iterator();
        while(seatIt.hasNext()) {
            Seat seat = seatDao.findBySectorAndSeatNum(sector, seatIt.next()).orElseThrow(() -> new ReservationException("user not found"));

            registerUserEventOrders(eventId, reservedSeats, user, seat);
        }

        return reservedSeats;
    }

    private void registerUserEventOrders(String eventId, List<EventSeat> reservedSeats, User user, Seat seat) {
        EventOrder reserveOrder;
        Event event;
        reserveOrder = new EventOrder();

        // creates a order with reserved status for later confirmation on check-out
        reserveOrder.setUser(user);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        reserveOrder.setOrdDate(LocalDateTime.parse(format.format(Calendar.getInstance().getTime())));
        reserveOrder.setOrderStatus(new OrderStatus(OrderStatusEnum.RESERVED.getStatus()));

        EventSeat eventSeat = new EventSeat();
        eventSeat.setOrder(reserveOrder);

        EventSeatId eventSeatId = new EventSeatId();
        eventSeatId.setEvtId(eventId);
        eventSeatId.setSeaId(seat.getId());

        event = new Event();
        event.setId(eventId);

        eventSeat.setId(eventSeatId);
        eventSeat.setEvent(event);
        eventSeat.setSeat(seat);

        orderDao.save(reserveOrder);
        eventSeatDao.save(eventSeat);
        reservedSeats.add(eventSeat);
    }

}
