package de.blocki.advancedrank.commands;

import de.blocki.advancedrank.main.Main;
import de.blocki.advancedrank.main.utils.config.Permissions;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RankCommand implements CommandExecutor, TabCompleter {
    private final Main plugin;
    private final LuckPerms luckPerms;

    public RankCommand(Main plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pSender = (Player) sender;
            if(args.length >= 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (pSender.hasPermission(Permissions.RANK_SET.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                        if (args.length != 3) {
                            sender.sendMessage(Main.getPrefix() + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        Player player = this.plugin.getServer().getPlayer(playerName);

                        if (player == null) {
                            sender.sendMessage(Main.getPrefix() + playerName + " wurde nicht gefunden.");
                            return true;
                        }

                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        if (group == null) {
                            sender.sendMessage(Main.getPrefix() + "Die Gruppe " + groupName.replace("&", "§") + "§7 existiert nicht.");
                            return true;
                        }

                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            user.data().clear(NodeType.INHERITANCE::matches);

                            Node node = InheritanceNode.builder(group).build();

                            user.data().add(node);

                            String groupnamevar;
                            String displayname = group.getDisplayName();
                            String name = group.getName();
                            if (displayname != null) {
                                groupnamevar = displayname;
                            } else {
                                groupnamevar = name;
                            }

                            sender.sendMessage((Main.getPrefix() + user.getUsername() + " ist jetzt in der Gruppe " + groupnamevar + "§7.").replace("&", "§"));
                            player.sendMessage((Main.getPrefix() + "Dir wurde der Rang " + groupnamevar + " §7zugewiesen!").replace("&", "§"));
                        });
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (pSender.hasPermission(Permissions.RANK_REMOVE.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                        if (args.length != 3) {
                            sender.sendMessage(Main.getPrefix() + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        Player player = this.plugin.getServer().getPlayer(playerName);

                        if (player == null) {
                            sender.sendMessage(Main.getPrefix() + playerName + " wurde nicht gefunden.");
                            return true;
                        }

                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        if (group == null) {
                            sender.sendMessage(Main.getPrefix() + "Die Gruppe " + groupName.replace("&", "§") + " existiert nicht.");
                            return true;
                        }

                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            Node node = InheritanceNode.builder(group).build();

                            if (!(this.luckPerms.getUserManager().getUser(player.getUniqueId()).getNodes().contains(node))) {
                                sender.sendMessage(Main.getPrefix() + "Der Spieler " + player.getName() + " hat die Gruppe " + group.getName() + " nicht.");
                            } else {
                                user.data().remove(node);

                                String groupnamevar;
                                String displayname = group.getDisplayName();
                                String name = group.getName();
                                if (displayname != null) {
                                    groupnamevar = displayname;
                                } else {
                                    groupnamevar = name;
                                }

                                sender.sendMessage((Main.getPrefix() + user.getUsername() + " wurde aus der Gruppe " + groupnamevar + "§7 entfernt.").replace("&", "§"));
                                player.sendMessage((Main.getPrefix() + "Dir wurde der Rang " + groupnamevar + " §7entfernt!").replace("&", "§"));
                            }

                        });
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (pSender.hasPermission(Permissions.RANK_ADD.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                        if (args.length != 3) {
                            sender.sendMessage(Main.getPrefix() + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        Player player = this.plugin.getServer().getPlayer(playerName);

                        if (player == null) {
                            sender.sendMessage(Main.getPrefix() + playerName + " wurde nicht gefunden.");
                            return true;
                        }

                        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

                        if (group == null) {
                            sender.sendMessage(Main.getPrefix() + "Die Gruppe " + groupName.replace("&", "§") + " existiert nicht.");
                            return true;
                        }

                        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

                            Node node = InheritanceNode.builder(group).build();

                            if (this.luckPerms.getUserManager().getUser(player.getUniqueId()).getNodes().contains(node)) {
                                sender.sendMessage(Main.getPrefix() + "Der Spieler " + player.getName() + " hat die Gruppe " + group.getName() + " bereits.");
                            } else {
                                user.data().add(node);

                                String groupnamevar;
                                String displayname = group.getDisplayName();
                                String name = group.getName();
                                if (displayname != null) {
                                    groupnamevar = displayname;
                                } else {
                                    groupnamevar = name;
                                }

                                sender.sendMessage((Main.getPrefix() + user.getUsername() + " wurde zur Gruppe " + groupnamevar + "§7 hinzugefügt.").replace("&", "§"));
                                player.sendMessage((Main.getPrefix() + "Dir wurde der Rang " + groupnamevar + " §7hinzugefügt!").replace("&", "§"));
                            }

                        });
                    }
                } else if (args[0].equalsIgnoreCase("info")) {
                    if (pSender.hasPermission(Permissions.RANK_INFO.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                        String pname = args[1];
                        Player player = Bukkit.getPlayer(pname);
                        if (player != null) {
                            PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);

                            User user = playerAdapter.getUser(player);

                            Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));

                            String groupsString = groups.stream().map(g -> Objects.isNull(g.getDisplayName()) ? g.getName() : g.getDisplayName()).collect(Collectors.joining("§r§7, ")).replace("&", "§");
                            if (groups.size() == 0) {
                                sender.sendMessage(Main.getPrefix() + "Der Spieler " + player.getName() + " hat keinen Rang!");
                            } else {
                                if (groups.size() >= 2) {
                                    sender.sendMessage(Main.getPrefix() + "Der Spieler "+player.getName()+" hat die Ränge: " + groupsString + "§7.");
                                } else {
                                    sender.sendMessage(Main.getPrefix() + "Der Spieler "+player.getName()+" hat den Rang: " + groupsString + "§7.");
                                }
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    if(pSender.hasPermission(Permissions.RANK_HELP.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                        pSender.sendMessage(Main.getPrefix() + "Befehle:");
                        pSender.sendMessage(Main.getPrefix() + "/rank set <Spieler> <Group> | Setzt dem Spieler die Gruppe.");
                        pSender.sendMessage(Main.getPrefix() + "/rank remove <Spieler> <Group> | Entfernt dem Spieler die Gruppe.");
                        pSender.sendMessage(Main.getPrefix() + "/rank add <Spieler> <Group> | Fügt eine Gruppe zum Spieler hinzu.");
                        pSender.sendMessage(Main.getPrefix() + "/rank info <Spieler> | Zeigt den Rang des Spielers an.");
                        pSender.sendMessage(Main.getPrefix() + "/rank | Zeigt den Rang von dir an.");
                    }
                }
            }else {
                if(pSender.hasPermission(Permissions.RANK_SELFINFO.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                    PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);
                    User user = playerAdapter.getUser(pSender);
                    Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(pSender));
                    String groupsString = groups.stream().map(g -> Objects.isNull(g.getDisplayName()) ? g.getName() : g.getDisplayName()).collect(Collectors.joining("§r§7, ")).replace("&", "§");
                    if (groups.size() == 0) {
                        sender.sendMessage(Main.getPrefix() + "Du hast keinen Rang!");
                    } else {
                        if (groups.size() >= 2) {
                            sender.sendMessage(Main.getPrefix() + "Du hast die Ränge: " + groupsString + "§7.");
                        } else {
                            sender.sendMessage(Main.getPrefix() + "Du hast den Rang: " + groupsString + "§7.");
                        }
                    }
                }
            }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        Player pSender = (Player) sender;

        Set<Group> groups = Main.lpApi.getGroupManager().getLoadedGroups();

        List<String> result = new ArrayList<>();
            
        //args[0]
        if (args.length == 1) {
            if (pSender.hasPermission(Permissions.RANK_ADD.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                result.add("add");
            }
            if (pSender.hasPermission(Permissions.RANK_REMOVE.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                result.add("remove");
            }
            if (pSender.hasPermission(Permissions.RANK_SET.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                result.add("set");
            }
            if (pSender.hasPermission(Permissions.RANK_INFO.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                result.add("info");
            }
            if (pSender.hasPermission(Permissions.RANK_HELP.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                result.add("help");
            }
        }

        //args[1]
        if (args.length == 2) {
            if (pSender.hasPermission(Permissions.RANK_ADD.toString()) || pSender.hasPermission(Permissions.RANK_REMOVE.toString()) || pSender.hasPermission(Permissions.RANK_SET.toString()) || pSender.hasPermission(Permissions.RANK_INFO.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                if(!args[0].equalsIgnoreCase("help")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        result.add(p.getName());
                    }
                }
            }
        }

        //args[2]
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (pSender.hasPermission(Permissions.RANK_REMOVE.toString()) || pSender.hasPermission(Permissions.RANK_OP.toString()) || pSender.hasPermission(Permissions.STAR.toString())) {
                    String playername = args[1];
                    if (args[1] != null) {
                        Player ptoget = Bukkit.getPlayer(playername);
                        if (ptoget != null) {
                            PlayerAdapter<Player> playerAdapter = Main.lpApi.getPlayerAdapter(Player.class);
                            User user = playerAdapter.getUser(ptoget);
                            Collection<Group> inheritedGroups = user.getInheritedGroups(playerAdapter.getQueryOptions(ptoget));
                            for (Group g : inheritedGroups) {
                                result.add(g.getName());
                            }
                        }
                    }
                }
            } else {
                if (!(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("help"))) {
                    for (Group g : groups) {
                        result.add(g.getName());
                    }
                }
            }
        }
        return result;
    }
}
