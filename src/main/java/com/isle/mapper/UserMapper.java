package com.isle.mapper;

import com.isle.pojo.*;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.lang.Long;
import java.util.Date;
import java.util.List;

public interface UserMapper {

    User selByUser(User user);

    User selByUser_name(User user);


    @Select("select * from url where id in (select uid from role_url where rid = #{0})")
    List<Url> selUrlByRid(int rid);

    @Select("select * from url")
    List<Url> selAllUrl();

    /*查询排行榜*/
    @Select("select user_name,point,sign_info,image from user order by point desc limit #{0}")
    List<User> selUsersPoint(int num);

    /*查询公告栏*/
    @Select("select a.id as id,a.name as name,a.create_time as create_time,a.info as info,b.user_name as user_name from public_info a left join user b on a.uid = b.id limit #{0}")
    List<GongGao> selGongGao(int num);

    /*更改状态*/
    @Update("update user set is_sign = 1 where id = #{0}")
    void updSign(int id);

    /*增加积分*/
    @Update("update user set point = #{1} where id = #{0}")
    void addPoint(int id,int point);

    /*修改密码*/
    @Update("update user set password = #{0} where id = #{1}")
    void updPassword(String password, int id);

    /*修改个性签名*/
    @Update("update user set sign_info = #{0} where id = #{1}")
    void updsign_info(String sign, int id);

    /*查询当前用户的所有补偿信息*/
    @Select("select * from make_up where uid = #{0} order by status,release_time desc limit #{1},#{2}")
    List<MakeUp> selMakeUp(int uid,int num,int end_num);

    /*查询当前用户的所有补偿信息*/
    @Select("select count(id) as count from make_up where uid = #{0}")
    MakeUp selMakeUpCount(int uid);

    /*领取积分*/
    @Update("update make_up set status = 1,lingqu_time = #{1} where id = #{0}")
    void lingQuJiFen(int id,String create_time);

    /*查询积分*/
    @Select("select * from make_up where id = #{0}")
    MakeUp selMakeUpStatus(int id);

    /*查询龙的中文名字*/
    @Select("select * from long_msg where name = #{0}")
    LongInfo selLongNameByName(String name);

    /*查询龙的中文名字*/
    @Select("select * from long_msg where id = #{0}")
    LongInfo selLongNameById(int id);

    /*查询积分记录*/
    @Select("select * from user_log where type = 1 and steam_id = #{0} order by create_time desc limit #{1},#{2}")
    List<UserLog> selPointLog(String steam_id,int start_num,int limit);

    /*查询积分记录*/
    @Select("select count(id) as count from user_log where type = 1 and steam_id = #{0} order by create_time")
    UserLog selPointLogCount(String steam_id,int start_num,int limit);

    /*更改签到*/
    @Update("update user set is_sign = 0")
    int updIsSign();

    /*查询所有的steamid*/
    @Select("select steamid from user where steamid is not null")
    List<User> selAllUser();

    /*查询所有的皮肤*/
    @Select("select * from long_colors where steamid = #{0}")
    List<Colors> selallcolorbysteamid(String steamid);

    /*查询所有的皮肤*/
    @Select("select count(id) as count from long_colors where steamid = #{0}")
    Colors selallcolorbysteamidcount(String steamid);

    /*删除皮肤*/
    @Select("delete from long_colors where id = #{0}")
    void delcolor(int id);

    /*插入皮肤*/
    @Select("insert into long_colors (steamid,color_name,save_person,save_time,SkinPaletteSection1,SkinPaletteSection2,SkinPaletteSection3,SkinPaletteSection4,SkinPaletteSection5,SkinPaletteSection6,SkinPaletteVariation) values (#{0},#{1},#{2},#{3},#{4},#{5},#{6},#{7},#{8},#{9},#{10})")
    void insertcolor(String steamid, String color_name, String save_person, Date save_time,int SkinPaletteSection1,int SkinPaletteSection2,int SkinPaletteSection3,int SkinPaletteSection4,int SkinPaletteSection5,int SkinPaletteSection6,String SkinPaletteVariation);
}
