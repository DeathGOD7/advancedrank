package de.blocki.advancedrank.main;

import de.blocki.advancedrank.luckperms.commands.lp_rank;
import de.blocki.advancedrank.luckperms.commands.lp_rankCommandsTabComplete;
import de.blocki.advancedrank.main.utils.ConfigManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static String private_prefix;
    public static String prefix = private_prefix + " ";

    //Sets the Plugin Variable Public
    public static Plugin plugin;
    //Sets the Plugin Variable Public
    public static LuckPerms lpApi;

    @Override
    public void onEnable() {
        plugin = this;

        //register rank command
        if(getServer().getPluginManager().getPlugin("LuckPerms").isEnabled()){
            System.out.println("[AdvancedNTE] Das Plugin LuckPerms wurde gefunden");
            LuckPerms api = LuckPermsProvider.get();
            lpApi = api;
            getCommand("rank").setExecutor(new lp_rank(this, api));
            getCommand("rank").setTabCompleter(new lp_rankCommandsTabComplete());
        }

        //sout the text
        System.out.println("              _                               _ _____             _    ");
        System.out.println("     /\\      | |                             | |  __ \\           | |   \n");
        System.out.println("    /  \\   __| |_   ____ _ _ __   ___ ___  __| | |__) |__ _ _ __ | | __");
        System.out.println("   / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` |  _  // _` | '_ \\| |/ /");
        System.out.println("  / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| | | \\ \\ (_| | | | |   < \n");
        System.out.println(" /_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|_|  \\_\\__,_|_| |_|_|\\_\\\n");
        System.out.println("");
        System.out.println("AdvancedRank by blocki");
        System.out.println("");


        setDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setDefaultConfig(){
        if(ConfigManager.get("MessagePrefix") == null){ ConfigManager.set("MessagePrefix", "§7[§6Rank§7] "); }
        private_prefix = ConfigManager.get("MessagePrefix");
    }
}
