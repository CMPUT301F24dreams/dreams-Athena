package com.example.athena.Models;


/**
 * The {@code Facility} class represents a facility in the application.
 * It encapsulates the details of the facility, such as its name, and location
 * This class is responsible for managing the operations of the facility
 *
 */
public class Facility {
    private String Name;
    private String Location;
    private String organizer;

    /**
     *This is a constructor used to create a facility without an imageURL, but including all of the other respective fields
     *
     */
    public Facility(String Name, String Location, String organizer){
        this.Name = Name;
        this.Location = Location;
        this.organizer = organizer;

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
    public void setOrganizer(String organizer){
        this.organizer = organizer;
    }
    /**
     * getter for the facility name
     * @return the facility name
     */
    public String getFacilityName(){
        return Name;
    }
    /**
     * setter for the facility organizer
     */
    public void setFacilityName(String facilityName){
        this.Name = facilityName;
    }
    /**
     * getter for the facility location
     * @return the facility location
     */
    public String getFacilityLocation(){
     return Location;
    }
    /**
     * setter for the facility location
     */
    public void setFacilityLocation(String location){
        this.Location = location;
    }



}

