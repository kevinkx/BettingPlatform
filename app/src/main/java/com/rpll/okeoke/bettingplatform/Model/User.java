package com.rpll.okeoke.bettingplatform.Model;

/**
 * Created by Kevin on 3/29/2018.
 */

public class User {
    String username, fullname, password;
    int point;

    public User() {
    }

    public User(String username, String fullname, String password, int point) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.point = point;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
