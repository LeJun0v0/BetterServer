package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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
}
