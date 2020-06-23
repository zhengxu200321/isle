package com.isle.mapper;

import com.isle.pojo.CunDang;
import com.isle.pojo.CunDangLog;
import com.isle.pojo.LongInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LongMapper {

    @Insert("insert into cundang (name,info,create_time,user_name,steam_id,name_china,remark,point) values (#{name},#{info},#{create_time},#{user_name},#{steam_id},#{name_china},#{remark},#{point})")
    void insertCunDang(CunDang cunDang);

    @Select("select * from cundang where steam_id = #{0} order by create_time desc limit #{1},#{2}")
    List<CunDang> selCunDang(String steam_id, int start_num, int limit_num);

    @Select("select count(id) as count from cundang where steam_id = #{0} order by create_time desc ")
    CunDang selCunDangCount(String steam_id);

    @Select("select * from cundang where id = #{0}")
    CunDang selcundangone(int id);

    @Delete("delete from cundang where id = #{0}")
    void delLong(int id);

    @Select("select * from cundang_log where steam_id = #{0} order by create_time desc limit #{1},#{2}")
    List<CunDangLog> selCunDangLog(String steam_id, int start_num, int limit_num);

    @Select("select count(id) as count from cundang_log where steam_id = #{0}")
    CunDangLog selCunDangLogCount(String steam_id);

    @Select("select id,name,name_china,point,status,is_gender,sell_count from long_msg where id < 25")
    List<LongInfo> longstore();


    @Update("update long_msg set sell_count = #{0} where id = #{1}")
    void addSellCount(int sell_count,int id);

    @Select("select * from long_msg where id = #{0}")
    LongInfo getLongCount(int id);

}
