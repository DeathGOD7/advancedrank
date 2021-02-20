package de.blocki.advancedrank.luckperms.commands;

import de.blocki.advancedrank.main.Main;
import net.luckperms.api.model.group.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class lp_rankCommandsTabComplete implements TabCompleter {

    List<String> mainCMD = new ArrayList<String>();
    List<String> groupsArg = new ArrayList<String>();

    Set<Group> groups = Main.lpApi.getGroupManager().getLoadedGroups();
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(mainCMD.isEmpty()){
            mainCMD.add("add");
            mainCMD.add("remove");
            mainCMD.add("set");
        }

        if(groupsArg.isEmpty()){
            for(Group group : groups) {
                groupsArg.add(group.getName());
            }
        }

        List<String> result = new ArrayList<String>();
        if(args.length == 1){
            for(String a : mainCMD){
                if(a.toLowerCase().startsWith(args[0].toLowerCase())){
                    result.add(a);
                }
            }
            return result;
        }else if(args.length == 3){
            for(String b : groupsArg){
                if(b.toLowerCase().startsWith(args[2].toLowerCase())){
                    result.add(b);
                }
            }
            return result;
        }
        return null;
    }
}
