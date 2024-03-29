package br.fagner.paultickets.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.fagner.paultickets.controller.request.OrderRequest;
import br.fagner.paultickets.controller.response.OrderResponse;
import br.fagner.paultickets.dao.SeatDao;
import br.fagner.paultickets.exception.ReservationException;
import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.Seat;
import br.fagner.paultickets.service.EventOrderService;
import br.fagner.paultickets.service.RedisMessagePublisher;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/event/eventorder")
@AllArgsConstructor
public class EventOrderController {

	private final EventOrderService eventOrder;

	private final SeatDao seatDao;

	private final RedisMessagePublisher redisMessagePublisher;

	private static final ObjectMapper mapper = new ObjectMapper();

	@PostMapping(value="/v0", consumes="application/json", produces = "application/json")
	public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderRequest orderRequest) {
		try {
			List<EventSeat> eventSeats = eventOrder.reserveSeats(orderRequest.getUserId(), orderRequest.getEventId(), orderRequest.getSectorId(), orderRequest.getNumSeats());

			OrderResponse responseSuccess = new OrderResponse();

			responseSuccess.setEventId(orderRequest.getEventId());
			responseSuccess.setSectorId(orderRequest.getSectorId());

			List<Integer> seats = new LinkedList<>();
			for (EventSeat eventSeat: eventSeats) {
				Seat sea = seatDao.findById(eventSeat.getSeat().getId()).orElseThrow(() -> new ReservationException("no seats found."));

				seats.add(sea.getSeaNum());
			}
			responseSuccess.setSeats(seats);

			return new ResponseEntity<>(responseSuccess, HttpStatus.CREATED);
		} catch (ReservationException e) {
			OrderResponse responseConflict = new OrderResponse();
			responseConflict.setMessage(e.getMessage());

			return new ResponseEntity<>(responseConflict, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value="/v1", consumes="application/json", produces = "application/json")
	public Callable<ResponseEntity<?>> createV1(@RequestBody @Valid OrderRequest orderRequest) {
		return () -> {
			try {
				redisMessagePublisher.publish(mapper.writeValueAsString(orderRequest));
			} catch (JsonProcessingException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		};

	}

}
