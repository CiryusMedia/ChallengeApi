package com.ciryusmedia.challengeapi.challenge;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import com.ciryusmedia.challengeapi.debug.ChallengeDebugger;
import com.ciryusmedia.challengeapi.debug.DebugLevel;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class Challenge {

    protected ChallengePlugin plugin;
    protected ChallengeDebugger debugger = ChallengeDebugger.getDebugger();

    protected String name;
    protected String displayName;
    protected String description;
    protected String type;
    protected String subType;
    protected ItemStack menuItem;
    protected List<String> itemDescription;
    protected boolean enabled = false;

    /**
     * The constructor for a challenge class. super() should be called before any other initiation calls to avoid problems
     *
     * @param plugin The main class of your challenge plugin
     * @param name The in-code name of the challenge, how it is saved in the config.yml
     * @param displayName The name that is supposed to show up on the menu item
     * @param type What type of challenge is this?
     * @param subType Used to avoid having multiple challenges active, that do similar things
     */
    public Challenge(ChallengePlugin plugin, String name, String displayName, String type, String subType) {
        this.plugin = plugin;
        this.name = name;
        this.displayName = displayName;
        updateItemDescription();
        this.description = String.valueOf(itemDescription);
        createMenuItem();
        this.type = type;
        this.subType = subType;
    }

    public abstract void createMenuItem();

    public abstract void updateItemDescription();

    public void updateMenuItem() {
        updateItemDescription();

        ItemMeta itemMeta = menuItem.getItemMeta();
        List<String> lore = itemDescription;

        itemMeta.setEnchantmentGlintOverride(enabled);

        if (isEnabled()) {
            itemMeta.setDisplayName(ChatColor.GREEN + displayName);
            lore.add(displayName + " is currently " + ChatColor.GREEN + "enabled");

        } else {
            itemMeta.setDisplayName(ChatColor.RED + displayName);
            lore.add(displayName + "is currently " + ChatColor.RED + "disabled");
        }

        itemMeta.setLore(lore);

        menuItem.setItemMeta(itemMeta);
    }

    public void setEnabled(boolean enabled) {
        debugger.log("Setting challenge " + getName() + " to " + enabled, DebugLevel.LEVEL_3);
        plugin.getConfig().set(name, enabled);
        plugin.saveConfig();
        this.enabled = enabled;
        debugger.log("Challenge " + getName() + " is now " + isEnabled(), DebugLevel.LEVEL_3);
        updateMenuItem();
    }

    public void setMenuItem(ItemStack item) {
        this.menuItem = item;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public List<String> getItemDescription() {
        return itemDescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean hasSubtype() {
        return subType != null;
    }
}
