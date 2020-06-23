package com.isle;

import java.awt.*;
import java.awt.event.InputEvent;

public class Roboot {


    private static Robot robot;

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        try {
            robot = new Robot();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        for(int i=0;i<6;i++){
            robot.delay(5000);
            robot.mouseMove(700, 480);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            for(int j=0;j<15;j++)//休息15分钟
                robot.delay(60000);
        }
    }


}
