package com.paweu.autofleet.data.service;

import com.paweu.autofleet.data.models.User;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class UserService {

    public UserService(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<User> findByEmail(String email){
        return mongoTemplate.findOne(Query.query(where("email").is(email)), User.class);
    }

    public Mono<User> save(User user){
        return mongoTemplate.save(user);
    }

}
