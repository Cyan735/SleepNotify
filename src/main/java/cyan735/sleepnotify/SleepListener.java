package cyan735.sleepnotify;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SleepListener implements Listener {

    //Checks if a player goes to sleep and sends a message to everyone: "<Player> went to bed (<Sleeping players>/<Players needed to skip the night>)"
    @EventHandler
    public void onEnterBedEvent(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getServer().getWorld("world");
        String PlayerBedEnterEventText = (player.getName() + " went to bed (" + SleepingPlayers() + "/" + OverworldPlayers() + ")");
        long timeUntilNightInTicks;
        long timeUntilNightInSeconds;
        long timeUntilNightInMinutes;

        if (world.isClearWeather()) {
            if (world.getTime() > 12542 && world.getTime() < 23460) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getWorld().getEnvironment() == World.Environment.NORMAL) {
                        player.sendMessage(PlayerBedEnterEventText);
                    }
                }
            }
            else if (world.getTime() < 12542 || world.getTime() > 23460) {
                if (world.getTime() >= 23460) {
                    timeUntilNightInTicks = 24000 - world.getTime() + 12542;
                    timeUntilNightInMinutes = timeUntilNightInTicks / 1200;
                    timeUntilNightInSeconds = ((timeUntilNightInTicks - timeUntilNightInMinutes * 1200) / 20);
                    player.sendMessage("You need to wait " + timeUntilNightInMinutes + "m " + timeUntilNightInSeconds + "s");
                } else if (world.getTime() <= 12542) {
                    timeUntilNightInTicks = 12542 - world.getTime();
                    timeUntilNightInMinutes = timeUntilNightInTicks / 1200;
                    timeUntilNightInSeconds = ((timeUntilNightInTicks - timeUntilNightInMinutes * 1200) / 20);
                    player.sendMessage("You need to wait " + timeUntilNightInMinutes + "m " + timeUntilNightInSeconds + "s");
                }
            }
        } else if (world.hasStorm() && !world.isThundering()) {
            if (world.getTime() > 12010 && world.getTime() < 23992) {
                Bukkit.broadcastMessage(PlayerBedEnterEventText);
            } else if (world.getTime() < 12010 || world.getTime() > 23992) {
                if (world.getTime() >= 23992) {
                    timeUntilNightInTicks = 24000 - world.getTime() + 12010;
                    timeUntilNightInMinutes = timeUntilNightInTicks / 1200;
                    timeUntilNightInSeconds = ((timeUntilNightInTicks - timeUntilNightInMinutes * 1200) % 20);
                    player.sendMessage("You need to wait " + timeUntilNightInMinutes + "m " + timeUntilNightInSeconds + "s");
                } else if (world.getTime() <= 12010) {
                    timeUntilNightInTicks = 12010 - world.getTime();
                    timeUntilNightInMinutes = timeUntilNightInTicks / 1200;
                    timeUntilNightInSeconds = ((timeUntilNightInTicks - timeUntilNightInMinutes * 1200) / 20);
                    player.sendMessage("You need to wait " + timeUntilNightInMinutes + "m " + timeUntilNightInSeconds + "s");
                }
            }
        } else if (world.isThundering()) {
            Bukkit.broadcastMessage(PlayerBedEnterEventText);
        }
    }

    //Returns the amount of sleeping players
    public int SleepingPlayers() {
        int SleepingPlayers = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isSleeping()) {
                SleepingPlayers++;
            }
            SleepingPlayers++;
        }
        return SleepingPlayers;
    }

    //Returns the amount of players in the overworld
    public int OverworldPlayers() {
        int OverworldPlayers = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                OverworldPlayers++;
            }
        }
        return OverworldPlayers;
    }
}
