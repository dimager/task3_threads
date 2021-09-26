package com.epam.jwd.service.executor;

import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class PassengerExecutor extends Thread {
    private final static Logger logger = LogManager.getLogger("AirportInfo");
    private final Passenger passenger;
    private final Semaphore semaphore;
    private final Exchanger<Flight> flightExchanger;
    private Flight flight;

    public PassengerExecutor(Semaphore semaphore, Passenger passenger, Flight flight, Exchanger<Flight> flightExchanger) {
        this.passenger = passenger;
        this.semaphore = semaphore;
        this.flight = flight;
        this.flightExchanger = flightExchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (Objects.nonNull(semaphore)) {
            if (passenger.isChangeTicket()) {
           //     ticketExchanger();
            }
            hasToWaitNextFlight();
            Thread.currentThread().setName("term. " + passenger.getNextTicket().getFlight().getTerminal().getTerminalId());
            try {
                semaphore.acquire();
                logger.info(passenger.getFirstName() + " " + passenger.getLastName() + " is going to next flight " + passenger.getNextTicket().getFlight().getCallsign());
                passenger.setNextTicket(null);
                flight.addPassengerToFlight(passenger);
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Thread.currentThread().interrupt();
                semaphore.release();
            }
        } else {
            Thread.currentThread().interrupt();
        }

    }

    private void hasToWaitNextFlight() {
        while (LocalDateTime.now().isBefore(passenger.getNextTicket().getFlight().getFlightTime().minusHours(2))) {
            Thread.currentThread().setName(passenger.getFirstName() + " " + passenger.getLastName() + " is waiting " + passenger.getNextTicket().getFlight().getCallsign());
            logger.info(passenger.getFirstName() + " " + passenger.getLastName() + " is waing flight" + passenger.getNextTicket().getFlight().getCallsign());
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
