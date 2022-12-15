package br.fagner.paultickets.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.EventSeatId;
import br.fagner.paultickets.model.Seat;

public interface EventSeatDao extends JpaRepository<EventSeat, EventSeatId> {

	@Query("SELECT sea FROM Seat sea "
			+ "INNER JOIN sea.sector sec "
    		+ "WHERE (sec.id = :secId) AND sea.seaNum IN :numSeat "
			+ "AND NOT EXISTS (SELECT 1 FROM EventSeat evs INNER JOIN evs.seat innerSea INNER JOIN evs.event innerEvt WHERE "
    		+ "(innerSea.seaNum IN :numSeat) AND (innerSea.sector.id = :secId) AND (innerEvt.id = :eventId) "
			+ "AND evs.order.id is not NULL) "
    		+ " ORDER BY sea.seaNum DESC")
	List<Seat> findNumSeatsForEventAvailable(@Param ("eventId") String eventId, @Param ("secId") String sectorId, @Param ("numSeat") Collection<Integer> numSeat );



}
