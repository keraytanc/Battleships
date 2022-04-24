package org.keraytanc.logic;

public class InputProcesser {

    public int getColumn(String input) throws UnsupportedOperationException {

        input = input.trim().toUpperCase();

        if (this.isValidInput(input)) {

            int column = (int) input.charAt(0) - 65;
            return column;

        } else {
            throw new UnsupportedOperationException();
        }
    }

    public int getRow(String input) throws UnsupportedOperationException {

        input = input.trim().toUpperCase();

        if (this.isValidInput(input)) {

            int row = Integer.parseInt(input.substring(1)) - 1;
            return row;

        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean isValidInput(String input) {

        input = input.trim();

        return input.matches("[A-Ja-j]{1}([1-9]{1}|10)");
    }
}
