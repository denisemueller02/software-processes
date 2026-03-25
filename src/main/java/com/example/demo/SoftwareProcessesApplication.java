package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoftwareProcessesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareProcessesApplication.class, args);
	}

	@Bean
	CommandLineRunner helloWorld() {
		return args -> System.out.println("Hello World");
	}
}
