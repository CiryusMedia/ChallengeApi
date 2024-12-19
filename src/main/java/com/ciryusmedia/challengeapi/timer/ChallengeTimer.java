package com.ciryusmedia.challengeapi.timer;

import com.ciryusmedia.challengeapi.ChallengePlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;

public class ChallengeTimer {

    ChallengePlugin plugin;

    boolean running;
    int time;

    public String getStringFromTime(int time) {

        int days = time / (60*60*24);
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        //get time values from time and then delete the values from this method's time for easier calculation
        if (days >= 1) {
            time -= days * (60*60*24);
        }
        if (time / (60*60) >= 1) {
            hours = time / (60*60);
            time -= hours * (60*60);
        }
        if (time / (60) >= 1) {
            minutes = time / (60);
            time -= minutes * (60);
        }

        if (time >= 1) {
            seconds = time;
        }

        return convertTimeToString(days, hours, minutes, seconds);

    }

    public String convertTimeToString(int days, int hours, int minutes, int seconds) {
        String s = "";

        if (days > 0) {
            s = s + days + " Days ";
        }

        if (hours < 10) {
            s = s + "0" + hours + ":";
        } else {
            s = s + hours + ":";
        }

        if (minutes < 10) {
            s = s + "0" + minutes + ":";
        } else {
            s = s + minutes + ":";
        }

        if (seconds < 10) {
            s = s + "0" + seconds;
        } else {
            s = s + seconds;
        }

        return s;
    }

    public void sendTimer() {
        ChatColor pausedColor = ChatColor.valueOf(plugin.getConfig().getString("PausedColor").toUpperCase(Locale.ROOT));
        ChatColor runningColor = ChatColor.valueOf(plugin.getConfig().getString("RunningColor").toUpperCase(Locale.ROOT));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (running) {
                if (plugin.getConfig().getBoolean("Visible")) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(runningColor.toString() + ChatColor.BOLD + getStringFromTime(time)));
                }
            } else {
                if (plugin.getConfig().getBoolean("ShowPaused")) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(pausedColor + "Timer paused"));
                }
            }
        }
    }

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendTimer();

                World world = Bukkit.getWorld("world");

                if (world != null) {
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, running);
                    world.setGameRule(GameRule.DO_WEATHER_CYCLE, running);
                    world.setGameRule(GameRule.MOB_GRIEFING, running);
                }

                if (running) {
                    time++;
                }
            }
        }. runTaskTimer(plugin, 20, 20);
    }

    public ChallengeTimer(ChallengePlugin plugin, boolean running, int time) {
        this.plugin = plugin;
        this.running = running;
        this.time = time;

        run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
