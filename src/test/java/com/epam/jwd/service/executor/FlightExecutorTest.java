package com.epam.jwd.service.executor;

import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.creator.AirportCreator;
import com.epam.jwd.service.printer.AirportPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

class FlightExecutorTest {

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
        airportMinsk.addDepartingFlightToAirport("WZZ1840", "Krakow", LocalDateTime.now().plusHours(1));
        airportMinsk.addArrivingFlightToAirport("BRU854", "Moscow", LocalDateTime.now().plusMinutes(1), 25);

    }

    @Test
    @DisplayName("FlightExecutor start thread test")
    void run_threadStarting_normalParameters() {
        Thread thread = new Thread(new FlightExecutor(airportMinsk.getAirport().getFlightList().get(1)));
        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> thread.start()),
                () -> Assertions.assertTrue(thread.isAlive()));
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(airportMinsk.getAirport().getFlightList().get(0).getPassengerList().size()>0);
    }
}
