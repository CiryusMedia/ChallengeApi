package com.ciryusmedia.challengeapi;

import com.ciryusmedia.challengeapi.timer.ChallengeTimer;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ChallengePlugin extends JavaPlugin {

    protected ChallengeTimer timer;

    public ChallengeTimer getTimer() {
        return timer;
    }
}
