package io.github.lejun0v0.betterserver.commands;

import io.github.lejun0v0.betterserver.items.Keys;
import io.github.lejun0v0.betterserver.others.NormalStick;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalStickCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "仅玩家可使用该指令！");
            return true;
        }
        final Player player = (Player) commandSender;
        if (strings.length == 0) {
            //no argument
            player.sendMessage(ChatColor.RED + "参数有误！");
            return true;
        } else if (strings.length == 1) {
            //1 argument
            if (strings[0].equalsIgnoreCase("get")) {
                ItemStack normalStick = new ItemStack(Material.STICK);
                ItemMeta meta = normalStick.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "普通的棍子");
                meta.setUnbreakable(true);
                meta.setLore(Arrays.asList("", ChatColor.GRAY + "平平无奇~"));
                meta.getPersistentDataContainer().set(Keys.NORMAL_STICK, PersistentDataType.BOOLEAN, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                normalStick.setItemMeta(meta);
                player.getInventory().addItem(normalStick);
                NormalStick.addPlayer(player);
                return true;
            } else if (strings[0].equalsIgnoreCase("lightning")) {
                NormalStick.addPlayer(player);
                NormalStick.setMode(player, NormalStick.LIGHTNING_MODE);
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "参数有误！");
                return true;
            }
        } else {
            //too many arguments
            player.sendMessage(ChatColor.RED + "参数有误！");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return Arrays.asList("get", "lightning");
        }
        return new ArrayList<>();
    }
}
