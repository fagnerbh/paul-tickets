package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat implements Serializable {
		
	@Id	
	@OneToOne
    @JoinColumn(name = "sec_id", nullable = false)
    private Sector sector;
	
	@Id	
	@Column(name="sea_num", nullable = false)
    private int seaNum;
}
