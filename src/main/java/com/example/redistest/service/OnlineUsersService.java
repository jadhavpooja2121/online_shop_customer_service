package com.example.redistest.service;

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
import com.example.redistest.dao.OnlineUserDAO;
import com.example.redistest.dao.UserDAO;
import com.example.redistest.dos.OnlineUserDO;
import com.example.redistest.dos.UserDO;
import com.fantasy.clash.framework.http.dos.OkResponseDO;
import com.fantasy.clash.framework.object_collection.enums.DataSource;

@Service
public class OnlineUsersService {
  public static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private OnlineUserDAO onlineUserDAO;

  @Async("asyncExecutor")
  public void getOnlineUsersByCity(String branch, CompletableFuture<ResponseEntity<?>> cf) {
    Set<Long> onlineUsers = onlineUserDAO.getUsersByCity(branch, DataSource.REDIS);
    OnlineUserDO onlineUserDO = new OnlineUserDO(onlineUsers);
    Iterator<Long> itr = onlineUsers.iterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
    }
    cf.complete(new ResponseEntity<>(new OkResponseDO<>(onlineUserDO), HttpStatus.OK));
  }
}
