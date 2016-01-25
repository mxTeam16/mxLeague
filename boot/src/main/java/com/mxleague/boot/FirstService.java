package com.mxleague.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class FirstService {
	public static void main(String[] args) {
		System.setProperty("server.port", "8187");
		SpringApplication.run(FirstService.class, args);
	}
}
