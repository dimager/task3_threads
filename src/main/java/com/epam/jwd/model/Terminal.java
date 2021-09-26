package com.epam.jwd.model;

import java.util.Comparator;
import java.util.concurrent.Semaphore;

public class Terminal implements Comparator<Terminal> {
    private String terminalId;
    private TerminalType terminalType;
    private Semaphore semaphore;

    public Terminal(String terminalId, TerminalType terminalType, int semaphoreSize ) {
        this.terminalId = terminalId;
        this.terminalType = terminalType;
        semaphore = new Semaphore(semaphoreSize,true);
    }

    public String getTerminalId() {
        return terminalId;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "terminalId='" + terminalId + '\'' +
                ", terminalType=" + terminalType +
                '}';
    }

    @Override
    public int compare(Terminal o1, Terminal o2) {
        return o1.getTerminalType().compareTo(o2.terminalType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Terminal terminal = (Terminal) o;

        if (!terminalId.equals(terminal.terminalId)) return false;
        if (terminalType != terminal.terminalType) return false;
        return semaphore.equals(terminal.semaphore);
    }

    @Override
    public int hashCode() {
        int result = terminalId.hashCode();
        result = 31 * result + terminalType.hashCode();
        result = 31 * result + semaphore.hashCode();
        return result;
    }
}
