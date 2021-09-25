package com.epam.jwd.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Flight {
    private String callsign = "";
    private FlightType flightType;
    private String destination;
    private Terminal terminal;
    private LocalDateTime flightTime;
    private final List<Passenger> passengerList = new ArrayList<>();
    private ReentrantLock reentrantLock;
    private Condition condition;


    public Flight(String callsign, FlightType flightType, String destination, Terminal terminal, LocalDateTime flightTime) {
        this.callsign = callsign;
        this.flightType = flightType;
        this.destination = destination;
        this.terminal = terminal;
        this.flightTime = flightTime;
        reentrantLock = new ReentrantLock();
        condition = reentrantLock.newCondition();
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

    public List<Passenger> getAllPassengerFromFlight (){
        return passengerList;
        /*reentrantLock.lock();
        List unmodifiablePassengerList = Collections.unmodifiableList(passengerList);
        reentrantLock.unlock();
        return unmodifiablePassengerList;*/
    }

    public void removePassengerFromFlight(Passenger passenger){
        reentrantLock.lock();
        passengerList.remove(passenger);
        reentrantLock.unlock();
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

    public void setFlightTime(LocalDateTime flightDateTime) {
        this.flightTime = flightDateTime;
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
}
