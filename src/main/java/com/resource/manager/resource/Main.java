package com.resource.manager.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

	@GetMapping(value = "/resource")
	public String getResourcePage() {
		return "Welcome to the resource page";
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
