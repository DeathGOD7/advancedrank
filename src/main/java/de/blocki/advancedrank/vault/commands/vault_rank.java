package de.blocki.advancedrank.vault.commands;

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

public class vault_rank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pSender = (Player) sender;
            if(args.length >= 1) {
                if (args[0].equalsIgnoreCase("add")) {
                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        // Get an OfflinePlayer object for the player
                        Player player = Bukkit.getPlayer(playerName);

                        // Player not known?
                        if (player == null || !player.hasPlayedBefore()) {
                            sender.sendMessage(Main.prefix + playerName + " hat den Server noch nie betreten.");
                            return true;
                        }

                        try {
                            if(!Main.getPermissions().playerInGroup(player, groupName)) {
                                Main.getPermissions().playerAddGroup(player, groupName);
                                pSender.sendMessage(Main.prefix + "Dem Spieler " + playerName + " wurde die Gruppe " + groupName + " erfolgreich hinzugefügt.");
                            }else {
                                pSender.sendMessage(Main.prefix + "Der Spieler "+playerName+ " ist bereits in der Gruppe "+groupName+".");
                            }
                        }catch (NullPointerException ignored){
                            pSender.sendMessage(Main.prefix + "Dem Speieler " + playerName + " konnte die Gruppe " + groupName + " nicht hinzugefügt werden.");
                        }


                } else if (args[0].equalsIgnoreCase("remove")) {

                        if (args.length != 3) {
                            sender.sendMessage(Main.prefix + "Bitte gib einen Spieler und eine Gruppe an.");
                            return true;
                        }

                        String playerName = args[1];
                        String groupName = args[2];

                        Player player = Bukkit.getPlayer(playerName);

                        // Player not known?
                        if (player == null || !player.hasPlayedBefore()) {
                            sender.sendMessage(Main.prefix + playerName + " hat den Server noch nie betreten.");
                            return true;
                        }

                    try {
                        if(Main.getPermissions().playerInGroup(player, groupName)) {
                            Main.getPermissions().playerRemoveGroup(player, groupName);
                            pSender.sendMessage(Main.prefix + "Dem Spieler " + playerName + " wurde die Gruppe " + groupName + " erfolgreich entfernt.");
                        }else {
                            pSender.sendMessage(Main.prefix + "Der Spieler " +playerName+ " ist nicht in der Gruppe "+ groupName+".");
                        }
                    }catch (NullPointerException ignored){
                        pSender.sendMessage(Main.prefix + "Dem Speieler " + playerName + " konnte die Gruppe " + groupName + " nicht entfernt werden werden.");
                    }

                }
            }else {
                pSender.sendMessage(Main.prefix + "Befehle:");
                pSender.sendMessage(Main.prefix + "/rank add <Spieler> <Group> | Added dem Spieler die Gruppe.");
                pSender.sendMessage(Main.prefix + "/rank remove <Spieler> <Group> | Entfernt dem Spieler die Gruppe.");
            }
        return true;
    }
}
