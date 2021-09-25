package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.Ticket;
import com.epam.jwd.model.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPassenger extends Thread {
    private final Random random = new Random();
    private final String[] firstNameArray = {"Sophia", "Liam", "Olivia", "Noah", "Riley", "Jackson", "Emma", "Aiden", "Ava", "Elijah", "Isabella", "Grayson", "Aria", "Lucas", "Aaliyah", "Oliver", "Amelia", "Caden", "Mia", "Mateo", "Layla", "Muhammad", "Zoe", "Mason", "Camilla", "Carter", "Charlotte", "Jayden", "Eliana", "Ethan", "Mila", "Sebastian", "Everly", "James", "Luna", "Michael", "Avery", "Benjamin", "Evelyn", "Logan", "Harper", "Leo", "Lily", "Luca", "Ella", "Alexander", "Gianna", "Levi", "Chloe", "Daniel", "Adalyn", "Josiah", "Charlie", "Henry", "Isla", "Jace", "Ellie", "Julian", "Leah", "Jack", "Nora", "Ryan", "Scarlett", "Jacob", "Maya", "Asher", "Abigail", "Wyatt", "Madison", "William", "Aubrey", "Owen", "Emily", "Gabriel", "Kinsley", "Miles", "Elena", "Lincoln", "Paisley", "Ezra", "Madelyn", "Isaiah", "Aurora", "Luke", "Peyton", "Cameron", "Nova", "Caleb", "Emilia", "Isaac", "Hannah", "Carson", "Sarah", "Samuel", "Ariana", "Colton", "Penelope", "Maverick", "Lila", "Matthew"};
    private final String[] lastNameArray = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"};
    private Flight flight;
    private Airport airport;
    public RandomPassenger(Flight flight, Airport airport) {
        this.flight = flight;
        this.airport = airport;
    }

    @Override
    public void run() {
        flight.addPassengerToFlight(generatePassenger(airport));
        Thread.currentThread().setName("aaa");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Passenger> generatePassengers(Airport airport, int count) {
        List<Passenger> passengerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (random.nextInt(10) % 3 == 0) {
                passengerList.add(new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], null, randomBoolean()));
            } else {
                passengerList.add(new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], new Ticket(TicketType.values()[random.nextInt(TicketType.values().length)], getFlight(airport)), randomBoolean()));
            }
        }
        return passengerList;
    }

    public Passenger generatePassenger (Airport airport){
        if (random.nextInt(10) % 3 == 0) {
            return new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], null, randomBoolean());
        } else {
            return new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], new Ticket(TicketType.values()[random.nextInt(TicketType.values().length)], getFlight(airport)), randomBoolean());
        }

    }

    private Flight getFlight(Airport airport) {
        return airport.getDepartureFlightList().get(random.nextInt(airport.getDepartureFlightList().size()));
    }

    private boolean randomBoolean() {
        return Math.random() > 0.1;
    }
}
