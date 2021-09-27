package com.epam.jwd;

import com.epam.jwd.view.View;
import org.apache.logging.log4j.Level;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        view.start(Level.INFO);
    }
}



