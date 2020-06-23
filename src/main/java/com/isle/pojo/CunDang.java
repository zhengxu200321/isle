package com.isle.pojo;

import java.util.Date;

public class CunDang {

    private int id;
    private String name;
    private String info;
    private Date create_time;
    private String steam_id;
    private String user_name;
    private String name_china;
    private String count;
    private String remark;
    private int point;


    @Override
    public String toString() {
        return "CunDang{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", create_time=" + create_time +
                ", steam_id='" + steam_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", name_china='" + name_china + '\'' +
                ", count='" + count + '\'' +
                ", remark='" + remark + '\'' +
                ", point=" + point +
                '}';
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName_china() {
        return name_china;
    }

    public void setName_china(String name_china) {
        this.name_china = name_china;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getSteam_id() {
        return steam_id;
    }

    public void setSteam_id(String steam_id) {
        this.steam_id = steam_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
