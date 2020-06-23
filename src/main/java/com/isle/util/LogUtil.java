package com.isle.util;

import com.isle.pojo.CunDangLog;
import com.isle.pojo.User;
import com.isle.pojo.UserLog;
import com.isle.service.LogService;
import com.isle.service.impl.LogServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class LogUtil {

    @Resource
    private  LogService logService;


    public static Logger getLogger(Class clazz){
        Logger logger = Logger.getLogger(clazz);
        return logger;
    }

    public  void insertLongLog(CunDangLog cunDangLog){
        logService.insertCunDangLog(cunDangLog);
    }


}
