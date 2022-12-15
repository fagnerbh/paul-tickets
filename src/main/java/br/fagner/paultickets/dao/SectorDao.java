package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.fagner.paultickets.model.Sector;

public interface SectorDao extends JpaRepository<Sector, String> {
	
	@Query("select count(s) from Sector s")
	public int countSectors();

}
