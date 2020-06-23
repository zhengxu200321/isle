package com.isle.controller;


import com.isle.pojo.LongInfo;
import com.isle.pojo.MakeUp;
import com.isle.pojo.User;
import com.isle.pojo.UserLog;
import com.isle.service.LogService;
import com.isle.service.UserService;

import com.isle.util.DBHelper;
import com.isle.util.PublicUtil;
import com.isle.util.ReadUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Resource
    private CmdController cmdController;


    public static Map<Integer, String> vipmap = new HashMap<>();

    @RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        if (session.getAttribute("user") != null && session.getAttribute("allurl") != null) {
            return "main";
        }
        if(request.getParameter("user_name")==null){
            session.setAttribute("err_msg","");
            return "login";
        }
        User user = new User();
        user.setUser_name(request.getParameter("user_name"));
        user.setPassword(request.getParameter("password"));
        Map<String, Object> map = userService.selUser(user);
        if (map.get("user") != null && map.get("allurl") != null) {
            session.setAttribute("user", map.get("user"));
            session.setAttribute("allurl", map.get("allurl"));
            session.removeAttribute("err_msg");
            UserLog userLog = new UserLog();
            userLog.setUser(user.getUser_name());
            userLog.setLog("登陆了系统");
            userLog.setType(0);
            userLog.setCreate_time(new Date());
            userLog.setSteam_id(((User)map.get("user")).getSteamid());
            logService.insertLog(userLog);
            User user1 = (User) map.get("user");
            if(user1.getRid()==2){
                return "manage_main";
            }else {
                return "main";
            }
        }else {
            User new_user = userService.selUsers(user);
            if(new_user == null){
                session.setAttribute("err_msg","用户不存在");
                return "login";
            }else {
                session.setAttribute("err_msg","密码不正确哦");
                return "login";
            }
        }
    }

    @RequestMapping(value = "loginout",produces = "text/html; charset=utf-8")
    public String loginout(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping(value = "users",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String users(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦....";
        }else {
            User users= userService.selUsers(user);
            session.setAttribute("user",users);
            JSONObject jsonObject = JSONObject.fromObject(users);
            jsonObject.remove("password");
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "userpoint",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String userpoint(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String json = userService.selUsersPoint(30);
        return json;
    }

    @RequestMapping(value = "showGongGao",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String showGongGao(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String gonggaoJson = userService.selGongGao(6);
        return gonggaoJson;
    }

    @RequestMapping(value = "sign",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String sign(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        if(vipmap==null||vipmap.size()<1){
            List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("select * from vip_type");
            for (int i = 0; i < dataMap.size(); i++) {
                vipmap.put((int) dataMap.get(i).get("vip_type"),(String) dataMap.get(i).get("point"));
            }
            System.out.println("vipmap===>"+vipmap.toString());
        }

        if(session.getAttribute("user")!=null){
            User users = (User) session.getAttribute("user");
            User user= userService.selUsers(users);

            System.out.println(user);
            if(user.getIs_sign()==1){
                return "今日已签到哦";
            }else {
                List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("select `values` from public_util where name = 'sign_point'");
                String points = (String) dataMap.get(0).get("values");
                int pointss = Integer.valueOf(points);
                userService.updateSign(user);
                int point = user.getPoint();

                if(user.getIs_vip()!=0&&user.getVip_type()!=0){
                    int vip_type = user.getVip_type();
                    String poin = vipmap.get(vip_type);
                    pointss = Integer.valueOf(poin);
                    System.out.println(pointss);
                }
                point = point + pointss;
                userService.addPoint(user,point);
                UserLog log = new UserLog();
                log.setCreate_time(new Date());
                log.setType(1);
                log.setUser(user.getUser_name());
                log.setSteam_id(user.getSteamid());
                log.setLog("签到 增加积分 "+pointss);
                logService.insertLog(log);
                return "签到成功 积分+"+pointss;
            }
        }else {
            return "请先登陆哦";
        }
    }



    @RequestMapping(value = "edituser",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String edituser(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        if(session.getAttribute("user")!=null){
            User user = (User) session.getAttribute("user");
            String password = request.getParameter("password");
            User users= userService.selUsers(user);
            if(!password.equals(users.getPassword())){
                return "旧密码错了哦";
            }
            String sign_info = request.getParameter("sign_info");
            if(StringUtils.isNotBlank(sign_info)){
                user.setSign_info(sign_info);
                userService.updateSignInfo(user);
            }
            String newpassword = request.getParameter("newpassword");
            user.setPassword(newpassword);
            userService.updatePassword(user);
            return "修改成功";
        }else {
            return "请先登陆哦";
        }
    }

    @RequestMapping(value = "showBuChang",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String showBuChang(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        User user = (User) session.getAttribute("user");
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        int start_num = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        return  userService.selMakeUp(user, start_num, Integer.valueOf(limit));
    }

    /*领取积分*/
    @RequestMapping(value = "lingqu_point",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String lingQuPoint(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String id_s = request.getParameter("id");
        int id = Integer.valueOf(id_s);
        MakeUp makeUp = userService.selMarkUpStatus(id);
        if(makeUp==null){
            return "对不起;该补偿不存在哦";
        }else {
            if(makeUp.getStatus()==1){
                return "对不起;该积分已经领取过了哦";
            }else if(makeUp.getStatus()==2){
                return "对不起;该积分已经过期了哦";
            }else {
                if(makeUp.getType()==0){
                    userService.update_mark_up_status(id);
                    int point = makeUp.getPoint();
                    int point1 = point;
                    User users = (User) session.getAttribute("user");
                    User user= userService.selUsers(users);
                    point = point+user.getPoint();
                    userService.addPoint(user, point);
                    UserLog log = new UserLog();
                    log.setCreate_time(new Date());
                    log.setType(1);
                    log.setUser(user.getUser_name());
                    log.setSteam_id(user.getSteamid());
                    log.setLog("补偿 增加积分 "+point1);
                    logService.insertLog(log);
                    return "领取成功;到个人中心刷新即可生效";
                }else {
                    return "对不起;该类型的积分暂时无法领取哦";
                }

            }
        }
    }


    /*获取当前龙信息*/
    @RequestMapping(value = "getlong_info",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getLongInfo( HttpServletRequest request, HttpServletResponse response,HttpSession session){
        JSONObject jsonObject = new JSONObject();
        if(session.getAttribute("user")!=null){
            User user = (User) session.getAttribute("user");
            String steamid = user.getSteamid();
            String info = ReadUtil.getUserInfo(steamid);
            if(StringUtils.isBlank(info)){
                jsonObject.put("status", 0);
                jsonObject.put("msg", "此StramID当前没有龙哦;如有问题请联系管理");
                return jsonObject.toString();
            }else {
                jsonObject = JSONObject.fromObject(info);
                String name = jsonObject.getString("CharacterClass");
                System.out.println(user.getUser_name()+" 查看的当前龙名字为 :" +name);
                LongInfo longInfo = userService.sellongNameZhong(name);
                String biaozhunInfo = longInfo.getInfo();
                JSONObject biaoJson = JSONObject.fromObject(biaozhunInfo);
//                System.out.println(longInfo.toString());
                jsonObject.put("CharacterClass", longInfo.getName_china());
                String bGender = jsonObject.getString("bGender");
                if(bGender.equals("false")){
                    jsonObject.put("bGender", "公");
                }else {
                    jsonObject.put("bGender", "母");
                }

                String huger_s = biaoJson.getString("Hunger");
                int biao_huger = Integer.valueOf(huger_s);
                String huger_ss = jsonObject.getString("Hunger");
                int huger = Integer.valueOf(huger_ss);
                jsonObject.put("Hunger", String.valueOf(Double.valueOf((Double.valueOf(huger) / Double.valueOf(biao_huger))*100)) + " %");


                String Thirst_s = biaoJson.getString("Thirst");
                int biao_Thirst = Integer.valueOf(Thirst_s);
                String Thirst_ss = jsonObject.getString("Thirst");
                int Thirst = Integer.valueOf(Thirst_ss);
                jsonObject.put("Thirst", String.valueOf(Double.valueOf((Double.valueOf(Thirst) / Double.valueOf(biao_Thirst))*100)) + " %");


                String Stamina_s = biaoJson.getString("Stamina");
                int biao_Stamina = Integer.valueOf(Stamina_s);
                String Stamina_ss = jsonObject.getString("Stamina");
                int Stamina = Integer.valueOf(Stamina_ss);
                jsonObject.put("Stamina", String.valueOf(Double.valueOf((Double.valueOf(Stamina) / Double.valueOf(biao_Stamina))*100)) + " %");


                String Health_s = biaoJson.getString("Health");
                int biao_Health = Integer.valueOf(Health_s);
                String Health_ss = jsonObject.getString("Health");
                int Health = Integer.valueOf(Health_ss);
                jsonObject.put("Health", String.valueOf(Double.valueOf((Double.valueOf(Health) / Double.valueOf(biao_Health))*100)) + " %");

                String BleedingRate_s = biaoJson.getString("BleedingRate");
                int biao_BleedingRate = Integer.valueOf(BleedingRate_s);
                if(biao_BleedingRate!=0){
                    String BleedingRate_ss = jsonObject.getString("BleedingRate");
                    int BleedingRate = Integer.valueOf(BleedingRate_ss);
                    jsonObject.put("BleedingRate", String.valueOf(Double.valueOf((Double.valueOf(BleedingRate) / Double.valueOf(biao_BleedingRate))*100)) + " %");

                }

                jsonObject.put("msg", "");
                jsonObject.put("steam_id", user.getSteamid());
                return jsonObject.toString();
            }

        }else {
            jsonObject.put("status", 0);
            jsonObject.put("msg", "请先登陆哦");
            return jsonObject.toString();
        }
    }


    /*获取的是积分记录*/
    @RequestMapping(value = "getpointlog",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getpointlog(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        int start_num = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        return userService.selPointLog(user, start_num, Integer.valueOf(limit));
    }


    /*获取队列*/
    @RequestMapping(value = "getqueue",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getqueue(HttpServletRequest request, HttpServletResponse response,HttpSession session){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("codes","1");
        jsonObject.put("error_msg","");
        User user = (User) session.getAttribute("user");
        if(user==null){
            jsonObject.put("error_msg", "请先登陆哦");
            return jsonObject.toString();
        }else {
            jsonObject.put("codes","0");
            jsonObject.put("queuesize", cmdController.queue.size());
            if(cmdController.queue.contains(user.getSteamid())){
                jsonObject.put("myqueue", "1");
            }else {
                jsonObject.put("myqueue", "0");
            }
            return jsonObject.toString();
        }
    }


    /*获取的是所有的颜色*/
    @RequestMapping(value = "getcolors",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getcolors(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        int start_num = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        return userService.selColorsBySteamId(user.getSteamid());
    }

    /*删除肤色*/
    @RequestMapping(value = "delcolor",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String delcolor(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        int id = Integer.valueOf(request.getParameter("id"));
        userService.delColor(id);
        return "该皮肤删除成功哦";
    }
    /*保存肤色*/
    @RequestMapping(value = "savcolor",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String savcolor(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String color_name = request.getParameter("color_name");
        if(StringUtils.isBlank(color_name)){
            return "对不起;肤色名字不可以为空";
        }
        String steamid = user.getSteamid();
        int count = userService.selColorCount(steamid);
        if(count>10){
            return "对不起;最多保存10个肤色哦";
        }
        String longinfo = ReadUtil.getUserInfo(steamid);
        if(StringUtils.isBlank(longinfo)){
            return "对不起;您当前没有龙信息;无法保存当前肤色";
        }
        JSONObject jsonObject = JSONObject.fromObject(longinfo);
        int SkinPaletteSection1 = jsonObject.getInt("SkinPaletteSection1");
        int SkinPaletteSection2 = jsonObject.getInt("SkinPaletteSection2");
        int SkinPaletteSection3 = jsonObject.getInt("SkinPaletteSection3");
        int SkinPaletteSection4 = jsonObject.getInt("SkinPaletteSection4");
        int SkinPaletteSection5 = jsonObject.getInt("SkinPaletteSection5");
        int SkinPaletteSection6 = jsonObject.getInt("SkinPaletteSection6");
        String SkinPaletteVariation = jsonObject.getString("SkinPaletteVariation");
        userService.saveColor(steamid, user.getUser_name(), color_name, SkinPaletteSection1, SkinPaletteSection2, SkinPaletteSection3, SkinPaletteSection4, SkinPaletteSection5, SkinPaletteSection6, SkinPaletteVariation);
        return "保存肤色成功哦";
    }
}
