package com.epam.jwd.service.executor;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.TerminalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class CheckTableExecutor implements Runnable {
    private final static Logger logger = LogManager.getLogger(CheckTableExecutor.class);
    private final static Logger appLogger = LogManager.getLogger("AirportOutputInfo");
    private static final String THEAD_NAME_STRING = "CheckTableThread";
    private static final String IS_ARRIVED_STRING = " is arrived";
    private static final String INTERRUPTION_ERROR_STRING = "InterruptedException CheckTableExecutor";
    private final Airport airport;
    private final int timeoutSeconds;


    public CheckTableExecutor(Airport airport, int timeoutSeconds) {
        this.airport = airport;
        this.timeoutSeconds = timeoutSeconds * 1000;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(THEAD_NAME_STRING);
        boolean state = true;
        while (state) {
            try {
                for (Flight flight : airport.getFlightList()) {
                    if (flight.getFlightTime().isBefore(LocalDateTime.now()) && flight.getTerminal().getTerminalType() == TerminalType.ARRIVING) {
                        appLogger.info(flight.getCallsign() + IS_ARRIVED_STRING);
                        new Thread(new FlightExecutor(flight)).start();
                        airport.getFlightList().remove(flight);
                    }
                }
                airport.getFlightList().removeIf(flight -> flight.getFlightTime().isBefore(LocalDateTime.now().minusHours(2))
                        && flight.getTerminal().getTerminalType() == TerminalType.DEPARTING);
//                printCheckingData(printTable);
                Thread.sleep(timeoutSeconds);
            } catch (InterruptedException e) {
                state = false;
                logger.error(INTERRUPTION_ERROR_STRING +e);
            }
        }
    }
}




