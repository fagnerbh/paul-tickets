package br.fagner.paultickets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "sea_id")
	private String id;
		
	@OneToOne
    @JoinColumn(name = "sec_id", nullable = false)
    private Sector sector;	
	
	@Column(name="sea_num", nullable = false)
    private int seaNum;
}
