package com.jpa.offer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class OfferApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferApplication.class, args);
	}

}
