package com.epam.jwd.service.generator;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.TerminalType;
import com.epam.jwd.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomPassengerThread extends Thread {
    private final static Logger logger = LogManager.getLogger(RandomPassengerThread.class);
    private static final String GENERATE_PASSENGER_MESSAGE = "Generate passenger";
    private final Random random = new Random();
    private final static String[] firstNameArray = {"Sophia", "Liam", "Olivia", "Noah", "Riley", "Jackson", "Emma", "Aiden", "Ava", "Elijah", "Isabella", "Grayson", "Aria", "Lucas", "Aaliyah", "Oliver", "Amelia", "Caden", "Mia", "Mateo", "Layla", "Muhammad", "Zoe", "Mason", "Camilla", "Carter", "Charlotte", "Jayden", "Eliana", "Ethan", "Mila", "Sebastian", "Everly", "James", "Luna", "Michael", "Avery", "Benjamin", "Evelyn", "Logan", "Harper", "Leo", "Lily", "Luca", "Ella", "Alexander", "Gianna", "Levi", "Chloe", "Daniel", "Adalyn", "Josiah", "Charlie", "Henry", "Isla", "Jace", "Ellie", "Julian", "Leah", "Jack", "Nora", "Ryan", "Scarlett", "Jacob", "Maya", "Asher", "Abigail", "Wyatt", "Madison", "William", "Aubrey", "Owen", "Emily", "Gabriel", "Kinsley", "Miles", "Elena", "Lincoln", "Paisley", "Ezra", "Madelyn", "Isaiah", "Aurora", "Luke", "Peyton", "Cameron", "Nova", "Caleb", "Emilia", "Isaac", "Hannah", "Carson", "Sarah", "Samuel", "Ariana", "Colton", "Penelope", "Maverick", "Lila", "Matthew"};
    private final static String[] lastNameArray = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"};
    private final Flight flight;
    private final Airport airport;

    public RandomPassengerThread(Flight flight, Airport airport) {
        this.flight = flight;
        this.airport = airport;
    }

    @Override
    public void run() {
        try {
            logger.debug(GENERATE_PASSENGER_MESSAGE);
            flight.addPassengerToFlight(generatePassenger(airport));
            Thread.currentThread().setName(GENERATE_PASSENGER_MESSAGE);
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Passenger generatePassenger(Airport airport) {
        if (random.nextInt(10) % 3 == 0) {
            return new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], null, false);
        } else {
            return new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], new Ticket(getFlight(airport)), isChangeTicket());
        }
    }

    private Flight getFlight(Airport airport) {
        List<Flight> list = airport.getFlightList().stream()
                .filter(flight1 -> flight1.getTerminal().getTerminalType().equals(TerminalType.DEPARTING))
                .collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }

    private boolean isChangeTicket() {
        return Math.random() < 0.5;
    }
}
