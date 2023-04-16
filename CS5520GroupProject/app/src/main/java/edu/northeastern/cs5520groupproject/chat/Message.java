package edu.northeastern.cs5520groupproject.chat;

import java.util.Date;

public class Message {
    private String messageTxt;
    private String user;
    private long time;

    private String receiver;

    public Message(){}

    public Message(String messageTxt, String user) {
        this.messageTxt = messageTxt;
        this.user = user;
        time  = new Date().getTime();
    }

    public Message(String messageTxt, String user, long time) {
        this.messageTxt = messageTxt;
        this.user = user;
        this.time = time;
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

    public void setReceiver(String receiver) {
        this.user = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
