package com.rpll.okeoke.bettingplatform.Model;

/**
 * Created by Kevin on 4/24/2018.
 */

public class Betting {
    int bet_value, selected_team;
    boolean rewardCollected;

    public boolean isRewardCollected() {
        return rewardCollected;
    }

    public void setRewardCollected(boolean rewardCollected) {
        this.rewardCollected = rewardCollected;
    }

    public Betting(int bet_value, int selected_team, boolean rewardCollected) {
        this.bet_value = bet_value;
        this.selected_team = selected_team;
        this.rewardCollected = rewardCollected;
    }

    public int getBet_value() {
        return bet_value;
    }

    public void setBet_value(int bet_value) {
        this.bet_value = bet_value;
    }

    public int getSelected_team() {
        return selected_team;
    }

    public void setSelected_team(int selected_team) {
        this.selected_team = selected_team;
    }
}
