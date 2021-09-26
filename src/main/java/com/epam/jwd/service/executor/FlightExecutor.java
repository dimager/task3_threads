package com.epam.jwd.service.executor;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import com.epam.jwd.service.creator.AirportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class FlightExecutor implements Runnable {
    private final static Logger logger = LogManager.getLogger(FlightExecutor.class);
    private Flight flight;
    private Exchanger<Flight> flightExchanger = new Exchanger<>();

    public FlightExecutor(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(flight.getCallsign() + "FlightService");
        for (Passenger passenger : flight.getAllPassengerFromFlight()) {
            if (Objects.nonNull(passenger.getNextTicket())) {
                if (isTicketValid(passenger)) {
                    new PassengerExecutor(getTerminalSemaphore(passenger), passenger, passenger.getNextTicket().getFlight(), flightExchanger);
                } else {
                    System.out.println("Going to town: " + passenger.getFirstName() + " " + passenger.getLastName() + " " + passenger.getNextTicket().getFlight().getCallsign() + " has been departed");
                }
            } else {
                System.out.println("Going to town: " + passenger.getFirstName() + " " + passenger.getLastName() + " doesn't have next ticket");
            }
            flight.removePassengerFromFlight(passenger);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Semaphore getTerminalSemaphore(Passenger passenger) {
        return passenger.getNextTicket().getFlight().getTerminal().getSemaphore();
    }

    private boolean isTicketValid(Passenger passenger) {
        return passenger.getNextTicket().getFlight().getFlightTime().isAfter(LocalDateTime.now());
    }
}
