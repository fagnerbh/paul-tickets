package br.fagner.paultickets.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import br.fagner.paultickets.PaulTicketsApplication;
import br.fagner.paultickets.controller.request.OrderRequest;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.service.EventOrderService;

public class OrderReceiver {

	Logger logger = LoggerFactory.getLogger(OrderReceiver.class);

	public void receiveOrder(OrderRequest order) {
		try {
			ApplicationContext ctx = SpringApplication.run(PaulTicketsApplication.class);

			EventOrderService eventOrder = ctx.getBean(EventOrderService.class);

			eventOrder.reserveSeats(order.getUserId(), order.getEventId(), order.getSectorId(), order.getNumSeats());


		} catch (ReservationException e) {
			logger.info("could not reserve seats for user " + order.getUserId());
		}
	}

}
