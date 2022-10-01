package com.example.redistest.utils;

import com.example.redistest.constants.RedisConstants;
import com.fantasy.clash.framework.redis.utils.RedisUtils;

public class RedisServiceUtils {
  public static String matchContestsKey(Long matchId) {
    // r:core_engine:match_contests:{matchId}
    return RedisUtils.getKey(RedisConstants.MATCH_CONTESTS_KEY, matchId.toString());
  }

  public static String matchKey(Long matchId) {
    // r:core_engine:match:{matchId}
    return RedisUtils.getKey(RedisConstants.MATCH_KEY, matchId.toString());
  }

  public static String matchStatusKey(Long matchId) {
    // r:core_engine:match:{matchId}
    return RedisUtils.getKey(RedisConstants.MATCH_STATUS_KEY, matchId.toString());
  }

  public static String matchMaxUserTeamsPerplayerKey(Long matchId) {
    // r:core_engine:match_max_user_teams_per_player:{matchId}
    return RedisUtils.getKey(RedisConstants.MATCH_MAX_USER_TEAMS_PER_PLAYER_KEY,
        matchId.toString());
  }

  public static String contestKey(Long contestId) {
    // r:core_engine:contest:{contestId}
    return RedisUtils.getKey(RedisConstants.CONTEST_KEY, contestId.toString());
  }

  public static String userTeamKey(Long userTeamId) {
    return RedisUtils.getKey(RedisConstants.USER_TEAM_KEY, userTeamId);
  }

  public static String contestTeams(Long contestId) {
    return RedisUtils.getKey(RedisConstants.CONTEST_TEAMS_KEY, contestId.toString());
  }

  public static String userTeams(Long userId) {
    return RedisUtils.getKey(RedisConstants.USER_TEAMS_KEY, userId);
  }

  public static String matchUserUserTeamsKey(Long matchId, Long userId) {
    return RedisUtils.getKey(RedisConstants.MATCH_USER_USER_TEAMS_KEY, matchId, userId);
  }

  public static String matchUserTeams(Long matchId, Long contestId) {
    return RedisUtils.getKey(RedisConstants.MATCH_USER_TEAMS, matchId.toString(),
        contestId.toString());
  }

  public static String userIdCityKey(String city) {
    return RedisUtils.getKey(RedisConstants.USER_ID_CITY_KEY, city);
  }

  public static String onlineUserIdBranchKey(String branch) {
    return RedisUtils.getKey(RedisConstants.ONLINE_USER_ID_BRANCH_KEY, branch);
  }
  public static String onlineOfflineUserKey() {
    return RedisUtils.getKey(RedisConstants.ONLINE_OFFLINE_USER_KEY);
  }
  
  public static String saveUserIdCityKey(Long userId,String city) {
    return RedisUtils.getKey(RedisConstants.SAVE_USER_REDIS_KEY, userId.toString(), city);
  }
}

