package com.epam.jwd.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Flight  {
    private final List<Passenger> passengerList = new LinkedList<>();
    private String callsign = "";
    private TerminalType terminalType;
    private String destination;
    private Terminal terminal;
    private LocalDateTime flightTime;
    private ReentrantLock reentrantLock;

    private void setFlightType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }

    private void setDestination(String destination) {
        this.destination = destination;
    }

    public void addPassengerToFlight(Passenger passenger) {
        reentrantLock.lock();
        passengerList.add(passenger);
        reentrantLock.unlock();
    }

    public List<Passenger> getAllPassengerFromFlight() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.addAll(passengerList);
        return copyOnWriteArrayList;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void removePassengerFromFlight(Passenger passenger) {
        reentrantLock.lock();
        passengerList.remove(passenger);
        reentrantLock.unlock();
    }

    public String getCallsign() {
        return callsign;
    }

    private void setCallsign(String callsign) {
        Flight.this.callsign = callsign;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    private void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public LocalDateTime getFlightTime() {
        return flightTime;
    }

    private void setFlightTime(LocalDateTime flightTime) {
        this.flightTime = flightTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "callsign='" + callsign + '\'' +
                ", flightType= " + terminalType +
                ", destination='" + destination + '\'' +
                ", flightTime=" + flightTime +
                ", passengerList=" + passengerList;
    }

    public static class Builder {
        private String callsign = "";
        private TerminalType terminalType;
        private String destination;
        private Terminal terminal;
        private LocalDateTime flightTime;

        public Builder() {
        }

        public Builder setCallsign(String callsign) {
            this.callsign = callsign;
            return this;
        }

        public Builder setTerminal(Terminal terminal) {
            this.terminal = terminal;
            return this;

        }

        public Builder setFlightTime(LocalDateTime flightTime) {
            this.flightTime = flightTime;
            return this;
        }

        public Builder setFlightType(TerminalType terminalType) {
            this.terminalType = terminalType;
            return this;
        }

        public Builder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Flight build() {
            Flight flight = new Flight();
            flight.reentrantLock = new ReentrantLock();
            flight.setCallsign(callsign);
            flight.setFlightTime(flightTime);
            flight.setTerminal(terminal);
            flight.setDestination(destination);
            flight.setFlightType(terminalType);
            return flight;

        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (!passengerList.equals(flight.passengerList)) return false;
        if (!callsign.equals(flight.callsign)) return false;
        if (terminalType != flight.terminalType) return false;
        if (!destination.equals(flight.destination)) return false;
        if (!terminal.equals(flight.terminal)) return false;
        if (!flightTime.equals(flight.flightTime)) return false;
        return reentrantLock.equals(flight.reentrantLock);
    }

    @Override
    public int hashCode() {
        int result = passengerList.hashCode();
        result = 31 * result + callsign.hashCode();
        result = 31 * result + terminalType.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + terminal.hashCode();
        result = 31 * result + flightTime.hashCode();
        result = 31 * result + reentrantLock.hashCode();
        return result;
    }
}
