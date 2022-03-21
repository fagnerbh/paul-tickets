package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;

@Entity
@Table(name = "event_seat")
@IdClass(EventSeatId.class)
@AllArgsConstructor
public class EventSeat implements Serializable {
		
	@ManyToOne
    @JoinColumn(name = "ord_id")
	@Id
    private Order order;
	
	@ManyToOne
    @JoinColumn(name = "evt_id")
	@Id
    private Event event;
	
	@OneToOne
	@JoinColumn(name = "set_id", nullable = false)
	@JsonManagedReference
	@Id
    private final Seat seat;

}
