package com.swatsolutions.bolt.utils;

import com.swatsolutions.bolt.apiObjects.DemoTwoObject;
import com.swatsolutions.bolt.apiObjects.createJiraIssue.JiraIssue;
import com.swatsolutions.bolt.apiObjects.MaybeThisWorks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.swatsolutions.bolt.apiObjects.demoObject;

import java.util.List;
import java.util.Map;

public class newApiDemo {

	public static void main (String[] args){
		RestTemplate restTemplate = new RestTemplate();
		Object customObject = restTemplate.getForObject("url", Object.class);


		/**
		 ResponseEntity<List<Rate>> rateResponse = restTemplate.exchange("https://bitpay.com/api/rates",HttpMethod.GET, null, new ParameterizedTypeReference<List<Rate>>() {});
		 List<Rate> rates = rateResponse.getBody();
		 */
	}

	public MultiValueMap creatHeaders(Map<String, String> values){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		for(Map.Entry<String, String> mapEntry : values.entrySet()) {
			headers.set(mapEntry.getKey(), mapEntry.getValue());
		}
		return headers;
	}

	public void callJiraWithHeaders(MultiValueMap headers, String url, JiraIssue jiraIssue){
		try {
			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			HttpEntity<JiraIssue> request = new HttpEntity<JiraIssue>(jiraIssue, headers);

			String response = restTemplate.postForObject(url, request, String.class);

			System.out.println(response.toString());
		} catch (Exception e){
			e.getMessage();
		}
		// HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		//ResponseEntity<Mall[]> respEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Mall[].class);

		//Mall[] resp = respEntity.getBody();
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
