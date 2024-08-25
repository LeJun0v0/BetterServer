package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Location;
import org.bukkit.World;

public class LineTest {
    private Location location;
    private int count;
    private World world;
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LineTest(Location location, int count, World world, String code) {
        this.location = location;
        this.count = count;
        this.world = world;
        this.code = code;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public LineTest(Location Location, int count) {
        this.location = Location;
        this.count = count;
    }

    public Location getLocation() {
        return location;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
