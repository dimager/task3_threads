package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.model.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AirportService {
    private final static Logger logger = LogManager.getLogger(AirportService.class);
    private static final Random random = new Random();
    private static final String TABLE_HEAD_MESSAGE_STRING = "Table -> Time: ";
    private static final String TABLE_COLLUM_NAMES_STRING = String.format("\n%8s %10s %12s %10s %12s %12s", "Flight", "Terminal", "Date", "Dep Time", "Passengers", "Flight type");
    private static final String DEPARTING_FLIGHTS_STRING = "Departing flights:";
    private static final String ARRIVING_FLIGHTS_STRING = "Arriving flights:";

    public static void printTable(Airport airport) {
        logger.info(TABLE_HEAD_MESSAGE_STRING + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        logger.info(DEPARTING_FLIGHTS_STRING + TABLE_COLLUM_NAMES_STRING);
        printFlightList(airport.getFlightList().stream()
                .filter(flight -> flight.getTerminal().getTerminalType() == TerminalType.DEPARTING)
                .collect(Collectors.toList()));
        logger.info(ARRIVING_FLIGHTS_STRING + TABLE_COLLUM_NAMES_STRING);
        printFlightList(airport.getFlightList().stream()
                .filter(flight -> flight.getTerminal().getTerminalType() == TerminalType.ARRIVING)
                .collect(Collectors.toList()));
    }

    public static void printFlightPassenger(Flight flight) {
        logger.info(flight.getCallsign());
        flight.getAllPassengerFromFlight().stream().forEach(passenger -> logger.info(passenger));
    }

    public static void printPassengerList(Airport airport){
        airport.getFlightList().stream().forEach( flight -> printFlightPassenger(flight));
    }

    public static Terminal getRandomArrivalTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.ARRIVING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }

    public static Terminal getRandomDepartureTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.DEPARTING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }

    private static void printFlightList(List<Flight> list){
        list.stream().sorted(Comparator.comparing(Flight::getFlightTime))
                .forEach(flight -> logger.info(String.format("%8s %10s %12s %10s %12s %12s",
                        flight.getCallsign(),
                        flight.getTerminal().getTerminalId(),
                        flight.getFlightTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        flight.getFlightTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                        flight.getAllPassengerFromFlight().size(),
                        flight.getTerminal().getTerminalType())));
    }
}
