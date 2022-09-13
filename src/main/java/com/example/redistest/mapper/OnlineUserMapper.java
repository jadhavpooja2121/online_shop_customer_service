package com.example.redistest.mapper;

import java.util.Set;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import com.example.redistest.dbo.OnlineUserDBO;

@Mapper
public interface OnlineUserMapper {
  // CREATE
  @Insert("INSERT INTO online_users (name, branch) VALUES " + "(#{name}, #{branch})")
  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false,
      resultType = Long.class)
  public Long save(OnlineUserDBO onlineUserDBO);

  // READ
  @Select("SELECT * FROM online_users WHERE id = #{id} AND is_active = true")
  public OnlineUserDBO findById(Long id);

  // UPDATE
  @Update("UPDATE online_users SET name =#{name}, branch = {branch}  WHERE id = #{id} AND is_active = true")
  public void update(OnlineUserDBO onlineUserDBO);

  // -- DELETE
  @Update("UPDATE online_users SET is_active = false WHERE id = #{id}")
  public void deleteById(Long id);

  @Select("Select id from online_users where branch = #{branch}")
  public Set<Long> getUsersByCity(String branch);
}
