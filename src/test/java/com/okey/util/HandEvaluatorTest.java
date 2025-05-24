package com.okey.util;

import com.okey.model.Color;
import com.okey.model.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class HandEvaluatorTest {

    @Test
    public void testFakeJokerInValidPosition() {
        // Gösterge taşı: Kırmızı-5 (yani okey Kırmızı-6)
        Tile okeyTile = new Tile(Color.RED, 5);
        
        List<Tile> hand = new ArrayList<>();
        // Kırmızı-4, Kırmızı-5, Sahte-Okey(Kırmızı-6 yerine), Kırmızı-7
        hand.add(new Tile(Color.RED, 4));
        hand.add(new Tile(Color.RED, 5));
        hand.add(new Tile(Color.RED, 6, true)); // Sahte Okey
        hand.add(new Tile(Color.RED, 7));

        int deadwood = HandEvaluator.evaluate(hand, okeyTile);
        assertEquals(0, deadwood, "Sahte okey doğru pozisyonda kullanıldığında deadwood 0 olmalı");
    }

    @Test
    public void testFakeJokerInInvalidPosition() {
        // Gösterge taşı: Kırmızı-5 (yani okey Kırmızı-6)
        Tile okeyTile = new Tile(Color.RED, 5);
        
        List<Tile> hand = new ArrayList<>();
        // Mavi-4, Mavi-5, Sahte-Okey(Mavi-6 yerine kullanılamaz), Mavi-7
        hand.add(new Tile(Color.BLUE, 4));
        hand.add(new Tile(Color.BLUE, 5));
        hand.add(new Tile(Color.RED, 6, true)); // Sahte Okey
        hand.add(new Tile(Color.BLUE, 7));

        int deadwood = HandEvaluator.evaluate(hand, okeyTile);
        assertEquals(4, deadwood, "Sahte okey yanlış pozisyonda kullanıldığında deadwood 4 olmalı");
    }

    @Test
    public void testRealJokerVsFakeJoker() {
        // Gösterge taşı: Kırmızı-5 (yani okey Kırmızı-6)
        Tile okeyTile = new Tile(Color.RED, 5);
        
        List<Tile> hand = new ArrayList<>();
        // Set: Sarı-8, Mavi-8, Gerçek-Okey(herhangi renk-8 olarak), Sahte-Okey(sadece Kırmızı-6 olarak kullanılabilir)
        hand.add(new Tile(Color.YELLOW, 8));
        hand.add(new Tile(Color.BLUE, 8));
        hand.add(new Tile(Color.RED, 6)); // Gerçek Okey
        hand.add(new Tile(Color.RED, 6, true)); // Sahte Okey

        int deadwood = HandEvaluator.evaluate(hand, okeyTile);
        assertEquals(1, deadwood, "Sahte okey yanlış yerde kullanıldığında ve gerçek okey doğru kullanıldığında deadwood 1 olmalı");
    }

    @Test
    public void testMultipleFakeJokers() {
        // Gösterge taşı: Kırmızı-5 (yani okey Kırmızı-6)
        Tile okeyTile = new Tile(Color.RED, 5);
        
        List<Tile> hand = new ArrayList<>();
        // Run: Kırmızı-4, Kırmızı-5, Sahte-Okey(Kırmızı-6), Kırmızı-7
        // İkinci Sahte-Okey başka yerde kullanılamaz
        hand.add(new Tile(Color.RED, 4));
        hand.add(new Tile(Color.RED, 5));
        hand.add(new Tile(Color.RED, 6, true)); // Sahte Okey 1
        hand.add(new Tile(Color.RED, 7));
        hand.add(new Tile(Color.RED, 6, true)); // Sahte Okey 2

        int deadwood = HandEvaluator.evaluate(hand, okeyTile);
        assertEquals(1, deadwood, "İkinci sahte okey kullanılamadığında deadwood 1 olmalı");
    }

    @Test
    public void testComplexHandWithBothJokers() {
        // Gösterge taşı: Siyah-10 (yani okey Siyah-11)
        Tile okeyTile = new Tile(Color.BLACK, 10);
        
        List<Tile> hand = new ArrayList<>();
        // Set: Sarı-7, Mavi-7, Kırmızı-7
        hand.add(new Tile(Color.YELLOW, 7));
        hand.add(new Tile(Color.BLUE, 7));
        hand.add(new Tile(Color.RED, 7));
        
        // Run: Siyah-9, Siyah-10, Sahte-Okey(Siyah-11), Siyah-12
        hand.add(new Tile(Color.BLACK, 9));
        hand.add(new Tile(Color.BLACK, 10));
        hand.add(new Tile(Color.BLACK, 11, true)); // Sahte Okey
        hand.add(new Tile(Color.BLACK, 12));
        
        // Gerçek Okey herhangi bir yerde kullanılabilir
        hand.add(new Tile(Color.BLACK, 11)); // Gerçek Okey

        int deadwood = HandEvaluator.evaluate(hand, okeyTile);
        assertEquals(0, deadwood, "Kompleks elde tüm taşlar kullanıldığında deadwood 0 olmalı");
    }
} 