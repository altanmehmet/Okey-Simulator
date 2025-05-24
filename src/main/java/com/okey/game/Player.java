package com.okey.game;

import com.okey.model.Tile;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private final List<Tile> hand;
    private int deadwoodCount;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.deadwoodCount = 0;
    }

    public void addTile(Tile tile) {
        hand.add(tile);
    }

    public List<Tile> getHand() {
        return new ArrayList<>(hand);
    }

    public int getId() {
        return id;
    }

    public void setDeadwoodCount(int count) {
        this.deadwoodCount = count;
    }

    public int getDeadwoodCount() {
        return deadwoodCount;
    }
} 