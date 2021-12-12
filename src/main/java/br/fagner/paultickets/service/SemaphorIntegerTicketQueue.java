package br.fagner.paultickets.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class SemaphorIntegerTicketQueue implements TicketQueue<Integer> {
	
	private static Queue<Integer> ticketsQueye = new ConcurrentLinkedQueue<>();
	
	private static Semaphore fanSemaphore = new Semaphore(20);
	
	static {
		int count = 1;
		do {
			ticketsQueye.add(count);
			count++;
		} while (count < 10001);
	}

	@Override
	public Integer getFirstTicket()  {
		Integer tmpTicket = null;
		try {
			if(!fanSemaphore.tryAcquire(2, TimeUnit.SECONDS)) {				
				return tmpTicket;
			}

			tmpTicket = ticketsQueye.poll();
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			return tmpTicket;
		} finally {
			if (fanSemaphore.availablePermits() == 0) {				
				fanSemaphore.release(20);
			}
		}

		return tmpTicket;
	}

	@Override
	public void addTicket(Integer ticket) {
		ticketsQueye.add(ticket);
		
	}

}
