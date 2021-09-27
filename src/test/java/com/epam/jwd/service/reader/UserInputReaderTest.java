package com.epam.jwd.service.reader;

import com.epam.jwd.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;

class UserInputReaderTest {

    @BeforeEach
    void setUp() {
        View view = new View();
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1".getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("User input test")
    void read_returnCorrectValue_whenInputIsCorrect() {
        Assertions.assertEquals(1, UserInputReader.read("1"));
    }
}