package com.lamvt.shcedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ShceduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShceduleApplication.class, args);
	}

}
