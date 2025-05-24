package com.okey.game;

import com.okey.model.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals(106, deck.remainingTiles());
    }

    @Test
    void testIndicatorIsNotFakeJoker() {
        Deck deck = new Deck();
        deck.shuffle();
        deck.determineIndicatorAndOkey();
        
        Tile indicator = deck.getIndicator();
        assertFalse(indicator.isFakeJoker());
    }
} 