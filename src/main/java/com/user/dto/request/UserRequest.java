package com.user.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
		
	@Schema(description ="userId, the User Id")
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
	private String dateOfBirth;

	@Schema(description="emailAddress")
	@JsonProperty("emailAddress")
	private String emailAddress;

	@Schema(description="phoneNumber")
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	private String action;
	
}
