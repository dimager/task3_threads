package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.model.Terminal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AirportService {
    private final static Logger logger = LogManager.getLogger(AirportService.class);
    private static final Random random = new Random();

    public static void printDepartureTable(Airport airport) {
        logger.info("Departure table");
        logger.info("%8s %10s %12s %10s %12s\n", "Flight", "Terminal", "Date", "Dep Time", "Passengers");
        airport.getDepartureFlightList().stream()
                .sorted(Comparator.comparing(Flight::getFlightTime))
                .forEach(flight -> logger.info("%8s %10s %12s %10s %12s\n",
                        flight.getCallsign(),
                        flight.getTerminal().getTerminalId(),
                        flight.getFlightTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        flight.getFlightTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                        flight.getAllPassengerFromFlight().size()));
    }

    public static void printArrivalTable(Airport airport) {
        logger.info("Arrive table");
        logger.info("%8s %10s %12s %10s %12s\n", "Flight", "Terminal", "Date", "Arr Time", "Passengers");
        airport.getArrivalFlightList().stream()
                .sorted(Comparator.comparing(Flight::getFlightTime))
                .forEach(flight -> logger.info("%8s %10s %12s %10s %12s\n",
                        flight.getCallsign(),
                        flight.getTerminal().getTerminalId(),
                        flight.getFlightTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        flight.getFlightTime().toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                        flight.getAllPassengerFromFlight().size()));
    }

    public static void printFlightPassenger(Flight flight) {
        flight.getAllPassengerFromFlight().stream().forEach(System.out::println);
    }

    public static void printArrPassangerList(Airport airport) {
        for (Flight flight : airport.getArrivalFlightList()) {
            System.out.println(flight.getCallsign());
            flight.getAllPassengerFromFlight().stream().forEach(System.out::println);
        }
    }

    public static void printDepPassengerList(Airport airport) {
        airport.getDepartureFlightList().stream().forEach(flight -> printFlightPassenger(flight));
    }

    public static Flight getFlightByCallsign(Airport airport, String callsing) {
        for (Flight flight : airport.getDepartureFlightList()) {
            if (flight.getCallsign().equals(callsing)) {
                return flight;
            }
        }
        return null;
    }

    public static Terminal getRandomArrivalTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.ARRIVING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }

    public static Terminal getRandomDepartureTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.DEPARTING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }
}
