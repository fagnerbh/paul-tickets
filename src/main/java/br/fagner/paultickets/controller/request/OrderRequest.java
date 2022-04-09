package br.fagner.paultickets.controller.request;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OrderRequest {
	
	@NotNull
	private String eventId;
	
	@NotNull
	private String userId;
	
	@NotNull
	private String sectorId;
	
	@NotNull
	private Collection<Integer> numSeats;

}
