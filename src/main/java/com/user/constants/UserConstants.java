package com.user.constants;

public interface UserConstants {

	String USER_APP = "User_App";
	String START_UP_SUCCESS ="startUp Success.";

	String APP_NAME = "appName";
	String APP_DESC = "appDesc";
	String APP_VERSION = "1.0.0";
	
	String API_NAME = "apiName";
	String START_TIME = "startTime";
	String END_TIME = "endTime";
	String RESPONSE_TIME = "responseTime";
		
	String HTTP_STATUS = "httpStatus";

	String ACTION = "action";
	String LOG_TYPE = "logType";
	String EXIT = "EXIT";
	String METHOD_CLASS_ENTRY = "Entering method={} class={}";
	String METHOD_CLASS_EXIT = "Exiting method={} class={}";
	
	String CODE="code";
	String MSG="msg";
	String DESC="desc";
	
	String GET = "get";
	String CREATE = "create";
	String UPDATE = "update";
	String DELETE = "delete";
	
	String SUCCESS_CODE = "200";
	String SUCCESS_MSG = "SUCCESS";
	
	String FAILURE_CODE = "500";
	String FAILURE_MSG = "FAILURE";
	
	String WARNING_CODE = "400";
	String WARNING_MSG = "WARNING";
	
	String NOT_FOUND_CODE = "404";
	String RECORD_NOT_FOUND_MSG = "Record Not Found in the Database";
	
	String USER_ID_EMAIL_PHONE_MISSING_MSG = "Either userId or emailAddress or phoneNumber should be present";	
	String FIRST_NAME_MISSING_MSG = "firstName should be present for action=create";
	String LAST_NAME_MISSING_MSG = "lastName should be present for action=create";
	String DOB_MISSING_MSG = "dateOfBirth should be present for action=create";
	String EMAIL_ADDRESS_MISSING_MSG = "emailAddress should be present for action=create";
	String PHONE_NUMBER_MISSING_MSG = "phoneNumber should be present for action=create";
	String FN_LN_DOB_EMAIL_PHONE_MISSING_MSG = "Atleast one out of firstName, lastName, dateOfBirth, emailAddress or phoneNumber should be present for action=update";

}
