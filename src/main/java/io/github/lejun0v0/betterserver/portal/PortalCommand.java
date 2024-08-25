package io.github.lejun0v0.betterserver.portal;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.items.Keys;
import io.github.lejun0v0.betterserver.utils.MultiLang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class PortalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MultiLang lang = MultiLang.getInstance();
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(lang.getText("CommandForPlayer"));
            return true;
        }
        Player player = (Player) commandSender;
        PortalManager portalManager = PortalManager.getInstance();
        if (strings[0].equals("create") && strings.length == 3) {
            player.sendMessage("Create");
            if (strings[1].equalsIgnoreCase("blue")) {
                portalManager.createPortal(player, player.getLocation(), PortalType.BLUE, strings[2]);//player.getLocation() -> ?
            } else if (strings[1].equalsIgnoreCase("orange")) {
                portalManager.createPortal(player, player.getLocation(), PortalType.ORANGE, strings[2]);//player.getLocation() -> ?
            }
        } else if (strings[0].equals("info")) {
            player.sendMessage("Trying to get the information of your portals...");
            Portal bluePortal = portalManager.getPortal(player, PortalType.BLUE);
            Portal orangePortal = portalManager.getPortal(player, PortalType.ORANGE);
            if (bluePortal != null) {
                player.sendMessage("BLUE PORTAL:\nOwner: " + bluePortal.getOwner() + "\nLocation: " + bluePortal.getLocation());
            }
            if (orangePortal != null) {
                player.sendMessage("ORANGE PORTAL:\nOwner: " + orangePortal.getOwner() + "\nLocation: " + orangePortal.getLocation());
            }
        } else if (strings[0].equals("delete") && strings.length == 2) {
            player.sendMessage("Delete");
            if (strings[1].equalsIgnoreCase("blue")) {
                portalManager.deletePortal(player, PortalType.BLUE);
            } else if (strings[1].equalsIgnoreCase("orange")) {
                portalManager.deletePortal(player, PortalType.ORANGE);
            }
        } else if (strings[0].equals("test")) {
            //Code for testing
            player.sendMessage("Running code for testing...");
            BetterServer plugin = BetterServer.getInstance();
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getX(), player.getY() + 2, player.getZ(), 100);
//            entity.setVisibleByDefault(false);

        } else if (strings[0].equals("gun")) {
            ItemStack portalGun = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta itemMeta = portalGun.getItemMeta();
            itemMeta.getPersistentDataContainer().set(Keys.PORTAL_GUN, PersistentDataType.BOOLEAN, true);
            itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "PORTAL GUN"));
            itemMeta.setDisplayName(ChatColor.AQUA + "Portal Gun");
            portalGun.setItemMeta(itemMeta);
            player.getInventory().addItem(portalGun);
        } else if (strings[0].equals("line") && strings[1].equals("add")) {
            LineTest lineTest = LineManager.createLineTest(player.getLocation(), Integer.parseInt(strings[3]), player.getWorld(), strings[2]);
            LineManager.addLine(lineTest);
        } else if (strings[0].equals("line") && strings[1].equals("remove")) {
            LineTest lineTest = LineManager.getLineTest(strings[2]);
            if (lineTest != null) {
                LineManager.removeLine(lineTest);
                player.sendMessage(ChatColor.GREEN + "SUCCEED!");
            } else {
                player.sendMessage(ChatColor.RED + "FAILED!");
            }
        } else if (strings[0].equals("line") && strings[1].equals("set")) {
            LineTest lineTest = LineManager.getLineTest(strings[2]);
            if (lineTest == null) {
                return true;
            }
            if (strings[3].equals("yaw")) {
                lineTest.getLocation().setYaw(Float.parseFloat(strings[4]));
            } else if (strings[3].equals("pitch")) {
                lineTest.getLocation().setPitch(Float.parseFloat(strings[4]));
            }
        } else if (strings[0].equals("yawstick")) {
            ItemStack itemStack = new ItemStack(Material.STICK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(Keys.YAW_STICK, PersistentDataType.BOOLEAN, true);
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(BetterServer.getInstance(), "code"), PersistentDataType.STRING, strings[1]);
            itemMeta.setLore(Arrays.asList(strings[1]));
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
        } else if (strings[0].equals("pitchstick")) {
            ItemStack itemStack = new ItemStack(Material.STICK);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.getPersistentDataContainer().set(Keys.PITCH_STICK, PersistentDataType.BOOLEAN, true);
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(BetterServer.getInstance(), "code"), PersistentDataType.STRING, strings[1]);
            itemMeta.setLore(Arrays.asList(strings[1]));
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
        }

        return true;
    }
}
