package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class GetCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }

        Player player = (Player) commandSender;
        //get block at x y z
        if (strings.length != 5 || !strings[0].equals("block") || !strings[1].equals("at") || !areDecimal(strings[2], strings[3], strings[4])) {
            player.sendMessage(ChatColor.RED + "参数错误！");
            return true;
        }
        double x = Double.parseDouble(strings[2]);
        double y = Double.parseDouble(strings[3]);
        double z = Double.parseDouble(strings[4]);
        BetterServer betterServer = BetterServer.getInstance();
        Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), x, y, z));
        ItemStack itemStack = new ItemStack(block.getType());
        player.getInventory().addItem(itemStack);
        player.sendMessage(block.toString());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("block");
        } else if (strings.length == 2) {
            return Arrays.asList("at");
        } else if (strings.length == 3) {
            return Arrays.asList("x");
        } else if (strings.length == 4) {
            return Arrays.asList("y");
        } else if (strings.length == 5) {
            return Arrays.asList("z");
        }
        return new ArrayList<>();
    }

    private static boolean areDecimal(String x, String y, String z) {
        String regex = "^[-+]?(\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][-+]?\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(x).matches() && pattern.matcher(y).matches() && pattern.matcher(z).matches();
    }
}
