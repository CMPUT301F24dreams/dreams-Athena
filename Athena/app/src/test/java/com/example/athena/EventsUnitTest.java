package com.example.athena;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.athena.Models.Event;

public class EventsUnitTest {
    @Test
    public void eventCheckTest() {
        Event event = new Event();
        loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setEventName("");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setEventDescription("");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setFacility("");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setMaxParticipants(0);
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setEventDate("");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setStartReg("");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkEvent());
        event.setEndReg("");
        Assertions.assertFalse(event.checkEvent());
    }

    @Test
    public void dateCheckTest() {
        Event event = new Event();

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkDate(event.getEventDate(), event.getStartReg(), event.getEndReg()));

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkDate(event.getEventDate(), event.getStartReg(), event.getEndReg()));
        event.setEventDate("11/23/24");
        event.setStartReg("11/24/24");
        event.setEndReg("11/25/24");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkDate(event.getEventDate(), event.getStartReg(), event.getEndReg()));
        event.setEventDate("11/24/24");
        event.setStartReg("11/23/24");
        event.setEndReg("11/25/24");
        Assertions.assertFalse(event.checkEvent());

        this.loadMockEvent(event);
        Assertions.assertTrue(event.checkDate(event.getEventDate(), event.getStartReg(), event.getEndReg()));
        event.setEventDate("11/31/24");
        event.setStartReg("11/26/24");
        event.setEndReg("11/25/24");
        Assertions.assertFalse(event.checkEvent());
    }

    public void loadMockEvent(Event event) {
        event.setOrganizer("0");
        event.setFacility("exampleFacility");
        event.setEventID("exampleEventID");
        event.setGeoRequire(false);
        event.setEventDate("11/31/24");
        event.setEventName("eventName");
        event.setEventDescription("exampleDescription");
        event.setMaxParticipants(30);
        event.setStartReg("11/24/24");
        event.setEndReg("11/25/24");
    }
}