package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.Event;

public interface EventDao extends JpaRepository<Event, String> {

}
