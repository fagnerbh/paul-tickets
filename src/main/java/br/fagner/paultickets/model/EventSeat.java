package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "event_seat")
@IdClass(EventSeatId.class)
@AllArgsConstructor
@Data
public class EventSeat implements Serializable {
		
	@ManyToOne
    @JoinColumn(name = "ord_id")	
    private Order order;
	
	@ManyToOne
    @JoinColumn(name = "evt_id", nullable = false)
	@Id
    private Event event;
	
	@OneToOne
	@JoinColumns({
        @JoinColumn(name = "sec_id", referencedColumnName = "sec_id"),
        @JoinColumn(name = "sea_num", referencedColumnName = "sea_num")        
    })
	@JsonManagedReference
	@Id
    private final Seat seat;

}
