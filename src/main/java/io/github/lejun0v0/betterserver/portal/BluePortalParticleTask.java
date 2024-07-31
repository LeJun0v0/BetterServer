package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BluePortalParticleTask extends BukkitRunnable {
    @Override
    public void run() {
        ArrayList<Portal> portals = PortalManager.getInstance().getPortals();
        for (Portal portal : portals) {
            generateParticle(portal);
        }

    }

    private void generateParticle(Portal portal) {
        //portal.getWorld().spawnParticle(Particle.BLOCK_DUST, portal.getLocation().getX(), portal.getLocation().getY(), portal.getLocation().getZ(), 0, Bukkit.createBlockData(Material.BLUE_CONCRETE));
        //N S(FACE)
        //Stage 1 : A circle
        Location location = portal.getLocation();
        World world = portal.getWorld();
        if (portal.getType() == PortalType.BLUE) {
            if (portal.getFacing().equals("NS")) {
                double deltaX;
                double deltaY;
                double a = 0.4;
                double b = 0.9;
                for (double theta = 0; theta < 360; theta += 5) {
                    deltaX = a * Math.cos(theta);
                    deltaY = b * Math.sin(theta);
                    world.spawnParticle(Particle.DUST_COLOR_TRANSITION, location.x() + deltaX, location.y() + deltaY, location.getZ(), 0, new Particle.DustTransition(Color.BLUE, Color.AQUA, 0.5f));
                }
            } else if (portal.getFacing().equals("WE")) {
                double deltaY;
                double deltaZ;
                double a = 0.4;
                double b = 0.9;
                for (double theta = 0; theta < 360; theta += 5) {
                    deltaZ = a * Math.cos(theta);
                    deltaY = b * Math.sin(theta);
                    world.spawnParticle(Particle.DUST_COLOR_TRANSITION, location.x(), location.y() + deltaY, location.getZ() + deltaZ, 0, new Particle.DustTransition(Color.BLUE, Color.AQUA, 0.5f));
                }
            }
        }
    }
}
