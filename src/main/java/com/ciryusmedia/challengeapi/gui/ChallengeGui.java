package com.ciryusmedia.challengeapi.gui;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ChallengeGui implements Listener {

    protected Inventory inv;

    protected ItemStack filler;
    protected ItemStack lineFiller;
    protected ItemStack exit;

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (!e.getInventory().equals(inv) || e.getCurrentItem() == null)
            return;

        e.setCancelled(true); //Player can't take items out of the inventory

        if (item.equals(filler) || item.equals(lineFiller))
            return;

        player.playSound(player, Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 100, 1);

        inventoryClickHandler(item, player);

        if (item.equals(exit))
            exitMenu(player);

        updateMenu();
    }

    /**
     *
     * @param item
     * @param player
     */
    public abstract void inventoryClickHandler(ItemStack item, Player player);

    /**
     * Exit the current menu. Should be overwritten to e.g. allow players to go
     * back into the main menu if they are in a sub menu<br>
     * <br>
     * Default: Closes the current inventory of the player
     * @param player
     */
    public void exitMenu(Player player) {
        player.closeInventory();
    }

    public abstract void initMenu();

    public abstract void updateMenu();

    /**
     * Fills the menu with its filler items and line filler items. If there is no filler item and/or line filler item, it does not
     */
    public void fillMenu() {
        for (int currentSlot = 0; currentSlot < inv.getSize(); currentSlot++) {
            if (filler != null) {
                inv.setItem(currentSlot, filler);
            }
            if (currentSlot % 9 == 0) {
                if (lineFiller != null) {
                    inv.setItem(currentSlot, lineFiller);
                }
            }
        }
    }

    public ItemStack getFiller() {
        return filler;
    }

    public ItemStack getLineFiller() {
        return lineFiller;
    }

    public ItemStack getExit() {
        return exit;
    }

    public Inventory getMenu() {
        return inv;
    }

    public void setLineFiller(ItemStack lineFiller) {
        this.lineFiller = lineFiller;
    }

    public void setFiller(ItemStack filler) {
        this.filler = filler;
    }

    public void setExit(ItemStack exit) {
        this.exit = exit;
    }

    public void setMenu(Inventory inv) {
        this.inv = inv;
    }

}
