package br.fagner.paultickets.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.Seat;
import br.fagner.paultickets.model.Sector;

public interface SeatDao extends JpaRepository<Seat, String> {

    public Optional<Seat> findBySectorAndSeatNum(Sector sector, int seatNum);

}
