package com.isle.service.impl;

import com.isle.mapper.LogMapper;
import com.isle.mapper.LongMapper;
import com.isle.pojo.*;
import com.isle.service.LogService;
import com.isle.service.LongService;
import com.isle.service.UserService;
import com.isle.util.DBHelper;
import com.isle.util.ReadUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class LongServiceImpl implements LongService {


    @Resource
    private LongMapper longMapper;

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Override
    public void insertCunDang(CunDang cunDang) {
        longMapper.insertCunDang(cunDang);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String selKuCun(String steamd_id, int start_num, int limit) {
        List<CunDang> cunDangs=  longMapper.selCunDang(steamd_id, start_num, limit);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", longMapper.selCunDangCount(steamd_id).getCount());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < cunDangs.size(); i++) {
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("name", cunDangs.get(i).getName());
            jsonObject1.put("name_china", cunDangs.get(i).getName_china());
            jsonObject1.put("create_time", sdf.format(cunDangs.get(i).getCreate_time()));
            jsonObject1.put("steam_id", cunDangs.get(i).getSteam_id());
            jsonObject1.put("user_name", cunDangs.get(i).getUser_name());
            jsonObject1.put("remark", cunDangs.get(i).getRemark());
            jsonObject1.put("zuobiao", "<input type=\"radio\" name=\"zuobiao"+cunDangs.get(i).getId()+"\" value=\"1\" title=\"随机\">\n" +
                    "      <input type=\"radio\" name=\"zuobiao"+cunDangs.get(i).getId()+"\" value=\"0\" title=\"原地\" checked>");
            jsonObject1.put("caozuo", "<button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-normal\" onclick=\"selLong(\'"+cunDangs.get(i).getId()+"\')\">查看</button><button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-normal\" onclick=\"qu(\'"+cunDangs.get(i).getId()+"\')\">取龙</button>");
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("data", jsonArray);
        return jsonObject.toString();
    }

    @Override
    public String selkucunone(int id) {
        CunDang selcundangone = longMapper.selcundangone(id);
        return selcundangone.getInfo();
    }

    @Override
    public String qu(int id, User user, int zuobiao_id) {
        CunDang selcundangone = longMapper.selcundangone(id);
        int point = selcundangone.getPoint();
        int user_point = user.getPoint();
        if (user_point < point) {
            return "对不起;您的积分不足哦...";
        }
        String steam_id = user.getSteamid();
        String zuobiao = "";
        if(zuobiao_id==0){
            String info = ReadUtil.getUserInfo(steam_id);
            if(StringUtils.isBlank(info)){
                return "对不起;您当前没有龙;获取不到您的坐标信息;请游戏新建小龙;或者选择随机坐标";
            }else {
                JSONObject jsonObject = JSONObject.fromObject(info);
                zuobiao = jsonObject.getString("Location_Thenyaw_Island");
            }
        }else {
            Random random = new Random();
            int r = random.nextInt(24)+1;
            List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("select * from long_msg where id = '" + r + "'");
            String info = (String) dataMap.get(0).get("info");
            JSONObject jsonObject = JSONObject.fromObject(info);
            zuobiao = jsonObject.getString("Location_Thenyaw_Island");
        }

        String info = selcundangone.getInfo();
        JSONObject jsonObject = JSONObject.fromObject(info);
        jsonObject.put("Location_Thenyaw_Island", zuobiao);
        ReadUtil.writeUserInfo(user.getSteamid(), jsonObject.toString());

        user_point = user_point - point;
        userService.addPoint(user, user_point);
        UserLog log = new UserLog();
        log.setCreate_time(new Date());
        log.setType(1);
        log.setUser(user.getUser_name());
        log.setSteam_id(user.getSteamid());
        log.setLog("取龙 减少积分 " + point);
        logService.insertLog(log);

        CunDangLog cunDangLog = new CunDangLog();
        cunDangLog.setName_china(selcundangone.getName_china());
        cunDangLog.setName(selcundangone.getName());
        cunDangLog.setUser_name(user.getUser_name());
        cunDangLog.setType("取");
        cunDangLog.setSteam_id(user.getSteamid());
        cunDangLog.setCreate_time(new Date());
        logService.insertCunDangLog(cunDangLog);
        longMapper.delLong(id);
        return "取龙成功了哦";
    }

    @Override
    public String cun_log(User user, int start_num, int limit) {
        List<CunDangLog> cunDangLogs = longMapper.selCunDangLog(user.getSteamid(), start_num, limit);
        for (int i = 0; i < cunDangLogs.size(); i++) {
            cunDangLogs.get(i).setCreate_t(sdf.format(cunDangLogs.get(i).getCreate_time()));
        }
        JSONArray jsonArray = JSONArray.fromObject(cunDangLogs);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", longMapper.selCunDangLogCount(user.getSteamid()).getCount());
        jsonObject.put("data", jsonArray.toString());
        return jsonObject.toString();
    }

    @Override
    public String longstore() {
        List<LongInfo> longInfos = longMapper.longstore();
        for (int i = 0; i < longInfos.size(); i++) {
            int is_gender = longInfos.get(i).getIs_gender();
            int point = longInfos.get(i).getPoint();
            String gender = "公 "+point+" 分;母 "+point*2+" 分";
            if(is_gender==1){
                gender = "公 "+point+" 分;母 "+point+" 分";
            }
            longInfos.get(i).setGender(gender);
            longInfos.get(i).setZuobiao("<input id = \"zuobiao"+longInfos.get(i).getId()+"\" type=\"text\" name=\"title\" required  lay-verify=\"required\" placeholder=\"例如 X=0.000 Y=0.000 Z=0.000\" autocomplete=\"off\"  class=\"layui-input\">\n");
            if(longInfos.get(i).getName().equals("Albert")){
                longInfos.get(i).setGongmu("<input type=\"radio\" name=\"gongmu"+longInfos.get(i).getId()+"\" value=\"false\" checked title=\"公\">\n");
            }else {
                longInfos.get(i).setGongmu("<input type=\"radio\" name=\"gongmu"+longInfos.get(i).getId()+"\" value=\"false\" title=\"公\">\n" +
                        "      <input type=\"radio\" name=\"gongmu"+longInfos.get(i).getId()+"\" value=\"true\" title=\"母\" checked>");
            }

            longInfos.get(i).setCaozuo("<button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-normal\" onclick=duihuan(\'"+longInfos.get(i).getId()+"\')>兑换</button>");

        }
        JSONArray jsonArray = JSONArray.fromObject(longInfos);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("data", jsonArray.toString());
        return jsonObject.toString();
    }

    @Override
    public String duihuan(int id, String zuobiao, boolean gongmu,User user1) {

        User user = userService.selUsers(user1);
//        int user_point = user.getPoint();
        LongInfo longInfo = userService.sellongById(id);
        int point = longInfo.getPoint();
        int is_gender = longInfo.getIs_gender();
        System.out.println("兑换 "+gongmu);
        if(is_gender==0&&gongmu){
            System.out.println(gongmu);
            point = point * 2;
        }
//        if(user_point<point){
//            return "无法兑换,积分不足哦...";
//        }
//        user_point = user_point - point;
//        userService.addPoint(user, user_point);

//        UserLog log = new UserLog();
//        log.setCreate_time(new Date());
//        log.setType(1);
//        log.setUser(user.getUser_name());
//        log.setSteam_id(user.getSteamid());
//        log.setLog("兑换龙 减少积分 "+point);
//        logService.insertLog(log);

        JSONObject longJSONObject = JSONObject.fromObject(longInfo.getInfo());
        if (StringUtils.isNotBlank(zuobiao) && !zuobiao.equals("undefined")) {
            longJSONObject.put("Location_Thenyaw_Island", zuobiao);
        }
        longJSONObject.put("bGender", gongmu);
        CunDangLog cunDangLog = new CunDangLog();
        cunDangLog.setName_china(longInfo.getName_china());
        cunDangLog.setName(longInfo.getName());
        cunDangLog.setUser_name(user.getUser_name());
        cunDangLog.setType("兑换");
        cunDangLog.setSteam_id(user.getSteamid());
        cunDangLog.setCreate_time(new Date());
        logService.insertCunDangLog(cunDangLog);

        CunDang cunDang = new CunDang();
        cunDang.setCreate_time(new Date());
        cunDang.setInfo(longJSONObject.toString());
        cunDang.setName(longInfo.getName());
        cunDang.setUser_name(user.getUser_name());
        cunDang.setSteam_id(user.getSteamid());
        cunDang.setName_china(longInfo.getName_china());
        cunDang.setRemark("兑换");
        cunDang.setPoint(point);
        insertCunDang(cunDang);
        addLongCount(longInfo.getId());
        return "兑换成功哦;请查看库存!";
    }

    @Override
    public void addLongCount(int id) {
        LongInfo longCount = longMapper.getLongCount(id);
        int sell_count = longCount.getSell_count();
        sell_count = sell_count + 1;
        longMapper.addSellCount(sell_count, id);
    }


}
