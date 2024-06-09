package br.fagner.paultickets.component.externalcache;

public interface SelectSeatsCache {

    public boolean reserveSeats(String eventId, String sectorId, Integer [] seatNums);
}
