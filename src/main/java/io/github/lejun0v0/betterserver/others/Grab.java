package io.github.lejun0v0.betterserver.others;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Grab {
    private final static Grab instance = new Grab();
    private HashMap<UUID, Boolean> toggledGrabbing = new HashMap<>();//player toggle grabbing?
    private HashMap<UUID, Entity> grabbing = new HashMap<>();//player -> staff grabbed
    private GrabTask task = new GrabTask();
    private boolean taskRunning = false;
    private HashMap<UUID, Double[]> grabbingMode = new HashMap<>();
    public final static double MODE_DEFAULT = 0.0;
    public final static double MODE_FORWARD = 1.0;
    public final static Double[] GRABBINGMODE_DEFAULT = new Double[]{MODE_DEFAULT, 0.0};
    public final static Double[] GRABBINGMODE_FORWARD = new Double[]{MODE_FORWARD, 1.0};

    public void addPlayer(Player player) {
        toggledGrabbing.put(player.getUniqueId(), false);
    }

    public void removePlayer(Player player) {
        toggledGrabbing.remove(player.getUniqueId());
        grabbing.remove(player.getUniqueId());
        grabbingMode.remove(player.getUniqueId());
    }

    public boolean hasToggledGrabbing(Player player) {
        return toggledGrabbing.get(player.getUniqueId());
    }

    public void setToggledGrabbing(Player player, boolean grabbing) {
        toggledGrabbing.replace(player.getUniqueId(), grabbing);
        if (!grabbingMode.containsKey(player.getUniqueId())) {
            grabbingMode.put(player.getUniqueId(), GRABBINGMODE_DEFAULT);
        }
    }

    public void setGrabbing(Player player, Entity entity) {
        if (!grabbing.containsKey(player.getUniqueId())) {
            grabbing.put(player.getUniqueId(), entity);
            return;
        }
        grabbing.replace(player.getUniqueId(), entity);
    }

    public Entity getEntityGrabbed(Player player) {
        return grabbing.get(player.getUniqueId());
    }

    public void positionUpdate(Entity entityGrabbed, Player player) {
        Double mode = grabbingMode.get(player.getUniqueId())[0];
        Double modeValue = grabbingMode.get(player.getUniqueId())[1];
        if (mode.equals(MODE_DEFAULT)) {
            entityGrabbed.teleport(new Location(player.getWorld(), player.getX(), player.getY() + 2, player.getZ(), entityGrabbed.getYaw(), entityGrabbed.getPitch()));
        } else if (mode.equals(MODE_FORWARD)) {
            double x = player.getX();
            double y = player.getY() + 1;
            double z = player.getZ();
            float yaw = player.getYaw();
            float pitch = player.getPitch();
            /*
            * Problems to solve:
            1.The Location that entity is located to is not precise.
            2.Player grabbed can't change visual angle(yaw,pitch)
            * */
            x -= modeValue * Math.sin(Math.toRadians(player.getYaw()));
            y -= modeValue * Math.sin(Math.toRadians(player.getPitch()));
            z += modeValue * Math.cos(Math.toRadians(player.getYaw()));
            entityGrabbed.teleport(new Location(player.getWorld(), x, y, z, entityGrabbed.getYaw(), entityGrabbed.getPitch()));
        }
    }

    public void setGrabbingMode(Player player, Double[] mode) {
        if (!Grab.getInstance().hasToggledGrabbing(player)) {
            player.sendMessage(ChatColor.RED + "你还未启用抓取(仅对自身有效)");
            return;
        }
        grabbingMode.replace(player.getUniqueId(), mode);
    }

    private Grab() {
    }

    public void startTask() {
        task = new GrabTask();
        task.runTaskTimer(BetterServer.getInstance(), 0, 1);
        taskRunning = true;
    }

    public void cancelTask() {
        task.cancel();
        taskRunning = false;

    }

    public boolean taskRunning() {
        return taskRunning;
    }

    public boolean taskCancelled() {
        return task.isCancelled();
    }

    public static Grab getInstance() {
        return instance;
    }

    public HashMap<UUID, Entity> getGrabbing() {
        return grabbing;
    }
}
