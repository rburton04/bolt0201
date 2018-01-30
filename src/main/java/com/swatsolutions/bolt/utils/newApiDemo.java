package com.swatsolutions.bolt.utils;

import com.swatsolutions.bolt.apiObjects.DemoTwoObject;
import com.swatsolutions.bolt.apiObjects.MaybeThisWorks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.swatsolutions.bolt.apiObjects.demoObject;

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

	public MaybeThisWorks m6(String url){
		try {
			RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForObject(url, MaybeThisWorks.class);
		} catch (Exception e){
			e.getMessage();
		}
		return null;
	}

	public List<MaybeThisWorks> m7(String url){
		try {
			//https://stackoverflow.com/questions/20837856/can-not-deserialize-instance-of-java-util-arraylist-out-of-start-object-token
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<MaybeThisWorks>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MaybeThisWorks>>() {});
			return rateResponse.getBody();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<DemoTwoObject> m5(String url){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<DemoTwoObject>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DemoTwoObject>>() {});
			return rateResponse.getBody();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public List<demoObject> m4(String url){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<demoObject>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<demoObject>>() {});
			return rateResponse.getBody();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<Object> m3(String url){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<List<Object>> rateResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {});
			return rateResponse.getBody();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void m2(String url) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
			HttpStatus status = response.getStatusCode();
			String restCall = response.getBody();
			System.out.println(restCall);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
