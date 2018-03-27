package net.byteplex.ByteplexMatches.teams;
import com.bringholm.nametagchanger.NameTagChanger;
import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexMatches.ByteplexMatches;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.byteplex.ByteplexMatches.ByteplexMatches.blueTeam;
import static net.byteplex.ByteplexMatches.ByteplexMatches.redTeam;

public class TeamCommand implements CommandExecutor {


    public static boolean isInTeam(Player player) {
        return redTeam.contains(player.getName()) || blueTeam.contains(player.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String input = args[0].toLowerCase();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (input) {
                case "red":
                    if (redTeam.contains(p.getName())) {
                        redTeam.remove(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have left the " + ChatColor.RED + "Red " + ChatColor.RESET + "team!"));
                    } else if (blueTeam.contains(p.getName())) {
                        blueTeam.remove(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have left the " + ChatColor.BLUE + "Blue " + ChatColor.RESET + "team!"));
                    }
                    redTeam.add(p.getName());
                    NameTagChanger.INSTANCE.changePlayerName(p, ChatColor.RED + p.getName());
                    p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + ChatColor.RED + "Red " + ChatColor.RESET + "team!"));
                    break;
                case "blue":
                    if (redTeam.contains(p.getName())) {
                        redTeam.remove(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have left the " + ChatColor.RED + "Red " + ChatColor.RESET + "team!"));
                    } else if (blueTeam.contains(p.getName())) {
                        blueTeam.remove(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have left the " + ChatColor.BLUE + "Blue " + ChatColor.RESET + "team!"));
                    }
                    ByteplexMatches.blueTeam.add(p.getName());
                    NameTagChanger.INSTANCE.changePlayerName(p, ChatColor.BLUE + p.getName());
                    p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + ChatColor.BLUE + "Blue " + ChatColor.RESET + "team!"));
                    break;
                default:
                    p.sendMessage("Incorrect usage!");
            }
        }
        return true;
    }
}
