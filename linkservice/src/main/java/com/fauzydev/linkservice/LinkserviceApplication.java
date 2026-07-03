package com.fauzydev.linkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinkserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkserviceApplication.class, args);
	}

}
