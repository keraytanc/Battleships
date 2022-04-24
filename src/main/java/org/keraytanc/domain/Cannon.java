package org.keraytanc.domain;

public class Cannon {

    public void shoot(Battlefield battlefield, FlotillaHeadquarters flotillaHeadquarters, int column, int row) {

        int fieldValue = battlefield.getValue(column, row);

        if (fieldValue == 0) {
            battlefield.setValue(column, row, -1);
        } else if (fieldValue == 1) {
            flotillaHeadquarters.setBattleship0Life(flotillaHeadquarters.getBattleship0Life() - 1);
            battlefield.setValue(column, row, -2);
        } else if (fieldValue == 2) {
            flotillaHeadquarters.setDestroyer1Life(flotillaHeadquarters.getDestroyer1Life() - 1);
            battlefield.setValue(column, row, -2);
        } else if (fieldValue == 3) {
            flotillaHeadquarters.setDestroyer2Life(flotillaHeadquarters.getDestroyer2Life() - 1);
            battlefield.setValue(column, row, -2);
        }
    }
}
