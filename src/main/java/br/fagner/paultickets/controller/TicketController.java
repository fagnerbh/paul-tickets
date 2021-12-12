package br.fagner.paultickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fagner.paultickets.service.TicketQueue;

@RestController
@RequestMapping("/vo/ticket")
public class TicketController {
	
	@Autowired
	TicketQueue<Integer> ticketQueue;
	
	@GetMapping("/")	
	public ResponseEntity<?> ticket() {		
		
		Integer ticket = ticketQueue.getFirstTicket();
		
		ticket = (ticket != null) ? ticket : 0;	
		
		
		return new ResponseEntity<>(ticket, HttpStatus.OK);				
	}

}
