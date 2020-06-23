package com.isle.pojo;

import java.util.Date;

public class CunDangLog {

    private int id;
    private String name;
    private String type;
    private Date create_time;
    private String steam_id;
    private String user_name;
    private String name_china;
    private String create_t;
    private int count;

    @Override
    public String toString() {
        return "CunDangLog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", create_time=" + create_time +
                ", steam_id='" + steam_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", name_china='" + name_china + '\'' +
                ", create_t='" + create_t + '\'' +
                ", count=" + count +
                '}';
    }

    public String getCreate_t() {
        return create_t;
    }

    public void setCreate_t(String create_t) {
        this.create_t = create_t;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
