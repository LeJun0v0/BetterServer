package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.others.Grab;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entityRightClicked = event.getRightClicked();
        if (Grab.getInstance().hasToggledGrabbing(player)) {
            Grab.getInstance().setGrabbing(player, entityRightClicked);
        }
    }
}
