package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.others.Grab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {
    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        Grab.getInstance().setGrabbing(player, null);
        //player.sendActionBar(ChatColor.GREEN + "sin(yaw)-> " + Math.sin(Math.toRadians(player.getYaw())) + " - sin(pitch)-> " + Math.sin(Math.toRadians(player.getPitch())) + " - cos(yaw)-> " + Math.cos(Math.toRadians(player.getYaw())));
    }
}
