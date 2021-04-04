package de.blocki.advancedrank.luckperms.commands;

import de.blocki.advancedrank.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.LuckPermsEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Collectors;

public class LP_rankCommandsTabComplete implements TabCompleter {

    public static List<String> groupsArg = new ArrayList<>();

    Set<Group> groups = Main.lpApi.getGroupManager().getLoadedGroups();

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(groupsArg.isEmpty()){
            for(Group group : groups) {
                groupsArg.add(group.getName());
            }
        }

        List<String> result = new ArrayList<>();
        if(args.length == 1){
            result.add("add");
            result.add("remove");
            result.add("set");
            result.add("info");
        }
        if(args.length == 2){
            for (Player p : Bukkit.getOnlinePlayers()){
                result.add(p.getName());
            }
        }
        if(args.length == 3){
            if(args[0].equalsIgnoreCase("remove")){
                String playername = args[1];
                if(args[1] != null) {
                    Player ptoget = Bukkit.getPlayer(playername);
                    if (ptoget != null) {
                        PlayerAdapter<Player> playerAdapter = Main.lpApi.getPlayerAdapter(Player.class);
                        User user = playerAdapter.getUser(ptoget);
                        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(ptoget));
                        for (Group g : groups) {
                            result.add(g.getName());
                        }
                    }
                }
            }else {
                for(String b : groupsArg){
                    if(b.toLowerCase().startsWith(args[2].toLowerCase())){
                        result.add(b);
                    }
                }
            }
        }
        return result;
    }
}
