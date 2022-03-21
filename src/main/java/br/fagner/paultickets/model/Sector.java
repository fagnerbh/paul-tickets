package br.fagner.paultickets.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sector")
@AllArgsConstructor
@NoArgsConstructor
public class Sector {
	
	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "sec_id")
    private String id;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne
    @JoinColumn(name = "vnu_id")
    private Venue venue;
	
	@EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "sector")    
    @JsonIgnore
    private final Set<Seat> seats = new HashSet<>();
}
