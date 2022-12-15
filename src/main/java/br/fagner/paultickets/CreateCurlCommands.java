package br.fagner.paultickets;

public class CreateCurlCommands {
	
	public static void main(String[] args) {
				
		for (int i =0; i < 1000; i++) {
			System.out.println("curl --location --request POST 'http://localhost:8091/salaryweb/api/bankly/ --header 'Content-Type: application/json' --header 'Accept: application/json' --data-raw '{}'");
		}
	}

}
