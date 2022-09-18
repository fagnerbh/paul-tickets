package br.fagner.paultickets.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import br.fagner.paultickets.controller.request.OrderRequest;
import br.fagner.paultickets.exception.ReservationException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

	@Qualifier("simpleEventOrderService")
	private final EventOrderService eventOrderService;

	private static final ObjectMapper mapper = new ObjectMapper();

	private static final Gson gson = new Gson();

	@Override
	public void onMessage(Message message, byte[] pattern) {
		Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);

		OrderRequest order = null;
		try {
			JsonPrimitive jsonOrder = gson.fromJson(message.toString(), JsonPrimitive.class);

			order = mapper.readValue(jsonOrder.getAsString(), OrderRequest.class);

			eventOrderService.reserveSeats(order.getUserId(), order.getEventId(), order.getSectorId(), order.getNumSeats());


		} catch (ReservationException | JsonSyntaxException | JsonProcessingException e) {
			if (order != null) {
				logger.info("could not reserve seats for user " + order.getUserId());
			}
			return;
		}
		logger.debug("Message received: " + order.toString());
	}
}