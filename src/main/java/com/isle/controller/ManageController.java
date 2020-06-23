package com.isle.controller;

import com.isle.util.DBHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("manage")
public class ManageController {

    @RequestMapping(value = "adduser",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String user_name = request.getParameter("user_name");
        if(StringUtils.isBlank(user_name)){
            return "用户名不可为空";
        }
        String phone = request.getParameter("yy");
        if(StringUtils.isBlank(user_name)){
            return "YY号不可为空";
        }
        String email = request.getParameter("qq");
        if(StringUtils.isBlank(user_name)){
            return "QQ号不可为空";
        }
        String point = request.getParameter("user_point");
        if(StringUtils.isBlank(user_name)){
            return "用户积分不可为空";
        }
        String stemid = request.getParameter("SteamId");
        if(StringUtils.isBlank(stemid)){
            return "用户积分不可为空";
        }
        DBHelper.getInstance().insertSql("insert into user (user_name,phone,email,password,steamid,rid,point,image,regise_time,last_cun_time) values (?,?,?,?,?,?,?,?,?,?)",
                new Object[]{user_name,phone,email,"123456",stemid,1,point,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590088773109&di=eb330d15a26dc660271444320366fe96&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F35%2F88%2F67573bfa8b3656f.jpg",new Date(),"2020-01-01 00:00:00"});
        return "添加用户成功";
    }

}
