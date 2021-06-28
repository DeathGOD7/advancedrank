package de.blocki.advancedrank.main;

import de.blocki.advancedrank.luckperms.commands.RankCommand;
import de.blocki.advancedrank.main.utils.config.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static String prefix;
    private static Plugin plugin;
    public static LuckPerms lpApi;
    public static boolean isLuckPerms;
    private static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();
        setDefaultConfig();

        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            logger.log(Level.INFO, "[AdvancedRank] Found the plugin LuckPerms");
            isLuckPerms = true;
            lpApi = LuckPermsProvider.get();
            RankCommand cmd = new RankCommand(this, lpApi);
            getCommand("rank").setExecutor(cmd);
            getCommand("rank").setTabCompleter(cmd);
        }

        //sout the text
        logger.log(Level.INFO, "              _                               _ _____             _    ");
        logger.log(Level.INFO, "     /\\      | |                             | |  __ \\           | |   ");
        logger.log(Level.INFO, "    /  \\   __| |_   ____ _ _ __   ___ ___  __| | |__) |__ _ _ __ | | __");
        logger.log(Level.INFO, "   / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` |  _  // _` | '_ \\| |/ /");
        logger.log(Level.INFO, "  / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| | | \\ \\ (_| | | | |   < ");
        logger.log(Level.INFO, " /_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|_|  \\_\\__,_|_| |_|_|\\_\\");
        logger.log(Level.INFO, "");
        logger.log(Level.INFO, "by blocki");
        logger.log(Level.INFO, "");

    }

    private void setDefaultConfig(){
        ConfigManager.setDef("Message.Prefix", "§7[§6Rank§7]");
        prefix = ConfigManager.getString("Message.Prefix") + " ";
    }
    
    public static Plugin getPlugin(){
        return plugin;
    }
    
    public static Logger getPLuginLogger(){
        return logger;
    }

}
