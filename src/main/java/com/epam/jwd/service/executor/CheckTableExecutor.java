package com.epam.jwd.service.executor;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.service.AirportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CheckTableExecutor implements Runnable {
    private final static Logger logger = LogManager.getLogger(CheckTableExecutor.class);
    private final Airport airport;
    private final int timeoutSeconds;
    private final boolean printTable;


    public CheckTableExecutor(Airport airport, int timeoutSeconds, boolean printTable) {
        this.airport = airport;
        this.timeoutSeconds = timeoutSeconds * 1000;
        this.printTable = printTable;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("CheckTableThread");
        boolean state = true;
        while (state) {
            try {
                for (Flight flight : airport.getArrivalFlightList()) {
                    if (flight.getFlightTime().isBefore(LocalDateTime.now())) {
                        System.out.println(flight.getCallsign() + " is arrived");
                        new Thread(new FlightExecutor(flight)).start();
                        airport.getArrivalFlightList().remove(flight);
                    }
                }
                airport.getDepartureFlightList().removeIf(flight -> flight.getFlightTime().isBefore(LocalDateTime.now().minusHours(2)));
                printCheckingData(printTable);
                Thread.sleep(timeoutSeconds);
            } catch (InterruptedException e) {
                state = false;
                e.printStackTrace();
            }
        }
    }

    private void printCheckingData(boolean printTable) {
        if (printTable) {
            logger.info("-> Time: " + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            AirportService.printDepartureTable(airport);
            AirportService.printArrivalTable(airport);
        }
    }
}




