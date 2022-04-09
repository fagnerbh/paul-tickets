package br.fagner.paultickets.component;

import java.util.concurrent.Semaphore;

import br.fagner.paultickets.exception.SectorNotFoundException;

public interface SemaphorCache {

	Semaphore getSectorSemaphor(String secId) throws SectorNotFoundException;

}