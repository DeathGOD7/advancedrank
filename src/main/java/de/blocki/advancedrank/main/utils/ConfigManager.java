package de.blocki.advancedrank.main.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File file = new File("plugins//AdvancedRank//config.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);


    public static void set(String path, String value){
        yamlConfiguration.set(path, value);
        save();
    }

    public static String get(String path){
        return yamlConfiguration.getString(path);
    }

    public static void save(){
        try{
            yamlConfiguration.save(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
