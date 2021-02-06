package com.robosoftin.evaluation.util;

import com.robosoftin.evaluation.constants.AppConstants;
import com.robosoftin.evaluation.constants.ErrorConstants;
import com.robosoftin.evaluation.exception.RestTemplateTimeOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.util
 **/
@Component
public class GenericRestTemplateUtil {


	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<String> genericRestTemplateForGetWithQueryParameter(URI uri) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			ResponseEntity<String> response =restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			return response;
		} catch (Exception e) {
			throw new RestTemplateTimeOutException(ErrorConstants.TIMEOUT_EXCEPTION, AppConstants.TIME_OUT);
		}
	}
}
