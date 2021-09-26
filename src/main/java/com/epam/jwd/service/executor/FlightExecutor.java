package com.epam.jwd.service.executor;

import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class FlightExecutor implements Runnable {
    private final static Logger logger = LogManager.getLogger(FlightExecutor.class);
    private final static Logger appLogger = LogManager.getLogger("AirportOutputInfo");
    private static final String INTERRUPTION_ERROR_MESSAGE_STRING = "InterruptedException FlightExecutor";
    private static final String GOING_TO_TOWN_STRING = "Going to town: ";
    private static final String FLIGHT_DEPARTED_STRING = " has been departed";
    private static final String DOESNT_HAS_NEXT_TICKET_STRING = " doesn't have next ticket";
    private static final String FLIGHT_EXECUTOR_THREAD_START_STRING = "FlightExecutor thread start";
    private static final String THREAD_NAME_STRING = "FlightService for flight ";
    private static final String TICKET_TIME_CHECK_STRING = "isTicketValid method for ";
    private Flight flight;
    private Exchanger<Passenger> ticketExchanger = new Exchanger<>();

    public FlightExecutor(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void run() {
        logger.debug(FLIGHT_EXECUTOR_THREAD_START_STRING );
        Thread.currentThread().setName(THREAD_NAME_STRING + flight.getCallsign());
        for (Passenger passenger : flight.getAllPassengerFromFlight()) {
            if (Objects.nonNull(passenger.getNextTicket())) {
                if (isTicketValid(passenger)) {
                    new PassengerExecutor(getTerminalSemaphore(passenger), passenger, ticketExchanger);
                } else {
                    appLogger.info(GOING_TO_TOWN_STRING + passenger.getFirstName() + " " + passenger.getLastName() + " " + passenger.getNextTicket().getFlight().getCallsign() + FLIGHT_DEPARTED_STRING);
                }
            } else {
                appLogger.info(GOING_TO_TOWN_STRING + passenger.getFirstName() + " " + passenger.getLastName() + DOESNT_HAS_NEXT_TICKET_STRING);
            }
            flight.removePassengerFromFlight(passenger);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(INTERRUPTION_ERROR_MESSAGE_STRING);
                e.printStackTrace();
            }
        }
    }

    private Semaphore getTerminalSemaphore(Passenger passenger) {
        logger.debug(FLIGHT_EXECUTOR_THREAD_START_STRING );
        return passenger.getNextTicket().getFlight().getTerminal().getSemaphore();
    }

    private boolean isTicketValid(Passenger passenger) {
        logger.debug(TICKET_TIME_CHECK_STRING + passenger.getFirstName() + " " + passenger.getLastName());
        return passenger.getNextTicket().getFlight().getFlightTime().isAfter(LocalDateTime.now());
    }
}
