package io.github.lejun0v0.betterserver.others;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NormalStick {
    private static HashMap<UUID, Integer> mode = new HashMap<>();
    public static final Integer NO_MODE = -1;
    public static final Integer DEFAULT_MODE = 0;
    public static final Integer LIGHTNING_MODE = 1;

    public static void addPlayer(Player player) {
        if (!mode.containsKey(player.getUniqueId())) {
            mode.put(player.getUniqueId(), DEFAULT_MODE);
        }
    }

    public static void removePlayer(Player player) {
        if (mode.containsKey(player.getUniqueId())) {
            mode.remove(player.getUniqueId());
        }
    }

    public static void setMode(Player player, Integer modeValue) {
        if (!mode.containsKey(player.getUniqueId())) {
            addPlayer(player);
        }
        mode.replace(player.getUniqueId(), modeValue);
    }

    public static Integer getMode(Player player) {
        if (!mode.containsKey(player.getUniqueId())) {
            return -1;
        }
        return mode.get(player.getUniqueId());
    }
}