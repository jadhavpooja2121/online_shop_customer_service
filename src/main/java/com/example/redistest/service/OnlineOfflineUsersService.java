package com.example.redistest.service;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.example.redistest.dao.OnlineOfflineUserDAO;
import com.example.redistest.dos.OnlineOfflineUserDO;
import com.example.redistest.utils.RedisServiceUtils;
import com.fantasy.clash.framework.http.dos.OkResponseDO;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;

@Service
public class OnlineOfflineUsersService {
  public static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private OnlineOfflineUserDAO onlineOfflineUserDAO;

  @Autowired
  private ClusteredRedis redis;

  @Async("asyncExecutor")
  public void getUsersByCity(String city, String branch, CompletableFuture<ResponseEntity<?>> cf,
      DataSource dataSource) {
    Set<String> userIds =
        redis.smembers(RedisConstants.REDIS_ALIAS, RedisServiceUtils.onlineOfflineUserKey());
    if (userIds == null || userIds.size() == 0) {
      Set<Long> users = onlineOfflineUserDAO.getOnlineOfflineUsers(city, branch, DataSource.REDIS);
      OnlineOfflineUserDO onlineOfflineUserDO = new OnlineOfflineUserDO(users);
      cf.complete(new ResponseEntity<>(new OkResponseDO<>(onlineOfflineUserDO), HttpStatus.OK));
    }
  }
}
