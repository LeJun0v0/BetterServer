package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.functions.Home;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HomeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }
        final Player player = (Player) commandSender;
        if (strings.length != 1) {
            player.sendMessage(ChatColor.RED + "参数错误！");
            return true;
        }
        if (!Pattern.compile("^[a-zA-Z0-9_-]{1,16}$").matcher(strings[0]).matches()) {
            player.sendMessage(ChatColor.RED + "家的名字不符合命名规则！");
            return true;
        }
        String homeName = strings[0];
        Home home = HomeConfig.getInstance().getHome(homeName);
        if (home == null) {
            player.sendMessage(ChatColor.RED + "家 '" + homeName + "' 不存在！");
            return true;
        } else if (!home.getOwner().equals(player.getName())) {
            player.sendMessage(ChatColor.RED + "家 '" + homeName + "' 并不属于你！");
            return true;
        }
        player.teleport(new Location(home.getWorld(), home.getX(), home.getY(), home.getZ()));
        player.sendMessage(ChatColor.GREEN + "已传送至家 '" + homeName + "'");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return HomeConfig.getInstance().getHomes((Player) commandSender);
        }
        return new ArrayList<>();
    }
}
