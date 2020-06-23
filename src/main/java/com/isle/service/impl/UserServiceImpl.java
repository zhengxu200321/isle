package com.isle.service.impl;


import com.isle.mapper.LogMapper;
import com.isle.mapper.UserMapper;
import com.isle.pojo.*;
import com.isle.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Resource
    private LogMapper logMapper;

    @Override
    public Map<String, Object> selUser(User users) {
        System.out.println(logMapper);
        Map<String, Object> map = new HashedMap();
        User user = userMapper.selByUser(users);
        if(user!=null){
            map.put("user", user);
            if(StringUtils.isNotBlank(String.valueOf(user.getRid()))) {
                List<Url> urls = selUrlByRid(user.getRid());
                user.setUrl(urls);
                map.put("user", user);
                map.put("allurl", selAllUrl());
            }
            return map;
        }else {
            return map;
        }
    }

    @Override
    public List<Url> selUrlByRid(int rid) {
        return userMapper.selUrlByRid(rid);
    }

    @Override
    public List<Url> selAllUrl() {
        return userMapper.selAllUrl();
    }

    @Override
    public User selUsers(User user1) {
        User user = userMapper.selByUser_name(user1);
        if(user!=null) {
            List<Url> urls = selUrlByRid(user.getRid());
            user.setUrl(urls);
        }
        return user;
    }

    /*获取积分排行榜*/
    @Override
    public String selUsersPoint(int num){
        List<User> users = userMapper.selUsersPoint(num);
//        System.out.println(users);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i <users.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_name", users.get(i).getUser_name());
            jsonObject.put("point", users.get(i).getPoint());
            if(users.get(i).getSign_info()==null||StringUtils.isBlank(users.get(i).getSign_info())){
                jsonObject.put("sign_info", "这个小玩家有点懒,没留下签名哦...");
            }else {
                jsonObject.put("sign_info", users.get(i).getSign_info());
            }
            if(users.get(i).getImage()==null||StringUtils.isBlank(users.get(i).getImage())){
                jsonObject.put("image", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590088773109&di=eb330d15a26dc660271444320366fe96&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F01%2F35%2F88%2F67573bfa8b3656f.jpg");
            }else {
                jsonObject.put("image", users.get(i).getImage());
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*获取公告栏*/
    @Override
    public String selGongGao(int num) {
        List<GongGao> gongGaos = userMapper.selGongGao(num);
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < gongGaos.size(); i++) {
            JSONObject jsonObject = JSONObject.fromObject(gongGaos.get(i));
            jsonObject.put("create_time",sdf.format(gongGaos.get(i).getCreate_time()));
            jsonObject.put("button", "<button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-normal\" onclick=\"showGongGao(\'"+gongGaos.get(i).getName()+"','"+gongGaos.get(i).getInfo()+"\')\">查看</button>");
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray.toString());
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        return jsonObject.toString();
    }

    /*更改签到状态*/
    @Override
    public void updateSign(User user) {
        userMapper.updSign(user.getId());
    }

    /*增加积分*/
    @Override
    public void addPoint(User user,int point) {
        userMapper.addPoint(user.getId(),point);
    }

    @Override
    public void updatePassword(User user) {
        userMapper.updPassword(user.getPassword(),user.getId());
    }

    @Override
    public void updateSignInfo(User user) {
        userMapper.updsign_info(user.getSign_info(),user.getId());
    }

    @Override
    public String selMakeUp(User user, int start_num,int end_num) {
        List<MakeUp> makeUps = userMapper.selMakeUp(user.getId(), start_num,end_num);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < makeUps.size(); i++) {
            MakeUp makeUp = makeUps.get(i);
            JSONObject jsonObject = new JSONObject();
            int type = makeUp.getType();
            String type_s = "积分";
            if(type!=0){
//                其他TYPE
//                type_s = "";
            }
            String status_s = "未使用";
            int status = makeUp.getStatus();
            if(status==1){
                status_s = "已使用";
            }
            if(status==2){
                status_s = "已过期";
            }
            jsonObject.put("name",makeUp.getName());
            jsonObject.put("type",type_s);
            jsonObject.put("status",status_s);
            jsonObject.put("point",makeUp.getPoint());
            jsonObject.put("person",makeUp.getPerson());
            jsonObject.put("release_time",sdf.format(makeUp.getRelease_time()));
            if(makeUp.getLingqu_time()!=null){
                jsonObject.put("lingqu_time",sdf.format(makeUp.getLingqu_time()));
            }
            jsonObject.put("exp_time",sdf.format(makeUp.getExp_time()));
            if(status==0){
                jsonObject.put("button", "<button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-normal\" onclick=\"lingqu(\'"+makeUp.getId()+"','"+makeUp.getType()+"\')\">领取</button>");
            }
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray.toString());
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", userMapper.selMakeUpCount(user.getId()).getCount());
        return jsonObject.toString();
    }


    @Override
    public void update_mark_up_status(int id) {
        Date date = new Date();
        userMapper.lingQuJiFen(id,sdf.format(date));
    }

    @Override
    public MakeUp selMarkUpStatus(int id) {
        return userMapper.selMakeUpStatus(id);
    }

    @Override
    public LongInfo sellongNameZhong(String name) {
        return userMapper.selLongNameByName(name);
    }

    @Override
    public LongInfo sellongById(int id) {
        return userMapper.selLongNameById(id);
    }


    @Override
    public String selPointLog(User user,int start_num,int limit) {
        List<UserLog> userLogs = userMapper.selPointLog(user.getSteamid(), start_num, limit);

        for (int i = 0; i < userLogs.size(); i++) {
            userLogs.get(i).setCreate_time_t(sdf.format(userLogs.get(i).getCreate_time()));
        }

        JSONArray jsonArray = JSONArray.fromObject(userLogs);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray.toString());
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", userMapper.selPointLogCount(user.getSteamid(),start_num,limit).getCount());
        return jsonObject.toString();
    }

    @Override
    public void updIsSign() {
        userMapper.updIsSign();
    }

    @Override
    public List<User> selAllUsers() {
        return userMapper.selAllUser();
    }

    @Override
    public String selColorsBySteamId(String steamid) {
        List<Colors> colors = userMapper.selallcolorbysteamid(steamid);
        for (int i = 0; i < colors.size(); i++) {
            colors.get(i).setSave_time_s(sdf.format(colors.get(i).getSave_time()));
        }
        JSONArray jsonArray = JSONArray.fromObject(colors);

        for (int i = 0; i < jsonArray.size(); i++) {
            jsonArray.getJSONObject(i).put("caozuo", "<button class=\"layui-btn layui-btn-sm layui-btn-radius layui-btn-danger\" onclick=\"delcolor(\'"+jsonArray.getJSONObject(i).getInt("id")+"')\">删除</button>");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray.toString());
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", userMapper.selallcolorbysteamidcount(steamid).getCount());
        return jsonObject.toString();
    }

    @Override
    public void delColor(int id) {
        userMapper.delcolor(id);
    }

    @Override
    public void saveColor(String steamid, String user_name, String color_name, int SkinPaletteSection1, int SkinPaletteSection2, int SkinPaletteSection3, int SkinPaletteSection4, int SkinPaletteSection5, int SkinPaletteSection6, String SkinPaletteVariation) {
        userMapper.insertcolor(steamid,color_name,user_name,new Date(),SkinPaletteSection1,SkinPaletteSection2,SkinPaletteSection3,SkinPaletteSection4,SkinPaletteSection5,SkinPaletteSection6,SkinPaletteVariation);
    }


    @Override
    public List<Colors> selColorsBySteamIdPojo(String steamid) {
        List<Colors> colors = userMapper.selallcolorbysteamid(steamid);
        return colors;
    }

    @Override
    public int selColorCount(String steamid) {
        return userMapper.selallcolorbysteamidcount(steamid).getCount();
    }
}
