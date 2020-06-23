package com.isle.exception;

import com.isle.pojo.User;
import com.isle.util.LogUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class ExeptionSave {

    Logger logger = LogUtil.getLogger(ExeptionSave.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("user");
        String name = "未知";
        if(user!=null){
            name = user.getUser_name();
        }
        logger.error(name+" 错误日志记录");
        Map map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String[] datas = (String[]) map.get(key);
            String value = new String();
            for (String data : datas) {
                value += data + ",";
            }
            logger.error("Param: " + key + " = " + value.substring(0, value.length() - 1));
        }
        logger.error(ExceptionUtils.getFullStackTrace(e));
        String msg = e.getMessage();
        if(StringUtils.isBlank(msg)){
            msg = "服务器出错";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        jsonObject.put("state", 0);
        return jsonObject.toString();
    }
}
