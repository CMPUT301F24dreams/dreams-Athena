package com.example.athena.Models;

public class Notification {
    private String bodyText;
    private String title;
    private String iconID;
    private String activityLink;

    public Notification(String bodyText, String title, String activityLink) {
        this.bodyText = bodyText;
        this.title = title;
        this.activityLink = activityLink;
        // icon id will presumably be the same for all notifications
        // so i will update it here once we know what it is
        this.iconID = "PLACEHOLDER";
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconID() {
        return iconID;
    }

    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

    public String getActivityLink() {
        return activityLink;
    }

    public void setActivityLink(String activityLink) {
        this.activityLink = activityLink;
    }
}
