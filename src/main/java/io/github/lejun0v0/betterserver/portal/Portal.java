package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Portal {
    private Player owner;
    private Location location;
    private int status;
    private int type;
    private World world;
    private String facing;

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private Portal() {
    }

    public Portal(Player owner, Location location, int type, World world, String facing) {
        setOwner(owner);
        setLocation(location);
        setType(type);
        setWorld(world);
        setFacing(facing);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
