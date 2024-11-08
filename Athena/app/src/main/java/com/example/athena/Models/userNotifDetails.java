package com.example.athena.Models;

public class userNotifDetails {
    private String userName;
    private boolean chosenNotif;
    private boolean notChosenNotif;

    public userNotifDetails(String userName, boolean chosenNotif, boolean notChosenNotif) {
        this.userName = userName;
        this.chosenNotif = chosenNotif;
        this.notChosenNotif = notChosenNotif;
    }

    public userNotifDetails() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isChosenNotif() {
        return chosenNotif;
    }

    public void setChosenNotif(boolean chosenNotif) {
        this.chosenNotif = chosenNotif;
    }

    public boolean isNotChosenNotif() {
        return notChosenNotif;
    }

    public void setNotChosenNotif(boolean notChosenNotif) {
        this.notChosenNotif = notChosenNotif;
    }
}
