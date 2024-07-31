package io.github.lejun0v0.betterserver.utils;

import io.github.lejun0v0.betterserver.BetterServer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MultiLang {
    private static final MultiLang instance = new MultiLang();
    private static final File langFolder = new File(BetterServer.getInstance().getDataFolder(), "lang");
    private YamlConfiguration langConfig = new YamlConfiguration();

    private MultiLang() {

    }

    public void init() {
        FileConfiguration config = BetterServer.getInstance().getConfig();
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }//判断lang文件夹是否存在，确保目录存在
        String temp = config.getString("Language");
        //释放语言文件
        String[] lang = {"zh_cn"};
        for (String s : lang) {
            BetterServer.getInstance().saveResource("lang\\" + s + ".yaml", false);
        }
        //指定语言(待修改)
        String language;
        if (temp == null) {
            BetterServer.getInstance().getLogger().warning("The item in 'Language' is illegal! It has been automatically set to zh_cn.");
            language = "zh_cn";
            config.set("Language", "zh_cn");
        } else {
            if (!new File(langFolder, temp + ".yaml").exists()) {
                language = "zh_cn";
                config.set("Language", "zh_cn");
                BetterServer.getInstance().getLogger().warning("File " + temp + ".yaml" + " doesn't exist! Language has been set to zh_cn");
            } else {
                language = temp;
            }
        }
        //读取
        File langFile = new File(langFolder, language + ".yaml");
        try {
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getText(String textFlag) {
        String text = langConfig.getString(textFlag);
        return text == null ? textFlag : text;
    }

    public static MultiLang getInstance() {
        return instance;
    }
}
