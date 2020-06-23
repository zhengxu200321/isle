package com.isle.pojo;

import java.util.Date;

public class MakeUp {

    private int id;
    private int type;
    private int status;
    private int point;
    private String person;
    private Date release_time;
    private Date exp_time;
    private Date lingqu_time;
    private String user_name;
    private String name;
    private int count;


    @Override
    public String toString() {
        return "MakeUp{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", point=" + point +
                ", person='" + person + '\'' +
                ", release_time=" + release_time +
                ", exp_time=" + exp_time +
                ", lingqu_time=" + lingqu_time +
                ", user_name='" + user_name + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

    public Date getLingqu_time() {
        return lingqu_time;
    }

    public void setLingqu_time(Date lingqu_time) {
        this.lingqu_time = lingqu_time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Date getRelease_time() {
        return release_time;
    }

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    public Date getExp_time() {
        return exp_time;
    }

    public void setExp_time(Date exp_time) {
        this.exp_time = exp_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
