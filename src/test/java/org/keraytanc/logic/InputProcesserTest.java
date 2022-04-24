package org.keraytanc.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputProcesserTest {

    private InputProcesser inputProcesser;

    @BeforeEach
    public void createInputProcesser() {
        this.inputProcesser = new InputProcesser();
    }

    @Test
    public void validInputGiven() {

        String[] validInputs = getValidInputs();

        for (String input: validInputs) {
            mockInput(input);

            try {
                inputProcesser.getRow(input);
                inputProcesser.getColumn(input);
            } catch (Exception e) {
                fail("failed despite proper input");
            }
        }
    }

    @Test
    public void invalidInputsGiven() {

        String[] invalidInputs = {"K11", "B0", "K9", "", "foobar",
                "B5x", "D32"};

        for (String input: invalidInputs) {
            mockInput(input);
            assertThrows(UnsupportedOperationException.class, () -> inputProcesser.getColumn(input));
            assertThrows(UnsupportedOperationException.class, () -> inputProcesser.getRow(input));
        }
    }

    public void mockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    public String[] getValidInputs() {

        String[] validColumns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] validInputs = new String[100];
        int position = 0;

        for (String column: validColumns) {
            for (int i = 1; i <= 10; i++) {

                String input = column + i;
                validInputs[position] = input;
                position += 1;
            }
        }
        return validInputs;
    }

}
