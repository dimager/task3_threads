package com.epam.jwd.model;


public class Passenger {
    private String firstName;
    private String lastName;
    private Ticket nextTicket;
    private boolean changeTicket;

    public Passenger(String firstName, String lastName, Ticket nextTicket, boolean changeTicket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nextTicket = nextTicket;
        this.changeTicket = changeTicket;
    }

    public boolean isChangeTicket() {
        return changeTicket;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (changeTicket != passenger.changeTicket) return false;
        if (!firstName.equals(passenger.firstName)) return false;
        if (!lastName.equals(passenger.lastName)) return false;
        return nextTicket != null ? nextTicket.equals(passenger.nextTicket) : passenger.nextTicket == null;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + (nextTicket != null ? nextTicket.hashCode() : 0);
        result = 31 * result + (changeTicket ? 1 : 0);
        return result;
    }
}
