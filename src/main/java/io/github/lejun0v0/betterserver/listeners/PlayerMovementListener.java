package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.portal.Portal;
import io.github.lejun0v0.betterserver.portal.PortalManager;
import io.github.lejun0v0.betterserver.portal.PortalType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerMovementListener implements Listener {
    private HashMap<Player, Long> lastTimeStamps = new HashMap<>();
    private HashMap<Player, Location> lastLocations = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        if (!lastLocations.containsKey(player)) {
            lastLocations.put(player, from);
            lastTimeStamps.put(player, System.currentTimeMillis());
            return;
        }
        double deltaX = to.x() - lastLocations.get(player).x();
        double deltaY = to.y() - lastLocations.get(player).y();
        double deltaZ = to.z() - lastLocations.get(player).z();
        double deltaTime = BigDecimal.valueOf(System.currentTimeMillis() - lastTimeStamps.get(player)).divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP).doubleValue();
        double vx = deltaX / deltaTime;
        double vy = deltaY / deltaTime;
        double vz = deltaZ / deltaTime;
        Vector v = new Vector(vx, vy, vz);
        ArrayList<Portal> portals = PortalManager.getInstance().getPortals();
        for (Portal portal : portals) {
            //2d(x-z)
            player.sendActionBar("" + getCosine(new Vector2f((float) v.getX(), (float) v.getZ()), new Vector2f((float) portal.getLocation().getDirection().getX(), (float) portal.getLocation().getDirection().getZ())));
            if (areClose(to, portal.getLocation()) && getCosine(new Vector2f((float) v.getX(), (float) v.getZ()), new Vector2f((float) portal.getLocation().getDirection().getX(), (float) portal.getLocation().getDirection().getZ())) < 0) {
                Portal pairedPortal;
                if ((pairedPortal = PortalManager.getInstance().getPortal(portal.getOwner(), portal.getType() == PortalType.BLUE ? PortalType.ORANGE : PortalType.BLUE)) != null) {
                    Location destination = pairedPortal.getLocation().clone();
                    destination.setY(destination.y() - 1);
                    //计算传送门夹角(angle)并进行视角调整
                    Vector portalDir = portal.getLocation().getDirection();
                    Vector pairedPortalDir = pairedPortal.getLocation().getDirection();
                    Vector3f playerDirection = player.getLocation().getDirection().toVector3f();
                    Vector2f vRotated = null;
                    if (portalDir.dot(pairedPortalDir) == 1) {
                        //alpha = 0
                        Vector2f vectorRotated = rotateVector(new Vector2f(playerDirection.x, playerDirection.z), 180);
                        vRotated = rotateVector(new Vector2f((float) v.getX(), (float) v.getZ()), 180);
                        destination.setDirection(new Vector(vectorRotated.x, playerDirection.y, vectorRotated.y));
                    } else if (portalDir.dot(pairedPortalDir) == -1) {
                        //alpha = 180
                        destination.setDirection(Vector.fromJOML(playerDirection));
                        vRotated = new Vector2f((float) v.getX(), (float) v.getZ());
                    } else {
                        //alpha = 90
                        Vector2f vector1 = new Vector2f((float) portalDir.getX(), (float) portalDir.getZ());
                        Vector2f vector2 = new Vector2f((float) pairedPortalDir.getX(), (float) pairedPortalDir.getZ());
                        if (equals(rotateVector(vector1, 90), vector2)) {
                            Vector2f vectorRotated = rotateVector(new Vector2f(playerDirection.x, playerDirection.z), 90);
                            destination.setDirection(new Vector(vectorRotated.x, playerDirection.y, vectorRotated.y));
                            vRotated = rotateVector(new Vector2f((float) v.getX(), (float) v.getZ()), 90);
                        } else if (equals(rotateVector(rotateVector(vector1, 90), 180), vector2)) {
                            Vector2f vectorRotated = rotateVector(rotateVector(new Vector2f(playerDirection.x, playerDirection.z), 90), 180);
                            destination.setDirection(new Vector(vectorRotated.x, playerDirection.y, vectorRotated.y));
                            vRotated = rotateVector(rotateVector(new Vector2f((float) v.getX(), (float) v.getZ()), 90), 180);
                        }
                    }
                    player.teleport(destination);
                    //动量继承
                    if (vRotated != null) {
                        player.setVelocity(new Vector(vRotated.x, v.getY(), vRotated.y));
                    }
                }
            }
        }
    }

    public static boolean areClose(Location playerLocation, Location portalLocation) {
        double x = playerLocation.x();
        double y = playerLocation.y() + 1;
        double z = playerLocation.z();
        double deltaX = x - portalLocation.x();
        double deltaY = y - portalLocation.y();
        double deltaZ = z - portalLocation.z();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        return distance <= 0.3;
    }

    public static Vector2f rotateVector(Vector2f vector, double angle) {
        if (angle == 90) {
            return new Vector2f(vector.y, -vector.x);
        } else if (angle == 180) {
            return new Vector2f(-vector.x, -vector.y);
        } else {
            return vector;
        }

    }

    public static boolean equals(Vector2f v1, Vector2f v2) {
        return v1.x == v2.x && v1.y == v2.y;
    }

    public static double getCosine(Vector2f v1, Vector2f v2) {
        BigDecimal numerator = BigDecimal.valueOf(v1.dot(v2));
        BigDecimal denominator = BigDecimal.valueOf(v1.x).pow(2).add(BigDecimal.valueOf(v1.y).pow(2)).multiply(BigDecimal.valueOf(v2.x).pow(2).add(BigDecimal.valueOf(v2.y).pow(2))).sqrt(MathContext.DECIMAL32);
        return numerator.divide(denominator, RoundingMode.HALF_UP).doubleValue();
    }
}

