package br.fagner.paultickets.config;

import java.io.File;
import java.time.Duration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import br.fagner.paultickets.service.EventOrderService;
import br.fagner.paultickets.service.RedisMessageSubscriber;

@Configuration
public class RedisConfig {

    @Value(value = "${spring.redis.host}")
    private String redisHostName;

    @Value(value = "${event.cache.time-to-live}")
    private long eventCacheTtl;

    @Bean
    public JedisConnectionFactory createRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostName);
        redisStandaloneConfiguration.setPort(6379);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(createRedisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener(final EventOrderService eventOrderService) {
        return new MessageListenerAdapter(new RedisMessageSubscriber(eventOrderService));
    }

    @Bean
    RedisMessageListenerContainer container(final EventOrderService eventOrderService, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(createRedisConnectionFactory());
        container.addMessageListener(messageListener(eventOrderService), new PatternTopic("orders"));

        return container;
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("orders");
    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("event-cache",
                        RedisCacheConfiguration.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
                        .entryTtl(Duration.ofSeconds(eventCacheTtl)))
                .build();
    }

    @Bean
    RedissonClient redissonClientConfig() {
        File redisConfFile = new File("singleNodeConfig.yaml");

        Config config = Config.fromYAML(redisConfFile);
        return Redisson.create(config);
    }

}
