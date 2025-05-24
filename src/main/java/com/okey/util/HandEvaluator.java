package com.okey.util;

import com.okey.model.Color;
import com.okey.model.Tile;
import java.util.*;

public class HandEvaluator {
    private static final int MIN_MELD_SIZE = 3;
    private static final int MAX_SET_SIZE = 4;
    private static final int MAX_RUN_SIZE = 13;

    public static int evaluate(List<Tile> hand, Tile okeyTile) {
        // Gerçek okey değerini hesapla (gösterge + 1)
        int okeyNumber = (okeyTile.getNumber() % 13) + 1;
        Color okeyColor = okeyTile.getColor();

        // Joker ve normal taşları ayır
        List<Tile> realJokers = new ArrayList<>(); // Gerçek okey taşları
        List<Tile> fakeJokers = new ArrayList<>(); // Sahte okey taşları
        List<Tile> normalTiles = new ArrayList<>();

        for (Tile tile : hand) {
            if (tile.isFakeJoker()) {
                fakeJokers.add(tile);
            } else if (tile.isJoker() || 
                      (tile.getColor() == okeyColor && tile.getNumber() == okeyNumber)) {
                realJokers.add(tile);
            } else {
                normalTiles.add(tile);
            }
        }

        // En iyi kombinasyonu bul
        List<List<Tile>> bestMelds = new ArrayList<>();
        findBestMeldCombination(normalTiles, realJokers, fakeJokers, okeyColor, okeyNumber, 
                              new ArrayList<>(), new HashSet<>(), bestMelds);

        // Kullanılan taşları say
        Set<Tile> usedTiles = new HashSet<>();
        for (List<Tile> meld : bestMelds) {
            usedTiles.addAll(meld);
        }

        // Deadwood = Toplam taş - Kullanılan taş
        return hand.size() - usedTiles.size();
    }

    private static void findBestMeldCombination(
            List<Tile> remainingTiles,
            List<Tile> realJokers,
            List<Tile> fakeJokers,
            Color okeyColor,
            int okeyNumber,
            List<List<Tile>> currentMelds,
            Set<Tile> usedTiles,
            List<List<Tile>> bestMelds) {

        // Mevcut kombinasyon daha iyiyse güncelle
        int currentScore = getTotalSize(currentMelds);
        int bestScore = getTotalSize(bestMelds);
        if (currentScore > bestScore) {
            bestMelds.clear();
            bestMelds.addAll(currentMelds);
        }

        // Tüm olası başlangıç taşlarını dene
        for (int i = 0; i < remainingTiles.size(); i++) {
            Tile startTile = remainingTiles.get(i);
            if (usedTiles.contains(startTile)) continue;

            // Run dene
            List<Tile> run = findRun(remainingTiles, realJokers, fakeJokers, okeyColor, okeyNumber, i, usedTiles);
            if (run != null && run.size() >= MIN_MELD_SIZE) {
                // Kullanılan taşları işaretle
                Set<Tile> newUsedTiles = new HashSet<>(usedTiles);
                List<Tile> newRealJokers = new ArrayList<>(realJokers);
                List<Tile> newFakeJokers = new ArrayList<>(fakeJokers);
                
                for (Tile tile : run) {
                    if (tile.isFakeJoker()) {
                        newFakeJokers.remove(tile);
                    } else if (tile.isJoker() || 
                             (tile.getColor() == okeyColor && tile.getNumber() == okeyNumber)) {
                        newRealJokers.remove(tile);
                    } else {
                        newUsedTiles.add(tile);
                    }
                }

                currentMelds.add(run);
                findBestMeldCombination(remainingTiles, newRealJokers, newFakeJokers, 
                                      okeyColor, okeyNumber, currentMelds, newUsedTiles, bestMelds);
                currentMelds.remove(currentMelds.size() - 1);
            }

            // Set dene
            List<Tile> set = findSet(remainingTiles, realJokers, fakeJokers, okeyColor, okeyNumber, i, usedTiles);
            if (set != null && set.size() >= MIN_MELD_SIZE) {
                // Kullanılan taşları işaretle
                Set<Tile> newUsedTiles = new HashSet<>(usedTiles);
                List<Tile> newRealJokers = new ArrayList<>(realJokers);
                List<Tile> newFakeJokers = new ArrayList<>(fakeJokers);
                
                for (Tile tile : set) {
                    if (tile.isFakeJoker()) {
                        newFakeJokers.remove(tile);
                    } else if (tile.isJoker() || 
                             (tile.getColor() == okeyColor && tile.getNumber() == okeyNumber)) {
                        newRealJokers.remove(tile);
                    } else {
                        newUsedTiles.add(tile);
                    }
                }

                currentMelds.add(set);
                findBestMeldCombination(remainingTiles, newRealJokers, newFakeJokers,
                                      okeyColor, okeyNumber, currentMelds, newUsedTiles, bestMelds);
                currentMelds.remove(currentMelds.size() - 1);
            }
        }
    }

