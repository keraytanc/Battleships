package org.keraytanc.domain;

public class Battlefield {

    private final int[][] matrix = new int[10][10];

    public int getValue(int column, int row) {
        return this.matrix[row][column];
    }

    protected void setValue(int column, int row, int value) {
        this.matrix[row][column] = value;
    }

    public void printBattlefield() {

        StringBuilder matrixPrint = new StringBuilder();
        matrixPrint.append("    A   B   C   D   E   F   G   H   I   J").append(System.lineSeparator());

        for (int row = 0; row < matrix.length; row++) {
            if (row < 9) {
                matrixPrint.append(row + 1).append("  ");
            } else {
                matrixPrint.append(row + 1).append(" ");
            }
            for (int column = 0; column < matrix[row].length; column++) {
                matrixPrint.append(convertBattlefieldValuesToString(this.getValue(column, row))).append(" ");
            }
            matrixPrint.append(System.lineSeparator());
        }
        System.out.println(matrixPrint);
    }

    private String convertBattlefieldValuesToString(int value) {
        if (value < -1) {
            return " O ";
        } else if (value == -1) {
            return " * ";
        } else {
            return "[ ]";
        }
    }
}
