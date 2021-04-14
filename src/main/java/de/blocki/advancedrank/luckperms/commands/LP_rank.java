package de.blocki.advancedrank.luckperms.commands;

import de.blocki.advancedrank.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class LP_rank implements CommandExecutor {
    private final Main plugin;
    private final LuckPerms luckPerms;

    public LP_rank(Main plugin, LuckPerms luckPerms) {
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

                        // Get an Player object for the player
                        Player player = this.plugin.getServer().getPlayer(playerName);

                        // Player not known?
                        if (player == null) {
                            sender.sendMessage(Main.prefix + playerName + " wurde nicht gefunden.");
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
                            if (displayname != null) {
                                groupnamevar = displayname;
                            } else {
                                groupnamevar = name;
                            }

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

                        // Get an Player object for the player
                        Player player = this.plugin.getServer().getPlayer(playerName);

                        // Player not known?
                        if (player == null) {
                            sender.sendMessage(Main.prefix + playerName + " wurde nicht gefunden.");
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

                            if(!(this.luckPerms.getUserManager().getUser(player.getUniqueId()).getNodes().contains(node))){
                                sender.sendMessage(Main.prefix + "Der Spieler " + player.getName() + " hat die Gruppe " + group.getName() + " nicht.");
                            }else {
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
                            }

                        });

                }else if(args[0].equalsIgnoreCase("add")){

                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        // Get an Player object for the player
                        Player player = this.plugin.getServer().getPlayer(playerName);

                        // Player not known?
                        if (player == null) {
                            sender.sendMessage(Main.prefix + playerName + " wurde nicht gefunden.");
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

                            if(this.luckPerms.getUserManager().getUser(player.getUniqueId()).getNodes().contains(node)){
                                sender.sendMessage(Main.prefix + "Der Spieler " + player.getName() + " hat die Gruppe " + group.getName() + " bereits.");
                            }else {
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
                            }

                        });
                }else if(args[0].equalsIgnoreCase("info")){

                    String pname = args[1];
                    Player player = Bukkit.getPlayer(pname);

                    if(player != null) {
                        // Get a Bukkit player adapter.
                        PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);

                        // Get a LuckPerms user for the player.
                        User user = playerAdapter.getUser(player);

                        // Get all of the groups they inherit from on the current server.
                        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));

                        String groupsString = groups.stream().map(g -> Objects.isNull(g.getDisplayName()) ? g.getName() : g.getDisplayName()).collect(Collectors.joining("§r§7, ")).replace("&", "§");
                        if(groups.size() == 0) {
                            sender.sendMessage(Main.prefix + "Der Spieler " + player.getName() +" hat keinen Rang!");
                        }else {
                            if(groups.size() >= 1){
                                sender.sendMessage(Main.prefix + "Der Spieler " + player.getName() +" hat den Rang: " + groupsString + "§7.");
                            } else {
                                sender.sendMessage(Main.prefix + "Der Spieler " + player.getName() +" hat die Ränge: " + groupsString + "§7.");
                            }
                        }
                    }
                }else if(args[0].equalsIgnoreCase("help")) {
                    pSender.sendMessage(Main.prefix + "Befehle:");
                    pSender.sendMessage(Main.prefix + "/rank set <Spieler> <Group> | Setzt dem Spieler die Gruppe.");
                    pSender.sendMessage(Main.prefix + "/rank remove <Spieler> <Group> | Entfernt dem Spieler die Gruppe.");
                    pSender.sendMessage(Main.prefix + "/rank add <Spieler> <Group> | Fügt eine Gruppe zum Spieler hinzu.");
                    pSender.sendMessage(Main.prefix + "/rank (info <Spieler>) | Zeigt den Rang des Spielers an.");
                    pSender.sendMessage(Main.prefix + "/rank | Zeigt den Rang von dir an.");

                }
            }else {
                // Get a Bukkit player adapter.
                PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);

                // Get a LuckPerms user for the player.
                User user = playerAdapter.getUser(pSender);

                // Get all of the groups they inherit from on the current server.
                Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(pSender));

                // Convert to a comma separated string (e.g. "admin, mod, default")
                String groupsString = groups.stream().map(g -> Objects.isNull(g.getDisplayName()) ? g.getName() : g.getDisplayName()).collect(Collectors.joining("§r§7, ")).replace("&", "§");

                if (groups.size() == 0) {
                    sender.sendMessage(Main.prefix + "Du hast keinen Rang!");
                } else {
                    if (groups.size() >= 1) {
                        sender.sendMessage(Main.prefix + "Du hast den Rang: " + groupsString + "§7.");
                    } else {
                        sender.sendMessage(Main.prefix + "Du hast die Ränge: " + groupsString + "§7.");
                    }
                }
            }
        return true;
    }
}
