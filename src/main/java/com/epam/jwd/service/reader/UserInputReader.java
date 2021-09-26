package com.epam.jwd.service.reader;

import com.epam.jwd.service.validator.UserInputValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class UserInputReader {
    private final static Logger logger = LogManager.getLogger(UserInputReader.class);
    private static final String INCORRECT_CHOICE_MESSAGE = "Incorrect choice";

    public static int read(String message) {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        do {
            try {
                logger.info(message);
                choice = scanner.next();
            } catch (Exception e) {
                logger.info(INCORRECT_CHOICE_MESSAGE);
            }
        } while (!UserInputValidator.validateInput(choice));
        return Integer.parseInt(choice);
    }
}
