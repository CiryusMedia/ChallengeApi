package com.ciryusmedia.challengeapi.listener.challenge.random;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import com.ciryusmedia.challengeapi.challenge.Challenge;
import com.ciryusmedia.challengeapi.debug.DebugLevel;
import com.ciryusmedia.challengeapi.listener.challenge.ChallengeListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public abstract class RandomEntityDeathListener extends ChallengeListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!challenge.isEnabled() || !timer.isRunning() || event.getEntityType().equals(EntityType.BLAZE))
            return;

        debugger.log(event.getEventName(), DebugLevel.LEVEL_3);

        handleRandomEntityDeathLogic(event);
    }

    public abstract void handleRandomEntityDeathLogic(EntityDeathEvent event);

    public RandomEntityDeathListener(ChallengePlugin plugin, Challenge challenge) {
        super(plugin, challenge);
    }
}
