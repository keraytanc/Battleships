package org.keraytanc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.keraytanc.logic.GameTest;
import org.keraytanc.logic.InputProcesser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class FlotillaHeadquartersTest {

    Battlefield battlefield;
    FlotillaHeadquarters flotillaHeadquarters;
    Cannon cannon;
    InputProcesser inputProcesser;

    @BeforeEach
    public void initialize() {
        this.battlefield = new Battlefield();
        this.flotillaHeadquarters = new FlotillaHeadquarters(this.battlefield);
        this.cannon = new Cannon();
        this.inputProcesser = new InputProcesser();
    }

    @Test
    public void allShipsSunkTest() {

        String[] allValidInputs = GameTest.getValidInputs();

        this.flotillaHeadquarters.placeAllShips();

        int allShipsLifeLeft = 13;

        for (String command: allValidInputs) {
            allShipsLifeLeft = lifeLeftAfterShot(allShipsLifeLeft, command);

            if (allShipsLifeLeft > 0) {
                assertFalse(this.flotillaHeadquarters.allShipsSunk());
            } else {
                assertTrue(this.flotillaHeadquarters.allShipsSunk());
            }
        }
    }

    @Test
    public void testShipLife() {

        String[] allValidInputs = GameTest.getValidInputs();

        this.flotillaHeadquarters.placeAllShips();

        int allShipsLifeLeft = 13;

        for (String command: allValidInputs) {
            allShipsLifeLeft = lifeLeftAfterShot(allShipsLifeLeft, command);

            int actualLifeLeft = this.flotillaHeadquarters.getBattleship0Life() + this.flotillaHeadquarters.getDestroyer1Life()
                    + this.flotillaHeadquarters.getDestroyer2Life();

            assertEquals(allShipsLifeLeft, actualLifeLeft);
        }

    }

    @RepeatedTest(100)
    public void testShipsProperCreationAndAlignment() {

        this.flotillaHeadquarters.placeAllShips();

        String printedMatrix = getMatrixAfterShootingAllFields();

        int expectedHits = 13;
        int hitsCounter = countActualHits(printedMatrix);


        assertEquals(expectedHits, hitsCounter, "Ships weren't properly created");

    }

    private int lifeLeftAfterShot(int allShipsLifeLeft, String command) {
        try {
            int column = this.inputProcesser.getColumn(command);
            int row = this.inputProcesser.getRow(command);

            int fieldValue = this.battlefield.getValue(column, row);

            if (fieldValue > 0) {
                allShipsLifeLeft -= 1;
            }

            this.cannon.shoot(this.battlefield, this.flotillaHeadquarters, column, row);

        } catch (UnsupportedOperationException e) {
            fail("valid input rejected by InputProcesser class");
        }
        return allShipsLifeLeft;
    }

    private String getMatrixAfterShootingAllFields() {

        //saving standard output variable to reset output to the console at the end of the method
        PrintStream standardOutput = System.out;

        //in order to verify output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String[] allValidInputs = GameTest.getValidInputs();


        for (String command: allValidInputs) {
            try {

                int column = this.inputProcesser.getColumn(command);
                int row = this.inputProcesser.getRow(command);

                this.cannon.shoot(this.battlefield, this.flotillaHeadquarters, column, row);

            } catch (UnsupportedOperationException e) {
                fail("valid input rejected by InputProcesser class");
            }
        }

        this.battlefield.printBattlefield();

        System.setOut(standardOutput);

        String printedMatrix = extractMatrixFromOutput(outputStream.toString());

        return printedMatrix;
    }

    private String extractMatrixFromOutput(String output) {

        String[] outputs = output.split(System.lineSeparator());

        StringBuilder printedMatrix = new StringBuilder();

        for (int line = outputs.length - 11; line < outputs.length; line++) {
            printedMatrix.append(outputs[line]).append(System.lineSeparator());
        }

        return printedMatrix.toString();
    }

    private int countActualHits(String printedMatrix) {

        int hitsCounter = 0;

        for (int charNo = 0; charNo < printedMatrix.length(); charNo++) {
            if (printedMatrix.charAt(charNo) == 'O') {
                hitsCounter += 1;
            }
        }

        return hitsCounter;
    }


}
