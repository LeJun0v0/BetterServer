package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.others.Grab;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Collection;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entityRightClicked = event.getRightClicked();
        Grab grabInstance = Grab.getInstance();
        if (grabInstance.hasToggledGrabbing(player)) {
            if (entityRightClicked instanceof Player) {
                boolean playerIsGrabbed = false;//"player" in "playerIsGrabbed" means the one who intends to grab sth.
                Collection collection = grabInstance.getGrabbing().values();
                for (Object o : collection) {
                    playerIsGrabbed = player.equals(o);
                }
                if (!playerIsGrabbed) {
                    grabInstance.setGrabbing(player, entityRightClicked);
                }
            } else {
                grabInstance.setGrabbing(player, entityRightClicked);
            }
        }
    }
}
