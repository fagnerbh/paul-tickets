package br.fagner.paultickets.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fagner.paultickets.model.EventSeat;
import br.fagner.paultickets.model.Seat;

public interface EventSeatDao extends JpaRepository<EventSeat, String> {
	
	@Query("SELECT seat FROM Seat set "			
    		+ "WHERE (set.sector.id = :secId) AND set.seaNum IN :numSeat " 
			+ "AND NOT EXISTS (SELECT 1 FROM EventSeat evs INNER JOIN Seat sea WHERE "  
    		+ "(sea.seaNum IN :numSeat) AND (sea.sector.id = :secId) AND (evs.event.id = :eventId) "
			+ "AND evs.order.id is not NULL)"
    		+ " ORDER BY set.seaNum DESC FOR UPDATE")
	List<Seat> findNumSeatsForEventAvailable(@Param ("eventId") String eventId, @Param ("secId") String sectorId, @Param ("numSeat") Collection<Integer> numSeat );

}
