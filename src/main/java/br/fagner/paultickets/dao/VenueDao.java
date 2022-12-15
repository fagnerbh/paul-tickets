package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.Venue;

public interface VenueDao extends JpaRepository<Venue, String> {

}
