package com.rpll.okeoke.bettingplatform.Model;

import java.util.Date;

/**
 * Created by Kevin on 3/29/2018.
 */

public class Match {
    String id_match, team_1, team_2;
    Date date;

    public Match() {
    }

    public Match(String id_match, String team_1, String team_2, Date date) {
        this.id_match = id_match;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.date = date;
    }

    public String getId_match() {
        return id_match;
    }

    public void setId_match(String id_match) {
        this.id_match = id_match;
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
