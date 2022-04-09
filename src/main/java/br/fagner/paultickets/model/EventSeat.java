package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "event_seat")
@NoArgsConstructor
@Data
public class EventSeat implements Serializable {	
	
	@JsonIgnore
	@EmbeddedId
	private EventSeatId id;	
	
	@ManyToOne
    @JoinColumn(name = "ord_id")	
    private EventOrder order;
		
	@ManyToOne
    @JoinColumn(name = "evt_id",referencedColumnName="evt_id", insertable=false, updatable=false)
	@MapsId("evt_id")
    private Event event;
	
	@ManyToOne
	@JoinColumn(name = "sea_id",referencedColumnName="sea_id", insertable=false, updatable=false)
	@JsonManagedReference
	@MapsId("sea_id")
    private Seat seat;

}
