package org.keraytanc.domain;

import org.junit.jupiter.api.Test;
import org.keraytanc.logic.InputProcesser;

import static org.junit.jupiter.api.Assertions.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BattlefieldTest {

    @Test
    public void eachFieldUnshotAndShootable() {

        String[] command1 = {"foobar"};
        int command1ProperInputsNumber = 0;
        String[] command2 = {"foobar", "H5", "G7", "C10", "A1"};
        int command2ProperInputsNumber = 4;
        String[] command3 = {"foobar", "H3", "A11"};
        int command3ProperInputsNumber = 1;

        String[][] commands = {command1, command2, command3};
        int[] properArgumentsCount = {command1ProperInputsNumber, command2ProperInputsNumber, command3ProperInputsNumber};

        for (int i = 0; i < commands.length; i++) {

            String battlefielAfterShots = this.getBattlefieldPrintFromOutput(commands[i]);

            int expectedLeftBracketCounter = 100 - properArgumentsCount[i];
            int expectedRightBracketCounter = 100 - properArgumentsCount[i];

            int leftBracketCounter = 0;
            int rightBracketCounter = 0;

            for (int charNo = 0; charNo < battlefielAfterShots.length(); charNo++) {

                if (battlefielAfterShots.charAt(charNo) == '[' ) {
                    leftBracketCounter += 1;
                } else if (battlefielAfterShots.charAt(charNo) == ']' ) {
                    rightBracketCounter += 1;
                }
            }
            assertEquals(expectedLeftBracketCounter, leftBracketCounter, "fields don't behave properly");
            assertEquals(expectedRightBracketCounter, rightBracketCounter, "fields don't behave properly");

        }
    }
 
    @Test
    public void printingBattlefieldTest() {

        String[] commands = {"foobar"};

        assertEquals(this.mockBattlefieldPrint(), this.getBattlefieldPrintFromOutput(commands));
    }

    private String getBattlefieldPrintFromOutput(String[] commands) {
        //saving standard output variable to reset output to the console at the end of the method
        PrintStream standardOutput = System.out;

        //in order to verify output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Battlefield battlefield = new Battlefield();
        FlotillaHeadquarters flotillaHeadquarters = new FlotillaHeadquarters(battlefield);
        Cannon cannon = new Cannon();
        InputProcesser inputProcesser = new InputProcesser();

        flotillaHeadquarters.placeAllShips();

        for (String command: commands) {
            try {
                int column = inputProcesser.getColumn(command);
                int row = inputProcesser.getRow(command);
                cannon.shoot(battlefield, flotillaHeadquarters, column, row);
            } catch (UnsupportedOperationException e) {
                System.out.println("Invalid command. Choose from columns A to J and from rows 1 to 10");
            }
        }

        battlefield.printBattlefield();


        String[] outputs = outputStream.toString().split(System.lineSeparator());

        StringBuilder printedMatrix = new StringBuilder();

        for (int line = outputs.length - 11; line < outputs.length; line++) {
            printedMatrix.append(outputs[line]).append(System.lineSeparator());
        }

        System.setOut(standardOutput);

        return printedMatrix.toString();
    }

    public String mockBattlefieldPrint() {
        int[][] matrix = new int[10][10];

        StringBuilder matrixPrint = new StringBuilder();
        matrixPrint.append("    A   B   C   D   E   F   G   H   I   J").append(System.lineSeparator());

        for (int row = 0; row < matrix.length; row++) {
            if (row < 9) {
                matrixPrint.append(row + 1).append("  ");
            } else {
                matrixPrint.append(row + 1).append(" ");
            }
            for (int column = 0; column < matrix[row].length; column++) {
                matrixPrint.append(convertValues(matrix[row][column])).append(" ");
            }
            matrixPrint.append(System.lineSeparator());
        }
        return matrixPrint.toString();
    }

    private String convertValues(int value) {
        if (value < - 1) {
            return " O ";
        } else if (value == -1) {
            return " * ";
        } else {
            return "[ ]";
        }
    }




}

