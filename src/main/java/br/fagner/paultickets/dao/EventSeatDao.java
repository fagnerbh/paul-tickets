package br.fagner.paultickets.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fagner.paultickets.model.EventSeat;

public interface EventSeatDao extends JpaRepository<EventSeat, String> {
	
	@Query("SELECT seat FROM Seat set INNER JOIN Sector sec ON (sec.id = set.sector.id) "
			+ "LEFT OUTER JOIN EventSeat evs ON (evs.seat.id = set.id WITH evs.seat.id is NULL) "
    		+ "WHERE (sec.id = :secId) "
    		+ "AND (evs.event.id = :eventId) LIMIT :numSeatsOrder FOR UPDATE")
	List<EventSeat> findNumSeatsForEventAvailable(@Param ("secId") String eventId, @Param ("secId") String sectorId, @Param ("numSeatsOrder") Integer numSeatsOrder );

}
