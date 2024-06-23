package br.fagner.paultickets.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import br.fagner.paultickets.service.RedisMessageSubscriber;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MessageListenerConfig {

    @Value(value = "${spring.redis.host}")
    private String redisHostName;

    private final RedisMessageSubscriber redisMessageSubscriber;

    private final JedisConnectionFactory jedisConnectionFactory;

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisMessageSubscriber, "onMessage");
    }

    @Bean
    RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(messageListener(), new PatternTopic("orders"));

        return container;
    }
}
