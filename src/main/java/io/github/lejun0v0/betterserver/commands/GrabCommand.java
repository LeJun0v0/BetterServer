package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.others.Grab;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class GrabCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }
        final Player player = (Player) commandSender;
        if (strings.length == 0) {
            boolean taskStatus = Grab.getInstance().taskRunning() && !Grab.getInstance().taskCancelled();
            if (!Grab.getInstance().hasToggledGrabbing(player)) {
                Grab.getInstance().setToggledGrabbing(player, true);
                player.sendMessage(ChatColor.GREEN + "已启用右键抓取(仅对自身有效) " + (taskStatus ? ChatColor.GREEN : ChatColor.RED) + "(GrabTask: " + taskStatus + ")");
            } else {
                Grab.getInstance().setToggledGrabbing(player, false);
                player.sendMessage(ChatColor.RED + "已禁用右键抓取(仅对自身有效) " + (taskStatus ? ChatColor.GREEN : ChatColor.RED) + "(GrabTask: " + taskStatus + ")");
            }
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("position")) {
                player.sendMessage(ChatColor.RED + "参数错误！");
            } else if (strings[0].equalsIgnoreCase("status")) {
                boolean taskStatus = Grab.getInstance().taskRunning() && !Grab.getInstance().taskCancelled();
                if (Grab.getInstance().hasToggledGrabbing(player)) {
                    Entity entityGrabbed;
                    player.sendMessage(ChatColor.GREEN + "已启用右键抓取(仅对自身有效) " + (taskStatus ? ChatColor.GREEN : ChatColor.RED) + "(GrabTask: " + taskStatus + ")");
                    player.sendMessage(ChatColor.GREEN + "正在抓取: " + ChatColor.GRAY + ((entityGrabbed = Grab.getInstance().getEntityGrabbed(player)) != null ? entityGrabbed.getName() : "无"));
                } else {
                    player.sendMessage(ChatColor.RED + "已禁用右键抓取(仅对自身有效) " + (taskStatus ? ChatColor.GREEN : ChatColor.RED) + "(GrabTask: " + taskStatus + ")");
                }
            } else if (strings[0].equalsIgnoreCase("grabTask")) {
                player.sendMessage(ChatColor.RED + "参数错误！");
            } else {
                player.sendMessage(ChatColor.RED + "参数错误！");
            }
        } else if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("position")) {
                if (strings[1].equalsIgnoreCase("default")) {
                    Grab.getInstance().setGrabbingMode(player, Grab.GRABBINGMODE_DEFAULT);
                } else if (strings[1].equalsIgnoreCase("forward")) {
                    Grab.getInstance().setGrabbingMode(player, Grab.GRABBINGMODE_FORWARD);
                } else {
                    player.sendMessage(ChatColor.RED + "参数错误！");
                }
            } else if (strings[0].equalsIgnoreCase("GrabTask")) {
                if (strings[1].equalsIgnoreCase("on")) {
                    if (!Grab.getInstance().taskRunning()) {
                        Grab.getInstance().startTask();
                        player.sendMessage(ChatColor.GREEN + "已执行GrabTask开启操作");
                    } else {
                        player.sendMessage(ChatColor.RED + "GrabTask已处在开启状态");
                    }
                } else if (strings[1].equalsIgnoreCase("off")) {
                    if (Grab.getInstance().taskRunning() && !Grab.getInstance().taskCancelled()) {
                        Grab.getInstance().cancelTask();
                        player.sendMessage(ChatColor.GREEN + "已执行GrabTask关闭操作");
                    } else {
                        player.sendMessage(ChatColor.RED + "GrabTask已处在关闭状态！");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "参数错误！");
                }
            } else {
                player.sendMessage(ChatColor.RED + "参数错误！");
            }
        } else if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("position")) {
                if (strings[1].equalsIgnoreCase("forward")) {
                    if (Pattern.compile("^[-+]?(\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)?$").matcher(strings[2]).matches()) {
                        Grab.getInstance().setGrabbingMode(player, new Double[]{Grab.MODE_FORWARD, Double.valueOf(strings[2])});
                    } else {
                        player.sendMessage(ChatColor.RED + "参数错误！");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "参数错误！");
                }
            } else {
                player.sendMessage(ChatColor.RED + "参数错误！");
            }
        } else {
            player.sendMessage(ChatColor.RED + "参数错误！");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("GrabTask", "status", "position");
        }
        if (strings.length == 2 && strings[0].equalsIgnoreCase("grabTask")) {
            if (Grab.getInstance().taskRunning()) {
                return Arrays.asList("off");
            } else {
                return Arrays.asList("on");
            }
        }
        if (strings.length == 2 && strings[0].equalsIgnoreCase("position")) {
            return Arrays.asList("default", "forward");
        }
        if (strings.length == 3 && strings[1].equalsIgnoreCase("forward")) {
            return Arrays.asList("NUMBER");
        }
        return new ArrayList<>();
    }
}
