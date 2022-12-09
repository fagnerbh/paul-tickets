package br.fagner.paultickets.component;

import java.util.concurrent.atomic.AtomicReference;

public interface TakenSeatsCache<T> {

	public AtomicReference<T> setTakenEventSeat(String seatKey, String ownerUser);

	public AtomicReference<T> getTakenEventSeat(String seatKey);

	public void removeValueInKey(String seatKey);

}
