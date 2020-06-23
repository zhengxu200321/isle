package com.isle.service.impl;

import com.isle.mapper.LogMapper;
import com.isle.pojo.CunDang;
import com.isle.pojo.CunDangLog;
import com.isle.pojo.UserLog;
import com.isle.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public void insertLog(UserLog userLog) {
        logMapper.insertLog(userLog);
    }

    @Override
    public void insertCunDangLog(CunDangLog cunDangLog) {
        logMapper.insertcundang_log(cunDangLog);
    }

}
