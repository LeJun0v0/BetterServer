package io.github.lejun0v0.betterserver.portal;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.Vector3f;

import java.util.ArrayList;

public class LineTestTask extends BukkitRunnable {
    @Override
    public void run() {
        ArrayList<LineTest> lines = LineManager.getLines();
        for (LineTest lineTest : lines) {
            Location location = lineTest.getLocation();
            World world = lineTest.getWorld();
            double x = location.x();
            double y = location.y();
            double z = location.z();
            world.spawnParticle(Particle.END_ROD, x, y, z, 0);
            double tempX = x;
            double tempY = y;
            double tempZ = z;
            for (int i = 0; i < 50; i++) {
                tempX -= Math.sin(Math.toRadians(location.getYaw()));
                tempY -= Math.sin(Math.toRadians(location.getPitch()));
                tempZ += Math.cos(Math.toRadians(location.getYaw()));
                world.spawnParticle(Particle.VILLAGER_HAPPY, tempX, tempY, tempZ, 0);
            }
            Vector3f direction = location.getDirection().toVector3f();
            // new Vector3f((float) -Math.sin(Math.toRadians(location.getYaw())), (float) -Math.sin(Math.toRadians(location.getPitch())), (float) Math.cos(Math.toRadians(location.getYaw())));
            direction.normalize();
            Vector3f pos = new Vector3f((float) location.x(), (float) location.y(), (float) location.z());
            Vector3f tempPos = new Vector3f((float) location.x(), (float) location.y(), (float) location.z());
            for (int i = 0; i < 50; i++) {
                world.spawnParticle(Particle.END_ROD, tempPos.x, tempPos.y, tempPos.z, 0);
                tempPos.add(direction);
            }
            BetterServer.getInstance().getLogger().info(lineTest.getCode() + "|" + direction.x + " | " + direction.y + " | " + direction.z);
            if (lineTest.getCount() != 0) {

            }
        }
    }
}
