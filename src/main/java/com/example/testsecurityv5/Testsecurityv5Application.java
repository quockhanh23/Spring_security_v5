package com.example.testsecurityv5;

import com.example.testsecurityv5.constants.Constants;
import com.example.testsecurityv5.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Testsecurityv5Application {

	public static void main(String[] args) {
		SpringApplication.run(Testsecurityv5Application.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args ->
		{
			if (roleRepository.findByName(Constants.Roles.ROLE_ADMIN) == null) {
				roleRepository.save(new com.example.testsecurityv5.models.Role(Constants.Roles.ROLE_ADMIN));
			}
			if (roleRepository.findByName(Constants.Roles.ROLE_USER) == null) {
				roleRepository.save(new com.example.testsecurityv5.models.Role(Constants.Roles.ROLE_USER));
			}
		};
	}

}
