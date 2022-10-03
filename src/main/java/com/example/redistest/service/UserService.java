package com.example.redistest.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.redistest.dao.UserDAO;
import com.example.redistest.dbo.UserDBO;
import com.example.redistest.dos.OnlineOfflineUserDO;
import com.example.redistest.dos.UserDO;
import com.fantasy.clash.framework.http.dos.ErrorResponseDO;
import com.fantasy.clash.framework.http.dos.MessageResponseDO;
import com.fantasy.clash.framework.http.dos.OkResponseDO;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class UserService {
  public static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserDAO userDAO;

  @Async("asyncExecutor")
  public void getUsersByCity(String city, CompletableFuture<ResponseEntity<?>> cf) {
    Set<Long> users = userDAO.getUsersByCity(city, DataSource.REDIS);
    UserDO userDO = new UserDO(users);
    Iterator<Long> itr = users.iterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
    }
    cf.complete(new ResponseEntity<>(new OkResponseDO<>(userDO), HttpStatus.OK));
    // cf.complete(new ResponseEntity<>(new MessageResponseDO(1033, userDO.toString()),
    // HttpStatus.OK)
    // );
  }

  @Async("asyncExecutor")
  public void saveDataIntoRedis(CompletableFuture<ResponseEntity<?>> cf, DataSource dataSource)
      throws IOException {
    UserDBO userData = userDAO.getById(1L, DataSource.SLAVE);
//    userDAO.saveIntoRedisDb(userData);
//    cf.complete(new ResponseEntity<>(new OkResponseDO<>(userData), HttpStatus.OK));
  }
}
