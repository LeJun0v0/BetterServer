package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import io.github.lejun0v0.betterserver.functions.WelcomeMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BetterServerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "该指令需要参数！");
            return true;
        }
        if (strings[0].equals("config")) {
            if (strings[1].equals("reload")) {
                BetterServer.getInstance().reloadConfig();
                HomeConfig.getInstance().reload();
                WelcomeMessage.reloadWelcomeMessage();
                if (commandSender instanceof Player) {
                    commandSender.sendMessage("配置文件已重载！");
                }
                BetterServer.getInstance().getLogger().info("配置文件已重载！");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("config");
        } else if (strings.length == 2) {
            return Arrays.asList("reload");
        }
        return new ArrayList<>();
    }
}
