package com.example.experiment.Domain;

import java.util.ArrayList;


public class DBData {

    private String time;
    private ArrayList tem;
    private ArrayList hum;
    private ArrayList co;


    public DBData() {
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public ArrayList getTem() {
        return tem;
    }

    public void setTem(ArrayList tem) {
        this.tem = tem;
    }

    public ArrayList getHum() {
        return hum;
    }

    public void setHum(ArrayList hum) {
        this.hum = hum;
    }

    public ArrayList getCo() {
        return co;
    }

    public void setCo(ArrayList co) {
        this.co = co;
    }


}
