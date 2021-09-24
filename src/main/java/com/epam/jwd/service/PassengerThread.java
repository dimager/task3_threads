package com.epam.jwd.service;

import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PassengerThread extends Thread {

    private final Passenger passenger;
    private final Semaphore semaphore;
    private final Exchanger<Flight> flightExchanger;
    private Flight flight;

    public PassengerThread(Semaphore semaphore, Passenger passenger, Flight flight, Exchanger<Flight> flightExchanger) {
        this.passenger = passenger;
        this.semaphore = semaphore;
        this.flight = flight;
        this.flightExchanger = flightExchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (Objects.isNull(semaphore)) {
            Thread.currentThread().interrupt();
        }

        if (passenger.getFirstName().equals("Oliver")) {
            System.out.println("change");
            ticketExchanger();
        }

        waiting();
        Thread.currentThread().setName("term. " + passenger.getNextTicket().getFlight().getTerminal().getTerminalId());
        try {
            semaphore.acquire();
            System.out.println(passenger.getFirstName() + " " + passenger.getLastName() + " is going to next flight " + passenger.getNextTicket().getFlight().getCallsign());
            passenger.setNextTicket(null);
            flight.getPassengerList().add(passenger);
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
            semaphore.release();
        }
    }


    private void waiting() {
        while (LocalDateTime.now().isBefore(passenger.getNextTicket().getFlight().getFlightTime().minusHours(2))) {
            Thread.currentThread().setName("wait " + passenger.getNextTicket().getFlight().getCallsign());
            System.out.println(passenger.getFirstName() + " " + passenger.getLastName() + " is waing flight" + passenger.getNextTicket().getFlight().getCallsign());
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ticketExchanger() {
        try {

            Thread.currentThread().setName("exchenger " + flight.getCallsign());
            this.flight = flightExchanger.exchange(flight, 90, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