    private static List<Tile> findRun(List<Tile> tiles, List<Tile> realJokers, List<Tile> fakeJokers,
                                    Color okeyColor, int okeyNumber, int startIdx, Set<Tile> usedTiles) {
        Tile start = tiles.get(startIdx);
        if (usedTiles.contains(start)) return null;

        List<Tile> run = new ArrayList<>();
        run.add(start);

        int currentNumber = start.getNumber();
        Color color = start.getColor();
        List<Tile> availableRealJokers = new ArrayList<>(realJokers);
        List<Tile> availableFakeJokers = new ArrayList<>(fakeJokers);

        while (run.size() < MAX_RUN_SIZE) {
            currentNumber = (currentNumber % 13) + 1;
            boolean found = false;

            // Önce normal taşları dene
            for (Tile tile : tiles) {
                if (!usedTiles.contains(tile) && tile.getColor() == color && tile.getNumber() == currentNumber) {
                    run.add(tile);
                    found = true;
                    break;
                }
            }

            // Bulunamadıysa ve aranan sayı okey değeriyse, sahte okey kullan
            if (!found && currentNumber == okeyNumber && color == okeyColor && !availableFakeJokers.isEmpty()) {
                run.add(availableFakeJokers.remove(0));
                found = true;
            }
            // Bulunamadıysa gerçek okey kullan
            else if (!found && !availableRealJokers.isEmpty()) {
                run.add(availableRealJokers.remove(0));
                found = true;
            }

            if (!found) break;
        }

        return run.size() >= MIN_MELD_SIZE ? run : null;
    }

    private static List<Tile> findSet(List<Tile> tiles, List<Tile> realJokers, List<Tile> fakeJokers,
                                    Color okeyColor, int okeyNumber, int startIdx, Set<Tile> usedTiles) {
        Tile start = tiles.get(startIdx);
        if (usedTiles.contains(start)) return null;

        List<Tile> set = new ArrayList<>();
        set.add(start);

        int number = start.getNumber();
        Set<Color> usedColors = new HashSet<>();
        usedColors.add(start.getColor());
        List<Tile> availableRealJokers = new ArrayList<>(realJokers);
        List<Tile> availableFakeJokers = new ArrayList<>(fakeJokers);

        // Önce normal taşları ekle
        for (Tile tile : tiles) {
            if (!usedTiles.contains(tile) && tile.getNumber() == number && !usedColors.contains(tile.getColor())) {
                set.add(tile);
                usedColors.add(tile.getColor());
            }
        }

        // Eksik renkleri tamamla
        while (set.size() < MAX_SET_SIZE && set.size() < MIN_MELD_SIZE) {
            // Eğer aranan sayı okey değeriyse ve rengi henüz kullanılmamışsa, sahte okey kullan
            if (number == okeyNumber && !usedColors.contains(okeyColor) && !availableFakeJokers.isEmpty()) {
                set.add(availableFakeJokers.remove(0));
                usedColors.add(okeyColor);
            }
            // Değilse ve gerçek okey varsa onu kullan
            else if (!availableRealJokers.isEmpty()) {
                set.add(availableRealJokers.remove(0));
                // Gerçek okey herhangi bir renk olabilir
                for (Color color : Color.values()) {
                    if (!usedColors.contains(color)) {
                        usedColors.add(color);
                        break;
                    }
                }
            }
            // Hiç joker kalmadıysa çık
            else {
                break;
            }
        }

        return set.size() >= MIN_MELD_SIZE ? set : null;
    }

    private static int getTotalSize(List<List<Tile>> melds) {
        Set<Tile> uniqueTiles = new HashSet<>();
        for (List<Tile> meld : melds) {
            uniqueTiles.addAll(meld);
        }
        return uniqueTiles.size();
    }
} 