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
public class VehicleUpdateRequest {
		
	@Schema(description ="vehicleId, the Vehicle Id")
	@JsonProperty("vehicleId")
	private String vehicleId;

	@Schema(description="availability")
	@JsonProperty("availability")
	private String availability;
	
}
