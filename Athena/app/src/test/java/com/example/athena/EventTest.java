package com.example.athena;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.athena.Event.Event;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

public class EventTest {

    @Test
    public void eventCreateTest() {
        String eventName = "UAlberta Pizza Party";
        String description = "Pizza party in Edmonton";
        String facilityID = "1";
        Boolean geoRequire = false;
        Integer maxParticipants = 30;
        String poster = "pizzaurl";
        String regStart = "2024-10-1";
        String regEnd = "2024-10-5";
        String eventDate = "2024-10-31";

        Event testEvent = new Event(eventName, description, facilityID, geoRequire, maxParticipants, poster, regStart, regEnd, eventDate);
        assertEquals(eventName, testEvent.getEventName());
    }
}
