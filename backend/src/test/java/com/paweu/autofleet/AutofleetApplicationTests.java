package com.paweu.autofleet;

import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.service.UserServiceDb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
class AutofleetApplicationTests {

	@Autowired
	private UserServiceDb userServiceDb;

	@Test
	void contextLoads() {
	}

	@Test
	void jwtRefreshDb(){
		User user = userServiceDb.updateJWT("testjwt", "6484e1adf4f3676f14bf0ddd").block();
		assert user != null;
		Assertions.assertEquals("testjwt",user.getRefToken());
	}

}
