package com.rpll.okeoke.bettingplatform.Model;

import java.util.Date;

/**
 * Created by Kevin on 3/29/2018.
 */

public class TopUp {
    String id_topup, username, status;
    int value;
    Date date;
    User user;

    public TopUp() {

    }

    public TopUp(String id_topup, String username, String status, int value, Date date, User user) {
        this.id_topup = id_topup;
        this.username = username;
        this.status = status;
        this.value = value;
        this.date = date;
        this.user = user;
    }

    public String getId_topup() {
        return id_topup;
    }

    public void setId_topup(String id_topup) {
        this.id_topup = id_topup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
