package com.epam.jwd.model;


public class Ticket {
    private Flight flight;

    public Ticket(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                ", flight=" + flight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return flight != null ? flight.equals(ticket.flight) : ticket.flight == null;
    }

    @Override
    public int hashCode() {
        return flight != null ? flight.hashCode() : 0;
    }
}
