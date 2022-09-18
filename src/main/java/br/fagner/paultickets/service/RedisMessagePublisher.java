package br.fagner.paultickets.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<Object, Object> redisTemplate;

    private final ChannelTopic topic;

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}

}
