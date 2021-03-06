package com.epam.jwd.view;

import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.printer.AirportPrinter;
import com.epam.jwd.service.creator.AirportCreator;
import com.epam.jwd.service.reader.UserInputReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class View {
    private final static Logger logger = LogManager.getLogger(View.class);
    private static final Map<Integer, Runnable> userInputMap = new HashMap<>();
    private static final String MENU = "1. Start checking table\n" +
            "2. Stop  checking table\n" +
            "3. Print Flight table\n" +
            "4. Add new demo arriving flight\n" +
            "5. Print passenger list\n" +
            "6. Exit\n" +
            "Select:";
    private static final String ALLREADY_STARTED_STRING = "Allready started";
    private static final String NOT_STARTED_STRING = "Not started";
    private static final String STARTED_STRING = " started";
    private AirportCreator airportMinsk;
    private Thread thread = new Thread();
    private boolean state = true;

    public static int getUserMapSize(){
        return userInputMap.size();
    }
    public View() {
        createDemoAirport(2);
        userInputMap.put(1, this::startCheckingFlightTableOption);
        userInputMap.put(2, this::stopCheckingFlightTableOption);
        userInputMap.put(3, this::printFlightTableOption);
        userInputMap.put(4, this::addNewArrivalFlightOption);
        userInputMap.put(5, this::printPassengerList);
        userInputMap.put(6, this::exitOption);
    }

    private void printPassengerList() {
        AirportPrinter.printAirportPassengerList(airportMinsk.getAirport());
    }

    private void startCheckingFlightTableOption() {
        if (!thread.isAlive()) {
            thread = new Thread(airportMinsk.startWorking());
            thread.start();
            logger.info(STARTED_STRING);
        }
        else
            logger.info(ALLREADY_STARTED_STRING);
    }

    private void stopCheckingFlightTableOption() {
        if (thread.isAlive()) {
            thread.interrupt();
        }
        else {
            logger.info(NOT_STARTED_STRING);
        }
    }

    private void printFlightTableOption() {
        AirportPrinter.printTable(airportMinsk.getAirport());
    }

    private void addNewArrivalFlightOption() {
        logger.info("Add BRU111. check table");
        airportMinsk.addArrivingFlightToAirport("BRU111", "Moscow", LocalDateTime.now().plusMinutes(1), 30);
    }

    private void exitOption() {
        stopCheckingFlightTableOption();
        state=false;
        System.exit(0);
    }

    public void start(Level level) {
        Configurator.setLevel("com.epam.jwd",level);
        while (state) {
            userInputMap.get(UserInputReader.read(MENU)).run();
        }
    }

    private void createDemoAirport(int terminalSemaphoreSize) {
        airportMinsk = new AirportCreator("National Airport Minsk", "Minsk, Belarus");
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
        airportMinsk.addArrivingFlightToAirport("BRU854", "Moscow", LocalDateTime.now().plusMinutes(1), 65);
        airportMinsk.addArrivingFlightToAirport("AFL2502", "Saint-Petersburg", LocalDateTime.now().plusMinutes(2), 55);
        airportMinsk.addArrivingFlightToAirport("THY1532", "Istanbul", LocalDateTime.now().plusMinutes(6), 45);
        airportMinsk.addArrivingFlightToAirport("DLH422", "Berlin", LocalDateTime.now().plusMinutes(7), 55);
    }
}
