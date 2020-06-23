package com.isle.controller;

import com.isle.pojo.*;
import com.isle.service.LogService;
import com.isle.service.LongService;
import com.isle.service.UserService;
import com.isle.util.DBHelper;
import com.isle.util.LogUtil;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class LongController {


    @Resource
    private UserService userService;

    @Resource
    private LogUtil logUtil;

    @Resource
    private LongService longService;

    @Resource
    private CmdController cmdController;

    @Resource
    private LogService logService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "cun",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String cun(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦....";
        }else {
            User users= userService.selUsers(user);
            session.setAttribute("user",users);
            String steamid = users.getSteamid();

            Date last_cun_time = users.getLast_cun_time();
            Date date = new Date();
            if(date.getTime()-last_cun_time.getTime()<1000*60*5){
                return "您必须距离上次存龙时间大于五分钟才可存龙;您上次的存龙时间为:" + sdf.format(last_cun_time);
            }

            if(StringUtils.isBlank(steamid)){
                return "您的账户没有绑定您的Steam哦";
            }else {
                String userInfo = ReadUtil.getUserInfo(steamid);
                if(StringUtils.isBlank(userInfo)){
                    return "您当前没有龙哦;可以去背包看看";
                }else {
                    JSONObject jsonObject = JSONObject.fromObject(userInfo);
                    String name = jsonObject.getString("CharacterClass");
                    LongInfo longInfo = userService.sellongNameZhong(name);
                    int point = longInfo.getPoint();
                    int is_gender = longInfo.getIs_gender();
                    if(is_gender==0&&jsonObject.getString("bGender").equals("true")){
                        point = point * 2;
                    }
                    int user_point = users.getPoint();
                    String Growth = jsonObject.getString("Growth");
                    if(!Growth.equals("1.0")){
                        return "您好;成长值必须为1.0才可以存呢";
                    }
                    String BleedingRate = jsonObject.getString("BleedingRate");
                    if(!BleedingRate.equals("0")){
                        return "您好;您的出血必须为0才可以存呢";
                    }
                    int Hunger = Integer.valueOf(jsonObject.getString("Hunger"));
                    JSONObject jsonObject_Biao = JSONObject.fromObject(longInfo.getInfo());
                    int Hunger_bioa = Integer.valueOf(jsonObject_Biao.getString("Hunger"));
                    if(Hunger*2<Hunger_bioa){
                        return "您好;您的饥饿值必须大于总体的一半才可以存哦";
                    }
                    int Thirst = Integer.valueOf(jsonObject.getString("Thirst"));
                    int Thirst_biao = Integer.valueOf(jsonObject_Biao.getString("Thirst"));
                    if(Thirst*2<Thirst_biao){
                        return "您好;您的水值必须大于总体的一半才可以存哦";
                    }
                    int Stamina = Integer.valueOf(jsonObject.getString("Stamina"));
                    int Stamina_biao = Integer.valueOf(jsonObject_Biao.getString("Stamina"));
                    if(Stamina*2<Stamina_biao){
                        return "您好;您的体力值必须大于总体的一半才可以存哦";
                    }
                    int Health = Integer.valueOf(jsonObject.getString("Health"));
                    int Health_biao = Integer.valueOf(jsonObject_Biao.getString("Health"));
                    if(Health*2<Health_biao){
                        return "您好;您的健康值必须大于总体的一半才可以存哦";
                    }

                    String remark = request.getParameter("remark");

                    if(StringUtils.isNotBlank(remark)){
                        if (remark.length() > 15) {
                            return "备注不可以超过15字哦";
                        }
                    }
                    String long_name = jsonObject.getString("CharacterClass");
                    if(long_name.contains("Juv")||long_name.contains("Sub")){
                        return "不许利用网站漏洞哦...请找下管理吧 YY 716090";
                    }
                    String userName = PublicUtil.getUserName(user.getUser_name(), steamid);
                    if(StringUtils.isBlank(userName)||userName.contains("ERROR")){
                        return userName;
                    }
                    ReadUtil.writeUserInfo(steamid, "");
                    userName = userName.replaceAll("○N゛『闪电』﹏", "");
                    userName = userName.replaceAll("KZ 战堂", "");
                    userName = userName.replaceAll("酒馆♫", "");
                    userName = userName.replaceAll("☠悍匪☠", "");
                    userName = userName.replaceAll("丨⇘ H C ⇙丨", "");
                    userName = userName.replaceAll("帝龍集团-", "");
                    userName = userName.replaceAll("-飯 团 - ", "");
                    cmdController.put("bringa&" + userName+"&"+steamid);
                    while (true){
                        boolean contains = cmdController.queue.contains(steamid);
                        if(!contains){
                            break;
                        }
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return cunUtil(jsonObject_Biao,jsonObject,request,name,user,longInfo,point,user_point,steamid,remark);
                }
            }
        }
    }


    public String cunUtil(JSONObject jsonObject_Biao,JSONObject jsonObject,HttpServletRequest request,String name,User user,LongInfo longInfo,int point,int user_point,String steamid,String remark) {
        jsonObject_Biao.put("CameraRotation_Thenyaw_Island", jsonObject.get("CameraRotation_Thenyaw_Island"));
        jsonObject_Biao.put("CameraDistance_Thenyaw_Island", jsonObject.get("CameraDistance_Thenyaw_Island"));
        jsonObject_Biao.put("SkinPaletteSection1", jsonObject.get("SkinPaletteSection1"));
        jsonObject_Biao.put("SkinPaletteSection2", jsonObject.get("SkinPaletteSection2"));
        jsonObject_Biao.put("SkinPaletteSection3", jsonObject.get("SkinPaletteSection3"));
        jsonObject_Biao.put("SkinPaletteSection4", jsonObject.get("SkinPaletteSection4"));
        jsonObject_Biao.put("SkinPaletteSection5", jsonObject.get("SkinPaletteSection5"));
        jsonObject_Biao.put("SkinPaletteSection6", jsonObject.get("SkinPaletteSection6"));
        jsonObject_Biao.put("SkinPaletteVariation", jsonObject.get("SkinPaletteVariation"));
        jsonObject_Biao.put("bGender", jsonObject.get("bGender"));


        CunDang cunDang = new CunDang();
        cunDang.setCreate_time(new Date());
        cunDang.setInfo(jsonObject_Biao.toString());
        cunDang.setName(name);
        cunDang.setUser_name(user.getUser_name());
        cunDang.setSteam_id(user.getSteamid());
        cunDang.setName_china(longInfo.getName_china());
        cunDang.setRemark(remark);
        cunDang.setPoint(point);
//        longService.insertCunDang(cunDang);


        CunDangLog cunDangLog = new CunDangLog();
        cunDangLog.setCreate_time(new Date());
        cunDangLog.setType("存");
        cunDangLog.setSteam_id(user.getSteamid());
        cunDangLog.setUser_name(user.getUser_name());
        cunDangLog.setName(name);
        cunDangLog.setName_china(longInfo.getName_china());
        logUtil.insertLongLog(cunDangLog);
        ReadUtil.writeUserInfo(steamid, "");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ReadUtil.writeUserInfo(steamid, "");
        user_point = user_point + point;
        userService.addPoint(user, user_point);
        UserLog log = new UserLog();
        log.setCreate_time(new Date());
        log.setType(1);
        log.setUser(user.getUser_name());
        log.setSteam_id(user.getSteamid());
        log.setLog("存龙 增加积分 " + point);
        logService.insertLog(log);

        DBHelper.getInstance().updateSql("update user set last_cun_time = ? where id = ?", new Object[]{new Date(),user.getId()});
        return "存龙成功哦;增加积分 " + point + " ;快去查看你的龙吧";
    }

    /*查看本人的库存*/
    @RequestMapping(value = "kucun",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String kucun(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        int start_num = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        return longService.selKuCun(user.getSteamid(), start_num, Integer.valueOf(limit));
    }

    /*查看本人的库存*/
    @RequestMapping("kucunone")
    @ResponseBody
    public String kucunone(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String id = request.getParameter("id");
        String long1 = longService.selkucunone(Integer.valueOf(id));
//        System.out.println(long1);
        JSONObject jsonObject = JSONObject.fromObject(long1);
        LongInfo longInfo = userService.sellongNameZhong(jsonObject.getString("CharacterClass"));
//        System.out.println(longInfo);
        JSONObject jsonObject1 = JSONObject.fromObject(longInfo.getInfo());

        String bGender = jsonObject.getString("bGender");
        if(bGender.equals("false")){
            jsonObject.put("bGender", "公");
        }else {
            jsonObject.put("bGender", "母");
        }

        String huger_s = jsonObject1.getString("Hunger");
        int biao_huger = Integer.valueOf(huger_s);
        String huger_ss = jsonObject.getString("Hunger");
        int huger = Integer.valueOf(huger_ss);
        jsonObject.put("Hunger", String.valueOf(Double.valueOf((Double.valueOf(huger) / Double.valueOf(biao_huger))*100)).substring(0,4) + " %");


        String Thirst_s = jsonObject1.getString("Thirst");
        int biao_Thirst = Integer.valueOf(Thirst_s);
        String Thirst_ss = jsonObject.getString("Thirst");
        int Thirst = Integer.valueOf(Thirst_ss);
        jsonObject.put("Thirst", String.valueOf(Double.valueOf((Double.valueOf(Thirst) / Double.valueOf(biao_Thirst))*100)).substring(0,4) + " %");


        String Stamina_s = jsonObject1.getString("Stamina");
        int biao_Stamina = Integer.valueOf(Stamina_s);
        String Stamina_ss = jsonObject.getString("Stamina");
        int Stamina = Integer.valueOf(Stamina_ss);
        jsonObject.put("Stamina", String.valueOf(Double.valueOf((Double.valueOf(Stamina) / Double.valueOf(biao_Stamina))*100)).substring(0,4) + " %");


        String Health_s = jsonObject1.getString("Health");
        int biao_Health = Integer.valueOf(Health_s);
        String Health_ss = jsonObject.getString("Health");
        int Health = Integer.valueOf(Health_ss);
        jsonObject.put("Health", String.valueOf(Double.valueOf((Double.valueOf(Health) / Double.valueOf(biao_Health))*100)).substring(0,4) + " %");

        String BleedingRate_s = jsonObject1.getString("BleedingRate");
        int biao_BleedingRate = Integer.valueOf(BleedingRate_s);
        if(biao_BleedingRate!=0){
            String BleedingRate_ss = jsonObject.getString("BleedingRate");
            int BleedingRate = Integer.valueOf(BleedingRate_ss);
            jsonObject.put("BleedingRate", String.valueOf(Double.valueOf((Double.valueOf(BleedingRate) / Double.valueOf(biao_BleedingRate))*100)).substring(0,4) + " %");

        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "qu",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String qu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        user = userService.selUsers(user);
        String id_s = request.getParameter("id");
        String zuobiao = request.getParameter("zuobiao");
        int zuobiao_id = Integer.valueOf(zuobiao);
        int id = Integer.valueOf(id_s);
        return longService.qu(id, user,zuobiao_id);
    }


    /*查看存取记录*/
    @RequestMapping(value = "cundanglog",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String cundanglong(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        int start_num = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
        return longService.cun_log(user,start_num,Integer.valueOf(limit));
    }

    /*查看龙的商店*/
    @RequestMapping(value = "longstore",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String longstore(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        return longService.longstore();
    }

    /*兑换*/
    @RequestMapping(value = "duihuan",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String duihuan(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user==null){
            return "请先登陆哦...";
        }
        String id = request.getParameter("id");
        String namegongmu = request.getParameter("gongmu");
        String namezuobiao = request.getParameter("zuobiao");
        boolean gongmu = Boolean.valueOf(namegongmu);
        return longService.duihuan(Integer.valueOf(id),namezuobiao,gongmu,user);
    }

    /*保存肤色*/
//    @RequestMapping(value = "savefuse",produces = "text/html; charset=utf-8")
//    @ResponseBody
//    public String savefuse(HttpServletRequest request, HttpServletResponse response, HttpSession session){
//        User user = (User) session.getAttribute("user");
//        if(user==null){
//            return "请先登陆哦...";
//        }
//        String name = request.getParameter("保存")
//    }
}
