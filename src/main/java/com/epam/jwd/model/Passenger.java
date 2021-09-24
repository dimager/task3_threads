package com.epam.jwd.model;


public class Passenger {
    private String firstName;
    private String lastName;
    private Ticket nextTicket;

    public Passenger(String firstName, String lastName, Ticket nextTicket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nextTicket = nextTicket;
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
        return "\nPassenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nextTicket=" + nextTicket +
                "}";
    }


    public Ticket getNextTicket() {
        return nextTicket;
    }

    public void setNextTicket(Ticket nextTicket) {
        this.nextTicket = nextTicket;
    }

}
