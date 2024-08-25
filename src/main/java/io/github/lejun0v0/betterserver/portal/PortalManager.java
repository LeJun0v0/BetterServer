package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class PortalManager {
    private static final PortalManager instance = new PortalManager();
    private static ArrayList<Portal> bluePortals = new ArrayList<>();
    private static ArrayList<Portal> orangePortals = new ArrayList<>();
    private static ArrayList<Portal> portals = new ArrayList<>();

    private PortalManager() {
    }

    public ArrayList<Portal> getPortals() {
        return portals;
    }

    public Portal createPortal(Player owner, Location location, int type, String facing) {
        Portal portal = new Portal(owner, location, type, owner.getWorld(), facing);
        if (type == PortalType.BLUE) {
            bluePortals.add(portal);
        } else if (type == PortalType.ORANGE) {
            orangePortals.add(portal);
        }
        portals.add(portal);
        return portal;
    }

    public boolean deletePortal(Player owner, int type) {
        Portal portal = getPortal(owner, type);
        return Objects.requireNonNull(getPortalList(type)).remove(portal) && portals.remove(portal);
    }

    @Nullable
    public Portal getPortal(Player owner, int type) {
        ArrayList<Portal> portalList = null;
        if (type == PortalType.BLUE) {
            portalList = bluePortals;
        } else if (type == PortalType.ORANGE) {
            portalList = orangePortals;
        }
        Portal portal = null;
        if (portalList != null) {
            for (Portal p : portalList) {
                if (p.getOwner().equals(owner) && p.getType() == type) {
                    portal = p;
                }
            }
        }
        return portal;
    }

    public boolean layPortal(Player player, Location playerLocation, Location portalLocation, Location blockLocation, int portalType) {
        //检查是否可以放置传送门
        if (!new Location(portalLocation.getWorld(), portalLocation.x(), portalLocation.y() - 1, portalLocation.z()).getBlock().getType().equals(Material.AIR)) {
            return false;
        }
        //
        if ((int) portalLocation.x() == (int) blockLocation.x()) {
            deletePortal(player, portalType);
            Vector3f z = new Vector3f(0, 0, 1);
            double deltaZ = 1.1;
            int num = (new Vector3f(0, 0, (int) portalLocation.z() - (int) blockLocation.z()).dot(z) > 0) ? 1 : -1;
            deltaZ *= num;
            createPortal(player, new Location(player.getWorld(), (int) portalLocation.x() + (portalLocation.x() > 0 ? 0.5 : -0.5), (int) portalLocation.y(), getInt(blockLocation.z(), num) + deltaZ).setDirection(new Vector(0, 0, num)), portalType, "NS");
        } else if ((int) portalLocation.z() == (int) blockLocation.z()) {
            deletePortal(player, portalType);
            Vector3f x = new Vector3f(1, 0, 0);
            double deltaX = 1.1;
            int num = (new Vector3f((int) portalLocation.x() - (int) blockLocation.x(), 0, 0).dot(x) > 0) ? 1 : -1;
            deltaX *= num;
            createPortal(player, new Location(player.getWorld(), getInt(blockLocation.x(), num) + deltaX, (int) portalLocation.y(), (int) portalLocation.z() + (portalLocation.z() > 0 ? 0.5 : -0.5)).setDirection(new Vector(num, 0, 0)), portalType, "WE");
        }
        return true;
    }


    @Nullable
    public ArrayList<Portal> getPortalList(int type) {
        if (type == PortalType.BLUE) {
            return bluePortals;
        } else if (type == PortalType.ORANGE) {
            return orangePortals;
        }
        return null;
    }

    public static PortalManager getInstance() {
        return instance;
    }

    public static double getInt(double num, int n) {
        //if the num is positive, it will lose decimal fraction. For example: 1.23 -> 1.00
        //if the num is negative, it will also lose decimal fraction, but the result will be a number bigger. For example: -1.23 -> -1.00
        if (n == 1) {
            return Math.floor(num);
        } else {
            return Math.ceil(num);
        }
    }
}

