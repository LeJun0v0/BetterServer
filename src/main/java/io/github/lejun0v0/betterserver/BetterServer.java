package io.github.lejun0v0.betterserver;

import io.github.lejun0v0.betterserver.commands.*;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.listeners.PlayerInteractAtEntityListener;
import io.github.lejun0v0.betterserver.listeners.PlayerJoinListener;
import io.github.lejun0v0.betterserver.listeners.PlayerQuitListener;
import io.github.lejun0v0.betterserver.listeners.PlayerToggleSneakListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class BetterServer extends JavaPlugin {

    @Override
    public void onEnable() {
        //Plugin startup logic
        //Load configs
        HomeConfig.getInstance().load();
        saveDefaultConfig();
        //Register commands
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("whereis").setExecutor(new WhereisCommand());
        getCommand("get").setExecutor(new GetCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("betterserver").setExecutor(new BetterServerCommand());
        getCommand("grab").setExecutor(new GrabCommand());
        //Register events
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : getServer().getOnlinePlayers()) {

                }
            }
        }.runTaskTimer(BetterServer.getInstance(), 0, 1);
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }

    public static BetterServer getInstance() {
        return getPlugin(BetterServer.class);
    }
}
