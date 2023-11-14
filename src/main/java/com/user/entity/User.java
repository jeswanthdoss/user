package com.user.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User extends Maintenance {

	@Id
	@Column(name="USER_ID")
	private String userId;

	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name="PHONE_NUMBER")
	private String phoneNumber;
	
}
