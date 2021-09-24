package com.epam.jwd.model;

import java.time.LocalDate;

public class Ticket {
    private TicketType ticketType;
    private Flight flight;


    public Ticket(Flight flight) {
        this.ticketType = TicketType.ECONOMY;
        this.flight = flight;
    }

    public Ticket(TicketType ticketType, Flight flight) {
        this.ticketType = ticketType;
        this.flight = flight;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
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
                "ticketType=" + ticketType +
                ", flight=" + flight +
                '}';
    }
}
