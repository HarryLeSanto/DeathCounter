package me.harrylesanto.deathcounter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File file;
    private static FileConfiguration configFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("DeathCounter").getDataFolder(), "PlayerDeaths.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // do nothing
            }
        }

        configFile = YamlConfiguration.loadConfiguration(file);
        configFile.options().copyDefaults(true);
        save();
    }

    public static FileConfiguration getConfigFile() {
        return configFile;
    }

    public static void save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            System.out.println("Failed to save file.");
        }
    }

    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}
