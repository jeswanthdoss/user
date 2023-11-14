package com.user.helper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//import com.user.config.UserConfiguration;
import com.user.constants.UserConstants;
import com.user.dto.request.VehicleRentalRequest;
import com.user.dto.request.UserRequest;
import com.user.entity.User;
import com.user.repository.helper.UserRepositoryHelper;
import com.user.utils.UserUtils;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserHelper implements UserConstants {

	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//private UserConfiguration userConfiguration;

	@Autowired
	private UserRepositoryHelper userRepositoryHelper;

	@Autowired
	private UserUtils userUtils;
	
	@Autowired
	private ExternalHelper externalHelper;

	public void validateGetOrDeleteUser(String userId, String emailId, String phoneNumber,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateGetOrDeleteUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		if (StringUtils.isBlank(userId) && StringUtils.isBlank(emailId) && StringUtils.isBlank(phoneNumber)) {
			userUtils.prepareResponse(WARNING_CODE, USER_ID_EMAIL_PHONE_MISSING_MSG,
					WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public void validateCreateOrUpdateUser(UserRequest userRequest,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateCreateOrUpdateUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		
		if(null != userRequest) {
			if (StringUtils.isBlank(userRequest.getUserId())){
				userRequest.setAction(CREATE);
				logMap.put(ACTION, CREATE);
			}else {
				logMap.put("userId", userRequest.getUserId());
				logMap.put(ACTION, UPDATE);
				userRequest.setAction(UPDATE);
			}
			
			if(userRequest.getAction().equalsIgnoreCase(CREATE)) {
				if(StringUtils.isBlank(userRequest.getFirstName())) {
					userUtils.prepareResponse(WARNING_CODE, FIRST_NAME_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(userRequest.getLastName())) {
					userUtils.prepareResponse(WARNING_CODE, LAST_NAME_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(userRequest.getDateOfBirth())) {
					userUtils.prepareResponse(WARNING_CODE, DOB_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(userRequest.getEmailAddress())) {
					userUtils.prepareResponse(WARNING_CODE, EMAIL_ADDRESS_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(userRequest.getPhoneNumber())) {
					userUtils.prepareResponse(WARNING_CODE, PHONE_NUMBER_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
			}
			
			if(userRequest.getAction().equalsIgnoreCase(UPDATE)) {
				if(StringUtils.isBlank(userRequest.getFirstName()) &&
						StringUtils.isBlank(userRequest.getLastName()) &&
						StringUtils.isBlank(userRequest.getDateOfBirth()) &&
						StringUtils.isBlank(userRequest.getEmailAddress()) &&
						StringUtils.isBlank(userRequest.getPhoneNumber())) {
					userUtils.prepareResponse(WARNING_CODE, PHONE_NUMBER_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}				
			}
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> getUser(String userId, String emailAddress, String phoneNumber, Map<String, String> logMap) {
		String methodName = "getUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<User> optUser;
		User user = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(userId)) {
			logMap.put("userId", userId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for userId: "+userId;
			optUser = userRepositoryHelper.findByUserId(userId);

		} else if (StringUtils.isNotBlank(emailAddress)) {
			logMap.put("emailAddress", emailAddress);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for emailAddress: "+emailAddress;
			optUser = userRepositoryHelper.findByEmailAddress(emailAddress);
		} else if (StringUtils.isNotBlank(phoneNumber)) {
			logMap.put("phoneNumber", phoneNumber);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for phoneNumber: "+phoneNumber;
			optUser = userRepositoryHelper.findByPhoneNumber(phoneNumber);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optUser = null;
		}

		if (null!= optUser && optUser.isPresent()) {
			user = optUser.get();
		}
		logger.debug("user={}", user);

		if (null != user) {
			populateGetUserResponse(user, response);
		} else {
			userUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;

	}

	private void populateGetUserResponse(User user, Map<String, Object> response) {
		String methodName = "populateGetUserResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		response.put("userId", user.getUserId());
		response.put("firstName", user.getFirstName());
		response.put("lastName", user.getLastName());
		response.put("dateOfBirth", user.getDateOfBirth());
		response.put("emailAddress", user.getEmailAddress());
		response.put("phoneNumber", user.getPhoneNumber());
		response.put("createdTimestamp", user.getCreatedTimestamp());
		response.put("createdBy", user.getCreatedBy());
		response.put("modifiedTimestamp", user.getModifiedTimestamp());
		response.put("modifiedBy", user.getModifiedBy());
		
		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> createOrUpdateUser(UserRequest userRequest, Map<String, String> logMap) {
		String methodName = "createOrUpdateUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<User> optUser;
		User user = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		String successMsg = null;
		
		if(userRequest.getAction().equalsIgnoreCase(UPDATE)) {
			optUser = userRepositoryHelper.findByUserId(userRequest.getUserId());
			
			if (null!= optUser && optUser.isPresent()) {
				user = optUser.get();
				
				if (null != user) {
					if(StringUtils.isNotBlank(userRequest.getFirstName())) {
						user.setFirstName(userRequest.getFirstName());
					}
					
					if(StringUtils.isNotBlank(userRequest.getLastName())) {
						user.setLastName(userRequest.getLastName());
					}
					
					if(StringUtils.isNotBlank(userRequest.getDateOfBirth())) {
						user.setDateOfBirth(java.sql.Date.valueOf(userRequest.getDateOfBirth()));
					}
					
					if(StringUtils.isNotBlank(userRequest.getEmailAddress())) {
						user.setEmailAddress(userRequest.getEmailAddress());
					}
					
					if(StringUtils.isNotBlank(userRequest.getPhoneNumber())) {
						user.setPhoneNumber(userRequest.getPhoneNumber());
					}
					
					user.setModifiedBy(USER_APP);
					user.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
					
					userRepositoryHelper.saveUser(user);
					successMsg = UPDATE + " completed for userId: "+userRequest.getUserId();
					userUtils.prepareResponse(SUCCESS_CODE, successMsg,
							SUCCESS_MSG, HttpStatus.OK, response, logMap);
				}	
			}else {
				recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for userId: "+userRequest.getUserId();
				userUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
						WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
			}
		}
		
		if(userRequest.getAction().equalsIgnoreCase(CREATE)) {
			user = new User();
			String userId = "user-"+UUID.randomUUID().toString();
			user.setUserId(userId);
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setDateOfBirth(java.sql.Date.valueOf(userRequest.getDateOfBirth()));
			user.setEmailAddress(userRequest.getEmailAddress());
			user.setPhoneNumber(userRequest.getPhoneNumber());
			
			user.setCreatedBy(USER_APP);
			user.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));			
			user.setModifiedBy(USER_APP);
			user.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
			
			userRepositoryHelper.saveUser(user);
			successMsg = CREATE + " completed. userId: "+userId;
			logMap.put("userId", userId);
			response.put("userId", userId);
			userUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
		}
	
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
		
		return response;
	}
	
	public Map<String, Object> deleteUser(String userId, String emailAddress, String phoneNumber, Map<String, String> logMap) {
		String methodName = "deleteUser";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<User> optUser;
		User user = null;
		Map<String, Object> response = new HashMap<>();
		String successMsg = null;
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(userId)) {
			logMap.put("userId", userId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for userId: "+userId;
			successMsg = "User with userId: "+userId+" deleted successfully.";
			
			optUser = userRepositoryHelper.findByUserId(userId);

		} else if (StringUtils.isNotBlank(emailAddress)) {
			logMap.put("emailAddress", emailAddress);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for emailAddress: "+emailAddress;
			successMsg = "User with emailAddress: "+emailAddress+" deleted successfully.";

			optUser = userRepositoryHelper.findByEmailAddress(emailAddress);
		} else if (StringUtils.isNotBlank(phoneNumber)) {
			logMap.put("phoneNumber", phoneNumber);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for phoneNumber: "+phoneNumber;
			successMsg = "User with phoneNumber: "+phoneNumber+" deleted successfully.";

			optUser = userRepositoryHelper.findByPhoneNumber(phoneNumber);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optUser = null;
		}

		if (null!= optUser && optUser.isPresent()) {
			user = optUser.get();
		}
		logger.debug("user={}", user);

		if (null != user) {
			userRepositoryHelper.deleteUser(user);
			userUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
			
		} else {
			userUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
	
	private void populateGetUsersResponse(List<User> usersList, Map<String, Object> response,
			Map<String, String> logMap) {
		String methodName = "populateGetUsersResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		response.put("users", usersList);

		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.info("populateGetUsersResponse response={}", response);

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> getAllUsers(Map<String, String> logMap) {
		String methodName = "getAllUsers";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		List<User> usersList = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = "No Users found";
		
		usersList = userRepositoryHelper.findAllUsers();
		
		logger.info("usersList={}", usersList);
		
		if (null != usersList && usersList.size() > 0) {
			populateGetUsersResponse(usersList, response, logMap);
		} else {
			userUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
	


	
	public Map<String, Object> seeAvailableVehicles(Map<String, String> logMap) {
		String methodName = "seeAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Map<String, Object> response = new HashMap<>();
		String serviceResponse=null;
		String recordNotFoundMsg = "No Vehicles Available";
		
		try {
		
		serviceResponse = externalHelper.callVehicleAvailabilityService(logMap);
		
		logger.info("callVehicleAvailabilityService serviceResponse={}", serviceResponse);
		
		if (null != serviceResponse ) {
				response = userUtils.jsonToMap(serviceResponse);
				response.put(HTTP_STATUS, HttpStatus.OK);
		} else {
			userUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in seeAvailableVehicles method {}", e.getMessage());
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
	
	public Map<String, Object> vehicleRental(VehicleRentalRequest vehicleRentalRequest,Map<String, String>  logMap) {
		String methodName = "vehicleRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Map<String, Object> response = new HashMap<>();
		Map<String, Object>  serviceResponse=null;
		String unableToBookMsg = "Unable to Rent Vehicle:"+vehicleRentalRequest.getVehicleId();
		
		try {
		
		serviceResponse = externalHelper.vehicleRentalService(vehicleRentalRequest, logMap);
		
		logger.info("vehicleRental serviceResponse={}", serviceResponse);
		
		if (null != serviceResponse) {
				response.put(HTTP_STATUS, HttpStatus.OK);
				String desc="vehicleId:"+vehicleRentalRequest.getVehicleId()+
								" booked successfully for userId:"+vehicleRentalRequest.getUserId();
				response.put(DESC, desc);

		} else {
			userUtils.prepareResponse(FAILURE_CODE, unableToBookMsg,
					FAILURE_MSG, HttpStatus.INTERNAL_SERVER_ERROR, response, logMap);
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in vehicleRental method {}", e.getMessage());
		}
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
		return response;
	}
	

}
