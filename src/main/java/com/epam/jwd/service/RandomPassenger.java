package com.epam.jwd.service;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Flight;
import com.epam.jwd.model.Passenger;
import com.epam.jwd.model.Ticket;
import com.epam.jwd.model.TicketType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPassenger {

    private static final Random random = new Random();
    private static final String[] firstNameArray = {"Sophia", "Liam", "Olivia", "Noah", "Riley", "Jackson", "Emma", "Aiden", "Ava", "Elijah", "Isabella", "Grayson", "Aria", "Lucas", "Aaliyah", "Oliver", "Amelia", "Caden", "Mia", "Mateo", "Layla", "Muhammad", "Zoe", "Mason", "Camilla", "Carter", "Charlotte", "Jayden", "Eliana", "Ethan", "Mila", "Sebastian", "Everly", "James", "Luna", "Michael", "Avery", "Benjamin", "Evelyn", "Logan", "Harper", "Leo", "Lily", "Luca", "Ella", "Alexander", "Gianna", "Levi", "Chloe", "Daniel", "Adalyn", "Josiah", "Charlie", "Henry", "Isla", "Jace", "Ellie", "Julian", "Leah", "Jack", "Nora", "Ryan", "Scarlett", "Jacob", "Maya", "Asher", "Abigail", "Wyatt", "Madison", "William", "Aubrey", "Owen", "Emily", "Gabriel", "Kinsley", "Miles", "Elena", "Lincoln", "Paisley", "Ezra", "Madelyn", "Isaiah", "Aurora", "Luke", "Peyton", "Cameron", "Nova", "Caleb", "Emilia", "Isaac", "Hannah", "Carson", "Sarah", "Samuel", "Ariana", "Colton", "Penelope", "Maverick", "Lila", "Matthew"};
    private static final String[] lastNameArray = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts"};

    public static List<Passenger> generatePassenger(Airport airport, int count) {
        List<Passenger> passengerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (random.nextInt(10) % 3 == 0) {
                passengerList.add(new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], null));
            } else {
                passengerList.add(new Passenger(firstNameArray[random.nextInt(firstNameArray.length)], lastNameArray[random.nextInt(lastNameArray.length)], new Ticket(TicketType.values()[random.nextInt(TicketType.values().length)],getFlight(airport))));
            }
        }
        return passengerList;
    }

    private static Flight getFlight(Airport airport) {
            return airport.getDepartureFlightList().get(random.nextInt(airport.getDepartureFlightList().size()));
    }
}
