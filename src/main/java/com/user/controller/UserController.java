package com.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.constants.UserConstants;
import com.user.dto.request.VehicleRentalRequest;
import com.user.dto.request.UserRequest;
import com.user.dto.response.UserResponse;
import com.user.dto.response.UsersResponse;
import com.user.service.UserService;
import com.user.utils.UserUtils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import javax.validation.Valid;


@RestController
@Validated
@RequestMapping(path="/")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = UserConstants.APP_NAME, description = UserConstants.APP_DESC, version = UserConstants.APP_VERSION))
public class UserController implements UserConstants{
	
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserUtils userUtils;
	
	@Autowired
	private UserService userService;
	
	private ObjectMapper objectMapper;
	
	@GetMapping("/")
	@Operation(description = "startUp API", summary = "startUp", tags = "startUp")
	public ResponseEntity<String> startUp(){
		String methodName = "startUp";
		logger.info(METHOD_CLASS_ENTRY, methodName,className);

		return ResponseEntity.ok().body(START_UP_SUCCESS);
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<UsersResponse> getAllUsers(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = userService.getAllUsers(httpRequest, httpResponse, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		UsersResponse usersResponse = objectMapper.readValue(jsonResponse, UsersResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		userUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(usersResponse, httpStatus);
	}
	
	@GetMapping("/user")
	public ResponseEntity<UserResponse> getUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="userId", defaultValue="") final String userId, 
			@RequestParam(value="emailAddress", defaultValue="") final String emailAddress,
			@RequestParam(value="phoneNumber", defaultValue="") final String phoneNumber,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = userService.getUser(httpRequest, httpResponse, userId, emailAddress,
				phoneNumber, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		UserResponse userResponse = objectMapper.readValue(jsonResponse, UserResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		userUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(userResponse, httpStatus);
	}
	
	@PostMapping("/user")
	public ResponseEntity<UserResponse> createOrUpdateUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody UserRequest userRequest,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
				
		Map<String, Object> response =  userService.createOrUpdateUser(httpRequest, httpResponse,
												userRequest, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		UserResponse userResponse = objectMapper.readValue(jsonResponse, UserResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		userUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(userResponse, httpStatus);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<UserResponse> deleteUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="userId", defaultValue="") final String userId, 
			@RequestParam(value="emailAddress", defaultValue="") final String emailAddress,
			@RequestParam(value="phoneNumber", defaultValue="") final String phoneNumber,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "deleteUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, DELETE);

		Map<String, Object> response = userService.deleteUser(httpRequest, httpResponse, userId, emailAddress,
				phoneNumber, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		UserResponse userResponse = objectMapper.readValue(jsonResponse, UserResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		userUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(userResponse, httpStatus);
	}
	
	@GetMapping("/seeAvailableVehicles")
	public ResponseEntity<Map<String, Object>> seeAvailableVehicles(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, @RequestHeader Map<String, String> requestHeaderMap)
			throws JsonProcessingException {
		String methodName = "seeAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		long startTime = System.currentTimeMillis();

		Map<String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = userService.seeAvailableVehicles(httpRequest, httpResponse,
						logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;

		if (null != response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		userUtils.logMap(logger, logMap);

		return new ResponseEntity<>(response, httpStatus);
	}
	
	@PostMapping("/vehicleRental")
	public ResponseEntity<UserResponse> vehicleRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody VehicleRentalRequest vehicleRentalRequest,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
				
		Map<String, Object> response =  userService.vehicleRental(httpRequest, httpResponse,
				vehicleRentalRequest, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		UserResponse userResponse = objectMapper.readValue(jsonResponse, UserResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		userUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(userResponse, httpStatus);
	}
	
}
