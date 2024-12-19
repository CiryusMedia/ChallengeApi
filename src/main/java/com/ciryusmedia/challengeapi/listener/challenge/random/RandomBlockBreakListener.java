package com.ciryusmedia.challengeapi.listener.challenge.random;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import com.ciryusmedia.challengeapi.challenge.Challenge;
import com.ciryusmedia.challengeapi.debug.DebugLevel;
import com.ciryusmedia.challengeapi.listener.challenge.ChallengeListener;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.ExplosionResult;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class RandomBlockBreakListener extends ChallengeListener implements Listener {

    public RandomBlockBreakListener(ChallengePlugin plugin, Challenge challenge) {
        super(plugin, challenge);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Block b = e.getBlock();
        boolean badBisectedHalf = false;

        //Shit for blocks with 2 block in volume
        if (timer.isRunning() && challenge.isEnabled()) {
            debugger.log(e.getEventName(), DebugLevel.LEVEL_3);
            debugger.log(b.translationKey() + " " + b.getType().translationKey() + " "
                    + b.getLocation().getBlockX() + b.getLocation().getBlockY() + b.getLocation().getBlockZ(), DebugLevel.LEVEL_5);
            debugger.log(challenge.getDisplayName() + " is " + challenge.isEnabled(), DebugLevel.LEVEL_5);
            debugger.log("Timer is " + timer.isRunning(), DebugLevel.LEVEL_5);

            //Code for the "top" part of bisected blocks
            if (b.getBlockData() instanceof Bed bedData) {
                debugger.log("Block is a bed", DebugLevel.LEVEL_4);
                debugger.log(bedData.getPart().toString(), DebugLevel.LEVEL_4);
                if (bedData.getPart() == Bed.Part.FOOT) {
                    bedData.setPart(Bed.Part.HEAD);
                    b.setBlockData(bedData);
                    badBisectedHalf = true;
                }
            } else if (b.getBlockData() instanceof Bisected bisectedData) {
                debugger.log("Block is bisected", DebugLevel.LEVEL_4);
                debugger.log(bisectedData.getHalf().toString(), DebugLevel.LEVEL_4);
                if (bisectedData.getHalf() == Bisected.Half.TOP) {
                    badBisectedHalf = true;
                    b.setType(Material.AIR);
                }
            } else {
                debugger.log("Block is not bisected", DebugLevel.LEVEL_4);
            }
        } else {
            debugger.log(challenge.getDisplayName() + " is " + challenge.isEnabled(), DebugLevel.LEVEL_5);
            debugger.log("Timer is " + timer.isRunning(), DebugLevel.LEVEL_5);
        }

        Collection<ItemStack> orgDrops = b.getDrops(p.getInventory().getItemInMainHand());

        if (!orgDrops.isEmpty() && !badBisectedHalf) {
            debugger.log(orgDrops.toString(), DebugLevel.LEVEL_3);
            handleRandomBlocks(b, orgDrops);
        } else {
            debugger.log("Not dropping...", DebugLevel.LEVEL_3);
        }

        if (challenge.isEnabled())
            e.setDropItems(false); //Cancel drop of the original itemdrops
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {

        Block block = e.getBlock();

        if (challenge.isEnabled()) {
            debugger.log(e.getEventName(), DebugLevel.LEVEL_3);
            debugger.log(e.getEventName() + " cancelled", DebugLevel.LEVEL_3);
            e.setCancelled(true);

            BlockData blockData = block.getBlockData();
            BlockDestroyEvent blockDestroyEvent = new BlockDestroyEvent(block, blockData, blockData, 0, true); //Sending a new BlockDestroyEvent, it's just easier to handle :)
            blockDestroyEvent.callEvent();

            debugger.log("Setting block to air", DebugLevel.LEVEL_3);
            block.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        float y = e.getYield();
        List<Block> bs = e.blockList();

        if (challenge.isEnabled()
                //Only call the code if the explosion actually destroys blocks (unlike the wind charge)
                && (e.getExplosionResult().equals(ExplosionResult.DESTROY)
                || e.getExplosionResult().equals(ExplosionResult.DESTROY_WITH_DECAY))) {

            debugger.log(e.getEventName(), DebugLevel.LEVEL_3);
            debugger.log(String.valueOf(y), DebugLevel.LEVEL_4);
            debugger.log(e.getExplosionResult().toString(), DebugLevel.LEVEL_4);
            debugger.log(e.getEntityType().toString(), DebugLevel.LEVEL_4);

            for (Block b : bs) {
                Random r = new Random();
                if (r.nextInt(5) < y) //Blocks drop with a 5:yield chance
                    handleRandomBlocks(b);
                e.setYield(0); //Cancel drop of the original itemdrops (setting the yield to 0 so that the original drops have a 0% chance of dropping)
            }
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent e) {
        Block b = e.getBlock();

        if (challenge.isEnabled() && timer.isRunning()) {
            debugger.log(e.getEventName() + " " + b.getType().name() + " " + b.getType().translationKey() + " "
                    + b.getLocation().getBlockX() + b.getLocation().getBlockY() + b.getLocation().getBlockZ(), DebugLevel.LEVEL_5);
            e.setWillDrop(false); //Cancel drop of the original itemdrops
        }

        handleRandomBlocks(b);
    }

    public abstract void handleRandomBlocks(Block block, Collection<ItemStack> drops);

    public void handleRandomBlocks(Block block) {
        handleRandomBlocks(block, block.getDrops());
    }

}
