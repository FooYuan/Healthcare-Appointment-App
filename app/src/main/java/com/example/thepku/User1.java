package com.example.thepku;

public class User1 {
    String reason,time,date,name,matrixnum,phone;

    public User1(){}

    public User1(String reason, String time, String date, String name, String matrixnum, String phone) {
        this.reason = reason;
        this.time = time;
        this.date = date;
        this.name = name;
        this.matrixnum = matrixnum;
        this.phone=phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatrixnum() {
        return matrixnum;
    }

    public void setMatrixnum(String matrixnum) {
        this.matrixnum = matrixnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
