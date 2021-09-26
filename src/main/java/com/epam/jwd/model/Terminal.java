package com.epam.jwd.model;

import java.util.concurrent.Semaphore;

public class Terminal {
    private String terminalId;
    private FlightType terminalType;
    private Semaphore semaphore;

    public Terminal(String terminalId, FlightType terminalType, int semaphoreSize ) {
        this.terminalId = terminalId;
        this.terminalType = terminalType;
        semaphore = new Semaphore(semaphoreSize,true);
    }

    public String getTerminalId() {
        return terminalId;
    }

    public FlightType getTerminalType() {
        return terminalType;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "terminalId='" + terminalId + '\'' +
                ", terminalType=" + terminalType +
                '}';
    }
}
