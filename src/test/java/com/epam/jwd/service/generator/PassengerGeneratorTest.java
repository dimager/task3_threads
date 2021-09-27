package com.epam.jwd.service.generator;

import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.creator.AirportCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


class PassengerGeneratorTest {

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
        airportMinsk.addArrivingFlightToAirport("DLH422", "Berlin", LocalDateTime.now().plusMinutes(7), 45);

    }

    @Test
    @DisplayName("Passenger generator start thread test")
    void run_threadStarting_normalParameters() {
        Thread thread = new Thread(new PassengerGenerator(airportMinsk.getAirport().getFlightList().get(1), airportMinsk.getAirport()));
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> thread.start()),
                () -> Assertions.assertTrue(() -> thread.isAlive()));
    }

    @Test
    @DisplayName("Test generatePassenger method")
    void generatePassenger() {
        PassengerGenerator passengerGenerator = new PassengerGenerator(airportMinsk.getAirport().getFlightList().get(0), airportMinsk.getAirport());
        Assertions.assertTrue(() -> passengerGenerator.generatePassenger(airportMinsk.getAirport()) instanceof Passenger);
    }
}