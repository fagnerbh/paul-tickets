import http from 'k6/http';
import exec from 'k6/execution';
import { group } from 'k6';

export const options = {

   stages: [

    { duration: '1m', target: 10 },

    { duration: '1m', target: 20 },

    { duration: '1m', target: 0 },

  ],
};


export default function () {

  const url = 'http://192.168.59.101:30008/event/eventorder/v0/create';

  const payload1 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "91", "numSeats": [683]});
  const payload2 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "89", "numSeats": [583, 582, 580]});
  const payload3 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "71", "numSeats": [120, 121]});
  const payload4 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "51", "numSeats": [683]});
  const payload5 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "52", "numSeats": [583, 582, 580]});
  const payload6 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "8", "numSeats": [120, 121]});
  const payload7 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "141", "numSeats": [683]});
  const payload8 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "142", "numSeats": [583, 582, 580]});
  const payload9 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "131", "numSeats": [120, 121]});
  const payload10 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "130", "numSeats": [683]});
  const payload12 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "135", "numSeats": [583, 582, 580]});
  const payload13 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "19", "numSeats": [120, 121]});
  const payload14 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "19", "numSeats": [683]});
  const payload15 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "121", "numSeats": [583, 582, 580]});
  const payload16 = JSON.stringify({"eventId" : "a9df7835-cb72-4d60-8397-7860aad4806d", "userId": "a8df7835-cb72-4d60-8397-7860aad4806d", "sectorId": "132", "numSeats": [120, 121]});

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  };
  
    exec.vu.tags.containerGroup = 'main';

  group('main', function () {
    http.post(url, payload1, params);
    http.post(url, payload2, params);    
    http.post(url, payload3, params);
    http.post(url, payload4, params);
    http.post(url, payload5, params);    
    http.post(url, payload6, params);
    http.post(url, payload7, params);
    http.post(url, payload8, params);    
    http.post(url, payload9, params);
    http.post(url, payload10, params);
    http.post(url, payload12, params);    
    http.post(url, payload13, params);
    http.post(url, payload14, params);
    http.post(url, payload15, params);    
    http.post(url, payload16, params);    
  });

  delete exec.vu.tags.containerGroup; 

}
