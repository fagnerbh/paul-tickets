package br.fagner.paultickets.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import br.fagner.paultickets.service.EventOrderService;


public class EventOrderMessageListener extends MessageListenerAdapter {

    private final EventOrderService eventOrderService;

    public EventOrderMessageListener(@Qualifier("externalCacheEventOrderService") EventOrderService eventOrderService) {
        super(eventOrderService);
        this.eventOrderService = eventOrderService;
    }

}
