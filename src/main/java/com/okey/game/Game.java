package com.okey.game;

import com.okey.model.Tile;
import com.okey.util.HandEvaluator;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final Deck deck;
    private final List<Player> players;
    private Tile indicator;

    public Game() {
        this.deck = new Deck();
        this.players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player(i));
        }
    }

    public void play() {
        // Desteyi karıştır
        deck.shuffle();

        // Gösterge ve okey belirle
        deck.determineIndicatorAndOkey();
        indicator = deck.getIndicator();

        // Rastgele bir oyuncuya 15, diğerlerine 14 taş dağıt
        int startingPlayer = new Random().nextInt(4);
        for (int i = 0; i < 4; i++) {
            int tilesToDraw = (i == startingPlayer) ? 15 : 14;
            Player player = players.get(i);
            for (int j = 0; j < tilesToDraw; j++) {
                player.addTile(deck.drawTile());
            }
        }

        // Elleri değerlendir
        evaluateHands();
    }

    private void evaluateHands() {
        for (Player player : players) {
            int deadwood = HandEvaluator.evaluate(player.getHand(), indicator);
            player.setDeadwoodCount(deadwood);
        }
    }

    public List<Player> getWinners() {
        int minDeadwood = players.stream()
                .mapToInt(Player::getDeadwoodCount)
                .min()
                .orElse(Integer.MAX_VALUE);

        return players.stream()
                .filter(p -> p.getDeadwoodCount() == minDeadwood)
                .collect(Collectors.toList());
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Tile getIndicator() {
        return indicator;
    }
} 