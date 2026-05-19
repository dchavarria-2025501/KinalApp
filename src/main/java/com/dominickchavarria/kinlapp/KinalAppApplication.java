package com.dominickchavarria.kinlapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class KinalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinalAppApplication.class, args);
	}

}
