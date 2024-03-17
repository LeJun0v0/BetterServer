package io.github.lejun0v0.betterserver.others;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GrabTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : BetterServer.getInstance().getServer().getOnlinePlayers()) {
            Entity entityGrabbed = Grab.getInstance().getEntityGrabbed(player);
            if (entityGrabbed != null) {
                if (Grab.getInstance().hasToggledGrabbing(player) && !entityGrabbed.equals(player)) {
                    Grab.getInstance().positionUpdate(entityGrabbed, player);
                }
            }
        }
    }
}
