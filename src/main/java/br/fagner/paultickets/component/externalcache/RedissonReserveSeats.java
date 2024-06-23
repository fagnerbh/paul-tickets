package br.fagner.paultickets.component.externalcache;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class RedissonReserveSeats implements SelectSeatsCache {

    private static final int SEAT_TAKEN = 1;
    private static final int SEAT_NOT_TAKEN = 0;
    private final RedissonClient redissonClient;

    @Override
    public boolean reserveSeats(String eventId, String sectorId, Integer [] seatNums) {
        //Redisson's objects are thread safe.
        RList<Integer> sectorList = redissonClient.getList(String.format("event-cache::%s:%s", eventId, sectorId));


        for (int seatNum: seatNums) {
            int status = sectorList.get(seatNum);

            log.info("status value for seatNum {} for sector {} ", status, seatNum, sectorId);

            if (status == SEAT_TAKEN) {

                for (Integer seat: seatNums) {
                    // restaure previous seats marked as taken in this reservation request.
                    sectorList.setAsync(seat, SEAT_NOT_TAKEN);
                }

                return false;
            }

            log.info("seat {} fo sector {} for event {} has been taken", seatNum, sectorId, eventId);
            sectorList.setAsync(seatNum, SEAT_TAKEN);

        }
        return true;
    }

}
