package com.isle.controller;

import com.isle.util.DBHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
@RequestMapping("cmd")
public class CmdController {

    public LinkedBlockingQueue<String> queue = new LinkedBlockingQueue(50);

    public static void main(String[] args) {
        CmdController c = new CmdController();
//        System.out.println(c.getqueueSize());
    }




    @RequestMapping(value = "exec",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String exec(){
//        System.out.println("队列数： "+queue.size());
        return 0==queue.size()?"null":queue.poll();
    }

    public void puttime(String cmd){
        try {
            synchronized (CmdController.class){
                queue.put(cmd);
            }
        }catch (Exception e){
//            System.out.println(e);
            e.printStackTrace();
//            return "error";
        }
    }

    public String put(String cmd){
        try {
            synchronized (CmdController.class){
                String steamid = cmd.split("&")[0];
                if(queue.size()>50){
                    return "max_size";
                }if(queue.contains(steamid)) {
                    return "chong";
                }else {
                    queue.put(cmd);
                    return "y";
                }
            }

        }catch (Exception e){
//            System.out.println(e);
            e.printStackTrace();
            return "error";
        }
    }

//    public int getqueueSize(){
//        try {
//            queue.put("123");
//            queue.put("456");
//            System.out.println(queue.contains("123"));
//            System.out.println(queue.peek());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return queue.size();
//    }


}
