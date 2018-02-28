package com.georgebrown.comp3074.a2100984638;

/**
 * Created by Owner on 11/5/2017.
 */

public class Doctor {
    private String id;
    private String firstName;
    private String lastName;
    private String department;

    public Doctor(){}

    public Doctor(String id, String firstName, String lastName, String department){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }
}
