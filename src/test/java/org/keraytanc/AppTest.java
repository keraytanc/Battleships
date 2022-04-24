package org.keraytanc;

import org.junit.jupiter.api.Test;
import org.keraytanc.logic.Game;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppTest {

    @Test
    public void createAndStartGame() {
        String randomInput = "foobar";

        InputStream in = new ByteArrayInputStream(randomInput.getBytes());
        System.setIn(in);

        Game theGame = new Game();

        assertThrows(NoSuchElementException.class, () -> theGame.start());
    }
}
