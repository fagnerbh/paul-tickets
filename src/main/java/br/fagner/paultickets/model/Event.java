package br.fagner.paultickets.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
	
	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "evt_id")
    private String id;
	
	@Column(name = "evt_description", nullable = false)
    private String evtDescription;
	
	@Column(name = "evt_date", nullable = false)
    private LocalDateTime evtDate;

}
