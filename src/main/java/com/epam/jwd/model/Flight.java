package com.epam.jwd.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Flight {
    private final List<Passenger> passengerList = new LinkedList<>();
    private String callsign = "";
    private FlightType flightType;
    private String destination;
    private Terminal terminal;
    private LocalDateTime flightTime;
    private ReentrantLock reentrantLock;

    private Flight() {
        reentrantLock = new ReentrantLock();
    }

    public void addPassengerToFlight(Passenger passenger) {
        reentrantLock.lock();
        passengerList.add(passenger);
        reentrantLock.unlock();
    }

    public void addAllPassengersToFlight(List<Passenger> passengerList) {
        reentrantLock.lock();
        passengerList.addAll(passengerList);
        reentrantLock.unlock();
    }

    public List<Passenger> getAllPassengerFromFlight() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.addAll(passengerList);
        return copyOnWriteArrayList;
    }

    public void removePassengerFromFlight(Passenger passenger) {
        reentrantLock.lock();
        passengerList.remove(passenger);
        reentrantLock.unlock();
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        Flight.this.callsign = callsign;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public LocalDateTime getFlightTime() {
        return flightTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "callsign='" + callsign + '\'' +
                ", flightType= " + flightType +
                ", destination='" + destination + '\'' +
                ", flightTime=" + flightTime +
                ", passengerList=" + passengerList;
    }

    public static class Builder {
        private String callsign = "";
        private FlightType flightType;
        private String destination;
        private Terminal terminal;
        private LocalDateTime flightTime;

        public Builder() {
        }
        public void setCallsign(String callsign) {
            this.callsign = callsign;
        }
        public void setTerminal(Terminal terminal) {
            this.terminal = terminal;
        }

        public void setFlightTime(LocalDateTime flightTime) {
            this.flightTime = flightTime;
        }

        public void setFlightType(FlightType flightType) {
            this.flightType = flightType;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Flight build(){
            Flight flight = new Flight();
            flight.setCallsign(callsign);
        }
    }
}
