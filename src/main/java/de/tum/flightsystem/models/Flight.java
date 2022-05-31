package de.tum.flightsystem.models;

import org.springframework.data.annotation.Id;

public class Flight {
    @Id
    public String id;

    public String firstName;
    public String lastName;

    public Flight() {}

    public Flight(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Flight[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }


}