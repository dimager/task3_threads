package com.epam.jwd.service.executor;

import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PassengerExecutor extends Thread {
    private final static Logger logger = LogManager.getLogger(PassengerExecutor.class);
    private final static Logger appLogger = LogManager.getLogger("AirportOutputInfo");
    private static final String TERM_THREAD_STRING = "term. ";
    private static final String INTERRUPTION_ERROR_MESSAGE_STRING = "InterruptedException PassengerExecutor";
    private static final String GOING_NEXT_FLIGHT_STRING = " is going to next flight ";
    private static final String IS_WAITING_STRING = " is waiting ";
    private static final String PASSENGER_EXECUTOR_THREAD_START_STRING = "PassengerExecutor start";
    private static final String TICKET_EXCHANGER_START_METHOD_STRING = "ticketExchanger method start";
    private static final String WAIT_METHOD_START_STRING = "waitNextFlight method ";
    private static final String TIMEOUT_EXCEPTION_STRING = "Timeout exception. Ticket is not changed";
    private static final String EXCHANGER_STRING = "*Exchanger: ";
    private static final String WANT_TO_CHANGE_TICKET_STRING = " want to change ticket  ";
    private static final String CHANGED_TICKET_WITH_STRING = " changed ticket with ";
    private static final String FAILED_CHANGE_STRING = " change is failed, same flight with ";
    private final Passenger passenger;
    private final Semaphore semaphore;
    private final Exchanger<Passenger> ticketExchanger;
    private Flight flight;

    public PassengerExecutor(Semaphore semaphore, Passenger passenger, Exchanger<Passenger> ticketExchanger) {
        this.passenger = passenger;
        this.semaphore = semaphore;
        this.ticketExchanger = ticketExchanger;
        new Thread(this).start();
    }

    @Override
    public void run() {
        logger.debug(PASSENGER_EXECUTOR_THREAD_START_STRING);
        if (Objects.nonNull(semaphore)) {
            Thread.currentThread().setName(TERM_THREAD_STRING + passenger.getNextTicket().getFlight().getTerminal().getTerminalId());
            if (passenger.wantToChangeTicket()) {
                ticketExchanger();
            }
            flight=passenger.getNextTicket().getFlight();
            waitNextFlight();
            try {
                semaphore.acquire();
                appLogger.info(passenger.getFirstName() + " " + passenger.getLastName() + GOING_NEXT_FLIGHT_STRING + passenger.getNextTicket().getFlight().getCallsign());
                passenger.setNextTicket(null);
                flight.addPassengerToFlight(passenger);
                sleep(2000);
            } catch (InterruptedException e) {
                logger.error(INTERRUPTION_ERROR_MESSAGE_STRING + e);
                e.printStackTrace();
            } finally {
                semaphore.release();
                Thread.currentThread().interrupt();
            }
        } else {
            Thread.currentThread().interrupt();
        }
    }

    private void ticketExchanger() {
        logger.debug(TICKET_EXCHANGER_START_METHOD_STRING);
        try {
            appLogger.info(EXCHANGER_STRING + passenger.getFirstName() + " " + passenger.getLastName() + WANT_TO_CHANGE_TICKET_STRING + passenger.getNextTicket().getFlight().getCallsign());
            if (!passenger.getNextTicket().getFlight().equals(ticketExchanger.exchange(passenger,60, TimeUnit.SECONDS).getNextTicket().getFlight())) {
                Ticket ticket = passenger.getNextTicket();
                passenger.setNextTicket(ticketExchanger.exchange(passenger,60, TimeUnit.SECONDS).getNextTicket());
                ticketExchanger.exchange(passenger).setNextTicket(ticket);
                passenger.setChangeTicket(false);
                appLogger.info(EXCHANGER_STRING + passenger.getFirstName() + " " + passenger.getLastName() + CHANGED_TICKET_WITH_STRING + ticketExchanger.exchange(passenger).getFirstName() + " " + ticketExchanger.exchange(passenger).getLastName());
            } else {
                appLogger.info(EXCHANGER_STRING + passenger.getFirstName() + passenger.getLastName() + FAILED_CHANGE_STRING + ticketExchanger.exchange(passenger).getFirstName() + " " + ticketExchanger.exchange(passenger).getLastName());            }
        } catch (InterruptedException e) {
            logger.error(INTERRUPTION_ERROR_MESSAGE_STRING + e);
        } catch (TimeoutException e) {
            logger.error(TIMEOUT_EXCEPTION_STRING );
        }
    }

    private void waitNextFlight() {
        logger.debug(WAIT_METHOD_START_STRING + passenger.getFirstName() + " " + passenger.getLastName());
        while (LocalDateTime.now().isBefore(passenger.getNextTicket().getFlight().getFlightTime().minusHours(2))) {
            Thread.currentThread().setName(passenger.getFirstName() + " " + passenger.getLastName() + IS_WAITING_STRING + passenger.getNextTicket().getFlight().getCallsign());
            appLogger.info(passenger.getFirstName() + " " + passenger.getLastName() + IS_WAITING_STRING + passenger.getNextTicket().getFlight().getCallsign());
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                logger.error(INTERRUPTION_ERROR_MESSAGE_STRING + e);
            }
        }
    }
}
