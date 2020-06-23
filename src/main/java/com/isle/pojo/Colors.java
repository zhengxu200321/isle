package com.isle.pojo;

import java.util.Date;

public class Colors {

    private int id;
    private String color_name;
    private Date save_time;
    private String steamid;
    private String save_person;
    private int SkinPaletteSection1;
    private int SkinPaletteSection2;
    private int SkinPaletteSection3;
    private int SkinPaletteSection4;
    private int SkinPaletteSection5;
    private int SkinPaletteSection6;
    private String SkinPaletteVariation;
    private String save_time_s;
    private int count;

    @Override
    public String toString() {
        return "Colors{" +
                "id=" + id +
                ", color_name='" + color_name + '\'' +
                ", save_time=" + save_time +
                ", steamid='" + steamid + '\'' +
                ", save_person='" + save_person + '\'' +
                ", SkinPaletteSection1=" + SkinPaletteSection1 +
                ", SkinPaletteSection2=" + SkinPaletteSection2 +
                ", SkinPaletteSection3=" + SkinPaletteSection3 +
                ", SkinPaletteSection4=" + SkinPaletteSection4 +
                ", SkinPaletteSection5=" + SkinPaletteSection5 +
                ", SkinPaletteSection6=" + SkinPaletteSection6 +
                ", SkinPaletteVariation='" + SkinPaletteVariation + '\'' +
                ", save_time_s='" + save_time_s + '\'' +
                ", count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSave_time_s() {
        return save_time_s;
    }

    public void setSave_time_s(String save_time_s) {
        this.save_time_s = save_time_s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public Date getSave_time() {
        return save_time;
    }

    public void setSave_time(Date save_time) {
        this.save_time = save_time;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getSave_person() {
        return save_person;
    }

    public void setSave_person(String save_person) {
        this.save_person = save_person;
    }

    public int getSkinPaletteSection1() {
        return SkinPaletteSection1;
    }

    public void setSkinPaletteSection1(int skinPaletteSection1) {
        SkinPaletteSection1 = skinPaletteSection1;
    }

    public int getSkinPaletteSection2() {
        return SkinPaletteSection2;
    }

    public void setSkinPaletteSection2(int skinPaletteSection2) {
        SkinPaletteSection2 = skinPaletteSection2;
    }

    public int getSkinPaletteSection3() {
        return SkinPaletteSection3;
    }

    public void setSkinPaletteSection3(int skinPaletteSection3) {
        SkinPaletteSection3 = skinPaletteSection3;
    }

    public int getSkinPaletteSection4() {
        return SkinPaletteSection4;
    }

    public void setSkinPaletteSection4(int skinPaletteSection4) {
        SkinPaletteSection4 = skinPaletteSection4;
    }

    public int getSkinPaletteSection5() {
        return SkinPaletteSection5;
    }

    public void setSkinPaletteSection5(int skinPaletteSection5) {
        SkinPaletteSection5 = skinPaletteSection5;
    }

    public int getSkinPaletteSection6() {
        return SkinPaletteSection6;
    }

    public void setSkinPaletteSection6(int skinPaletteSection6) {
        SkinPaletteSection6 = skinPaletteSection6;
    }

    public String getSkinPaletteVariation() {
        return SkinPaletteVariation;
    }

    public void setSkinPaletteVariation(String skinPaletteVariation) {
        SkinPaletteVariation = skinPaletteVariation;
    }
}
