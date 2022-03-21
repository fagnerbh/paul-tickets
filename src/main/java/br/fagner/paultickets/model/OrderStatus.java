package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status")
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus implements Serializable {
	
	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "ors_id")
    private String id;
	
	@Column(name = "ors_description", nullable = false)
    private String orsDescription;

}
