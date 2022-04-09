package br.fagner.paultickets.controller.response;

import java.util.Collection;

import lombok.Data;

@Data
public class OrderResponse {
		
	private String eventId;
			
	private String sectorId;	
	
	private Collection<Integer> seats;
	
	private String message;

}
