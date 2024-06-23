package br.fagner.paultickets.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import br.fagner.paultickets.component.externalcache.EventCacheLoader;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationStartup {

    private final EventCacheLoader eventCacheLoader;

    //@EventListener(ApplicationReadyEvent.class)
    @PostConstruct
    public void onApplicationEvent() {
        eventCacheLoader.eventCacheInit();
    }

}
