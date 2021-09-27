package com.epam.jwd.service.validator;

import com.epam.jwd.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputValidator {
    private static final Logger logger = LogManager.getLogger(UserInputValidator.class);
    private static final String INPUT_PATTERN = "[1-" + View.getUserMapSize() + "]";
    private static final String INCORRECT_CHOICE_MESSAGE = "Incorrect choice";
    private static final Pattern pattern = Pattern.compile(INPUT_PATTERN);

    public static boolean validateInput(String inputString) {
        if (inputString.trim().length() != 1) {
            logger.info(INCORRECT_CHOICE_MESSAGE);
            return false;
        } else {
            Matcher matcher = pattern.matcher(inputString);
            if (matcher.find()) {
                return true;
            }
            logger.info(INCORRECT_CHOICE_MESSAGE);
            return false;
        }
    }
}
