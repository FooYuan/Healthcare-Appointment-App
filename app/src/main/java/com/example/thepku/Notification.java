package com.example.thepku;

public class Notification {

    String date_time,content;

    public Notification(){}

    public Notification(String date_time, String content) {
        this.date_time = date_time;
        this.content = content;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
