package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhereisCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1) {
            commandSender.sendMessage(ChatColor.RED + "该指令只能有一个参数！");
            return true;
        }
        BetterServer betterServer = BetterServer.getInstance();
        String playerName = strings[0];
        Player player = betterServer.getServer().getPlayer(playerName);
        if (player == null) {
            commandSender.sendMessage(ChatColor.RED + "玩家 " + playerName + " 已离线");
            return true;
        }
        commandSender.sendMessage(ChatColor.GOLD + "[" + playerName + "]" + ChatColor.GRAY + " -> " + ChatColor.GREEN + ChatColor.GREEN + player.getWorld().getName() + "  [ X : " + (int) player.getX() + " ]  [ Y : " + (int) player.getY() + " ]  [ Z : " + (int) player.getZ() + " ]");
        return true;
    }
}
