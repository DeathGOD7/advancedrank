package de.blocki.advancedrank.main;

import de.blocki.advancedrank.luckperms.commands.RankCommand;
import de.blocki.advancedrank.main.utils.config.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static String prefix;
    public static Plugin plugin;
    public static LuckPerms lpApi;
    public static boolean isLuckPerms;

    @Override
    public void onEnable() {
        plugin = this;
        setDefaultConfig();

        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            System.out.println("[AdvancedRank] Found the plugin LuckPerms");
            isLuckPerms = true;
            LuckPerms api = LuckPermsProvider.get();
            lpApi = api;
            RankCommand cmd = new RankCommand(this, api);
            getCommand("rank").setExecutor(cmd);
            getCommand("rank").setTabCompleter(cmd);
        }

        //sout the text
        System.out.println("              _                               _ _____             _    ");
        System.out.println("     /\\      | |                             | |  __ \\           | |   \n");
        System.out.println("    /  \\   __| |_   ____ _ _ __   ___ ___  __| | |__) |__ _ _ __ | | __");
        System.out.println("   / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` |  _  // _` | '_ \\| |/ /");
        System.out.println("  / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| | | \\ \\ (_| | | | |   < \n");
        System.out.println(" /_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|_|  \\_\\__,_|_| |_|_|\\_\\\n");
        System.out.println("");
        System.out.println("by blocki");
        System.out.println("");

    }

    private void setDefaultConfig(){
        ConfigManager.setDef("Message.Prefix", "§7[§6Rank§7]");
        prefix = ConfigManager.getString("Message.Prefix") + " ";
    }

}
