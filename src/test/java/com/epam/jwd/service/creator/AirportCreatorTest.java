package com.epam.jwd.service.creator;

import com.epam.jwd.model.TerminalType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AirportCreatorTest {

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
        airportMinsk.addDepartingFlightToAirport("BTI3201", "Warsaw", LocalDateTime.now().plusHours(2).plusMinutes(5));
        airportMinsk.addArrivingFlightToAirport("DLH422", "Berlin", LocalDateTime.now().plusMinutes(7), 45);

    }

    @Test
    @DisplayName("Add new terminal to airport")
    void addTerminalToAirport() {
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> airportMinsk.addTerminalToAirport("F", TerminalType.DEPARTING, 4)),
                () -> Assertions.assertTrue(airportMinsk.getAirport().getTerminals().stream()
                        .anyMatch(terminal -> terminal.getTerminalId().equals("F"))));
    }

    @Test
    @DisplayName("Add new arriving flight to airport")
    void addArrivingFlightToAirport() {
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> airportMinsk.addArrivingFlightToAirport("TestArrivingFlight", "Berlin", LocalDateTime.now().plusMinutes(7), 5)),
                () -> Assertions.assertTrue(airportMinsk.getAirport().getFlightList().stream()
                        .anyMatch(flight -> flight.getCallsign().equals("TestArrivingFlight"))));
    }

    @Test
    @DisplayName("Add new departing flight to airport")
    void addDepartingFlightToAirport() {
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> airportMinsk.addDepartingFlightToAirport("TestDepartingFlight", "Warsaw", LocalDateTime.now().plusHours(2).plusMinutes(5))),
                () -> Assertions.assertTrue(airportMinsk.getAirport().getFlightList().stream()
                        .anyMatch(flight -> flight.getCallsign().equals("TestDepartingFlight"))));
    }


    @Test
    @DisplayName("Add  nullable arriving flight to airport")
    void addArrivingFlightToAirport_Exception_NullableInput() {
        Assertions.assertAll(() -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addArrivingFlightToAirport("TestDepartingFlight", "Warsaw", LocalDateTime.now(), -4)),
                () -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addArrivingFlightToAirport("TestDepartingFlight", null, LocalDateTime.now(), 1)),
                () -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addArrivingFlightToAirport("TestDepartingFlight", "Warsaw", null, 1)),
                () -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addArrivingFlightToAirport(null, "Warsaw", LocalDateTime.now(), 1)));
    }

    @Test
    @DisplayName("Add nullable departing flight to airport")
    void addDepartingFlightToAirport_Exception_NullableInput() {
        Assertions.assertAll(() -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addDepartingFlightToAirport(null, "Warsaw", LocalDateTime.now())),
                () -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addDepartingFlightToAirport("BTI3201", null, LocalDateTime.now())),
                () -> Assertions.assertThrows(NullPointerException.class, () -> airportMinsk.addDepartingFlightToAirport("BTI3201", "Warsaw", null)));
    }
}