package br.fagner.paultickets.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

@Component
public class LocalMemoryTakenSeatsCache implements TakenSeatsCache<String> {

	private static final Map<String, AtomicReference<String>> takenSeatsMap = new HashMap<>();

	@Override
	public AtomicReference<String> setTakenEventSeat(String seatKey, String ownerUser) {
		AtomicReference<String> atmId = new AtomicReference<>();
		atmId.set(ownerUser);
		takenSeatsMap.put(seatKey, atmId);

		return atmId;
	}

	@Override
	public AtomicReference<String> getTakenEventSeat(String seatKey) {
		return takenSeatsMap.get(seatKey);
	}

}
