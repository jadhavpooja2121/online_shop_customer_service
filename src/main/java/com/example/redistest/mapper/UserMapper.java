package com.example.redistest.mapper;

import java.util.Set;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import com.example.redistest.dbo.UserDBO;


@Mapper
public interface UserMapper {
//CREATE
 @Insert("INSERT INTO users (name, city) VALUES "
     + "(#{name}, #{city})")
 @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false,
     resultType = Long.class)
 public Long save(UserDBO userDBO);

 // READ
 @Select("SELECT * FROM tournaments WHERE id = #{id} AND is_active = true")
 public UserDBO findById(Long id);

 // UPDATE
 @Update("UPDATE tournaments SET tp_id = #{tpId}, name = #{name}, display_name = #{displayName}, "
     + "start_time = #{startTime} WHERE id = #{id} AND is_active = true")
 public void update(UserDBO userDBO);

 // -- DELETE
 @Update("UPDATE tournaments SET is_active = false WHERE id = #{id}")
 public void deleteById(Long id);

@Select("Select id from users where city = #{city}") 
public Set<Long> getUsersByCity(String cityName);
}
