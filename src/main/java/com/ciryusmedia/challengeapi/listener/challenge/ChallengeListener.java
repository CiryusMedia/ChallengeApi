package com.ciryusmedia.challengeapi.listener.challenge;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import com.ciryusmedia.challengeapi.challenge.Challenge;
import com.ciryusmedia.challengeapi.timer.ChallengeTimer;

public abstract class ChallengeListener {

    protected ChallengePlugin plugin;
    protected ChallengeTimer timer;
    protected Challenge challenge;

    public ChallengeListener(ChallengePlugin plugin, Challenge challenge) {
        this.plugin = plugin;
        this.timer = plugin.getTimer();
        this.challenge = challenge;
    }

}
