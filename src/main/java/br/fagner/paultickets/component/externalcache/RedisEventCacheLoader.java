package br.fagner.paultickets.component.externalcache;

import java.util.Arrays;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import br.fagner.paultickets.model.dto.EventSectorDto;
import br.fagner.paultickets.service.EventSeatService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Validated
public class RedisEventCacheLoader implements EventCacheLoader {

    private final EventSeatService eventSeatService;

    private final CacheManager cacheManager;

    @Override
    public void eventCacheInit() {
        List<EventSectorDto> eventSectors = eventSeatService.listSectorsByEvent();

        if (eventSectors == null || eventSectors.isEmpty()) {
            return;
        }

        Cache cache = cacheManager.getCache("event-cache");

        for (EventSectorDto dto: eventSectors) {
            int [] seatArray = new int[dto.getSeatCount()];

            Arrays.stream(seatArray).forEach(seat -> seat = 0);

            cache.put(String.format("%s:%s", dto.getEventId(), dto.getSectorId()), seatArray);
        }

    }

}
