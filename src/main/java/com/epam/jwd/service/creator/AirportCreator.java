package com.epam.jwd.service.creator;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Terminal;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.AirportService;
import com.epam.jwd.service.executor.CheckTableExecutor;
import com.epam.jwd.service.generator.RandomPassengerThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class AirportCreator {
    private final static Logger logger = LogManager.getLogger(AirportCreator.class);
    private Airport airport;

    public AirportCreator(String airportName, String airportLocation) {
        this.airport = new Airport(airportName, airportLocation);
    }

    public Airport getAirport() {
        return airport;
    }

    public void addTerminalToAirport(String terminalId, TerminalType terminalType, int semaphoreSize) {
        airport.getTerminals().add(new Terminal(terminalId, terminalType, semaphoreSize));
    }

    public void addArrivingFlightToAirport(String callsign, String destination, LocalDateTime localDateTime, int numberOfPassenger) {
        Flight arrivingFlight = new Flight.Builder()
                .setCallsign(callsign)
                .setFlightType(TerminalType.ARRIVING)
                .setDestination(destination)
                .setFlightTime(localDateTime)
                .setTerminal(AirportService.getRandomArrivalTerminal(airport))
                .build();
        fillFlightByRandomPassenger(arrivingFlight, airport, numberOfPassenger);
        airport.getArrivalFlightList().add(arrivingFlight);
    }

    public void addDepartingFlightToAirport(String callsign, String destination, LocalDateTime localDateTime) {
        Flight departingFlight = new Flight.Builder()
                .setCallsign(callsign)
                .setFlightType(TerminalType.DEPARTING)
                .setDestination(destination)
                .setFlightTime(localDateTime)
                .setTerminal(AirportService.getRandomDepartureTerminal(airport))
                .build();
        airport.getDepartureFlightList().add(departingFlight);
    }

    public Thread startWorking(Boolean printTableToConsole) {
        Thread thread = new Thread(new CheckTableExecutor(airport, 5, printTableToConsole));
        return thread;
    }

    private void fillFlightByRandomPassenger(Flight flight, Airport airport, int countPassenger) {
        RandomPassengerThread passenger = new RandomPassengerThread(flight, airport);
        for (int i = 0; i < countPassenger; i++) {
            Thread thread = new Thread(passenger);
            thread.start();
        }
    }

}
