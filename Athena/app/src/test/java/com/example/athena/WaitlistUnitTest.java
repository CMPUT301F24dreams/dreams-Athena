package com.example.athena;

import android.util.Log;

import com.example.athena.Models.Event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WaitlistUnitTest {
    @Test
    public void waitlistCheckSetGet() {
        Event event = new Event();
        ArrayList<String> list = new ArrayList<>();
        this.loadMockEvent(event);
        //loads empty
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getWaiting().toArray());
        //sets list
        this.loadMockList(list);
        event.getWaitList().setWaiting(list);
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getWaiting().toArray());
        list.clear();


        //loads empty
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getDeclined().toArray());
        //sets list
        this.loadMockList(list);
        event.getWaitList().setDeclined(list);
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getDeclined().toArray());
        list.clear();


        //loads empty
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getAccepted().toArray());
        //sets list
        this.loadMockList(list);
        event.getWaitList().setAccepted(list);
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getAccepted().toArray());
        list.clear();

        //loads empty
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getInvited().toArray());
        //sets list
        this.loadMockList(list);
        event.getWaitList().setInvited(list);
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getInvited().toArray());
        list.clear();
    }

    @Test
    public void waitlistCheckAdd(){
        Event event = new Event();
        ArrayList<String> list = new ArrayList<>();
        this.loadMockEvent(event);
        this.loadMockList(list);

        event.getWaitList().addWaiting("12391",event.getEventID());
        event.getWaitList().addWaiting("11290587",event.getEventID());
        event.getWaitList().addWaiting("15217",event.getEventID());
        event.getWaitList().addWaiting("1231",event.getEventID());
        event.getWaitList().addWaiting("1213",event.getEventID());
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getWaiting().toArray());

        event.getWaitList().addDecline("12391",event.getEventID());
        event.getWaitList().addDecline("11290587",event.getEventID());
        event.getWaitList().addDecline("15217",event.getEventID());
        event.getWaitList().addDecline("1231",event.getEventID());
        event.getWaitList().addDecline("1213",event.getEventID());
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getDeclined().toArray());

        event.getWaitList().addAccept("12391",event.getEventID());
        event.getWaitList().addAccept("11290587",event.getEventID());
        event.getWaitList().addAccept("15217",event.getEventID());
        event.getWaitList().addAccept("1231",event.getEventID());
        event.getWaitList().addAccept("1213",event.getEventID());
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getAccepted().toArray());

        event.getWaitList().addInvited("12391",event.getEventID());
        event.getWaitList().addInvited("11290587",event.getEventID());
        event.getWaitList().addInvited("15217",event.getEventID());
        event.getWaitList().addInvited("1231",event.getEventID());
        event.getWaitList().addInvited("1213",event.getEventID());
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getInvited().toArray());
    }


    @Test
    public void waitlistCheckMove(){
        Event event = new Event();
        ArrayList<String> list = new ArrayList<>();
        this.loadMockEvent(event);
        list.add("1231");

        //checks it moves a user to accepted
        event.getWaitList().addInvited("1231", event.getEventID());
        event.getWaitList().moveUserFromInvited("1231","accepted");
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getAccepted().toArray());

        //checks it moves a user to declined
        event.getWaitList().addInvited("1231", event.getEventID());
        event.getWaitList().moveUserFromInvited("1231","declined");
        Assertions.assertArrayEquals(list.toArray(),event.getWaitList().getDeclined().toArray());
    }

    @Test
    public void waitlistCheckSelectUser(){
        Event event = new Event();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> expect = new ArrayList<>();
        this.loadMockEvent(event);
        this.loadMockList(list);
        this.loadMockList(expect);

        //selects only three users
        loadMockList(event.getWaitList().getWaiting());
        event.chooseUsers(3,event.getEventID());

        Assertions.assertEquals(3,event.getWaitList().getInvited().size());
        event.getWaitList().getInvited().clear();
        event.getWaitList().getWaiting().clear();


        //selects all users
        loadMockList(event.getWaitList().getWaiting());
        event.chooseUsers(20,event.getEventID());
        Assertions.assertEquals(expect.size(),event.getWaitList().getInvited().size());
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

    public void loadMockList(ArrayList<String> list){
        list.add("12391");
        list.add("11290587");
        list.add("15217");
        list.add("1231");
        list.add("1213");
    }



}
