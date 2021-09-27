package com.epam.jwd.service.printer;

import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.creator.AirportCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AirportPrinterTest {
    private static final AirportCreator airportMinsk = new AirportCreator("National Airport Minsk", "Minsk, Belarus");

    @BeforeAll
    static void beforeAll() {
        int terminalSemaphoreSize = 2;
        airportMinsk.addTerminalToAirport("1", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("2", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("3", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("4", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("5", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("6", TerminalType.ARRIVING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("A", TerminalType.DEPARTING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("B", TerminalType.DEPARTING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("D", TerminalType.DEPARTING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("E", TerminalType.DEPARTING, terminalSemaphoreSize);
        airportMinsk.addTerminalToAirport("F", TerminalType.DEPARTING, terminalSemaphoreSize);
        airportMinsk.addDepartingFlightToAirport("BTI3201", "Warsaw", LocalDateTime.now().plusHours(2).plusMinutes(5));
        airportMinsk.addDepartingFlightToAirport("RYR1201", "Vilnius", LocalDateTime.now().minusHours(1));
        airportMinsk.addDepartingFlightToAirport("AUI351", "Kiev", LocalDateTime.now().plusMinutes(10));
        airportMinsk.addDepartingFlightToAirport("WZZ1840", "Krakow", LocalDateTime.now().plusHours(1));
        airportMinsk.addArrivingFlightToAirport("BRU854", "Moscow", LocalDateTime.now().plusMinutes(1), 25);
        airportMinsk.addArrivingFlightToAirport("AFL2502", "Saint-Petersburg", LocalDateTime.now().plusMinutes(2), 20);
        airportMinsk.addArrivingFlightToAirport("THY1532", "Istanbul", LocalDateTime.now().plusMinutes(6), 30);
        airportMinsk.addArrivingFlightToAirport("DLH422", "Berlin", LocalDateTime.now().plusMinutes(7), 45);

    }


    @Test
    @DisplayName("Airport printtable test")
    void printTable() {
        Assertions.assertDoesNotThrow(() -> AirportPrinter.printTable(airportMinsk.getAirport()));
    }

    @Test
    @DisplayName("Airportprinter printFlightPassenger test")
    void printFlightPassenger() {
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> AirportPrinter.printFlightPassenger(airportMinsk.getAirport().getFlightList().get(5))),
                () -> Assertions.assertDoesNotThrow(() -> airportMinsk.getAirport().getFlightList().stream().forEach(flight -> AirportPrinter.printFlightPassenger(flight))));
    }

    @Test
    @DisplayName("Airportprinter printAirportPassengerList test")
    void printAirportPassengerList() {
        Assertions.assertDoesNotThrow(() -> AirportPrinter.printAirportPassengerList(airportMinsk.getAirport()));
    }


}