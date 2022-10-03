package com.example.redistest.dao;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.example.redistest.dbo.UserDBO;
import com.example.redistest.mapper.UserMapper;
import com.example.redistest.utils.RedisServiceUtils;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;
import com.fantasy.clash.framework.redis.utils.RedisUtils;
import com.fantasy.clash.framework.utils.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;

@Service
public class UserDAO extends BaseDAO {

  @Autowired
  private ClusteredRedis redis;

  private UserMapper getMapper(DataSource db) {
    if (db == DataSource.MASTER) {
      return sqlSessionTemplateMaster.getMapper(UserMapper.class);
    } else {
      return sqlSessionTemplateSlave.getMapper(UserMapper.class);
    }
  }

  public void save(UserDBO userDBO) {
    UserMapper UserMapper = getMapper(DataSource.MASTER);
    UserMapper.save(userDBO);
  }

  public UserDBO getById(Long id, DataSource dataSource) {
    UserMapper UserMapper = getMapper(dataSource);
    return UserMapper.findById(id);
  }

  public List<UserDBO> getAll() {
    UserMapper UserMapper = getMapper(DataSource.SLAVE);
    return UserMapper.getAllData();
  }

  // save data into Redis
  public Set<Long> getUsersByCity(String city, DataSource dataSource) {
    if (dataSource == DataSource.MASTER) {
      return getMapper(dataSource).getUsersByCity(city);
    }
    if (dataSource == DataSource.SLAVE) {
      return getMapper(dataSource).getUsersByCity(city);
    }
    if (dataSource == DataSource.REDIS) {
      Set<String> users =
          redis.smembers(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city));
      if (users == null || users.size() == 0) {
        Set<Long> userIds = getUsersByCity(city, DataSource.SLAVE);
        String[] allUsers = userIds.stream().map(String::valueOf).collect(Collectors.toSet())
            .toArray(new String[userIds.size()]);
        redis.sadd(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city), allUsers);
        redis.expire(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city),
            RedisConstants.USER_ID_CITY_KEY_TTL);
        return userIds;
      }
      return users.stream().map(id -> Long.parseLong(id)).collect(Collectors.toSet());
    }
    return null;
  }

//  public void saveIntoRedisDb(UserDBO userDBO) throws IOException {
//    String userData = JacksonUtils.toJson(userDBO);
//    // redis.sadd(RedisConstants.REDIS_ALIAS,
//    // RedisServiceUtils.saveUserIdCityKey(userDBO.getId(), userDBO.getCity()), userData);
//    redis.lpush(RedisConstants.REDIS_ALIAS,
//        RedisServiceUtils.saveUserIdCityKey(userDBO.getId(), userDBO.getCity()), userData);
//    String lastInserted = redis.lastindex(RedisConstants.REDIS_ALIAS,
//        RedisServiceUtils.saveUserIdCityKey(userDBO.getId(), userDBO.getCity()));
//    // redis.loadScript("lua_alias",
//    // "C:\\Users\\Admin\\FantasyClash\\redistest\\src\\main\\java\\redis_script.lua");
//    // redis.execute(userData, lastInserted, null, null);
//    System.out.println(lastInserted);
//  }

  public void update(UserDBO userDBO) {
    getMapper(DataSource.MASTER).update(userDBO);
  }

  public void delete(Long id) {
    getMapper(DataSource.MASTER).deleteById(id);
  }
}
