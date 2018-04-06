package com.rpll.okeoke.bettingplatform.Model;

import java.util.Date;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Broadcast {
    String id_broadcast, id_match, message;
    Date date;
    Match match;

    public Broadcast() {
    }

    public Broadcast(String id_broadcast, String id_match, String message, Date date, Match match) {
        this.id_broadcast = id_broadcast;
        this.id_match = id_match;
        this.message = message;
        this.date = date;
        this.match = match;
    }

    public String getId_broadcast() {
        return id_broadcast;
    }

    public void setId_broadcast(String id_broadcast) {
        this.id_broadcast = id_broadcast;
    }

    public String getId_match() {
        return id_match;
    }

    public void setId_match(String id_match) {
        this.id_match = id_match;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
