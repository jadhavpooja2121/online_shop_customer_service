package com.example.redistest.dao;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.example.redistest.dbo.UserDBO;
import com.example.redistest.mapper.UserMapper;
import com.example.redistest.utils.RedisServiceUtils;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;

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

  // save data into Redis
  public Set<Long> getUsersByCity(String city, DataSource dataSource) {
    if (dataSource == DataSource.MASTER) {
      return getMapper(dataSource).getUsersByCity(city);
    }
    if (dataSource == DataSource.SLAVE) {
      return getMapper(dataSource).getUsersByCity(city);
    }
    if (dataSource == DataSource.REDIS) {
      Set<String> users = redis.smembers(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city));
      if (users == null || users.size() == 0) {
        Set<Long> userIds = getUsersByCity(city, DataSource.SLAVE);
        String[] allUsers = userIds.stream().map(String::valueOf).collect(Collectors.toSet()).toArray(new String[userIds.size()]);
        redis.sadd(RedisConstants.REDIS_ALIAS, RedisServiceUtils.userIdCityKey(city), allUsers);
        redis.expire(RedisConstants.REDIS_ALIAS,RedisServiceUtils.userIdCityKey(city), RedisConstants.USER_ID_CITY_KEY_TTL);
        return userIds;
      }
      return users.stream().map(id -> Long.parseLong(id)).collect(Collectors.toSet());
    }
    return null;
  }

  public void update(UserDBO userDBO) {
    getMapper(DataSource.MASTER).update(userDBO);
  }

  public void delete(Long id) {
    getMapper(DataSource.MASTER).deleteById(id);
  }
}
