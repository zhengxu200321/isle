package com.isle.pojo;

public class LongInfo {

    private String name;
    private String name_china;
    private int id;
    private int point;
    private int status;
    private int is_gender;
    private String info;
    private int sell_count;
    private String gender;
    private String gongmu;
    private String zuobiao;
    private String caozuo;

    @Override
    public String toString() {
        return "LongInfo{" +
                "name='" + name + '\'' +
                ", name_china='" + name_china + '\'' +
                ", id=" + id +
                ", point=" + point +
                ", status=" + status +
                ", is_gender=" + is_gender +
                ", info='" + info + '\'' +
                ", sell_count=" + sell_count +
                ", gender='" + gender + '\'' +
                ", gongmu='" + gongmu + '\'' +
                ", zuobiao='" + zuobiao + '\'' +
                ", caozuo='" + caozuo + '\'' +
                '}';
    }

    public String getCaozuo() {
        return caozuo;
    }

    public void setCaozuo(String caozuo) {
        this.caozuo = caozuo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGongmu() {
        return gongmu;
    }

    public void setGongmu(String gongmu) {
        this.gongmu = gongmu;
    }

    public String getZuobiao() {
        return zuobiao;
    }

    public void setZuobiao(String zuobiao) {
        this.zuobiao = zuobiao;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSell_count() {
        return sell_count;
    }

    public void setSell_count(int sell_count) {
        this.sell_count = sell_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_china() {
        return name_china;
    }

    public void setName_china(String name_china) {
        this.name_china = name_china;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_gender() {
        return is_gender;
    }

    public void setIs_gender(int is_gender) {
        this.is_gender = is_gender;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
