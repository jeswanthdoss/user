package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.user.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findAll();
	
	Optional<User> findByUserId(String userId);
	
	Optional<User> findByEmailAddress(String emailAddress);
	
	Optional<User> findByPhoneNumber(String phoneNumber);



}
