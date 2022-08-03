package com.hopital.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HopitalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HopitalAppApplication.class, args);
	}

}
