package com.okey;

import com.okey.game.Game;
import com.okey.game.Player;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();

        System.out.println("Gösterge taşı: " + game.getIndicator());
        
        for (Player player : game.getPlayers()) {
            System.out.printf("Oyuncu %d eli: %s (deadwood: %d)%n", 
                player.getId(), 
                player.getHand(),
                player.getDeadwoodCount());
        }

        System.out.println("\nKazanan(lar):");
        for (Player winner : game.getWinners()) {
            System.out.printf("Oyuncu %d (deadwood: %d)%n", 
                winner.getId(),
                winner.getDeadwoodCount());
        }
    }
} 