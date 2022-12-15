import http from 'k6/http';
import exec from 'k6/execution';

export const options = {
		   stages: [
		    { duration: '1m', target: 5 },
		    { duration: '1m', target: 10 },
		    { duration: '1m', target: 1 },
		  ],		  
		};

export default function () {
  const url = 'http://192.168.0.8:30008/event/eventorder/v1';
  let seatSeed = (exec.vu.iterationInInstance % 10) * 100;
  let mod3 = 0;  

	  const params = {
			  headers: {
				  'Content-Type': 'application/json',
				  'Accept': 'application/json',
			  },
	  };
  
  for (let id = 1; id <= 10; id++) {
	  mod3 = id % 5;
	  seatSeed += 3;
	  seatSeed += id;	
	  
	  const payload1 = JSON.stringify({
		    "eventId": "a9df7835-cb72-4d60-8397-7860aad4806d",
		    "userId" : "a8df7835-cb72-4d60-8397-7860aad4806d",
		    "sectorId" : "19",
		    "numSeats" : [++seatSeed ,++seatSeed,++seatSeed]
		  });
		  
		  const payload2 = JSON.stringify({
			    "eventId": "a9df7835-cb72-4d60-8397-7860aad4806d",
			    "userId" : "a8df7836-cb72-4d60-8397-7860aad4806d",
			    "sectorId" : "19",
			    "numSeats" : [++seatSeed ,++seatSeed,++seatSeed]
			  });
		  
		  const payload3 = JSON.stringify({
			    "eventId": "a9df7835-cb72-4d60-8397-7860aad4806d",
			    "userId" : "a8df7835-cb72-4d61-8397-7860aad4806d",
			    "sectorId" : "19",
			    "numSeats" : [++seatSeed ,++seatSeed,++seatSeed]
			  });
		  
		  const payload4 = JSON.stringify({
			    "eventId": "a9df7835-cb72-4d60-8397-7860aad4806d",
			    "userId" : "a8df7f35-cb72-4d60-8397-7860aad4806d",
			    "sectorId" : "19",
			    "numSeats" : [++seatSeed ,++seatSeed,++seatSeed]
			  });
		  
		  
		  const payload5 = JSON.stringify({
			    "eventId": "a9df7835-cb72-4d60-8397-7860aad4806d",
			    "userId" : "a8df7835-cb73-4d60-8397-7860aad4806d",
			    "sectorId" : "19",
			    "numSeats" : [++seatSeed ,++seatSeed,++seatSeed]
			  });
	  
	  switch(mod3) {
	  	case 0:
		  http.post(url, payload1, params);
		  break;
	  	case 1:
		  http.post(url, payload2, params);
		  break;
	  	case 2:
		  http.post(url, payload3, params);
		  break;
	  	case 3:
		  http.post(url, payload4, params);
		  break;		  
	  	default:
		  http.post(url, payload5, params);
	 }  
	  
  }

  
}