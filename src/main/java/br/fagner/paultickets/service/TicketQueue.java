package br.fagner.paultickets.service;

public interface TicketQueue<T> {
	
	public T getFirstTicket();
	
	public void addTicket(T ticket);

}
