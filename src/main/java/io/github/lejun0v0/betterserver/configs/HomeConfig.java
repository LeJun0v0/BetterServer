package io.github.lejun0v0.betterserver.configs;

import io.github.lejun0v0.betterserver.BetterServer;
import io.github.lejun0v0.betterserver.functions.Home;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

public class HomeConfig {
    private final static HomeConfig instance = new HomeConfig();
    private File file;
    private YamlConfiguration config;

    private HomeConfig() {

    }

    public static HomeConfig getInstance() {
        return instance;
    }

    public void load() {
        file = new File(BetterServer.getInstance().getDataFolder(), "homes.yml");
        if (!file.exists()) {
            BetterServer.getInstance().saveResource("homes.yml", false);
        }
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public Home getHome(String name) {
        if (!config.contains(name)) {
            return null;
        }
        return new Home(config.getString(name + ".owner")
                , BetterServer.getInstance().getServer().getWorld(config.getString(name + ".world"))
                , config.getDouble(name + ".x")
                , config.getDouble(name + ".y")
                , config.getDouble(name + ".z"));
    }

    public void reload() {
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean homeExists(String name) {
        return config.contains(name);
    }

    public void setHome(String name, Player player) {
        String owner = player.getName();
        String world = player.getWorld().getName();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        set(name + ".owner", owner);
        set(name + ".world", world);
        set(name + ".x", x);
        set(name + ".y", y);
        set(name + ".z", z);
    }

    public ArrayList<String> getHomes(Player player) {
        ArrayList<String> homeList = new ArrayList<>();
        Set<String> homesToVerify = config.getKeys(false);
        homesToVerify.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (player.getName().equals(config.get(s + ".owner"))) {
                    homeList.add(s);
                }
            }
        });
        return homeList;
    }
}
