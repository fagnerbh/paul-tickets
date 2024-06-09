package br.fagner.paultickets.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import br.fagner.paultickets.component.externalcache.EventCacheLoader;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationStartup {

    private final EventCacheLoader eventCacheLoader;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent() {
        eventCacheLoader.eventCacheInit();
    }

}
