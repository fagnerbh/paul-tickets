package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.Seat;

public interface SeatDao extends JpaRepository<Seat, String> {

}
