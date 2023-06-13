package com.paweu.autofleet.data.service;

import com.paweu.autofleet.data.models.User;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class UserServiceDb {

    public UserServiceDb(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<User> findByEmail(String email){
        return mongoTemplate.findOne(Query.query(where("email").is(email)), User.class);
    }

    public Mono<User> save(User user){
        return mongoTemplate.save(user);
    }


    public Mono<User> updateJWT(String jwt, String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        return mongoTemplate.findOne(query, User.class)
                .flatMap(user -> {
                    user.setRefToken(jwt);
                    return mongoTemplate.save(user);
                });
    }
}
