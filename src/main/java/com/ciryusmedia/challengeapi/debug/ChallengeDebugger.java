package com.ciryusmedia.challengeapi.debug;

import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

public class ChallengeDebugger {

    private static final ChallengeDebugger DEBUGGER = new ChallengeDebugger();

    public String messagePrefix = "";
    private int debugLevel = 0;

    public void log(String message, int messageDebuglevel) {
        ChatColor color = DebugLevel.debugColor(messageDebuglevel);
        if (messageDebuglevel <= debugLevel) {
            getServer().getConsoleSender().sendMessage(messagePrefix + color + message);
        }
    }

    /**
     * Sets the message prefix of debug messages to your chosen prefix
     * @param messagePrefix A message prefix
     */
    public void setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    /**
     * Sets the level of debug, how "deep" the debug messages should go
     * @param debugLevel DebugLevel
     */
    public void setDebugLevel(int debugLevel) {
        this.debugLevel = debugLevel;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    public ChallengeDebugger() {

    }

    public static ChallengeDebugger getDebugger() {
        return DEBUGGER;
    }

}
