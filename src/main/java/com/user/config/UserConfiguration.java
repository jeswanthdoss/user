package com.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "main")
@Data
public class UserConfiguration {

	private String vehicleAvailabilityServiceURL;
	private String vehicleUpdateServiceURL;
	private String createRentalServiceURL;
}
