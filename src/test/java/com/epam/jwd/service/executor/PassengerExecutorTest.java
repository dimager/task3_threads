package com.epam.jwd.service.executor;

import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.creator.AirportCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

class PassengerExecutorTest {

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
    @DisplayName("PassengerExecutor start thread test")
    void run_threadStarting_normalParameters() {
        Passenger passenger1 = airportMinsk.getAirport().getFlightList().get(1).getPassengerList().stream()
                .filter(passenger -> passenger.getNextTicket() != null && !passenger.wantToChangeTicket())
                .findFirst()
                .get();
        Semaphore semaphore = passenger1.getNextTicket().getFlight().getTerminal().getSemaphore();

        Thread thread = new Thread(new PassengerExecutor(semaphore, passenger1, new Exchanger<>()));

        Assertions.assertAll(() -> Assertions.assertDoesNotThrow(() -> thread.start()),
                () -> Assertions.assertTrue(thread.isAlive()));
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(airportMinsk.getAirport().getFlightList().get(0).getPassengerList().get(0).equals(passenger1));
    }
}