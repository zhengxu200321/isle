package com.isle.service;

import com.isle.pojo.CunDang;
import com.isle.pojo.CunDangLog;
import com.isle.pojo.UserLog;

import java.util.List;


public interface LogService {

    void insertLog(UserLog userLog);

    void insertCunDangLog(CunDangLog cunDangLog);


}
