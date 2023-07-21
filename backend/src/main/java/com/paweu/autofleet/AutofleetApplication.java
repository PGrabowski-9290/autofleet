package com.paweu.autofleet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
public class AutofleetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofleetApplication.class, args);
	}
}
