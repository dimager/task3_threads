package com.epam.jwd.service.generator;

import com.epam.jwd.model.Airport;
import com.epam.jwd.model.Terminal;
import com.epam.jwd.model.TerminalType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TerminalGenerator {
    private static final Random random = new Random();
    public static Terminal getRandomArrivalTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.ARRIVING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }

    public static Terminal getRandomDepartureTerminal(Airport airport) {
        List<Terminal> list = airport.getTerminals().stream().filter(terminal -> terminal.getTerminalType() == TerminalType.DEPARTING).collect(Collectors.toList());
        return list.get(random.nextInt(list.size()));
    }
}
