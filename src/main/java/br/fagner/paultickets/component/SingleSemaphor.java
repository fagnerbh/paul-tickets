package br.fagner.paultickets.component;

import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.fagner.paultickets.exception.SectorNotFoundException;

@Component
@Qualifier("singleSemaphor")
public class SingleSemaphor implements SemaphorCache {
	
	@Value("${sector.semaphor.max.queue.size}")
	private String semaphorQueueSize;
	
	private Semaphore semaphore;

	public SingleSemaphor() {
		super();
		
		
	}


	@Override
	public Semaphore getSectorSemaphor(String secId) throws SectorNotFoundException {	
		
		synchronized (this) {
			if (semaphore == null) {
				semaphore = new Semaphore(Integer.valueOf(semaphorQueueSize));
			}
		}		
		
		return semaphore;
	}

}
