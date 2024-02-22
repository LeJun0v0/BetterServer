package io.github.lejun0v0.betterserver;

import io.github.lejun0v0.betterserver.commands.GetCommand;
import io.github.lejun0v0.betterserver.commands.SuicideCommand;
import io.github.lejun0v0.betterserver.commands.WhereisCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterServer extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Register commands
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("whereis").setExecutor(new WhereisCommand());
        getCommand("get").setExecutor(new GetCommand());

        getLogger().info("Just for testing.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BetterServer getInstance() {
        return getPlugin(BetterServer.class);
    }
}
