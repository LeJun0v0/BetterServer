package io.github.lejun0v0.betterserver;

import io.github.lejun0v0.betterserver.commands.*;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterServer extends JavaPlugin {

    @Override
    public void onEnable() {
        //Plugin startup logic
        //config
        HomeConfig.getInstance().load();
        saveDefaultConfig();
        //Register commands
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("whereis").setExecutor(new WhereisCommand());
        getCommand("get").setExecutor(new GetCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("betterserver").setExecutor(new BetterServerCommand());

        //Register events
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }

    public static BetterServer getInstance() {
        return getPlugin(BetterServer.class);
    }
}
