package com.user.dto.response;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends MaintenanceResponse{

	@Schema(description="userId")
	@JsonProperty("userId")
	private String userId;

	@Schema(description="firstName")
	@JsonProperty("firstName")
	private String firstName;
	
	@Schema(description="lastName")
	@JsonProperty("lastName")
	private String lastName;

	@Schema(description="dateOfBirth")
	@JsonProperty("dateOfBirth")
	private Date dateOfBirth;

	@Schema(description="emailAddress")
	@JsonProperty("emailAddress")
	private String emailAddress;

	@Schema(description="phoneNumber")
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
}
