package com.user.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.user.dto.request.VehicleRentalRequest;
import com.user.dto.request.UserRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	
	Map<String, Object> getAllUsers(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap);
		
	Map<String, Object> getUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String userId, String emailAddress, String phoneNumber, Map<String, String> logMap, Map<String, String> requestHeaderMap);

	Map<String, Object> createOrUpdateUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody UserRequest userRequest, Map<String, String> logMap,  Map<String, String> requestHeaderMap);

	Map<String, Object> deleteUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String userId, String emailAddress, String phoneNumber, Map<String, String> logMap, Map<String, String> requestHeaderMap);

	Map<String, Object> seeAvailableVehicles(HttpServletRequest httpRequest,HttpServletResponse httpResponse,
			 Map<String, String> logMap, Map<String, String>  requestHeaderMap);
	
	Map<String, Object> vehicleRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody VehicleRentalRequest vehicleRentalRequest, Map<String, String>  logMap,  Map<String, String> requestHeaderMap);
	
}