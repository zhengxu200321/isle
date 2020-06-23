package com.isle.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

public class User {

    private int id;
    private String user_name;
    private String phone;
    private String email;
    private String password;
    private String steamid;
    private String steam_name;
    private int rid;
    private String image;
    private int point;
    private int is_sign;
    private int yesday_save;
    private int yesday_out;
    private String sign_info;
    private int vip_type;
    private int is_vip;
    private Date vip_start_time;
    private Date vip_end_time;
    private Date last_cun_time;

    private List<Url> url;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", steamid='" + steamid + '\'' +
                ", steam_name='" + steam_name + '\'' +
                ", rid=" + rid +
                ", image='" + image + '\'' +
                ", point=" + point +
                ", is_sign=" + is_sign +
                ", yesday_save=" + yesday_save +
                ", yesday_out=" + yesday_out +
                ", sign_info='" + sign_info + '\'' +
                ", vip_type=" + vip_type +
                ", is_vip=" + is_vip +
                ", vip_start_time=" + vip_start_time +
                ", vip_end_time=" + vip_end_time +
                ", last_cun_time=" + last_cun_time +
                ", url=" + url +
                '}';
    }

    public String getSteam_name() {
        return steam_name;
    }

    public void setSteam_name(String steam_name) {
        this.steam_name = steam_name;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public Date getLast_cun_time() {
        return last_cun_time;
    }

    public void setLast_cun_time(Date last_cun_time) {
        this.last_cun_time = last_cun_time;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public Date getVip_start_time() {
        return vip_start_time;
    }

    public void setVip_start_time(Date vip_start_time) {
        this.vip_start_time = vip_start_time;
    }

    public Date getVip_end_time() {
        return vip_end_time;
    }

    public void setVip_end_time(Date vip_end_time) {
        this.vip_end_time = vip_end_time;
    }

    public String getSign_info() {
        return sign_info;
    }

    public void setSign_info(String sign_info) {
        this.sign_info = sign_info;
    }

    public int getYesday_save() {
        return yesday_save;
    }

    public void setYesday_save(int yesday_save) {
        this.yesday_save = yesday_save;
    }

    public int getYesday_out() {
        return yesday_out;
    }

    public void setYesday_out(int yesday_out) {
        this.yesday_out = yesday_out;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public List<Url> getUrl() {
        return url;
    }

    public void setUrl(List<Url> url) {
        this.url = url;
    }



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }
}
