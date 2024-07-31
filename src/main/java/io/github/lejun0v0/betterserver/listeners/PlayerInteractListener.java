package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.items.Keys;
import io.github.lejun0v0.betterserver.others.NormalStick;
import org.bukkit.*;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().isRightClick()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta().getPersistentDataContainer().has(Keys.NORMAL_STICK)) {
                player.sendActionBar(ChatColor.GREEN + "RIGHT CLICK!");
                double yaw = player.getYaw();
                double pitch = player.getPitch();
                player.sendActionBar(ChatColor.YELLOW + "yaw: " + yaw + " | pitch: " + pitch);
                double x = player.getX();
                double y = player.getY() + 1;
                double z = player.getZ();
                World world = player.getWorld();
                double distance = 1;
                int count = 0;
                Location location;
                while ((location = new Location(player.getWorld(), x, y, z)).getBlock().getType().equals(Material.AIR) && count <= 500) {
                    world.spawnParticle(Particle.HEART, location, 20);
                    x -= distance * Math.sin(Math.toRadians(yaw));
                    y -= distance * Math.sin(Math.toRadians(pitch));
                    z += distance * Math.cos(Math.toRadians(yaw));
                    player.sendActionBar("Running: x: " + x + " y: " + y + " z: " + z + " count: " + count);
                    count++;
                }
                if (NormalStick.getMode(player).equals(NormalStick.LIGHTNING_MODE)) {
                    world.spawn(location, LightningStrike.class);
                }
            }
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().isLeftClick()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getPersistentDataContainer().has(Keys.NORMAL_STICK)) {
                    player.sendActionBar(ChatColor.GREEN + "LEFT CLICK!");
                }
            }
        }
    }
}
