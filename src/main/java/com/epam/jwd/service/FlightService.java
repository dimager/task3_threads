package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class FlightService implements Runnable {
    Airport airport;
    Flight flight;
    Exchanger<Flight> flightExchanger = new Exchanger<>();
    public FlightService(Flight flight, Airport airport) {
        this.flight = flight;
        this.airport = airport;

    }

    @Override
    public void run() {
        for (Passenger passenger : flight.getAllPassengerFromFlight()) {
            if (Objects.nonNull(passenger.getNextTicket()) && isTicketValid(passenger)) {
                new PassengerThread(getTerminalSemaphore(passenger), passenger, passenger.getNextTicket().getFlight(), flightExchanger);
            }
            flight.removePassengerFromFlight(passenger);
        }
    }

    public Semaphore getTerminalSemaphore(Passenger passenger) {
        return passenger.getNextTicket().getFlight().getTerminal().getSemaphore();
    }

    private boolean isTicketValid(Passenger passenger) {
        return passenger.getNextTicket().getFlight().getFlightTime().isAfter(LocalDateTime.now());
    }
}
