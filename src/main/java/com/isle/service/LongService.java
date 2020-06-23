package com.isle.service;

import com.isle.pojo.CunDang;
import com.isle.pojo.User;

public interface LongService {

    void insertCunDang(CunDang cunDang);

    String selKuCun(String steamd_id,int start_num,int limit);

    String selkucunone(int id);

    String qu(int id, User user, int zuobiao_id);

    String cun_log(User user,int start_num,int limit);

    String longstore();

    String duihuan(int id,String zuobiao,boolean gongmu,User user);

    void addLongCount(int id);

}
