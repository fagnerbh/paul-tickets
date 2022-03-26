package br.fagner.paultickets.service;

import java.util.Collection;
import java.util.List;

import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.Seat;

public interface EventOrder {
	
	public List<Seat> reserveSeats(String userId, String eventId, String sectorId, Collection<Integer> numSeats) throws ReservationException;

}
