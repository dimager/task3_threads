package com.epam.jwd.model;


public class Ticket {
    private Flight flight;

    public Ticket(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
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
        return flight.equals(ticket.flight);
    }

    @Override
    public int hashCode() {
        return flight.hashCode();
    }
}
