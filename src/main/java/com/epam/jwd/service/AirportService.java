package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;

public class AirportService {
    public static void printDepartureTable(Airport airport) {
        System.out.format("%8s%10s%12s%10s%12s\n", "Flight", "Terminal", "Date", "Time", "Passengers");
        airport.getDepartureFlightList().stream()
                .forEach(flight -> System.out.format("%8s%10s%12s%10s\n", flight.getCallsign(), flight.getTerminal().getTerminalId(), flight.getFlightTime().toLocalDate(), flight.getFlightTime().toLocalTime(),flight.getPassengerList().size()));
    }

    public static void printArrivalTable(Airport airport) {
        System.out.format("%8s%10s%12s%10s%12s\n", "Flight", "Terminal", "Date", "Time", "Passengers");
        airport.getArrivalFlightList().stream()
                .forEach(flight -> System.out.format("%8s%10s%12s%10s\n", flight.getCallsign(), flight.getTerminal().getTerminalId(), flight.getFlightTime().toLocalDate(), flight.getFlightTime().toLocalTime(),flight.getPassengerList().size()));
    }

    public static void printFlightPassenger(Flight flight){
        flight.getPassengerList().stream().forEach(System.out::println);
    }

    public static void printArrPassangerList (Airport airport){
        for (Flight flight : airport.getArrivalFlightList()) {
            System.out.println(flight.getCallsign());
            flight.getPassengerList().stream().forEach(System.out::println);
        }
    }

    public static void printDepPassangerList (Airport airport){
        airport.getDepartureFlightList().stream().forEach(flight -> printFlightPassenger(flight));
    }

    public static Flight getFlightByCallsign(Airport airport, String callsing) {
        for (Flight flight : airport.getDepartureFlightList()) {
            if (flight.getCallsign().equals(callsing)) {
                return flight;
            }
        }
        return null;
    }
}
