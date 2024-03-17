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
    }
}
