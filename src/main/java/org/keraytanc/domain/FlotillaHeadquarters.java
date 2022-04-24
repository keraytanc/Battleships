package org.keraytanc.domain;

import java.util.Random;

public class FlotillaHeadquarters {

    private final int[] eachShipsLife;
    private final Battlefield battlefield;

    public FlotillaHeadquarters(Battlefield battlefield) {
        this.eachShipsLife = new int[3];
        this.battlefield = battlefield;
    }

    public void placeAllShips() {
        this.createBattleship0();
        this.createDestroyer1();
        this.createDestroyer2();
    }

    public boolean allShipsSunk() {
        for (Integer shipsLife: this.eachShipsLife) {
            if (shipsLife != 0) {
                return false;
            }
        }
        return true;
    }

    public int getBattleship0Life() {
        return this.eachShipsLife[0];
    }
    protected void setBattleship0Life(int life) {
        this.eachShipsLife[0] = life;
    }

    public int getDestroyer1Life() {
        return this.eachShipsLife[1];
    }
    protected void setDestroyer1Life(int life) {
        this.eachShipsLife[1] = life;
    }

    public int getDestroyer2Life() {
        return this.eachShipsLife[2];
    }
    protected void setDestroyer2Life(int life) {
        this.eachShipsLife[2] = life;
    }

    private void createBattleship0() {
        this.createShip(5, 1);
        this.setBattleship0Life(5);
    }

    private void createDestroyer1() {
        this.createShip(4, 2);
        this.setDestroyer1Life(4);
    }

    private void createDestroyer2() {
        this.createShip(4, 3);
        this.setDestroyer2Life(4);
    }

    private void createShip(int length, int shipsFieldValue) {

        Random randomer = new Random();

        while (true) {

            // generating position of the head of the ship. Ship can't exceed matrix so one number shorter
            boolean shipIsHorizontal = randomer.nextBoolean();
            int axis1 = randomer.nextInt(10);
            int axis2 = randomer.nextInt(11 - length);

            if (shipsFieldsAreEmpty(length, shipIsHorizontal, axis1, axis2)) {
                placeShip(length, shipsFieldValue, shipIsHorizontal, axis1, axis2);
                break;
            }
        }
    }

    private boolean shipsFieldsAreEmpty(int length, boolean isHorizontal, int axis1, int axis2) {

        if (isHorizontal) {
            for (int i = 0; i < length; i++) {
                if (this.battlefield.getValue((axis2 + i), axis1) != 0) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (this.battlefield.getValue(axis1, (axis2 + i)) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeShip(int length, int shipsFieldValue, boolean isHorizontal, int axis1, int axis2) {

        if (isHorizontal) {
            for (int i = 0; i < length; i++) {
                this.battlefield.setValue((axis2 + i), axis1, shipsFieldValue);
            }
        } else {
            for (int i = 0; i < length; i++) {
                this.battlefield.setValue(axis1, (axis2 + i), shipsFieldValue);
            }
        }
    }
}
