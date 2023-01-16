package com.example.thepku;

public class User {
    String reason,time,date;

    public User(){}

    public User(String reason, String time, String date) {
        this.reason = reason;
        this.time = time;
        this.date = date;
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
}
