package com.okey.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    @Test
    void testCreation() {
        Tile tile = new Tile(Color.RED, 5);
        assertEquals(Color.RED, tile.getColor());
        assertEquals(5, tile.getNumber());
        assertFalse(tile.isJoker());
        assertFalse(tile.isFakeJoker());
    }

    @Test
    void testInvalidTileNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Tile(Color.RED, -1));
        assertThrows(IllegalArgumentException.class, () -> new Tile(Color.RED, 14));
    }

    @Test
    void testToString() {
        Tile tile = new Tile(Color.RED, 5);
        assertEquals("RED-5", tile.toString());
    }

    @Test
    void testJokerFlag() {
        Tile tile = new Tile(Color.RED, 5);
        tile.setJoker(true);
        assertTrue(tile.isJoker());
        assertEquals("Gerçek-Okey", tile.toString());
    }

    @Test
    void testFakeJokerFlag() {
        Tile tile = new Tile(Color.RED, 5, true);
        assertTrue(tile.isFakeJoker());
        assertEquals("Sahte-Okey", tile.toString());
    }

    @Test
    void testEquality() {
        Tile tile1 = new Tile(Color.RED, 5);
        Tile tile2 = new Tile(Color.RED, 5);
        Tile tile3 = new Tile(Color.BLUE, 5);
        
        assertEquals(tile1, tile2);
        assertNotEquals(tile1, tile3);
        
        tile1.setJoker(true);
        assertNotEquals(tile1, tile2);
    }

    @Test
    void testHashCode() {
        Tile tile1 = new Tile(Color.RED, 5);
        Tile tile2 = new Tile(Color.RED, 5);
        
        assertEquals(tile1.hashCode(), tile2.hashCode());
    }
} 