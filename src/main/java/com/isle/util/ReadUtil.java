package com.isle.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Random;

public class ReadUtil {

    public static void main(String[] args) {
//        System.out.println(ReadUtil.getUserInfo("76561199055337255"));
//        JSONObject jsonObject = new JSONObject();
        File file = new File(save_path + "123" + ".json");
//        System.out.println(file);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int r = random.nextInt(24)+1;
            System.out.println(r);
        }

    }

    static Logger logger = Logger.getLogger(ReadUtil.class);

//    static String save_path = "C:\\isle\\steamcmd\\steamapps\\common\\TheIsleDedicatedServer\\TheIsle\\Saved\\Databases\\Survival\\Players\\";
    static String save_path = "D:\\isle\\steamcmd\\steamapps\\common\\The Isle Dedicated Server\\TheIsle\\Saved\\Databases\\Survival\\Players\\";
//    static String save_path = "D:\\";
    public static String getUserInfo(String staemdid) {
        String json_str = "";
        try {
            File file = new File(save_path + staemdid + ".json");
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),"Utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            json_str = sb.toString();
            return json_str;
        }catch (Exception e){
            logger.info("读取 " + staemdid + " 的个人信息文件报错"+e.getMessage());
            return null;
        }
    }

    public static void writeUserInfo(String steamid,String str){
        try {
            File file = new File(save_path + steamid + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(str);
            fileWriter.close();
        }catch (Exception e){
            logger.error("修改 "+steamid+" 的个人信息文件报错",e);
        }

    }

}
