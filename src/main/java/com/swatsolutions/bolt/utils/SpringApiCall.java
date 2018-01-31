package com.swatsolutions.bolt.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SpringApiCall {

	private MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

	public void addHeaderLine(String key, String value){
		headers.set(key, value);
	}

	public String postWithHeaders(String url, Object object) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			HttpEntity<Object> request = new HttpEntity<Object>(object, headers);

			String response = restTemplate.postForObject(url, request, String.class);

			//System.out.println(response.toString());
			return response;
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			return "error: " + e.getMessage();
		}
	}


}
