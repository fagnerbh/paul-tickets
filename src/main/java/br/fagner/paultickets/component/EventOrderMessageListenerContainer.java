package br.fagner.paultickets.component;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public class EventOrderMessageListenerContainer extends RedisMessageListenerContainer {

    private final MessageListenerAdapter messageListenerAdapter;

    private final JedisConnectionFactory jedisConnectionFactory;

    public EventOrderMessageListenerContainer(MessageListenerAdapter messageListenerAdapter, JedisConnectionFactory jedisConnectionFactory) {
        super();
        this.messageListenerAdapter = messageListenerAdapter;
        this.jedisConnectionFactory = jedisConnectionFactory;

        setConnectionFactory(this.jedisConnectionFactory);
        addMessageListener(this.messageListenerAdapter, new PatternTopic("orders"));
    }

}
