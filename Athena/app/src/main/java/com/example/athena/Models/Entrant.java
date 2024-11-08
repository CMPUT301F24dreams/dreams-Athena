package com.example.athena.Models;

public class Entrant {
    private String deviceId;
    private String status;
    private boolean notified;

    public Entrant(String deviceId, String status, boolean notified) {
        this.deviceId = deviceId;
        this.status = status;
        this.notified = notified;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
