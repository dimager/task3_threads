package com.epam.jwd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Airport {
    private String name;
    private String location;

    private final List<Terminal> terminals = new ArrayList<>();
    private final List<Flight> departureFlightList = new CopyOnWriteArrayList<>();
    private final List<Flight> arrivalFlightList = new CopyOnWriteArrayList<>();

    public List<Flight> getDepartureFlightList() {
        return departureFlightList;
    }

    public List<Flight> getArrivalFlightList() {
        return arrivalFlightList;
    }

    public Airport(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", terminals=" + terminals +
                ", departureFlightList=" + departureFlightList +
                ", arrivalFlightList=" + arrivalFlightList +
                '}';
    }
}
