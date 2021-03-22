package de.blocki.advancedrank.main;

import de.blocki.advancedrank.luckperms.commands.LP_rank;
import de.blocki.advancedrank.luckperms.commands.LP_rankCommandsTabComplete;
import de.blocki.advancedrank.luckperms.listener.GroupAddRemove;
import de.blocki.advancedrank.main.utils.ConfigManager;
import de.blocki.advancedrank.vault.commands.vault_rank;
import de.blocki.advancedrank.vault.commands.vault_rankCommandsTabComplete;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    public static String prefix;

    //Sets the Plugin Variable Public
    public static Plugin plugin;
    //Sets the Plugin Variable Public
    public static LuckPerms lpApi;

    public static boolean isLuckPerms;
    public static boolean isVault;

    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        plugin = this;

        //register rank command
        try {
            if (getServer().getPluginManager().getPlugin("LuckPerms").isEnabled()) {
                System.out.println("[AdvancedRank] Found the Plugin LuckPerms");
                LuckPerms api = LuckPermsProvider.get();
                lpApi = api;
                getCommand("rank").setExecutor(new LP_rank(this, api));
                getCommand("rank").setTabCompleter(new LP_rankCommandsTabComplete());
                new GroupAddRemove(this, api).register();
                isLuckPerms = true;
            }
        }catch (NullPointerException ignored){
            try {
                if (getServer().getPluginManager().getPlugin("Vault").isEnabled()) {
                    System.out.println("[AdvancedRank] Found the Plugin Vault");
                    getCommand("rank").setExecutor(new vault_rank());
                    getCommand("rank").setTabCompleter(new vault_rankCommandsTabComplete());
                    setupChat();
                    setupPermissions();
                    isVault = true;
                }
            }catch (NullPointerException ignored2){
                getLogger().log(Level.WARNING, "No supported permission Plugin (Vault) could be found. Deactivate...");
                getServer().getPluginManager().disablePlugin(this);
            }
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


        setDefaultConfig();
    }

    private void setDefaultConfig(){
        if(ConfigManager.get("MessagePrefix") == null){ ConfigManager.set("MessagePrefix", "§7[§6Rank§7]"); }
        prefix = ConfigManager.get("MessagePrefix") + " ";
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

}
