package com.paweu.autofleet;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@SpringBootApplication
public class AutofleetApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofleetApplication.class, args);
	}

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	@Bean
	public MongoClient reactiveMongoClient(){
		return MongoClients.create(uri);
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate(){
		return new ReactiveMongoTemplate(reactiveMongoClient(), databaseName);
	}

}
