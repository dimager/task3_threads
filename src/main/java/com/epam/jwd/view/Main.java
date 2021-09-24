package com.epam.jwd.view;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.FlightType;
import com.epam.jwd.model.Terminal;
import com.epam.jwd.service.AirportService;
import com.epam.jwd.service.RandomPassenger;
import com.epam.jwd.service.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int semaphoresize = 5;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Airport airportMinsk = new Airport("National Airport Minsk", "Minsk,Belarus");

        airportMinsk.getTerminals().add(new Terminal("1", FlightType.ARRIVING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("2", FlightType.ARRIVING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("3", FlightType.ARRIVING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("4", FlightType.ARRIVING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("5", FlightType.ARRIVING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("6", FlightType.ARRIVING, semaphoresize));

        airportMinsk.getTerminals().add(new Terminal("A", FlightType.DEPARTING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("B", FlightType.DEPARTING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("D", FlightType.DEPARTING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("E", FlightType.DEPARTING, semaphoresize));
        airportMinsk.getTerminals().add(new Terminal("F", FlightType.DEPARTING, semaphoresize));


        //
        Flight arrivingFlight1 = new Flight();
        arrivingFlight1.setCallsign("BRU854");
        arrivingFlight1.setDestination("LED");
        arrivingFlight1.setFlightType(FlightType.ARRIVING);
        arrivingFlight1.setFlightTime(LocalDateTime.now().plusMinutes(1));
        arrivingFlight1.setTerminal(airportMinsk.getTerminals().get(1));

        Flight arrivingFlight2 = new Flight();
        arrivingFlight2.setCallsign("AFL2501");
        arrivingFlight2.setDestination("LED");
        arrivingFlight2.setFlightType(FlightType.ARRIVING);
        arrivingFlight2.setFlightTime(LocalDateTime.now().plusMinutes(2));
        arrivingFlight2.setTerminal(airportMinsk.getTerminals().get(2));

        Flight arrivingFlight3 = new Flight();
        arrivingFlight3.setCallsign("BRU933");
        arrivingFlight3.setDestination("LED");
        arrivingFlight3.setFlightType(FlightType.ARRIVING);
        arrivingFlight3.setFlightTime(LocalDateTime.now().plusMinutes(4));
        arrivingFlight3.setTerminal(airportMinsk.getTerminals().get(3));


        //
        Flight departFlight1 = new Flight();
        departFlight1.setCallsign("AFL1840");
        departFlight1.setDestination("DME");
        departFlight1.setFlightType(FlightType.DEPARTING);
        departFlight1.setFlightTime(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1).plusMinutes(5)));
        departFlight1.setTerminal(airportMinsk.getTerminals().get(7));

        Flight departFlight2 = new Flight();
        departFlight2.setCallsign("BRU875");
        departFlight2.setDestination("LED");
        departFlight2.setFlightType(FlightType.DEPARTING);
        departFlight2.setFlightTime(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1).minusMinutes(55)));
        departFlight2.setTerminal(airportMinsk.getTerminals().get(8));

        Flight departFlight3 = new Flight();
        departFlight3.setCallsign("BRU366");
        departFlight3.setDestination("LED");
        departFlight3.setFlightType(FlightType.DEPARTING);
        departFlight3.setFlightTime(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1).minusMinutes(55)));
        departFlight3.setTerminal(airportMinsk.getTerminals().get(9));


        airportMinsk.getDepartureFlightList().addAll(Arrays.asList(departFlight1, departFlight2, departFlight3));
        airportMinsk.getArrivalFlightList().addAll(Arrays.asList(arrivingFlight1, arrivingFlight2, arrivingFlight3));

        arrivingFlight1.getPassengerList().addAll(RandomPassenger.generatePassenger(airportMinsk, 50));
        arrivingFlight2.getPassengerList().addAll(RandomPassenger.generatePassenger(airportMinsk, 50));
        arrivingFlight3.getPassengerList().addAll(RandomPassenger.generatePassenger(airportMinsk, 50));

        // airportMinsk.getArrivalFlightList().stream().forEach(System.out::println);

        new Thread(new Test(airportMinsk)).start();


        Scanner scanner = new Scanner(System.in);


        while (true) {
            int i = scanner.nextInt();

            switch (i) {
                case 1:
                    AirportService.printDepartureTable(airportMinsk);
                    break;
                case 2:
                    AirportService.printArrivalTable(airportMinsk);
                    break;
                case 3:
                    AirportService.printDepPassangerList(airportMinsk);
                    break;
                case 4:
                    AirportService.printArrPassangerList(airportMinsk);
                    break;
                default:
                    break;
            }

        }
    }
}
