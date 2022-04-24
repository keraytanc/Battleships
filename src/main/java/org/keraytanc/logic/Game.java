package org.keraytanc.logic;

import org.keraytanc.domain.Battlefield;
import org.keraytanc.domain.Cannon;
import org.keraytanc.domain.FlotillaHeadquarters;

import java.util.Scanner;

public class Game {

    private final Battlefield battlefield = new Battlefield();
    private final Cannon cannon = new Cannon();
    private final InputProcesser inputProcesser = new InputProcesser();
    private final FlotillaHeadquarters flotillaHeadquarters = new FlotillaHeadquarters(this.battlefield);


    public void start() {

        this.setUpGame();

        Scanner reader = new Scanner(System.in);

        while (true) {

            String command = reader.nextLine().trim().toUpperCase();

            if (command.equals("X")) {
                break;
            } else {
                this.processCommand(command);

                if (this.flotillaHeadquarters.allShipsSunk()) {
                    System.out.print("You won!");
                    break;
                }
            }
        }
    }

    private void setUpGame() {

        this.flotillaHeadquarters.placeAllShips();

        String instructions = "Welcome to the Battleship game!"
                + System.lineSeparator()
                + "Your task is to sink enemy's flotilla consisting of 3 ships; 1 Battleship and 2 Destroyers."
                + System.lineSeparator()
                + "To shoot enter the coordinates in the form of XY where X is a letter from A to J representing"
                + System.lineSeparator()
                + "columns of a 10x10 grid and Y is a number from 1 to 10 representing rows. Good luck!"
                + System.lineSeparator();

        System.out.println(instructions);
        this.battlefield.printBattlefield();
    }

    private void processCommand(String command) {

        try {
            command = command.trim().toUpperCase();

            //inputProcesser's get methods throw UnsupportedOperationException if wrong command
            int column = inputProcesser.getColumn(command);
            int row = inputProcesser.getRow(command);
            int fieldValue = this.battlefield.getValue(column, row);

            this.cannon.shoot(this.battlefield, this.flotillaHeadquarters, column, row);

            this.printShotEffects(fieldValue);
            battlefield.printBattlefield();

        } catch (UnsupportedOperationException e) {
            System.out.println("Invalid command. Choose from columns A to J and from rows 1 to 10");
        }
    }

    private void printShotEffects(int fieldValue) {

        if (fieldValue < 0) {
            System.out.println("Already shot");
        } else if (fieldValue == 0) {
            System.out.println("Miss");
        } else if (fieldValue == 1) {
            System.out.println("Battleship is hit!!!");
            if (this.flotillaHeadquarters.getBattleship0Life() == 0) {
                System.out.println("Battleship has sunk");
            }
        } else if (fieldValue == 2) {
            System.out.println("Destroyer 1 is hit!!!");
            if (this.flotillaHeadquarters.getDestroyer1Life() == 0) {
                System.out.println("Destroyer 1 has sunk");
            }
        } else if (fieldValue == 3) {
            System.out.println("Destroyer 2 is hit!!!");
            if (this.flotillaHeadquarters.getDestroyer2Life() == 0) {
                System.out.println("Destroyer 2 has sunk");
            }
        }
    }
}
