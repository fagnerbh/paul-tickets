package br.fagner.paultickets.component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.fagner.paultickets.dao.SectorDao;
import br.fagner.paultickets.exception.SectorNotFoundException;
import br.fagner.paultickets.model.Sector;

/**
 * Holds a cache of Venue's sector per event semaphors for availability queries
 * @author fagner
 *
 */
@Component
@Qualifier("eventSeatSemaphorCache")
public class EventSeatSemaphorCache implements SemaphorCache {
	
	@Value("${sector.semaphor.max.queue.size}")
	private String semaphorQueueSize;
	
	private ConcurrentHashMap<String, Semaphore> semCache;
	
	private SectorDao sectorDao;

	public EventSeatSemaphorCache(SectorDao sectorDao) {
		super();
		this.sectorDao = sectorDao;
		
		semCache = new ConcurrentHashMap<>(sectorDao.countSectors());
	}
	
	@Override
	public Semaphore getSectorSemaphor(String secId) throws SectorNotFoundException {
		if (semCache.containsKey(secId)) {
			return semCache.get(secId);
		} else {			
			Sector wantedSector = sectorDao.findById(secId).orElseThrow(() -> new SectorNotFoundException());
			
			Semaphore returnSem = new Semaphore(Integer.valueOf(semaphorQueueSize));
			semCache.put(wantedSector.getId(), returnSem);
			
			return returnSem;
		}
	}

}
