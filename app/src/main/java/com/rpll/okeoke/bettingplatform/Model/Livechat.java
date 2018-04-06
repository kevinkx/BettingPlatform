package com.rpll.okeoke.bettingplatform.Model;

import java.util.Date;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Livechat {
    String username, chat;
    Date date;
    User user;

    public Livechat() {
    }

    public Livechat(String username, String chat, Date date, User user) {
        this.username = username;
        this.chat = chat;
        this.date = date;
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
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
