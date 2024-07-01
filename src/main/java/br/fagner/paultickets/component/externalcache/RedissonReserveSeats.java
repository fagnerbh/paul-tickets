package br.fagner.paultickets.component.externalcache;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
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

    private static final String LUA_SCRIPT = "local arr = redis.call('JSON.GET', KEYS[1]) " +
            "if not arr or arr == nil then return 0 end " +
            "arr = cjson.decode(arr) " +
            "if arr[ARGV[1] + 1] == 0 then " +  //-- Verificar se o valor original é 0
            "arr[ARGV[1] + 1] = tonumber(ARGV[2]) " +  //-- Atualizar o valor
            "redis.call('JSON.SET', KEYS[1], '.', cjson.encode(arr)) " +
            "return 1 " +  //-- Retornar 1 para indicar que houve modificação
            "else " +
            "return 0 " +  //-- Retornar 0 para indicar que não houve modificação
            "end";

    @Override
    public boolean reserveSeats(String eventId, String sectorId, Integer [] seatNums) {
        //Redisson's objects are thread safe.
        //RList<Integer> sectorList = redissonClient.get.getList(String.format("event-cache::%s:%s", eventId, sectorId));


        /*for (int seatNum: seatNums) {
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
        return true;*/
        long opOk = 0;
        for (int seatNum: seatNums) {
            opOk = redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_WRITE, LUA_SCRIPT, RScript.ReturnType.INTEGER,
                      java.util.Collections.singletonList(String.format("event-cache::%s:%s", eventId, sectorId)),
                      seatNum, Short.valueOf("1"));
        }

        if (opOk == 0) {
            for (int seatNum: seatNums) {
                opOk = redissonClient.getScript(StringCodec.INSTANCE)
                    .eval(RScript.Mode.READ_WRITE, LUA_SCRIPT, RScript.ReturnType.INTEGER,
                          java.util.Collections.singletonList(String.format("event-cache::%s:%s", eventId, sectorId)),
                          seatNum, Short.valueOf("0"));
            }
        }

        return opOk == 1;
    }

}
