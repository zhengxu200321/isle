package com.isle.service;

import com.isle.pojo.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> selUser(User user);

    List<Url> selUrlByRid(int rid);

    List<Url> selAllUrl();

    User selUsers(User user);

    String selUsersPoint(int num);

    String selGongGao(int num);

    void updateSign(User user);

    void addPoint(User user,int point);

    void updatePassword(User user);

    void updateSignInfo(User user);

    String  selMakeUp(User user,int start_num,int end_num);

    void update_mark_up_status(int id);

    MakeUp selMarkUpStatus(int id);

    LongInfo sellongNameZhong(String name);

    String selPointLog(User user,int start_num,int limit);

    void updIsSign();

    List<User> selAllUsers();

    LongInfo sellongById(int id);

    String selColorsBySteamId(String steamid);
    List<Colors> selColorsBySteamIdPojo(String steamid);

    void delColor(int id);

    void saveColor(String steamid,String user_name,String color_name,int SkinPaletteSection1,int SkinPaletteSection2,int SkinPaletteSection3,int SkinPaletteSection4,int SkinPaletteSection5,int SkinPaletteSection6,String SkinPaletteVariation);

    int selColorCount(String steamid );

}
