package com.epam.jwd.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Flight {
    private String callsign = new String();
    private FlightType flightType;
    private String destination;
    private Terminal terminal;
    private LocalDateTime flightTime;
    private List<Passenger> passengerList = new CopyOnWriteArrayList<>();

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public LocalDateTime getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(LocalDateTime flightTime) {
        this.flightTime = flightTime;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    @Override
    public String toString() {
        return  "Flight{" +
                "callsign='" + callsign + '\'' +
                ", flightType= " + flightType +
                ", destination='" + destination + '\'' +
                ", flightTime=" + flightTime +
                ", passengerList=" + passengerList;
    }
}
