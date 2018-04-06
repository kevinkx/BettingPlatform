package com.rpll.okeoke.bettingplatform.Model;

import java.util.Date;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Betting {
    String id_match, username;
    int select_team, bet_value;
    Date date;
    Match match;
    User user;
    public Betting() {
    }

    public Betting(String id_match, String username, int select_team, int bet_value, Date date, Match match, User user) {
        this.id_match = id_match;
        this.username = username;
        this.select_team = select_team;
        this.bet_value = bet_value;
        this.date = date;
        this.match = match;
        this.user = user;
    }

    public String getId_match() {
        return id_match;
    }

    public void setId_match(String id_match) {
        this.id_match = id_match;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSelect_team() {
        return select_team;
    }

    public void setSelect_team(int select_team) {
        this.select_team = select_team;
    }

    public int getBet_value() {
        return bet_value;
    }

    public void setBet_value(int bet_value) {
        this.bet_value = bet_value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
