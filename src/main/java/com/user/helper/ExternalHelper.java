package com.user.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.config.UserConfiguration;
import com.user.constants.UserConstants;
import com.user.dto.request.VehicleRentalRequest;
import com.user.dto.request.VehicleUpdateRequest;

@Component
public class ExternalHelper implements UserConstants{
	
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	UserConfiguration userConfiguration;
	
	private RestTemplate userRestTemplate = new RestTemplate();
	
	public String callVehicleAvailabilityService(Map<String, String> logMap) {
		
		String methodName="callVehicleAvailabilityService";
		String vehicleAvailabilityServiceResponse = null;
		
		try {
			String vehicleAvailabilityServiceURL = userConfiguration.getVehicleAvailabilityServiceURL();
			
			vehicleAvailabilityServiceResponse = callGetService(vehicleAvailabilityServiceURL, logMap);	
		}
		catch(Exception e) {
			logger.error("Exception in ExternalHelper callVehicleAvailabilityService method {}", e.getMessage());
		}
		return vehicleAvailabilityServiceResponse;
	}
	
	public String callGetService(String apiURL, Map<String, String> logMap) {
		String methodName="callGetService";
		String response = null;
		
		try {
			response= userRestTemplate.getForObject(apiURL, String.class);
		}
		catch(Exception e) {
			logger.error("Exception in UserServiceImpl getAllUsers method {}", e.getMessage());
		}
		return response;
	}
	
	public Map<String, Object> vehicleRentalService(VehicleRentalRequest vehicleRentalRequest, Map<String, String> logMap) {		
		String methodName="vehicleRentalService";
		
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		String vehicleUpdateServiceRequest = null;
		JSONObject vehicleUpdateServiceResponse = null;
		
		String createRentalServiceRequest = null;
		JSONObject createRentalServiceResponse = null;

		try {
			String createRentalServiceURL= userConfiguration.getCreateRentalServiceURL();
			String vehicleUpdateServiceURL = userConfiguration.getVehicleUpdateServiceURL();
			
			VehicleUpdateRequest vuRequest = new VehicleUpdateRequest();
			vuRequest.setVehicleId(vehicleRentalRequest.getVehicleId());
			vuRequest.setAvailability("N");
			
			vehicleUpdateServiceRequest = objectMapper.writeValueAsString(vuRequest);
			logger.info("vehicleUpdateServiceRequest {}", vehicleUpdateServiceRequest);

			vehicleUpdateServiceResponse = callPOSTService(vehicleUpdateServiceRequest, vehicleUpdateServiceURL, requestHeaderMap, logMap).getBody();	
			logger.info("vehicleUpdateServiceResponse {}", vehicleUpdateServiceResponse);

			createRentalServiceRequest =  objectMapper.writeValueAsString(vehicleRentalRequest);
			logger.info("createRentalServiceRequest {}", createRentalServiceRequest);
			
			createRentalServiceResponse = callPOSTService(createRentalServiceRequest, createRentalServiceURL, requestHeaderMap, logMap).getBody();	
			logger.info("vehicleUpdateServiceResponse {}", vehicleUpdateServiceResponse);
			
			response.put(HTTP_STATUS, HttpStatus.OK);
			
		}
		catch(Exception e) {
			logger.error("Exception in ExternalHelper callVehicleAvailabilityService method {}", e.getMessage());
		}
		return response;
	}
	
	public ResponseEntity<JSONObject> callPOSTService(String jsonRequest, String apiURL,
			Map<String, String> requestHeaderMap, Map<String, String> logMap) {
		String methodName="callPOSTService";
		HttpEntity<String> httpEntity = null;
		ResponseEntity<JSONObject> response = null;
		
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			httpEntity = new HttpEntity<>(jsonRequest, httpHeaders);

			response= userRestTemplate.postForEntity(apiURL, httpEntity, JSONObject.class);
			
		}
		catch(Exception e) {
			logger.error("Exception in ExternalHelper callPOSTService method {}", e.getMessage());
		}
		return response;
	}
}
