package io.github.lejun0v0.betterserver;

import io.github.lejun0v0.betterserver.commands.*;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.listeners.*;
import io.github.lejun0v0.betterserver.others.VoteEvent;
import io.github.lejun0v0.betterserver.portal.BluePortalParticleTask;
import io.github.lejun0v0.betterserver.portal.OrangePortalParticleTask;
import io.github.lejun0v0.betterserver.portal.PortalCommand;
import io.github.lejun0v0.betterserver.portal.PortalManager;
import io.github.lejun0v0.betterserver.utils.MultiLang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.BiConsumer;

public final class BetterServer extends JavaPlugin {

    private BukkitTask voteTask;
    private BukkitTask bluePortalParticleTask;
    private BukkitTask orangePortalParticleTask;

    @Override
    public void onEnable() {
        //Plugin startup logic
        //Load configs
        saveDefaultConfig();
        HomeConfig.getInstance().load();
        MultiLang.getInstance().init();

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
        getCommand("portal").setExecutor(new PortalCommand());
        //Register events
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        voteTask = new BukkitRunnable() {
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
        }.runTaskTimer(this, 0, 20);
        PortalManager portalManager = PortalManager.getInstance();
        bluePortalParticleTask = new BluePortalParticleTask().runTaskTimer(this, 0, 1);
        orangePortalParticleTask = new OrangePortalParticleTask().runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }

    public static BetterServer getInstance() {
        return getPlugin(BetterServer.class);
    }
}
