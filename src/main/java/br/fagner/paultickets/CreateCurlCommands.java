package br.fagner.paultickets;

import java.util.Random;
import java.util.random.RandomGenerator;

public class CreateCurlCommands {
	
	public static void main(String[] args) {
		int numSeats = 0;
		
		for (int i =0; i < 1000; i++) {
			System.out.println("curl --location --request POST 'http://localhost:8091/salaryweb/api/bankly/ --header 'Content-Type: application/json' --header 'Accept: application/json' --data-raw '{}'");
		}
	}

}
