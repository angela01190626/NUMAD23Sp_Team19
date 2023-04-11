package edu.northeastern.cs5520groupproject.chat;

import java.util.Date;

public class Message {
    private String messageTxt;
    private String user;
    private long time;

    public Message(){

    }
    public Message(String messageTxt, String user) {
        this.messageTxt = messageTxt;
        this.user = user;
        time  = new Date().getTime();
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}