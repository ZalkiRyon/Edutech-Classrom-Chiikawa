package com.edutech.grades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableDiscoveryClient  // Comentado temporalmente para desarrollo sin Eureka
@EnableFeignClients(basePackages = "com.edutech.grades.client")
@SpringBootApplication
public class ClassroomGradesModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassroomGradesModuleApplication.class, args);
	}

}
