package com.swatsolutions.bolt.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class newApiDemo {

	public static void main (String[] args){
		RestTemplate restTemplate = new RestTemplate();
		Object customObject = restTemplate.getForObject("url", Object.class);


		/**
		 ResponseEntity<List<Rate>> rateResponse = restTemplate.exchange("https://bitpay.com/api/rates",HttpMethod.GET, null, new ParameterizedTypeReference<List<Rate>>() {});
		 List<Rate> rates = rateResponse.getBody();
		 */
	}

	public List<Object> m3(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Object>> rateResponse = restTemplate.exchange("https://bitpay.com/api/rates", HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {});
		return rateResponse.getBody();
	}

	public void m2(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity("url", null, String.class);
		HttpStatus status = response.getStatusCode();
		String restCall = response.getBody();
	}

}
