package de.blocki.advancedrank.luckperms.commands;

import de.blocki.advancedrank.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class lp_rank implements CommandExecutor {
    private final Main plugin;
    private final LuckPerms luckPerms;

    public lp_rank(Main plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pSender = (Player) sender;
            if(args.length >= 1) {
                if (args[0].equalsIgnoreCase("set")) {
                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        // Get an OfflinePlayer object for the player
                        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

                        // Player not known?
                        if (player == null || !player.hasPlayedBefore()) {
                            sender.sendMessage(Main.prefix + playerName + " hat den Server noch nie betreten.");
                            return true;
                        }

                        // Get a group object for the group name.
                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        // Group doesn't exist?
                        if (group == null) {
                            sender.sendMessage(Main.prefix + "Die Gruppe " + groupName.replace("&", "§") + "§7 existiert nicht.");
                            return true;
                        }

                        // Load, modify & save the user in LuckPerms.
                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            // Remove all other inherited groups the user had before.
                            user.data().clear(NodeType.INHERITANCE::matches);

                            // Create a node to add to the player.
                            Node node = InheritanceNode.builder(group).build();

                            // Add the node to the user.
                            user.data().add(node);

                            // Tell the sender.
                            String groupnamevar;
                            String displayname = group.getDisplayName();
                            String name = group.getName();
                            if(displayname != null){ groupnamevar = displayname; }else { groupnamevar = name; }

                            String message = Main.prefix + user.getUsername() + " ist jetzt in der Gruppe " + groupnamevar + "§7.";
                            sender.sendMessage(message.replace("&", "§"));
                            Player OnlinePlayerReciever = Bukkit.getPlayer(playerName);
                            String messageToSend = Main.prefix + "Dir wurde der Rang " + groupnamevar + " §7zugewiesen!";
                            OnlinePlayerReciever.sendMessage(messageToSend.replace("&", "§"));
                        });

                } else if (args[0].equalsIgnoreCase("remove")) {

                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        // Get an OfflinePlayer object for the player
                        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

                        // Player not known?
                        if (player == null || !player.hasPlayedBefore()) {
                            sender.sendMessage(Main.prefix + playerName + " hat den Server noch nie betreten.");
                            return true;
                        }

                        // Get a group object for the group name.
                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        // Group doesn't exist?
                        if (group == null) {
                            sender.sendMessage(Main.prefix + "Die Gruppe " + groupName.replace("&", "§") + " existiert nicht.");
                            return true;
                        }

                        // Load, modify & save the user in LuckPerms.
                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            // Create a node to add to the player.
                            Node node = InheritanceNode.builder(group).build();

                            // Add the node to the user.
                            user.data().remove(node);

                            // Tell the sender.
                            String groupnamevar;
                            String displayname = group.getDisplayName();
                            String name = group.getName();
                            if(displayname != null){ groupnamevar = displayname; }else { groupnamevar = name; }


                            String message = Main.prefix + user.getUsername() + " wurde aus der Gruppe " + groupnamevar + "§7 entfernt.";
                            sender.sendMessage(message.replace("&", "§"));
                            Player OnlinePlayerReciever = Bukkit.getPlayer(playerName);
                            String messageToSend = Main.prefix + "Dir wurde der Rang " + groupnamevar + " §7entfernt!";
                            OnlinePlayerReciever.sendMessage(messageToSend.replace("&", "§"));

                        });

                }else if(args[0].equalsIgnoreCase("add")){

                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        // Get an OfflinePlayer object for the player
                        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

                        // Player not known?
                        if (player == null || !player.hasPlayedBefore()) {
                            sender.sendMessage(Main.prefix + playerName + " hat den Server noch nie betreten.");
                            return true;
                        }

                        // Get a group object for the group name.
                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        // Group doesn't exist?
                        if (group == null) {
                            sender.sendMessage(Main.prefix + "Die Gruppe " + groupName.replace("&", "§") + " existiert nicht.");
                            return true;
                        }

                        // Load, modify & save the user in LuckPerms.
                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            // Create a node to add to the player.
                            Node node = InheritanceNode.builder(group).build();

                            // Add the node to the user.
                            user.data().add(node);

                            // Tell the sender.
                            String groupnamevar;
                            String displayname = group.getDisplayName();
                            String name = group.getName();
                            if(displayname != null){ groupnamevar = displayname; }else { groupnamevar = name; }


                            String message = Main.prefix + user.getUsername() + " wurde aus zur Gruppe " + groupnamevar + "§7 hinzugefügt.";
                            sender.sendMessage(message.replace("&", "§"));
                            Player OnlinePlayerReciever = Bukkit.getPlayer(playerName);
                            String messageSend = Main.prefix + "Dir wurde der Rang " + groupnamevar + " §7hinzugefügt!";
                            OnlinePlayerReciever.sendMessage(messageSend.replace("&", "§"));
                        });
                }
            }else {
                pSender.sendMessage(Main.prefix + "Befehle:");
                pSender.sendMessage(Main.prefix + "/rank set <Spieler> <Group> | Setzt dem Spieler die Gruppe.");
                pSender.sendMessage(Main.prefix + "/rank remove <Spieler> <Group> | Entfernt dem Spieler die Gruppe.");
                pSender.sendMessage(Main.prefix + "/rank add <Spieler> <Group> | Fügt eine Gruppe zum Spieler hinzu.");
            }
        return true;
    }
}
