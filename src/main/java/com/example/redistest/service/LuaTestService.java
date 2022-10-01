package com.example.redistest.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.fantasy.clash.framework.http.dos.MessageResponseDO;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;

@Service
public class LuaTestService {

  @Autowired
  private ClusteredRedis redis;
  
  @Async("asyncExecutor")
  public void testLua(String name, String value, CompletableFuture<ResponseEntity<?>> cf)
      throws IOException {
    redis.loadScript("LUA_ALIAS", "redis_script.lua");
    redis.execute(RedisConstants.REDIS_ALIAS, "LUA_ALIAS", List.of(name), value);  
    cf.complete(ResponseEntity.ok(new MessageResponseDO("Executed")));
  }

}
