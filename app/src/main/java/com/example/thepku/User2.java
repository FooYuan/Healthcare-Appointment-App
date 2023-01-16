package com.example.thepku;

public class User2 {

    String reason,time,date,name,matrixnum,cancel_reason;

    public User2(){}

    public User2(String reason, String time, String date, String name, String matrixnum, String cancel_reason) {
        this.reason = reason;
        this.time = time;
        this.date = date;
        this.name = name;
        this.matrixnum = matrixnum;
        this.cancel_reason = cancel_reason;
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

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }
}
