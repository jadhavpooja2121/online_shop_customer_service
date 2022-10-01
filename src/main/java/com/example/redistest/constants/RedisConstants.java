package com.example.redistest.constants;

public class RedisConstants {
    public static final String REDIS_ALIAS = "core_engine_redis";
    public static final String MATCH_CONTESTS_KEY = "r:core_engine:match_contests";
    public static final String MATCH_MAX_USER_TEAMS_PER_PLAYER_KEY =
        "r:core_engine:match_max_user_teams_per_player";
    public static final String MATCH_STATUS_KEY = "r:core_engine:match_status";
    public static final String MATCH_KEY = "r:core_engine:match";
    public static final String MATCH_USER_TEAMS = "r:core_engine:match_user_teams";
    public static final String MATCH_USER_USER_TEAMS_KEY = "r:core_engine:match_user_user_teams";
    public static final String USER_TEAM_KEY = "r:core_engine:user_teams";
    public static final String REDIS_CLUSTER_NODES_CONFIG_KEY = "redis.cluster.nodes";
    public static final Long REDIS_KEY_TTL = 30L * 60L * 1000;
    public static final Long REDIS_30S_TTL = 30L * 1000;
    // TODO: create utility to calculate time to live given a timestamp
    public static final Long REDIS_ONE_DAY_MATCH_TTL = 24L * 60L * 60L * 1000;
    public static final String MATCH_ID_KEY = "r:core_engine:matchId";
    public static final String CONTEST_KEY = "r:core_engine:contest";
    public static final String CONTEST_TEAMS_KEY = "r:core_engine:contest_teams";
    public static final String USER_TEAMS_KEY = "r:core_engine:user_teams";
    public static final String CONTEST_JOIN_LOCK_KEY = "r:core_engine:join_contest_lock";
    public static final Long CONTEST_JOIN_LOCK_TTL = 24L * 60L * 60L * 1000;
    public static final String USER_ID_CITY_KEY = "r:core_engine:user_id:city_name";
    public static final String ONLINE_USER_ID_BRANCH_KEY = "r:core_engine:user_id:branch_name";
    public static final Long USER_ID_CITY_KEY_TTL = 15L* 60L * 1000;
    public static final Long ONLINE_USER_ID_BRANCH_KEY_TTL = 15L* 60L * 1000;
    public static final String ONLINE_OFFLINE_USER_KEY = "r:core_engine:online_offline_users";
    public static final Long ONLINE_OFFLINE_USER_KEY_TTL = 15L* 60L * 1000;
    public static final String SAVE_USER_REDIS_KEY = "r:core_engine:user_id:branch_name";
  }

