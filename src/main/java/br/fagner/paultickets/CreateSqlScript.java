package br.fagner.paultickets;

public class CreateSqlScript {

	public static void main(String[] args) {
				
		for (int i = 1; i <= 100; i++) {
			//System.out.println("insert into sector (sec_id, vnu_id) values ('" + String.valueOf(i) + "', 'a9df7835-cb72-4d60-8397-7860aad4806d');");
			
			for (int j = 1; j < 1001; j++) {
				System.out.println(" insert into seat (sea_id, sec_id, sea_num) values ('" + String.valueOf(i) + String.valueOf(j) + "', " + "'" + String.valueOf(i) + "', " + String.valueOf(j) + ");");
			}
		}

	}

}
