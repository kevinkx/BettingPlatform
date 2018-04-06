package com.rpll.okeoke.bettingplatform.Model;

/**
 * Created by Kevin on 3/29/2018.
 */

public class User {
    String email, fullname, password;
    int point;

    public User() {
    }

    public User(String email, String fullname, String password, int point) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.point = point;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
