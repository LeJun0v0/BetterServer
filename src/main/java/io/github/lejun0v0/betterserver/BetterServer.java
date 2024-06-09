package io.github.lejun0v0.betterserver;

import io.github.lejun0v0.betterserver.commands.*;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.listeners.*;
import io.github.lejun0v0.betterserver.others.VoteEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.BiConsumer;

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
        getCommand("nstick").setExecutor(new NormalStickCommand());
        getCommand("vote").setExecutor(new VoteCommand());
        //Register events
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                VoteEvent.events.forEach(new BiConsumer<VoteEvent, Long>() {
                    @Override
                    public void accept(VoteEvent voteEvent, Long aLong) {
                        if (voteEvent.getStatus() == VoteEvent.STATUS_PENDING && time >= aLong) {
                            voteEvent.close();
                        } else if (!(voteEvent.getStatus() == VoteEvent.STATUS_APPROVAL) && !(voteEvent.getStatus() == VoteEvent.STATUS_PENDING) && !(voteEvent.getStatus() == VoteEvent.STATUS_PREVIOUS)) {
                            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                                onlinePlayer.sendMessage(ChatColor.GOLD + "投票“" + voteEvent.getEvent() + "”已结束，结果如下:");
                                onlinePlayer.sendMessage(ChatColor.GOLD + "" + voteEvent.getForList().size() + "人支持," + voteEvent.getAgainstList().size() + "人反对。");
                            }
                            voteEvent.setStatus(VoteEvent.STATUS_PREVIOUS);
                        } else if (voteEvent.getStatus() == VoteEvent.STATUS_APPROVAL) {
                            //event生效
                            if (voteEvent.getEvent().equalsIgnoreCase("stop_server")) {
                                for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                                    onlinePlayer.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "正在关闭服务器");
                                }
                                getServer().dispatchCommand(getServer().getConsoleSender(), "stop");
                            }
                            voteEvent.setStatus(VoteEvent.STATUS_PREVIOUS);
                        }
                    }
                });
            }
        }.runTaskTimer(BetterServer.getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }

    public static BetterServer getInstance() {
        return getPlugin(BetterServer.class);
    }
}
