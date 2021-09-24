package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;

import java.time.LocalDateTime;

public class Test implements Runnable {
    Airport airport;

    public Test(Airport airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Check arrivals flight");
        while (true) {
            for (Flight flight : airport.getArrivalFlightList()) {
                if (flight.getFlightTime().isBefore(LocalDateTime.now())) {
                    System.out.println(flight.getCallsign() + " is arrived");
                    new Thread(new FlightService(flight, airport)).start();
                }
            }
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
