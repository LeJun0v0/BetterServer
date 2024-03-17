package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.others.Grab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        Grab.getInstance().removePlayer(player);
    }
}
