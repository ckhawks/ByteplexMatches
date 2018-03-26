package net.byteplex.ByteplexMatches.teams;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexMatches.ByteplexMatches;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import static net.byteplex.ByteplexMatches.ByteplexMatches.blueTeam;
import static net.byteplex.ByteplexMatches.ByteplexMatches.redTeam;

public class TeamCommand implements CommandExecutor {


    public static boolean isInTeam(Player player) {
        return redTeam.contains(player.getName()) || blueTeam.contains(player.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!isInTeam(p)) {
                String input = args[0].toLowerCase();
                switch (input) {
                    case "red":
                        redTeam.add(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + ChatColor.RED + "Red " + ChatColor.RESET + "team!"));
                        break;
                    case "blue":
                        ByteplexMatches.blueTeam.add(p.getName());
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + ChatColor.BLUE + "Blue " + ChatColor.RESET + "team!"));
                        break;
                    default:
                        p.sendMessage("Incorrect usage!");
                }
            } else {
                p.sendMessage("You are already on a team!");
            }
        }

        return true;
    }
}
