package io.github.lejun0v0.betterserver.portal;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.utils.MultiLang;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


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

        }


        return true;
    }
}
