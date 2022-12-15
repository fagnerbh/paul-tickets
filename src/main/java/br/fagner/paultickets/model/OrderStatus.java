package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "order_status")
@AllArgsConstructor
@Data
public class OrderStatus implements Serializable {
	
	@Id    
    @Column(name = "ors_id")
    private final int id;
	
	@Column(name = "ors_description", nullable = false)
    private String orsDescription;
	
	public OrderStatus(int id) {
		this.id = id;
	}

}
