package com.epam.jwd.model;

import java.time.LocalDateTime;

public interface Builder {
    void setCallsign(String callsign);
    void setFlightType(FlightType flightType);
    void setDestination(String destination);
    void setTerminal(Terminal terminal);
    void setFlightTime(LocalDateTime flightTime);
}
