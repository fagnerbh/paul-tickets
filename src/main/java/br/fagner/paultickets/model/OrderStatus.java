package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderStatus implements Serializable {

	public OrderStatus(int id) {
        super();
        this.id = id;
    }

    @Id
    @Column(name = "ors_id")
    private int id;

	@Column(name = "ors_description", nullable = false)
    private String orsDescription;
}
