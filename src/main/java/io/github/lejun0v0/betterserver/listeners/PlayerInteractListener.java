package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.items.Keys;
import io.github.lejun0v0.betterserver.others.NormalStick;
import io.github.lejun0v0.betterserver.portal.LineManager;
import io.github.lejun0v0.betterserver.portal.LineTest;
import io.github.lejun0v0.betterserver.portal.PortalManager;
import io.github.lejun0v0.betterserver.portal.PortalType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Vector3f;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction().isRightClick()) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta().getPersistentDataContainer().has(Keys.NORMAL_STICK)) {
                player.sendActionBar(ChatColor.GREEN + "RIGHT CLICK!");
                World world = player.getWorld();
                int count = 0;
                Location playerLocation = player.getLocation();
                Vector3f direction = playerLocation.getDirection().toVector3f();
                direction.normalize();
                Vector3f pos = new Vector3f((float) playerLocation.x(), (float) playerLocation.y(), (float) playerLocation.z());
                Location location;
                while ((location = new Location(player.getWorld(), pos.x, pos.y, pos.z)).getBlock().getType().equals(Material.AIR) && count <= 500) {
                    pos.add(direction);
                    count++;
                    world.spawnParticle(Particle.VILLAGER_HAPPY, pos.x, pos.y, pos.z, 0);
                }
                if (NormalStick.getMode(player).equals(NormalStick.LIGHTNING_MODE)) {
                    world.spawn(location, LightningStrike.class);
                }
            } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.PORTAL_GUN)) {
                player.sendActionBar(ChatColor.GOLD + "ORANGE PORTAL");
                World world = player.getWorld();
                int count = 0;
                Location playerLocation = player.getLocation();
                Vector3f direction = playerLocation.getDirection().toVector3f();
                direction.normalize();
                Vector3f pos = new Vector3f((float) playerLocation.x(), (float) (playerLocation.y() + 1.62), (float) playerLocation.z());
                Location location;
                while (((location = new Location(player.getWorld(), pos.x, pos.y, pos.z)).getBlock().getType().equals(Material.AIR) || location.getBlock().getType().equals(Material.VOID_AIR)) && count <= 500) {
                    pos.add(direction);
                    count++;
                    world.spawnParticle(Particle.VILLAGER_HAPPY, pos.x, pos.y, pos.z, 0);
                }
                Block block;
                if ((!((block = location.getBlock()).getType().equals(Material.AIR) || block.getType().equals(Material.VOID_AIR)))) {
                    pos.add(direction.negate());
                    Location blockLocation = location.clone();
                    location.set(pos.x, pos.y, pos.z);
                    PortalManager.getInstance().layPortal(player, player.getLocation(), location, blockLocation, PortalType.ORANGE);
                }

            } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.YAW_STICK)) {
                String code = item.getItemMeta().getPersistentDataContainer().get(Keys.CODE, PersistentDataType.STRING);
                LineTest lineTest = LineManager.getLineTest(code);
                if (lineTest == null) {
                    player.sendMessage(ChatColor.RED + "FAILED!");
                    return;
                }
                Location location = lineTest.getLocation();
                if (location.getYaw() == 180) {
                    location.setYaw(-180);
                } else {
                    location.setYaw(location.getYaw() + 1);
                }
            } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.PITCH_STICK)) {
                String code = item.getItemMeta().getPersistentDataContainer().get(Keys.CODE, PersistentDataType.STRING);
                LineTest lineTest = LineManager.getLineTest(code);
                if (lineTest == null) {
                    player.sendMessage(ChatColor.RED + "FAILED!");
                    return;
                }
                Location location = lineTest.getLocation();
                if (location.getPitch() == 90) {
                    location.setPitch(-90);
                } else {
                    location.setPitch(location.getPitch() + 1);
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
                } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.YAW_STICK)) {
                    String code = item.getItemMeta().getPersistentDataContainer().get(Keys.CODE, PersistentDataType.STRING);
                    LineTest lineTest = LineManager.getLineTest(code);
                    if (lineTest == null) {
                        player.sendMessage(ChatColor.RED + "FAILED!");
                        return;
                    }
                    Location location = lineTest.getLocation();
                    player.sendActionBar("sin(yaw)=" + Math.sin(Math.toRadians(location.getYaw())) + " cos(yaw)=" + Math.cos(Math.toRadians(location.getYaw())) + " Yaw=" + location.getYaw());
                } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.PITCH_STICK)) {
                    String code = item.getItemMeta().getPersistentDataContainer().get(Keys.CODE, PersistentDataType.STRING);
                    LineTest lineTest = LineManager.getLineTest(code);
                    if (lineTest == null) {
                        player.sendMessage(ChatColor.RED + "FAILED!");
                        return;
                    }
                    Location location = lineTest.getLocation();
                    player.sendActionBar("sin(pitch)=" + Math.sin(Math.toRadians(location.getPitch())) + " cos(pitch)=" + Math.cos(Math.toRadians(location.getPitch())) + " Pitch=" + location.getPitch());
                } else if (item.getItemMeta().getPersistentDataContainer().has(Keys.PORTAL_GUN)) {
                    player.sendActionBar(ChatColor.BLUE + "BLUE PORTAL");
                    World world = player.getWorld();
                    int count = 0;
                    Location playerLocation = player.getLocation();
                    Vector3f direction = playerLocation.getDirection().toVector3f();
                    direction.normalize();
                    Vector3f pos = new Vector3f((float) playerLocation.x(), (float) ((float) playerLocation.y() + 1.625), (float) playerLocation.z());
                    Location location;
                    while (((location = new Location(player.getWorld(), pos.x, pos.y, pos.z)).getBlock().getType().equals(Material.AIR) || location.getBlock().getType().equals(Material.VOID_AIR)) && count <= 500) {
                        pos.add(direction);
                        count++;
                        world.spawnParticle(Particle.VILLAGER_HAPPY, pos.x, pos.y, pos.z, 0);
                    }
                    Block block;
                    if ((!((block = location.getBlock()).getType().equals(Material.AIR) || block.getType().equals(Material.VOID_AIR)))) {
                        pos.add(direction.negate());
                        Location blockLocation = location.clone();
                        location.set(pos.x, pos.y, pos.z);
                        PortalManager.getInstance().layPortal(player, player.getLocation(), location, blockLocation, PortalType.BLUE);
                    }

                }
            }
        }
    }
}
