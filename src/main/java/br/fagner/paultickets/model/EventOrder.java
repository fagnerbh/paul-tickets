package br.fagner.paultickets.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_order")
@Data
@NoArgsConstructor
public class EventOrder implements Serializable {

	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "ord_id")
    private String id;
	
	@Column(name = "ord_date", nullable = false)
    private LocalDateTime ordDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ors_id", nullable = false)
	@JsonManagedReference
    private OrderStatus orderStatus;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usr_id", nullable = false)
	@JsonManagedReference
    private User user;
}
