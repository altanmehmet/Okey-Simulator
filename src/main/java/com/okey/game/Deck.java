package com.okey.game;

import com.okey.model.Color;
import com.okey.model.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Tile> tiles;
    private Tile indicator;

    public Deck() {
        tiles = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        // Her renk ve sayıdan ikişer tane oluştur
        for (Color color : Color.values()) {
            for (int number = 1; number <= 13; number++) {
                tiles.add(new Tile(color, number));
                tiles.add(new Tile(color, number));
            }
        }
        
        // İki adet sahte okey ekle
        Tile fakeJoker1 = new Tile(Color.BLACK, 0);
        Tile fakeJoker2 = new Tile(Color.BLACK, 0);
        fakeJoker1.setFakeJoker(true);
        fakeJoker2.setFakeJoker(true);
        tiles.add(fakeJoker1);
        tiles.add(fakeJoker2);
    }

    public void shuffle() {
        Collections.shuffle(tiles);
    }

    public Tile drawTile() {
        if (tiles.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return tiles.remove(tiles.size() - 1);
    }

    public void determineIndicatorAndOkey() {
        Tile drawn;
        do {
            drawn = drawTile();
        } while (drawn.isFakeJoker());
        
        this.indicator = drawn;
        
        // Gösterge taşının aynı renkteki bir üst sayısı okey olarak belirlenir
        int okeyNumber = (indicator.getNumber() % 13) + 1;
        for (Tile tile : tiles) {
            if (tile.getColor() == indicator.getColor() && tile.getNumber() == okeyNumber) {
                tile.setJoker(true);
            }
        }
    }

    public Tile getIndicator() {
        return indicator;
    }

    public int remainingTiles() {
        return tiles.size();
    }
} 