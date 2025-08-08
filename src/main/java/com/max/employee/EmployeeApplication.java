package com.max.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashed = encoder.encode("Admin@123");
		System.out.println(hashed+" hashed password");
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println(encoder.matches("Admin@123", "$2a$10$Dow1H/JK.HYMG3YtveB8EemGqvVnd0gzA9t8LrH2iQ44B8Vbs6tZq"));
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
