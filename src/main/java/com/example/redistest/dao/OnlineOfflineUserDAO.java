package com.example.redistest.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.example.redistest.utils.RedisServiceUtils;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;

@Service
public class OnlineOfflineUserDAO {
  @Autowired
  private ClusteredRedis redis;

//  public Set<Long> getOnlineOfflineUsers(String city, String branch, DataSource dataSource) {
//    Set<String> online_offline_users = redis
//        .sinter(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city),
//            RedisServiceUtils.onlineUserIdBranchKey(branch))
//        .stream().map(String::valueOf).collect(Collectors.toSet());
//    String[] onlineOfflineUsers = online_offline_users.stream().map(String::valueOf)
//        .collect(Collectors.toSet()).toArray(new String[online_offline_users.size()]);
//    redis.sadd(RedisConstants.REDIS_ALIAS, RedisServiceUtils.onlineOfflineUserKey(),
//        onlineOfflineUsers);
//    redis.expire(RedisConstants.REDIS_ALIAS, RedisServiceUtils.onlineOfflineUserKey(),
//        RedisConstants.ONLINE_OFFLINE_USER_KEY_TTL);
//    return online_offline_users.stream().map(team -> Long.parseLong(team))
//        .collect(Collectors.toSet());
//  }
}
