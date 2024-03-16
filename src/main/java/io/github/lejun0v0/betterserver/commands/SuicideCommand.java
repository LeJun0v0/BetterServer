package io.github.lejun0v0.betterserver.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }
        final Player player = (Player) commandSender;
        player.setHealth(0.0);
        player.sendMessage(ChatColor.RED + "你自杀了");
        return true;
    }
}
