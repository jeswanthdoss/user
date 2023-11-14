package com.user.repository.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.user.entity.User;

@Component
@RequiredArgsConstructor
public class UserRepositoryHelper {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> findByUserId(String userId){
		return userRepository.findByUserId(userId);
	}
	
	public Optional<User> findByEmailAddress(String emailAddress){
		return userRepository.findByEmailAddress(emailAddress);
	}
	
	public Optional<User> findByPhoneNumber(String phoneNumber){
		return userRepository.findByPhoneNumber(phoneNumber);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUser(User user) {
		 userRepository.delete(user);
	}
}