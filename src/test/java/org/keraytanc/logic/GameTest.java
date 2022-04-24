package org.keraytanc.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.keraytanc.domain.BattlefieldTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game theGame;

    @BeforeEach
    public void createTheGame() {
        this.theGame = new Game();
    }

    //if one of the input line equals to "x" start() method execution should end
    @Nested
    class ExitTheGameTest {

        @Test
        public void exitCommandGiven() {

            String exitInput = "cat" + "\ndog" + "\n123" + "\nx" + "\nelephant";
            mockInput(exitInput);

            try {
                theGame.start();
            } catch (NoSuchElementException e) {
                fail("Didn't quit the game on the \"x\" command");
            }
        }

        @Test
        public void exitCommandNotGiven() {

            String nonExitInput = "cat" + "\ndog" + "\n123" + "\n not-x" + "\nelephant";
            mockInput(nonExitInput);

            assertThrows(NoSuchElementException.class, () -> theGame.start());
        }
    }

    @Nested
    class InputsTest {

        @Test
        public void testInvalidInputInfo() {

            String[] invalidInputs = {"K11", "B0", "K9", "", "foobar",
                    "B5x", "C92"};

            for (String argument: invalidInputs) {

                theGame = new Game();
                String output = emulateGameAndGetOutput(argument + System.lineSeparator() + "x");

                String[] outputs = output.split(System.lineSeparator());

                assertEquals("Invalid command. Choose from columns A to J and from rows 1 to 10", outputs[outputs.length - 1]);

            }
        }


        @Test
        public void testValidInput() {

            String[] validInputs = getValidInputs();

            for (String input: validInputs) {

                try {
                    theGame = new Game();

                    String output = emulateGameAndGetOutput(input + System.lineSeparator() + "x");
                    String[] outputs = output.split(System.lineSeparator());

                    if (outputs[outputs.length - 1].equals("Invalid command. Choose from columns A to J and from rows 1 to 10")) {
                        fail("Input " + input + " rejected despite being a proper input");
                    }
                } catch (Exception e) {
                    fail("Input " + input + " caused unexpected exception despite being proper input");
                }
            }
        }
    }

    @Nested
    class ShipsSinkingTest {

        @Test
        public void battleship0SinkingTest() {
            this.shipSinkingTest("Battleship has sunk");
        }

        @Test
        public void destroyer1SinkingTest() {
            this.shipSinkingTest("Destroyer 1 has sunk");
        }

        @Test
        public void destroyer2SinkingTest() {
            this.shipSinkingTest("Destroyer 2 has sunk");
        }

        public void shipSinkingTest(String sinkingMessage) {

            String input = getAllInputsAsString() + System.lineSeparator() + "X";
            String output = emulateGameAndGetOutput(input);

            assertTrue(output.contains(sinkingMessage), "program did not print information about sinking");
        }
    }

    @Nested
    class WinningTest {
        @Test
        public void winningMessageGiven() {

            String input = getAllInputsAsString() + System.lineSeparator() + "X";
            String output = emulateGameAndGetOutput(input);

            assertTrue(output.contains("You won!"), "winning message is not printed");
        }

        @Test
        public void exitingTheGameUponWinning() {

            String input = getAllInputsAsString() + System.lineSeparator() + "X";

            try {

                String output = emulateGameAndGetOutput(input);
                String outputsLastLine = output.substring(output.lastIndexOf(System.lineSeparator()) + 2);

                assertEquals("You won!", outputsLastLine);

            } catch (NoSuchElementException e) {
                fail("didn't quit upon sinking all the ships");
            }
        }
    }

    @Test
    public void welcomeInstructionsTest() {

        BattlefieldTest bfTest = new BattlefieldTest();

        String expectedOutput = "Welcome to the Battleship game!" + System.lineSeparator()
                + "Your task is to sink enemy's flotilla consisting of 3 ships; 1 Battleship and 2 Destroyers."
                + System.lineSeparator()
                + "To shoot enter the coordinates in the form of XY where X is a letter from A to J representing"
                + System.lineSeparator()
                + "columns of a 10x10 grid and Y is a number from 1 to 10 representing rows. Good luck!"
                + System.lineSeparator()
                + System.lineSeparator()
                + bfTest.mockBattlefieldPrint();

        String output = this.emulateGameAndGetOutput("X");

        StringBuilder actualOutput = new StringBuilder();

        String[] outputLines = output.split(System.lineSeparator());

        try {
            for (int i = 0; i < 16; i++) {
                actualOutput.append(outputLines[i]).append(System.lineSeparator());
            }
        } catch (Exception e) {
            fail("Proper instructions weren't printed");
        }

        assertEquals(expectedOutput, actualOutput.toString(), "Proper instructions weren't printed");
    }

    private String emulateGameAndGetOutput(String input) {

        //saving standard output variable to reset output to the console at the end of the method
        PrintStream standardOutput = System.out;

        //catching output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        this.mockInput(input);

        theGame.start();

        System.setOut(standardOutput);

        return outputStream.toString();
    }

    public void mockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private String getAllInputsAsString() {

        StringBuilder input = new StringBuilder();

        String[] validInputs = getValidInputs();

        for (String validInput: validInputs) {
            input.append(System.lineSeparator()).append(validInput);
        }

        return input.toString();
    }

    public static String[] getValidInputs() {

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
