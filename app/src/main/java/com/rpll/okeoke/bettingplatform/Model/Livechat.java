package com.rpll.okeoke.bettingplatform.Model;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Livechat {
    String username, chat;
    String date;

    public Livechat() {
    }

    public Livechat(String username, String chat, String date) {
        this.username = username;
        this.chat = chat;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
