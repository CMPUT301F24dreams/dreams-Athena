package com.example.athena;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.athena.Models.Facility;


public class FacilityTest {
    @Test
    public void testGetters() {
        String expectedOrgName = "New Organizer";
        String expectedFacName = "New Facility Name";
        String expectedFacLocation= "New Facility Location";
        String expectedFacID = "12345";

        Facility testFacility = new Facility();
        this.loadMockFacility(testFacility);
        Assertions.assertEquals(expectedFacName, testFacility.getFacilityName());
        Assertions.assertEquals(expectedOrgName, testFacility.getOrganizer());
        Assertions.assertEquals(expectedFacLocation, testFacility.getFacilityLocation());
        Assertions.assertEquals(expectedFacID, testFacility.getFacilityID());

    }

    @Test
    public void testSetters() {

        Facility testFacility = new Facility();

        testFacility.setOrganizer("Organizer");
        testFacility.setFacilityName("Name");
        testFacility.setFacilityLocation("New Facility Location");
        testFacility.setFacilityID("11111");

        String expectedOrgName = "Organizer";
        String expectedFacName = "Name";
        String expectedFacLocation= "New Facility Location";
        String expectedFacID = "11111";

        Assertions.assertEquals(expectedFacName, testFacility.getFacilityName());
        Assertions.assertEquals(expectedOrgName, testFacility.getOrganizer());
        Assertions.assertEquals(expectedFacLocation, testFacility.getFacilityLocation());
        Assertions.assertEquals(expectedFacID, testFacility.getFacilityID());
    }

    @Test
    public void testNotAllowNull() {

        Facility testFacility = new Facility();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setOrganizer(null);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityLocation(null);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityID(null);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityName(null);
        });

    }

    @Test
    public void EmptyParamNotAllowed() {

        Facility testFacility = new Facility();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setOrganizer("");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityLocation("");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityID("");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testFacility.setFacilityName("");
        });

    }

    public void loadMockFacility (Facility facility){
        facility.setOrganizer("New Organizer");
        facility.setFacilityName("New Facility Name");
        facility.setFacilityLocation("New Facility Location");
        facility.setFacilityID("12345");
    }
}