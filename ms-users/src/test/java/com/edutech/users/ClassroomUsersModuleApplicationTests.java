package com.edutech.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "spring.cloud.openfeign.client.config.default.logger-level=none"
    }
)
@ActiveProfiles("test")
class ClassroomUsersModuleApplicationTests {

	@Test
	void contextLoads() {
	}

}
