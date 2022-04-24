package org.keraytanc.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keraytanc.logic.GameTest;
import org.keraytanc.logic.InputProcesser;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CannonTest {

    private Cannon cannon;
    private Battlefield battlefield;
    private FlotillaHeadquarters flotillaHeadquarters;

    @BeforeEach
    public void init() {
        this.cannon = new Cannon();
        this.battlefield = new Battlefield();
        this.flotillaHeadquarters = new FlotillaHeadquarters(this.battlefield);
    }

    @Test
    public void shoot() {

        String[] validInputs = GameTest.getValidInputs();
        InputProcesser inputProcesser = new InputProcesser();

        for (String input: validInputs) {

            try {
                int column = inputProcesser.getColumn(input);
                int row = inputProcesser.getRow(input);

                this.cannon.shoot(this.battlefield, this.flotillaHeadquarters, column, row);
                int currentValue = this.battlefield.getValue(column, row);

                assertEquals(-1, currentValue);

            } catch (UnsupportedOperationException e) {
                fail("valid input rejected by InputProcesser class");
            }
        }
    }

    @Test
    public void shootAndMiss() {

        testShootEffects(0);
    }

    @Test
    public void shootAndHitBattleship() {

        this.flotillaHeadquarters.placeAllShips();

        testShootEffects(1);
    }

    @Test
    public void shootAndHitDestroyer1() {

        this.flotillaHeadquarters.placeAllShips();

        testShootEffects(2);
    }

    @Test
    public void shootAndHitDestroyer2() {

        this.flotillaHeadquarters.placeAllShips();

        testShootEffects(3);
    }

    public void testShootEffects(int targetValue) {

        Random randomer = new Random();
        InputProcesser inputProcesser = new InputProcesser();

        String[] validInputs = GameTest.getValidInputs();

        int shoots = 0;

        while (shoots < 3) {

            int argumentPosition = randomer.nextInt(validInputs.length);

            String input = validInputs[argumentPosition];

            try {
                int column = inputProcesser.getColumn(input);
                int row = inputProcesser.getRow(input);

                int fieldValue = this.battlefield.getValue(column, row);

                if (targetValue == fieldValue) {

                    this.cannon.shoot(this.battlefield, this.flotillaHeadquarters, column, row);

                    int afterShootingValue = this.battlefield.getValue(column, row);

                    if (targetValue == -2) {
                        assertEquals(-2, afterShootingValue, "shooting hit again didn't work properly");
                    } else if (targetValue == -1) {
                        assertEquals(-2, afterShootingValue, "shooting miss again didn't work properly");
                    } else if (targetValue == 0) {
                        assertEquals(-1, afterShootingValue, "missing shot didn't work properly");
                    } else if (targetValue > 1) {
                        assertEquals(-2, afterShootingValue, "hiting shot didn't work properly");
                    }
                    shoots += 1;
                }
            } catch (UnsupportedOperationException e) {
                fail("valid input rejected by InputProcesser class");
            }
        }
    }
}

