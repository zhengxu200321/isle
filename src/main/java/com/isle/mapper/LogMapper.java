package com.isle.mapper;

import com.isle.pojo.CunDangLog;
import com.isle.pojo.UserLog;
import org.apache.ibatis.annotations.Insert;


public interface LogMapper {
    @Insert("insert into user_log (user,log,create_time,type,steam_id) values (#{user},#{log},#{create_time},#{type},#{steam_id})")
    void insertLog(UserLog userLog);

    @Insert("insert into cundang_log (name,type,create_time,user_name,steam_id,name_china) values (#{name},#{type},#{create_time},#{user_name},#{steam_id},#{name_china})")
    void insertcundang_log(CunDangLog cunDangLog);

}
