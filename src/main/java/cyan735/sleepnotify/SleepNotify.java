package cyan735.sleepnotify;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SleepNotify extends JavaPlugin {
    boolean thunder;

    @Override
    public void onEnable() {

        //Registers a new SleepListener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SleepListener(), this);

        //Checks for nighttime or thunderstorm and sends sleep message
        new BukkitRunnable() {
            @Override
            public void run() {
                //Get World and time
                World world  = Bukkit.getWorld("world");
                long time = world.getTime();

                //Send sleep message at nightfall or beginning of thunderstorm
                if (time == 12542 && world.isClearWeather()) {
                    thunder = false;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                            player.sendMessage("You can go to sleep now");
                        }
                    }
                } else if (time == 12010 && world.hasStorm() && !world.isThundering()) {
                    thunder = false;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                            player.sendMessage("You can go to sleep now");
                        }
                    }
                } else if (world.hasStorm() && world.isThundering() && !thunder) {
                    thunder = true;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                            player.sendMessage("You can go to sleep now");
                        }
                    }
                } else if (world.isClearWeather() || (world.hasStorm() && !world.isThundering())) {
                    thunder = false;
                }
            }
        }.runTaskTimer(SleepNotify.getPlugin(SleepNotify.class), 0, 1);
    }
}
