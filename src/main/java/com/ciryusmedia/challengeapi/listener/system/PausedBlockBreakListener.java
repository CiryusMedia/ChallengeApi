package com.ciryusmedia.challengeapi.listener.system;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import com.ciryusmedia.challengeapi.debug.ChallengeDebugger;
import com.ciryusmedia.challengeapi.debug.DebugLevel;
import com.ciryusmedia.challengeapi.timer.ChallengeTimer;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class PausedBlockBreakListener implements Listener {

    ChallengePlugin plugin;
    ChallengeDebugger debugger = ChallengeDebugger.getDebugger();
    ChallengeTimer timer = plugin.getTimer();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!timer.isRunning()) {
            debugger.log("Cancelling block breaking", DebugLevel.LEVEL_3);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        if (!timer.isRunning()) {
            debugger.log("Cancelling leaves decaying", DebugLevel.LEVEL_3);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (!timer.isRunning()) {
            debugger.log("Cancelling blocks exploding", DebugLevel.LEVEL_3);
            e.setYield(0);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent e) {
        if (!timer.isRunning()) {
            debugger.log("Cancelling block being destroyed", DebugLevel.LEVEL_3);
            e.setCancelled(true);
        }
    }

    public PausedBlockBreakListener(ChallengePlugin plugin) {
        this.plugin = plugin;
    }

}