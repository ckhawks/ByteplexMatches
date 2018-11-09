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

import static net.byteplex.ByteplexMatches.ByteplexMatches.teams_map;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String input_team = args[0].toLowerCase();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            //  make sure the player is joining the red or blue team
            if (input_team.equals("red") || input_team.equals("blue")){
                //  if there is no one in the hashmap, add him to his desired team

                //  teams can be scalable with this for future
                if (teams_map.isEmpty()){
                    teams_map.put(p.getUniqueId(), input_team);
                }
                //  if player is already in a team and wants to switch
                else if(teams_map.containsKey(p.getUniqueId())){
                    //  make sure he is not trying to join the team he is already in
                    if((teams_map.get(p.getUniqueId()).equals(input_team))){
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You are already on the " + input_team + " team!"));
                    }
                    //  switch to his desired team
                    else {
                        teams_map.replace(p.getUniqueId(), input_team);
                        p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + input_team + " team!"));
                    }
                }
                //  if player has not joined team at all
                else {
                    teams_map.put(p.getUniqueId(), input_team);
                    p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You have joined the " + input_team + " team!"));
                }

            }
            else {
                p.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "You must either join the" + ChatColor.RED + "red team " + ChatColor.RESET + "or the "+ ChatColor.BLUE + "blue team!"));
            }
        }
        return true;
    }
}
