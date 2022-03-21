package br.fagner.paultickets.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "venue")
@AllArgsConstructor
@NoArgsConstructor
public class Venue implements Serializable {
	
	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "vnu_id")
    private String id;
	
	@Column(name = "vnu_location", nullable = false)
    private String vnuLocation;
	
	@EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "venue")    
    @JsonIgnore
    private final Set<Sector> sector = new HashSet<>();

}
