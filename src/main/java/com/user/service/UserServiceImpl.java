package com.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.user.constants.UserConstants;
import com.user.dto.request.VehicleRentalRequest;
import com.user.dto.request.UserRequest;
import com.user.helper.UserHelper;
import com.user.utils.UserUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserConstants {
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private UserUtils userUtils;

	@Override
	public Map<String, Object> getUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String userId,
			String emailAddress, String phoneNumber, Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {
			userHelper.validateGetOrDeleteUser(userId, emailAddress, phoneNumber, response, logMap);

			if (!response.isEmpty()) {
				return response;
			}

			response = userHelper.getUser(userId, emailAddress, phoneNumber, logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl getUser method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

	@Override
	public Map<String, Object> createOrUpdateUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid UserRequest userRequest, Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {
			userHelper.validateCreateOrUpdateUser(userRequest, response, logMap);

			if (!response.isEmpty()) {
				return response;
			}

			response = userHelper.createOrUpdateUser(userRequest, logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl getUser method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

	@Override
	public Map<String, Object> deleteUser(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String userId, String emailAddress, String phoneNumber, Map<String, String> logMap,
			Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "deleteUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {
			userHelper.validateGetOrDeleteUser(userId, emailAddress, phoneNumber, response, logMap);

			if (!response.isEmpty()) {
				return response;
			}

			response = userHelper.deleteUser(userId, emailAddress, phoneNumber, logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl getUser method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

	@Override
	public Map<String, Object> getAllUsers(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "getAllUsers";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {

			response = userHelper.getAllUsers(logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl getAllUsers method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

	@Override
	public Map<String, Object> seeAvailableVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		String methodName = "seeAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {

			response = userHelper.seeAvailableVehicles(logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl seeAvailableVehicles method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

	@Override
	public Map<String, Object> vehicleRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid VehicleRentalRequest vehicleRentalRequest, Map<String, String> logMap,
			Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();

		try {
			
			response = userHelper.vehicleRental(vehicleRentalRequest, logMap);

			if (!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess = true;
			}

		} catch (Exception e) {
			logger.error("Exception in UserServiceImpl getUser method {}", e.getMessage());
			userUtils.handleExceptions(e.getMessage(), response, logMap);
		} finally {
			userUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
}
