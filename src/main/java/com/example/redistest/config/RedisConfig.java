package com.example.redistest.config;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.example.redistest.constants.RedisConstants;
import com.fantasy.clash.framework.configuration.Configurator;
import com.fantasy.clash.framework.redis.cluster.ClusteredRedis;
import com.fantasy.clash.framework.utils.CollectionUtils;

@Configuration
public class RedisConfig {
  @Autowired
  private ClusteredRedis cRedis;

  @Autowired
  private Configurator configurator;

  @PostConstruct
  public void init() throws IOException {
    // Redis Nodes
    List<String> nodes = CollectionUtils
        .convertCsvToList(configurator.getString(RedisConstants.REDIS_CLUSTER_NODES_CONFIG_KEY));
    // Add alias with nodes
    cRedis.addAlias(RedisConstants.REDIS_ALIAS, nodes, null, null);
  }
}
