package br.fagner.paultickets.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSeatId implements Serializable {
	
	private Order order;
	private Event event;
	private Seat seat;
	
}
