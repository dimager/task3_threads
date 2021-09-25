package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;

import java.time.LocalDateTime;

public class CheckArrivalsThread implements Runnable {
    Airport airport;
    Boolean aBoolean;

    public CheckArrivalsThread(Airport airport, Boolean aBoolean) {
        this.airport = airport;
        this.aBoolean = aBoolean;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Check arrivals flight");
        while (aBoolean) {
            try {
                for (Flight flight : airport.getArrivalFlightList()) {
                    if (flight.getFlightTime().isBefore(LocalDateTime.now())) {
                        System.out.println(flight.getCallsign() + " is arrived");
                        new Thread(new FlightService(flight, airport)).start();
                        airport.getArrivalFlightList().remove(flight);
                    }
                }
                airport.getDepartureFlightList().removeIf(flight -> flight.getFlightTime().isBefore(LocalDateTime.now()));
                AirportService.printDepartureTable(airport);
                AirportService.printArrivalTable(airport);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }

        }

    }
}




