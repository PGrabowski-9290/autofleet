package com.paweu.autofleet;

import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutofleetApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void jwtRefreshDb(){
		User user = userRepository.updateJWT("testjwt", "6484e1adf4f3676f14bf0ddd").block();
		assert user != null;
		Assertions.assertEquals("testjwt",user.getRefToken());
	}

}
