package br.fagner.paultickets.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import br.fagner.paultickets.component.TakenSeatsCache;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.Seat;

public interface EventOrderService {

	public List<EventSeat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException;

	public default AtomicReference<String>[] concurrentControll(String userId, String eventId, String sectorId, List<Seat> seatList, TakenSeatsCache<String> takenSeatsCache) {
		AtomicReference<String>[] arrayAtmReferences = new AtomicReference[seatList.size()];

		int count = 0;
		for (Seat seat: seatList) {
			arrayAtmReferences[count++] = takenSeatsCache.setTakenEventSeat(eventId + sectorId + String.valueOf(seat.getSeaNum()), userId);
		}

		return arrayAtmReferences;
	}

}
