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

    public boolean wantToChangeTicket() {
        return changeTicket;
    }

    public void setChangeTicket(boolean changeTicket) {
        this.changeTicket = changeTicket;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Passenger: " + firstName + " " +
                lastName + " " +
                "(Next Fligt = " + getCallsign() + " "+
                "Change Ticket = " + changeTicket+")";
    }

    private String getCallsign() {
        return nextTicket == null ? "No" : nextTicket.getFlight().getCallsign();
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
