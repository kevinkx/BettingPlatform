package com.rpll.okeoke.bettingplatform.Model;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Livechat {
    String email, chat, username;
    boolean myChat;
    String date;

    public boolean isMyChat() {
        return myChat;
    }

    public void setMyChat(boolean myChat) {
        this.myChat = myChat;
    }

    public Livechat() {
    }

    public Livechat(String username, String email, String chat, String date) {
        this.email = email;
        this.chat = chat;
        this.username = username;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
