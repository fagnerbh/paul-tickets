package br.fagner.paultickets.component.externalcache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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

    private final CacheManager cacheManager;

    @Override
    public void eventCacheInit() {
        log.info("CHAMOU O CACHE LOADER");

        List<Map<String, Object>> eventSectors = eventSeatService.listSectorsByEvent();

        if (eventSectors == null || eventSectors.isEmpty()) {
            log.info("RETORNOU NADA DE EVENTOS");
            return;
        }

        Cache cache = cacheManager.getCache("event-cache");

        for (Map<String, Object> dto: eventSectors) {
            int [] seatArray = new int[Integer.valueOf(((Long) dto.get("seatNum")).toString())];

            Arrays.stream(seatArray).forEach(seat -> seat = 0);

            cache.put(String.format("%s:%s", dto.get("venue"), dto.get("sector")), seatArray);

            log.info("Cache sector array for key {}, {}: {}", dto.get("key"), dto.get("key"), seatArray);
        }

    }

}
