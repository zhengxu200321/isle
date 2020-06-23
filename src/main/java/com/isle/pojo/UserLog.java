package com.isle.pojo;

import java.util.Date;

public class UserLog {

    private String user;
    private String log;
    private Date create_time;
    private int type;
    private int id;
    private String steam_id;
    private int count;
    private String create_time_t;

    @Override
    public String toString() {
        return "UserLog{" +
                "user='" + user + '\'' +
                ", log='" + log + '\'' +
                ", create_time=" + create_time +
                ", type=" + type +
                ", id=" + id +
                ", steam_id='" + steam_id + '\'' +
                ", count=" + count +
                ", create_time_t='" + create_time_t + '\'' +
                '}';
    }

    public String getCreate_time_t() {
        return create_time_t;
    }

    public void setCreate_time_t(String create_time_t) {
        this.create_time_t = create_time_t;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSteam_id() {
        return steam_id;
    }

    public void setSteam_id(String steam_id) {
        this.steam_id = steam_id;
    }
}
