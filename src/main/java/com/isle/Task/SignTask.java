package com.isle.Task;


import com.isle.controller.CmdController;
import com.isle.mapper.UserMapper;
import com.isle.pojo.User;
import com.isle.service.UserService;
import com.isle.util.DBHelper;
import com.isle.util.PublicUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class SignTask {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    CmdController cmdController;

    @Resource
    UserService userService;
//
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateSign() {
        System.out.println("开始归零第二天签到....");
        userService.updIsSign();
        System.out.println("归零第二天签到完成....");
    }


    @Scheduled(cron = "0 */1 * * * ?")
    public void add() {
        cmdController.puttime("time");
        System.out.println("队列数为:"+cmdController.queue.size());
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void addgrow() {
        cmdController.puttime("grow&UU");
        System.out.println("队列数为:"+cmdController.queue.size());
    }

    public static void main(String[] args) throws IOException {
        SignTask s = new SignTask();
//        String userName = PublicUtil.getUserName("123", "76561198399743433");
//        List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("select * from user where user_name = '一碗泡面汤'");
//        System.out.println(dataMap.get(0));

//        System.out.println(userName);
//        s.getUserImage();
//        s.updatepoint();
//        s.updateCunDangLog();
//        PublicUtil.getUserName("123", "76561198412117412");

        List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("SELECT * from user_log where log like '%签到 增加积分 10%' and create_time > '2020-06-22' order by id desc");
        for (int i = 0; i < dataMap.size(); i++) {
            String user = (String) dataMap.get(i).get("user");
            List<Map<String, Object>> dataMap1 = DBHelper.getInstance().getDataMap("select * from user where user_name = '" + user + "'");

            DBHelper.getInstance().insertSql("insert into make_up (name,type,status,point,person,release_time,exp_time,uid) values (?,?,?,?,?,?,?,?)", new Object[]{
                    "20200622签到10分补偿", 0, 0, 490,user , new Date(), "2020-06-23 00:10:00", dataMap1.get(0).get("id")
            });
        }


    }


    /*统计昨日取龙数*/
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateCunDangLog(){
        System.out.println("开始执行。。。");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,-1);
        String datet = sdf.format(c.getTime());
        List<User> users = userService.selAllUsers();
        for (int i = 0; i < users.size(); i++) {
            String steadid = users.get(i).getSteamid();
            List<Map<String, Object>> dataMap1 = DBHelper.getInstance().getDataMap("select count(id) as count from cundang_log where steam_id = '" + steadid + "' and create_time > '" + datet + " 00:00:00' and create_time < '" + datet + " 23:59:59' and type = '存'");
            List<Map<String, Object>> dataMap2 = DBHelper.getInstance().getDataMap("select count(id) as count from cundang_log where steam_id = '" + steadid + "' and create_time > '" + datet + " 00:00:00' and create_time < '" + datet + " 23:59:59' and type = '取'");
            long cun_count = (long) dataMap1.get(0).get("count");
            long qu_count = (long) dataMap2.get(0).get("count");
            DBHelper.getInstance().updateSql("update user set yesday_save = '" + cun_count + "' , yesday_out = '" + qu_count + "' where steamid = '" + steadid+"'",null);
        }
    }


    /*更改积分补偿的过期时间*/
    @Scheduled(cron = "0 0 0 * * ?")
    public void updatepoint() {
        DBHelper.getInstance().updateSql("update make_up set status = 2 where exp_time < '" + sdf.format(new Date()) + "' and status = 0", null);
    }

    /*更改VIP的过期时间*/
    @Scheduled(cron = "0 0 0 * * ?")
    public void updatevip() {
        DBHelper.getInstance().updateSql("update user set is_vip = 0 where vip_end_time < '" + sdf.format(new Date()) + "'", null);
    }


//    @Scheduled
    public void getUserImage() {
        DefaultHttpClient client = new DefaultHttpClient();
        List<Map<String, Object>> dataMap = DBHelper.getInstance().getDataMap("select * from user order by id desc");
        System.out.println("开始统计用户头像信息");
        for (int i = 0; i < dataMap.size(); i++) {
            try {
                String steamid = (String) dataMap.get(i).get("steamid");
                System.out.println(steamid);
                int id = (int) dataMap.get(i).get("id");
                String user_name = (String) dataMap.get(i).get("user_name");
                HttpGet httpGet = new HttpGet("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=CF23C3E8FFDA0857C9763AC1193246AE&steamids="+steamid);
                HttpResponse execute = client.execute(httpGet);
                String content = PublicUtil.readHtmlContentFromEntity(execute.getEntity());
                System.out.println(content);
                JSONObject jsonObject = JSONObject.fromObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                if(response==null){
                    System.out.println(user_name+"|"+steamid+"->为获取到用户头像");
                }else {
                    JSONArray players = response.getJSONArray("players");
                    String url = players.getJSONObject(0).getString("avatarfull");
                    String personaname = players.getJSONObject(0).getString("personaname");
                    System.out.println(user_name+"|"+steamid+"->获取到用户头像为:"+url);
                    DBHelper.getInstance().updateSql("update user set image = ?,steam_name = ? where id = ?", new Object[]{url,personaname,id});
                }
            }catch (Exception e){
                System.out.println("获取用户数据异常"+i+";"+e.getMessage());
            }
        }
    }





}
