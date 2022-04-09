package br.fagner.paultickets.service;

import java.util.Collection;
import java.util.List;

import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.EventSeat;

public interface EventOrderService {
	
	public List<EventSeat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException;

}
