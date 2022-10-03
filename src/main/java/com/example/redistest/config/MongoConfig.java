package com.example.redistest.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.example.redistest.constants.MongoConstants;
import com.example.redistest.dbo.UserDBO;
import com.fantasy.clash.framework.configuration.Configurator;
import com.fantasy.clash.framework.mongo.MongoConfiguration;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;


@Configuration
public class MongoConfig {

  @Autowired
  private MongoConfiguration mongoConfiguration;
  
  @PostConstruct
  public void init() {
    try {
      MongoClient mongoClient = mongoConfiguration.createMongoClient(MongoConstants.MONGO_ALIAS, false);
      MongoTemplate mongoTemplate = mongoConfiguration.createMongoTemplate(mongoClient, "users");
      //MongoCredential mongoCredential = mongoConfiguration.
     UserDBO userDBO = new UserDBO();
      userDBO.setId(1L);
      userDBO.setName("Pooja");
      userDBO.setCity("Mumbai");
      mongoTemplate.save(userDBO, "student");
    } catch (Exception e) {
      // TODO: handle exception
    }
    
  }
}

  
 
