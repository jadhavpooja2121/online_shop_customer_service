package com.example.redistest.constants;

public class PropertyConstants {
  public static final String MYSQL_DRIVER_NAME = "mysql.driver";
  public static final String MYSQL_MASTER_USERNAME = "mysql.master.username";
  public static final String MYSQL_MASTER_PASSWORD = "mysql.master.password";
  public static final String MYSQL_MASTER_DB_URI = "mysql.master.db.url";
  public static final String MYSQL_MASTER_DB_NAME = "mysql.master.db.name";
  public static final String MYSQL_MASTER_PORT = "mysql.master.port";
  public static final String MYSQL_MASTER_MAX_ACTIVE_CONN = "mysql.master.max.active.conn";
  public static final String MYSQL_MASTER_MAX_IDLE_CONN = "mysql.master.max.idle.conn";
  public static final Integer DEFAULT_MYSQL_MASTER_MAX_ACTIVE_CONN = 30;
  public static final String MYSQL_SLAVE_USERNAME = "mysql.slave.username";
  public static final String MYSQL_SLAVE_PASSWORD = "mysql.slave.password";
  public static final String MYSQL_SLAVE_DB_URI = "mysql.slave.db.url";
  public static final String MYSQL_SLAVE_DB_NAME = "mysql.slave.db.name";
  public static final String MYSQL_SLAVE_PORT = "mysql.slave.port";
  public static final String MYSQL_SLAVE_MAX_ACTIVE_CONN = "mysql.slave.max.active.conn";
  public static final String MYSQL_SLAVE_MAX_IDLE_CONN = "mysql.slave.max.idle.conn";
  public static final int DEFAULT_MAX_POOL_SIZE = 10;
  public static final int DEFAULT_CORE_POOL_SIZE = 5;
  public static final int DEFAULT_QUEUE_CAPACITY = 5000;
  public static final int DEFAULT_AWAIT_TERMINATION_SECONDS = 5;
  public static final String ASYNC_THREAD_POOL_MAX_POOL_SIZE = "async.threadpool.maxpoolsize";
  public static final String ASYNC_THREAD_POOL_CORE_POOL_SIZE = "async.threadpool.corepoolsize";
  public static final String ASYNC_THREAD_POOL_QUEUE_CAPACITY = "async.threadpool.queuecapacity";
  public static final String ASYNC_THREAD_POOL_AWAIT_TERMINATION_SECONDS =
      "async.threadpool.await.termination.seconds";
  public static final String GAME_USER_TEAMS_MAX_TEAM_PER_PLAYER =
      "game.user.team.max.team.per.player";

  private PropertyConstants() {}
}
