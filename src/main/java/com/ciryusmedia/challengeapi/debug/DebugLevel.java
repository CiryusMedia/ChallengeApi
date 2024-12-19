package com.ciryusmedia.challengeapi.debug;

import org.bukkit.ChatColor;

public interface DebugLevel {
    int LEVEL_0 = 0; //None
    int LEVEL_1 = 1; //Basic (Gold)
    int LEVEL_2 = 2; //A little bit of extra info on startup (Yellow)
    int LEVEL_3 = 3; //Extra info for logic(Aqua)
    int LEVEL_4 = 4; //In depth info for logic(Blue)
    int LEVEL_5 = 5; //Even more in depth logic which is deemed nice to have but not necessary (Dark Aqua)
    int FULL = 99; //Everything

    static ChatColor debugColor(int level) {
        return switch (level) {
            case LEVEL_0 -> ChatColor.GREEN;
            case LEVEL_1 -> ChatColor.GOLD;
            case LEVEL_2 -> ChatColor.YELLOW;
            case LEVEL_3 -> ChatColor.AQUA;
            case LEVEL_4 -> ChatColor.BLUE;
            case LEVEL_5 -> ChatColor.DARK_AQUA;
            default -> ChatColor.RESET;
        };
    }
}
