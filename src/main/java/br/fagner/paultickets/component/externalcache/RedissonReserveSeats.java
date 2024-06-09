package br.fagner.paultickets.component.externalcache;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedissonReserveSeats implements SelectSeatsCache {

    private static final int SEAT_TAKEN = 1;
    private static final int SEAT_TAKEN_NOT_TAKEN = 0;
    private final RedissonClient redissonClient;

    @Override
    public boolean reserveSeats(String eventId, String sectorId, Integer [] seatNums) {
        //Redisson's objects are thread safe.
        RList<Integer> sectorList = redissonClient.getList(String.format("event-cache::%s:%s", eventId, sectorId));

        for (int seatNum: seatNums) {
            int status = sectorList.get(seatNum);

            if (status == SEAT_TAKEN_NOT_TAKEN) {

                for (Integer seat: sectorList) {
                    // restaure previous seats marked as taken in this reservation scope.
                    sectorList.setAsync(seat, SEAT_TAKEN_NOT_TAKEN);
                }

                return false;
            }

            sectorList.setAsync(seatNum, SEAT_TAKEN);

        }
        return true;
    }

}
