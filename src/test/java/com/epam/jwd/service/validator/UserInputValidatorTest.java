package com.epam.jwd.service.validator;
import com.epam.jwd.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UserInputValidatorTest {
    @BeforeEach
    void setUp() {
        View view = new View();
    }

    @Test
    @DisplayName("Validator test")
    void validateInput_returnValidationState_AnyInputString() {
        Assertions.assertAll(() -> Assertions.assertTrue(UserInputValidator.validateInput("3")),
                () -> Assertions.assertTrue(UserInputValidator.validateInput("3")),
                () -> Assertions.assertTrue(UserInputValidator.validateInput(" 4")),
                () -> Assertions.assertTrue(UserInputValidator.validateInput("3")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("1543")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("0")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("asdf")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("1caa")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput("asd fasdf a fzva")),
                () -> Assertions.assertFalse(UserInputValidator.validateInput(" 4asdf314ga32")));
    }
}