package com.ge.poc.datasource.repository.mongo;

import com.ge.poc.datasource.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <MongoUser, Long > {

}
