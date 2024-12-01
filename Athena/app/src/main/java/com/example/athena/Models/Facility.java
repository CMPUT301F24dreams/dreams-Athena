package com.example.athena.Models;


/**
 * The {@code Facility} class represents a facility in the application.
 * It encapsulates the details of the facility, such as its name, and location
 * This class is responsible for managing the operations of the facility
 *
 */
public class Facility {
    private String name;
    private String location;
    private String organizer;
    private String facilityID;

    public Facility(){

    }

    /**
     *This is a constructor used to create a facility.
     *It is used when the ID is not necessary
     */
    public Facility(String Name, String Location, String organizer){
        this.name = Name;
        this.location = Location;
        this.organizer = organizer;

    }
    /**
     *This is a constructor used to create a facility with and ID
     *
     */
    public Facility(String Name, String Location, String organizer, String ID){
        this.name = Name;
        this.location = Location;
        this.organizer = organizer;
        this.facilityID = ID;

    }


    /**
     * getter for the facility organizer
     * @return the organizer who owns this facility
     */
    public String getOrganizer(){
        return organizer;
    }
    /**
     * setter for the facility organizer
     */
    public void setOrganizer(String organizer) {
        if (organizer == null || !(organizer instanceof String) || organizer.isEmpty()) {
            throw new IllegalArgumentException("Value must be a non-null, non-empty string");
        } else {
            this.organizer = organizer;
        }
    }
    /**
     * getter for the facility name
     * @return the facility name
     */
    public String getFacilityName(){
        return name;
    }
    /**
     * setter for the facility organizer
     */
    public void setFacilityName(String facilityName){
        if (facilityName == null || facilityName.isEmpty()) {
            throw new IllegalArgumentException("Value must be a non-null, non-empty string");
        }else {
            this.name = facilityName;
        }
    }
    /**
     * getter for the facility location
     * @return the facility location
     */
    public String getFacilityLocation(){
     return location;
    }
    /**
     * setter for the facility location
     */
    public void setFacilityLocation(String location){
        if (location == null || location.isEmpty() || !(location instanceof String))  {
            throw new IllegalArgumentException("Value must be a non-null, non-empty string");
        }else {
            this.location = location;
        }

    }

    /**
     * getter for the facility ID
     * @return the facility ID
     */
    public String getFacilityID(){
        return facilityID;
    }
    /**
     * setter for the facility ID
     */
    public void setFacilityID(String facilityID){
        if (facilityID == null || facilityID.isEmpty())  {
            throw new IllegalArgumentException("Value must be a non-null, non-empty string");
        }else {
            this.facilityID = facilityID;
        }
    }



}

