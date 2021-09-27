package com.epam.jwd.service.creator;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Terminal;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.executor.CheckTableExecutor;
import com.epam.jwd.service.generator.PassengerGenerator;
import com.epam.jwd.service.generator.TerminalGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Objects;

public class AirportCreator {
    private final static Logger logger = LogManager.getLogger(AirportCreator.class);
    private static final String GREAT_AIRPORT_STRING = "Create airport ";
    private static final String ADD_TERMINAL_STRING = "Add terminal to airport ";
    private static final String AIRPORT_START_WORKING_STRING = "Airport start working thread";
    private static final String ADD_FLIGHT_TO_AIRPORT_STRING = " was added";
    private static final String EXCEPTIONS_STRING = "Invalid input parameter (null)";
    private Airport airport;

    public AirportCreator(String airportName, String airportLocation) {
        logger.debug(GREAT_AIRPORT_STRING);
        this.airport = new Airport(airportName, airportLocation);
    }

    public Airport getAirport() {
        return airport;
    }

    public void addTerminalToAirport(String terminalId, TerminalType terminalType, int semaphoreSize) {
        logger.debug(ADD_TERMINAL_STRING + airport.getName());
        airport.getTerminals().add(new Terminal(terminalId, terminalType, semaphoreSize));
    }

    public void addArrivingFlightToAirport(String callsign, String destination, LocalDateTime localDateTime, int numberOfPassenger) {
        if (Objects.isNull(callsign) || Objects.isNull(destination) || Objects.isNull(localDateTime)  || numberOfPassenger < 1){
            logger.error(EXCEPTIONS_STRING);
            throw new NullPointerException(EXCEPTIONS_STRING);
        }
        logger.debug(callsign + ADD_FLIGHT_TO_AIRPORT_STRING);
        Flight arrivingFlight = new Flight.Builder()
                .setCallsign(callsign)
                .setFlightType(TerminalType.ARRIVING)
                .setDestination(destination)
                .setFlightTime(localDateTime)
                .setTerminal(TerminalGenerator.getRandomArrivalTerminal(airport))
                .build();
        fillFlightByRandomPassenger(arrivingFlight, airport, numberOfPassenger);
        airport.getFlightList().add(arrivingFlight);
    }

    public void addDepartingFlightToAirport(String callsign, String destination, LocalDateTime localDateTime) {
        if (Objects.isNull(callsign) || Objects.isNull(destination) || Objects.isNull(localDateTime)){
            logger.error(EXCEPTIONS_STRING);
            throw new NullPointerException(EXCEPTIONS_STRING);
        }
        logger.debug(callsign + ADD_FLIGHT_TO_AIRPORT_STRING);
        Flight departingFlight = new Flight.Builder()
                .setCallsign(callsign)
                .setFlightType(TerminalType.DEPARTING)
                .setDestination(destination)
                .setFlightTime(localDateTime)
                .setTerminal(TerminalGenerator.getRandomDepartureTerminal(airport))
                .build();
        airport.getFlightList().add(departingFlight);
    }

    public Thread startWorking() {
        logger.debug(AIRPORT_START_WORKING_STRING);
        Thread thread = new Thread(new CheckTableExecutor(airport, 5));
        return thread;
    }

    private void fillFlightByRandomPassenger(Flight flight, Airport airport, int countPassenger) {
        PassengerGenerator passenger = new PassengerGenerator(flight, airport);
        for (int i = 0; i < countPassenger; i++) {
            Thread thread = new Thread(passenger);
            thread.start();
        }
    }

}
