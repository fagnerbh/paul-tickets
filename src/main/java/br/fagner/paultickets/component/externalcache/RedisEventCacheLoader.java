package br.fagner.paultickets.component.externalcache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import br.fagner.paultickets.service.EventSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Validated
@Log4j2
public class RedisEventCacheLoader implements EventCacheLoader {

    private final EventSeatService eventSeatService;

    private final RedissonClient redissonClient;

    @Override
    public void eventCacheInit() {
        log.info("CHAMOU O CACHE LOADER");

        List<Map<String, Object>> eventSectors = eventSeatService.listSectorsByEvent();

        if (eventSectors == null || eventSectors.isEmpty()) {
            log.info("RETORNOU NADA DE EVENTOS");
            return;
        }

        //RMapCache<String, int []> mapCache = redissonClient.getMapCache("event-cache");

        for (Map<String, Object> dto: eventSectors) {
            Short[] seatArray = new Short[Short.valueOf(((Long) dto.get("seatNum")).toString())];
            Arrays.fill(seatArray, Short.valueOf("0"));

            String[] seatArrayStrings = Arrays.stream(seatArray)
                                              .map(Object::toString)
                                              .toArray(String[]::new);
                        // Construir o script Lua para JSON.ARRINSERT
            String script = "local arr = redis.call('JSON.GET', KEYS[1], '$') " +
                    "if not arr or arr == nil or arr == '' then " +
                    "  arr = {} " +
                    "else " +
                    "  arr = cjson.decode(arr) " +
                    "end " +
                    "for i = 1, #ARGV do " +
                    "  table.insert(arr, tonumber(ARGV[i])) " +
                    "end " +
                    "redis.call('JSON.SET', KEYS[1], '.', cjson.encode(arr)) " +
                    "return 1";

            redissonClient.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER,
                    java.util.Collections.singletonList(String.format("event-cache::%s:%s", dto.get("venue"), dto.get("sector"))),
                    (Object[]) seatArrayStrings);

            log.info("Cache sector array for key {}, {}: {}", dto.get("key"), dto.get("key"), Arrays.toString(seatArray));

            //RBucket<String> bucket = redissonClient.getBucket(String.format("event-cache::%s:%s", dto.get("venue"), dto.get("sector")));
            //bucket.set(jsonArray);

            //mapCache.put(String.format("%s:%s", dto.get("venue"), dto.get("sector")), seatArray);

        }

    }

}
