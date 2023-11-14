package com.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.config.UserConfiguration;
import com.user.constants.UserConstants;
import com.user.dto.request.UserRequest;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import com.user.utils.UserUtils;
import com.user.constants.UserConstants;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
//@SpringBootTest(properties = { "spring.config.location=classpath:application-test.properties" })
//@TestPropertySource("classpath:application-test.properties")
class UserServiceApplicationTests implements UserConstants {

	@Autowired
	private UserService userService;

	@Autowired
	private UserUtils userUtils;

	// @Autowired
	// private UserConfiguration userConfiguration;

	@MockBean
	private UserRepository userRepository;

	private MockHttpServletRequest mockHttpRequest;
	private MockHttpServletResponse mockHttpResponse;
	
	long currentTimeMillis = System.currentTimeMillis();
	Date myDate = new Date(currentTimeMillis);

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateUser() {
		String methodName = "testCreateUser";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
		UserRequest ur = new UserRequest();

		long currentTimeMillis = System.currentTimeMillis();

		Timestamp startTimestamp = new Timestamp(currentTimeMillis + (1 * 24 * 60 * 60 * 1000));

		Timestamp endTimestamp = new Timestamp(currentTimeMillis + (7 * 24 * 60 * 60 * 1000));

		ur.setFirstName("Jeswanth");
		ur.setLastName("Mohandoss");
		ur.setDateOfBirth("1995-05-05");
		ur.setEmailAddress("abcd@efgh.com");
		ur.setPhoneNumber("123-456-7890");

		response = userService.createOrUpdateUser(mockHttpRequest, mockHttpResponse, ur, logMap, requestHeaderMap);

		System.out.println("testCreateUser response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testCreateUser assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}

	@Test
	public void testGetUser() {
		String methodName = "testGetUser";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean findByUserId = false;
		boolean findByEmail = false;
		boolean findByPhone = false;


		boolean assertFlag = false;

		Map<String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);
	
		when(userRepository.findByUserId("user-1")).thenReturn(Optional.of(
				new User("user-1", "Jeswanth", "Mohandoss",myDate, "jeswanth@xyz.com","123-456-7890")));

		// findByUserId
		response = userService.getUser(mockHttpRequest, mockHttpResponse, "user-1", null,null, logMap, requestHeaderMap);

		System.out.println("testGetUser findByUserId response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByUserId = true;
			}
		}

		when(userRepository.findByEmailAddress("jeswanth@xyz.com")).thenReturn(Optional.of(
				new User("user-1", "Jeswanth", "Mohandoss",myDate, "jeswanth@xyz.com","123-456-7890")));


		// findByEmail
		response = userService.getUser(mockHttpRequest, mockHttpResponse, null, "jeswanth@xyz.com",null, logMap,
				requestHeaderMap);

		System.out.println("testGetUser findByEmail response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByEmail = true;
			}
		}
		
		when(userRepository.findByPhoneNumber("123-456-7890")).thenReturn(Optional.of(
				new User("user-1", "Jeswanth", "Mohandoss",myDate, "jeswanth@xyz.com","123-456-7890")));


		// findByEmail
		response = userService.getUser(mockHttpRequest, mockHttpResponse, null, null,"123-456-7890", logMap,
				requestHeaderMap);

		System.out.println("testGetUser findByEmail response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByPhone = true;
			}
		}


		if (findByUserId && findByEmail && findByPhone) {
			assertFlag = true;
		}

		System.out.println("testGetUser assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

	@Test
	public void testGetAllUsers() {
		String methodName = "testGetAllUsers";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);

		when(userRepository.findAll())
				.thenReturn(Stream.of(
						new User("user-1", "Jeswanth", "Mohandoss",myDate, "jeswanth@xyz.com","123-456-7890"),
						new User("user-2", "Mohandoss", "Jeswanth",myDate, "mohandoss@xyz.com","234-567-8901")
						).collect(Collectors.toList()));

		response = userService.getAllUsers(mockHttpRequest, mockHttpResponse, logMap, requestHeaderMap);

		System.out.println("testGetAllUsers response=" + response);

		// assertEquals(2, response.size());

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testGetAllUsers assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}

	@Test
	public void testDeleteUser() {
		String methodName = "testDeleteUser";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean assertFlag = false;

		Map<String, String> logMap = userUtils.initiateLogMap(methodName, USER_APP, startTime, requestHeaderMap);

		User user = new User("user-1", "Jeswanth", "Mohandoss",myDate, "jeswanth@xyz.com","123-456-7890");

		when(userRepository.findByUserId("user-1")).thenReturn(Optional.of(user));

		doNothing().when(userRepository).delete(user);

		// deleteUser
		response = userService.deleteUser(mockHttpRequest, mockHttpResponse, user.getUserId(), null, null, logMap,
				requestHeaderMap);

		System.out.println("testDeleteUser deleteUser response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testDeleteUser assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

}
