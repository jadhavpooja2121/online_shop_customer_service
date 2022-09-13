package com.example.redistest.dao;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.redistest.constants.RedisConstants;
import com.example.redistest.dbo.OnlineUserDBO;
import com.example.redistest.mapper.OnlineUserMapper;
import com.example.redistest.utils.RedisServiceUtils;
import com.fantasy.clash.framework.object_collection.enums.DataSource;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;

@Service
public class OnlineUserDAO extends BaseDAO {
  @Autowired
  private ClusteredRedis redis;

  private OnlineUserMapper getMapper(DataSource db) {
    if (db == DataSource.MASTER) {
      return sqlSessionTemplateMaster.getMapper(OnlineUserMapper.class);
    } else {
      return sqlSessionTemplateSlave.getMapper(OnlineUserMapper.class);
    }
  }

  public void save(OnlineUserDBO OnlineUserDBO) {
    OnlineUserMapper onlineUserMapper = getMapper(DataSource.MASTER);
    onlineUserMapper.save(OnlineUserDBO);
  }

  public OnlineUserDBO getById(Long id, DataSource dataSource) {
    OnlineUserMapper onlineUserMapper = getMapper(dataSource);
    return onlineUserMapper.findById(id);
  }

  // save data into Redis
  public Set<Long> getUsersByCity(String branch, DataSource dataSource) {
    if (dataSource == DataSource.MASTER) {
      return getMapper(dataSource).getUsersByCity(branch);
    }
    if (dataSource == DataSource.SLAVE) {
      return getMapper(dataSource).getUsersByCity(branch);
    }
    if (dataSource == DataSource.REDIS) {
      Set<String> onlineUsers = redis.smembers(RedisConstants.REDIS_ALIAS,
          RedisServiceUtils.onlineUserIdBranchKey(branch));
      if (onlineUsers == null || onlineUsers.size() == 0) {
        Set<Long> onlineUserIds = getUsersByCity(branch, DataSource.SLAVE);
        String[] allOnlineUsers = onlineUserIds.stream().map(String::valueOf)
            .collect(Collectors.toSet()).toArray(new String[onlineUserIds.size()]);
        redis.sadd(RedisConstants.REDIS_ALIAS, RedisServiceUtils.onlineUserIdBranchKey(branch),
            allOnlineUsers);
        redis.expire(RedisConstants.REDIS_ALIAS, RedisServiceUtils.onlineUserIdBranchKey(branch),
            RedisConstants.ONLINE_USER_ID_BRANCH_KEY_TTL);
        return onlineUserIds;
      }
      return onlineUsers.stream().map(id -> Long.parseLong(id)).collect(Collectors.toSet());
    }
    return null;
  }

  public void update(OnlineUserDBO onlineUserDBO) {
    getMapper(DataSource.MASTER).update(onlineUserDBO);
  }

  public void delete(Long id) {
    getMapper(DataSource.MASTER).deleteById(id);
  }
}
