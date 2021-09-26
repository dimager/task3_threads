package com.epam.jwd.view;

import com.epam.jwd.model.TerminalType;
import com.epam.jwd.service.AirportService;
import com.epam.jwd.service.creator.AirportCreator;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        AirportCreator airportMinsk = new AirportCreator("National Airport Minsk", "Minsk, Belarus");

        airportMinsk.addTerminalToAirport("1", TerminalType.ARRIVING, 5);
        airportMinsk.addTerminalToAirport("2", TerminalType.ARRIVING, 5);
        airportMinsk.addTerminalToAirport("3", TerminalType.ARRIVING, 5);
        airportMinsk.addTerminalToAirport("4", TerminalType.ARRIVING, 5);
        airportMinsk.addTerminalToAirport("5", TerminalType.ARRIVING, 5);
        airportMinsk.addTerminalToAirport("6", TerminalType.ARRIVING, 5);

        airportMinsk.addTerminalToAirport("A", TerminalType.DEPARTING, 5);
        airportMinsk.addTerminalToAirport("B", TerminalType.DEPARTING, 5);
        airportMinsk.addTerminalToAirport("D", TerminalType.DEPARTING, 5);
        airportMinsk.addTerminalToAirport("E", TerminalType.DEPARTING, 5);
        airportMinsk.addTerminalToAirport("F", TerminalType.DEPARTING, 5);

        airportMinsk.addDepartingFlightToAirport("BTI3201", "Warsaw", LocalDateTime.now().plusHours(2).plusMinutes(5));
        airportMinsk.addDepartingFlightToAirport("RYR1201", "Vilnius", LocalDateTime.now().minusHours(1));
        airportMinsk.addDepartingFlightToAirport("AUI351", "Kiev", LocalDateTime.now().plusMinutes(10));
        airportMinsk.addDepartingFlightToAirport("WZZ1840", "Krakow", LocalDateTime.now().plusHours(1));

        airportMinsk.addArrivingFlightToAirport("BRU854", "Moscow", LocalDateTime.now().plusMinutes(1), 30);
        airportMinsk.addArrivingFlightToAirport("AFL2502", "Saint-Petersburg", LocalDateTime.now().plusMinutes(2), 35);
        airportMinsk.addArrivingFlightToAirport("THY1532", "Istanbul", LocalDateTime.now().plusMinutes(3), 40);
        airportMinsk.addArrivingFlightToAirport("DLH422", "Berlin", LocalDateTime.now().plusMinutes(5), 65);


        //airportMinsk.getAirport().getDepartureFlightList().stream().forEach(flight -> System.out.println(flight));
        Thread thread = new Thread();



        Scanner scanner = new Scanner(System.in);
        while (true) {
            int i = scanner.nextInt();

            switch (i) {
                case 1:
                    AirportService.printDepartureTable(airportMinsk.getAirport());
                    break;
                case 2:
                    AirportService.printArrivalTable(airportMinsk.getAirport());
                    break;
                case 3:
                    AirportService.printDepPassengerList(airportMinsk.getAirport());
                    break;
                case 4:
                    AirportService.printArrPassangerList(airportMinsk.getAirport());
                    break;
                case 5:
                    break;
                case 6:
                    // System.out.println("Wait " + Thread.getAllStackTraces().keySet().stream().map(thread -> thread.getName().contains("wait")).count());
                case 7:
                    thread = airportMinsk.startWorking(true);
                    thread.start();
                    break;
                case 8:
                    thread.interrupt();
                default:
                    break;
            }

        }

    }


}



