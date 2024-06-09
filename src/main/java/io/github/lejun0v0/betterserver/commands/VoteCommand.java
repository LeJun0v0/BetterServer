package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.others.VoteEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class VoteCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "参数有误！");
            return true;
        } else if (strings.length == 2) {
            if ("for".equalsIgnoreCase(strings[0])) {
                foR(commandSender, strings[1]);
                return true;
            } else if ("against".equalsIgnoreCase(strings[0])) {
                against(commandSender, strings[1]);
                return true;
            } else if ("cancel".equalsIgnoreCase(strings[0])) {
                cancel(commandSender, strings[1]);
                return true;
            }
        } else if (strings.length == 3) {
            if ("create".equalsIgnoreCase(strings[0])) {
                create(commandSender, strings[1], strings[2], "无");
                return true;
            }
        } else if (strings.length == 4) {
            if ("create".equalsIgnoreCase(strings[0])) {
                create(commandSender, strings[1], strings[2], strings[3]);
                return true;
            }
        }
        commandSender.sendMessage(ChatColor.RED + "参数有误！");
        return true;
    }

    private void foR(CommandSender commandSender, String event) {
        if (eventExists(event)) {
            VoteEvent voteEvent = null;
            for (VoteEvent vE : VoteEvent.events.keySet()) {
                voteEvent = event.equals(vE.getEvent()) ? vE : null;
            }
            if (voteEvent == null) {
                return;
            }
            List<UUID> foRList = voteEvent.getForList();
            List<UUID> againstList = voteEvent.getAgainstList();
            if (commandSender instanceof ConsoleCommandSender) {
                foRList.add(VoteEvent.CONSOLE_UUID);
                againstList.remove(VoteEvent.CONSOLE_UUID);
            } else {
                Player player = (Player) commandSender;
                foRList.add(player.getUniqueId());
                againstList.remove(player.getUniqueId());
            }
            commandSender.sendMessage(ChatColor.GREEN + "你投了支持票！");
        }
    }

    private void against(CommandSender commandSender, String event) {
        if (eventExists(event)) {
            VoteEvent voteEvent = null;
            for (VoteEvent vE : VoteEvent.events.keySet()) {
                voteEvent = event.equals(vE.getEvent()) ? vE : null;
            }
            if (voteEvent == null) {
                return;
            }
            List<UUID> foRList = voteEvent.getForList();
            List<UUID> againstList = voteEvent.getAgainstList();
            if (commandSender instanceof ConsoleCommandSender) {
                againstList.add(VoteEvent.CONSOLE_UUID);
                foRList.remove(VoteEvent.CONSOLE_UUID);
            } else {
                Player player = (Player) commandSender;
                againstList.add(player.getUniqueId());
                foRList.remove(player.getUniqueId());
            }
            commandSender.sendMessage(ChatColor.GREEN + "你投了反对票！");
        }
    }

    private void create(CommandSender commandSender, String event, String after, String description) {
        if (after.matches("^[0-9]+[dhms]$") && !event.isEmpty()) {
            long time = System.currentTimeMillis();
            long num = Integer.parseInt(after.substring(0, after.length() - 1));
            if (after.endsWith("d")) {
                time += num * 24 * 60 * 60 * 1000;
                after = after.replace("d", "天");
            } else if (after.endsWith("h")) {
                time += num * 60 * 60 * 1000;
                after = after.replace("h", "小时");
            } else if (after.endsWith("m")) {
                time += num * 60 * 1000;
                after = after.replace("m", "分钟");
            } else if (after.endsWith("s")) {
                time += num * 1000;
                after = after.replace("s", "秒");
            }

            if (eventExists(event)) {
                VoteEvent voteEvent = null;
                for (VoteEvent vE : VoteEvent.events.keySet()) {
                    voteEvent = event.equals(vE.getEvent()) ? vE : null;
                }
                if (voteEvent == null) {
                    return;
                }
                if (voteEvent.getStatus() == VoteEvent.STATUS_PREVIOUS) {
                    VoteEvent.events.remove(voteEvent);
                    VoteEvent.events.put(new VoteEvent(commandSender, event, time, description), time);
                    for (Player onlinePlayer : BetterServer.getInstance().getServer().getOnlinePlayers()) {
                        onlinePlayer.sendMessage(ChatColor.GREEN + commandSender.getName() + " 发起了一次投票(" + after + "后结束): " + event);
                        onlinePlayer.sendMessage(ChatColor.GRAY + "投票说明: " + description);
                    }
                } else {
                    commandSender.sendMessage(ChatColor.RED + "无法发起投票，你所发起的投票事件名称与现有的相同！");
                }
            } else {
                VoteEvent.events.put(new VoteEvent(commandSender, event, time, description), time);
                for (Player onlinePlayer : BetterServer.getInstance().getServer().getOnlinePlayers()) {
                    onlinePlayer.sendMessage(ChatColor.GREEN + commandSender.getName() + " 发起了一次投票(" + after + "后结束): " + event);
                    onlinePlayer.sendMessage(ChatColor.GRAY + "投票说明: " + description);
                }
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "参数有误！");
        }
    }

    private void cancel(CommandSender commandSender, String event) {
        if (!eventExists(event)) {
            commandSender.sendMessage(ChatColor.RED + "无法取消投票，你要取消的投票不存在！");
        } else {
            VoteEvent voteEvent = null;
            for (VoteEvent vE : VoteEvent.events.keySet()) {
                voteEvent = event.equals(vE.getEvent()) ? vE : null;
            }
            if (voteEvent == null) {
                return;
            }
            if (voteEvent.getVotingInitiator().equals(commandSender)) {
                VoteEvent.events.remove(voteEvent);
                for (Player onlinePlayer : BetterServer.getInstance().getServer().getOnlinePlayers()) {
                    onlinePlayer.sendMessage(ChatColor.GREEN + "投票“" + event + "”已取消！");
                }
            } else {
                commandSender.sendMessage(ChatColor.RED + "无法取消投票，你不是投票发起者！");
            }
        }
    }


    private boolean eventExists(String event) {
        for (VoteEvent voteEvent : VoteEvent.events.keySet()) {
            return event.equals(voteEvent.getEvent());
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("for", "against", "create", "cancel");
        } else if (strings.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            for (VoteEvent voteEvent : VoteEvent.events.keySet()) {
                list.add(voteEvent.getEvent());
            }
            if (strings[0].equalsIgnoreCase("create")) {
                return Arrays.asList("stop_server");
            } else {
                return list;
            }
        } else if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("create")) {
                return Arrays.asList("TIME->[NUMBER][d/h/m/s]");
            }
        } else if (strings.length == 4) {
            return Arrays.asList("投票说明");
        }
        return new ArrayList<>();
    }
}
