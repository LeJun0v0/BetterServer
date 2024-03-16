package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.configs.HomeConfig;
import org.bukkit.ChatColor;
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

public class SetHomeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }
        final Player player = (Player) commandSender;
        if (!BetterServer.getInstance().getConfig().getBoolean("BetterServerHome")) {
            player.sendMessage(ChatColor.RED + "BetterServerHome已禁用");
            return true;
        }
        if (strings.length != 1) {
            player.sendMessage(ChatColor.RED + "该命令只能有一个参数！");
            return true;
        }
        String homeName = strings[0];
        int isAppropriate = isAppropriate(homeName);
        if (isAppropriate == 0) {
            player.sendMessage(ChatColor.RED + "家的名字不符合命名规则！");
            return true;
        } else if (isAppropriate == 2) {
            player.sendMessage(ChatColor.RED + "存在相同名字的家！");
            return true;
        }
        //名字合适，考虑设置
        /*先实现最基本的设置，然后再去根据可设置的家的个数与已设置的个数，
        判断是否能设置家了，其次还有设置的家的位置，个数自增等等*/
        HomeConfig.getInstance().setHome(homeName, player);
        player.sendMessage(ChatColor.GREEN + "家 '" + homeName + "' 设置成功！");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }

    private static int isAppropriate(String homeName) {
        //0 --> illegal name
        //1 --> proper name
        //2 --> name exists

        //以下判断是否不符要求
        if (!Pattern.compile("^[a-zA-Z0-9_-]{1,16}$").matcher(homeName).matches()) {
            return 0;
        } else if (HomeConfig.getInstance().homeExists(homeName)) {
            //命名没有问题
            //判断是否重复并返回 值
            //实现思路，默认返回1，表示名字合适，一旦在判断过程中不符合要求那么就返回其他的值
            return 2;
        }
        return 1;
    }

}
