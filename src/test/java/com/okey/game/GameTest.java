package com.okey.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(4, game.getPlayers().size());
    }

    @Test
    void testTileDistribution() {
        Game game = new Game();
        game.play();

        int totalTiles = 0;
        boolean found15TilePlayer = false;

        for (Player player : game.getPlayers()) {
            int handSize = player.getHand().size();
            totalTiles += handSize;
            
            if (handSize == 15) {
                found15TilePlayer = true;
            } else {
                assertEquals(14, handSize);
            }
        }

        assertTrue(found15TilePlayer);
        assertEquals(57, totalTiles);
    }

    @Test
    void testIndicatorNotNull() {
        Game game = new Game();
        game.play();
        assertNotNull(game.getIndicator());
    }

    @Test
    void testWinnerSelection() {
        Game game = new Game();
        game.play();
        
        assertFalse(game.getWinners().isEmpty());
        assertTrue(game.getWinners().size() <= 4);
        
        int winningDeadwood = game.getWinners().get(0).getDeadwoodCount();
        for (Player player : game.getPlayers()) {
            assertTrue(player.getDeadwoodCount() >= winningDeadwood);
        }
    }
} 