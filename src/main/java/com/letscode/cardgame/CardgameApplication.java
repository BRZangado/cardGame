package com.letscode.cardgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardgameApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardgameApplication.class, args);
	}

}
