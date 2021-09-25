package com.epam.jwd.view;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.FlightType;
import com.epam.jwd.model.Terminal;
import com.epam.jwd.service.AirportService;
import com.epam.jwd.service.CheckArrivalsThread;
import com.epam.jwd.service.RandomPassenger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class start {
    public synchronized void test() throws InterruptedException {

        Airport airportMinsk = new Airport("National Airport Minsk", "Minsk,Belarus");


        Boolean aBoolean = new Boolean(true);
        int semaphoresize = 5;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        Flight arrivingFlight1 = new Flight("BRU854",FlightType.ARRIVING,"Saint-Petersburg",airportMinsk.getTerminals().get(1),LocalDateTime.now().plusMinutes(1));
        Flight arrivingFlight2 = new Flight("AFL2501",FlightType.ARRIVING,"Moscow",airportMinsk.getTerminals().get(2),LocalDateTime.now().plusMinutes(2));
        Flight arrivingFlight3 = new Flight("BRU933",FlightType.ARRIVING,"Kiev",airportMinsk.getTerminals().get(2),LocalDateTime.now().plusMinutes(3));

        //
        Flight departFlight1 = new Flight("AFL1840",FlightType.DEPARTING,"Warsaw",airportMinsk.getTerminals().get(7),LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(2).plusMinutes(10)));
        Flight departFlight2 = new Flight("BRU875",FlightType.DEPARTING,"Riga",airportMinsk.getTerminals().get(8),LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1).minusMinutes(55)));
        Flight departFlight3 = new Flight("BRU366",FlightType.DEPARTING,"Vilnius",airportMinsk.getTerminals().get(9),LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1).minusMinutes(55)));

        airportMinsk.getArrivalFlightList().addAll(Arrays.asList(arrivingFlight1, arrivingFlight2, arrivingFlight3));
        airportMinsk.getDepartureFlightList().addAll(Arrays.asList(departFlight1,departFlight2,departFlight3));
        new Thread(new CheckArrivalsThread(airportMinsk, aBoolean)).start();

        Thread.sleep(10000);


        fillFlightByRandomPassenger(arrivingFlight1,airportMinsk,50);
        fillFlightByRandomPassenger(arrivingFlight2,airportMinsk,50);
        fillFlightByRandomPassenger(arrivingFlight3,airportMinsk,50);




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
                    AirportService.printDepPassengerList(airportMinsk);
                    break;
                case 4:
                    AirportService.printArrPassangerList(airportMinsk);
                    break;
                case 5:
                    Flight arrivingFlight4 = new Flight("BRU124",FlightType.ARRIVING,"Saint-Petersburg",airportMinsk.getTerminals().get(2),LocalDateTime.now().plusMinutes(1));
                    fillFlightByRandomPassenger(arrivingFlight4,airportMinsk,50);
                    airportMinsk.getArrivalFlightList().add(arrivingFlight4);
                    System.out.println("added");
                    break;
                case 6:
                    System.out.println("Wait " + Thread.getAllStackTraces().keySet().stream().map(thread -> thread.getName().contains("wait")).count());
                case 7:
                    aBoolean = new Boolean(false);
                default:
                    break;
            }

        }

    }

    public void fillFlightByRandomPassenger(Flight flight, Airport airport, int countPassenger) {
        RandomPassenger passenger = new RandomPassenger(flight, airport);
        for (int i = 0; i <countPassenger; i++){
            Thread thread = new Thread(passenger);
            thread.start();
        }
    }

}
