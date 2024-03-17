package io.github.lejun0v0.betterserver.others;

import org.bukkit.World;

public class Home {
    private String owner;
    private World world;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private double x;
    private double y;
    private double z;

    public Home(String owner, World world, double x, double y, double z) {
        setOwner(owner);
        setWorld(world);
        setX(x);
        setY(y);
        setZ(z);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
