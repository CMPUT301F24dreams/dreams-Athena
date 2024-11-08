package com.example.athena.Models;

/**
 * Represents an Entrant in the application, containing details about the entrant's device ID,
 * status, and whether they have been notified.
 * This class is used for managing entrant-related data, particularly in relation to event participation.
 */
public class Entrant {

    private String deviceId;
    private String status;
    private boolean notified;

    /**
     * Constructor to initialize an Entrant object.
     *
     * @param deviceId The unique identifier for the entrant's device.
     * @param status The current status of the entrant (e.g., "invited", "waiting").
     * @param notified A boolean indicating whether the entrant has been notified about the event.
     */
    public Entrant(String deviceId, String status, boolean notified) {
        this.deviceId = deviceId;
        this.status = status;
        this.notified = notified;
    }

    /**
     * Gets the device ID of the entrant.
     *
     * @return The device ID of the entrant.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Gets the current status of the entrant.
     *
     * @return The status of the entrant.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the entrant.
     *
     * @param status The new status of the entrant.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the entrant has been notified.
     *
     * @return A boolean indicating whether the entrant has been notified.
     */
    public boolean isNotified() {
        return notified;
    }

    /**
     * Sets the notification status for the entrant.
     *
     * @param notified A boolean indicating whether the entrant has been notified.
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}
