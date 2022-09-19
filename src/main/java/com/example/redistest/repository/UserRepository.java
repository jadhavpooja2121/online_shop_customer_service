package com.example.redistest.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.example.redistest.dbo.UserDBO;

public interface UserRepository extends MongoRepository<UserDBO, Long> {

}
