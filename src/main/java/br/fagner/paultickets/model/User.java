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
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	@Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Column(name = "usr_id")
    private String id;
	
	@Column(name = "usr_username", nullable = false)
    private String userName;
}
