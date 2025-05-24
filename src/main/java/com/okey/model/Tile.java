package com.okey.model;

import java.util.Objects;

public class Tile {
    private Color color;
    private int number;
    private boolean isJoker;
    private boolean isFakeJoker;

    public Tile(Color color, int number) {
        if (number < 0 || number > 13) {
            throw new IllegalArgumentException("Tile number must be between 0 and 13");
        }
        this.color = color;
        this.number = number;
        this.isJoker = false;
        this.isFakeJoker = false;
    }

    public Tile(Color color, int number, boolean isFakeJoker) {
        this.color = color;
        this.number = number;
        this.isJoker = false;
        this.isFakeJoker = isFakeJoker;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public boolean isJoker() {
        return isJoker;
    }

    public void setJoker(boolean joker) {
        isJoker = joker;
    }

    public boolean isFakeJoker() {
        return isFakeJoker;
    }

    public void setFakeJoker(boolean fakeJoker) {
        this.isFakeJoker = fakeJoker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && 
               color == tile.color && 
               isJoker == tile.isJoker && 
               isFakeJoker == tile.isFakeJoker;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, number, isJoker, isFakeJoker);
    }

    @Override
    public String toString() {
        if (isJoker) {
            return "Gerçek-Okey";
        } else if (isFakeJoker) {
            return "Sahte-Okey";
        }
        return color + "-" + number;
    }
} 