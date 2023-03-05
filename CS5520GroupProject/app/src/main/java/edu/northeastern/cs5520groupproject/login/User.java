package edu.northeastern.cs5520groupproject.login;

import java.util.Date;

public class User {
    private String userName;
    //private Date date;

    public String token;

    public User(){};

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName,  String token) {
        this.userName = userName;

        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
