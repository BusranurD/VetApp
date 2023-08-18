package com.veterinary.followup;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import com.veterinary.followup.service.UserService;
import org.springframework.context.event.EventListener;
import com.veterinary.followup.model.Role;
import com.veterinary.followup.model.User;


@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Application(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	}
	@EventListener(ApplicationReadyEvent.class)
	public void createUser() {

		Long userCount = userService.userCount();

		if (userCount < 1L) {

			User admin = new User();
			admin.setAddress("Ankara");
			admin.setEmail("adminVet@gmail.com");
			admin.setPassword(bCryptPasswordEncoder.encode("secret"));
			admin.setFirstName("Duygu");
			admin.setLastName("Kara");
			admin.setPhone("905555550000");
			admin.setRole("Admin");
			admin.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
			
			Role adminRole = new Role("ROLE_ADMIN");
			adminRole.setName("ROLE_ADMIN");
			adminRole.setRole("Admin");
			
			userService.save(admin);
		}

	}
}
