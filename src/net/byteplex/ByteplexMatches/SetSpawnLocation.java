package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class SetSpawnLocation implements CommandExecutor {
    static boolean setloc;
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            setloc = true;
            Player player = (Player) commandSender;
            Location loc = player.getTargetBlock((Set< Material>)null,5).getLocation();

            //  set teh spawn of the red team to current location 
            if(args[0].equalsIgnoreCase("red")){

                Respawn.redSpawn = new Location(loc.getWorld(), loc.getX() + .5, loc.getY() + 1, loc.getZ() + .5);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO ,ChatColor.WHITE + "You set red's respawn location to "
                        + (int) Respawn.redSpawn.getX() + ", " + (int) Respawn.redSpawn.getY() + ", " + (int) Respawn.redSpawn.getZ()));

            //  set the spawn of blue team to current location 
            } else if(args[0].equalsIgnoreCase("blue")){

                Respawn.blueSpawn = new Location(loc.getWorld(), loc.getX() + .5, loc.getY() + 1, loc.getZ() + .5);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO ,ChatColor.WHITE + "You set blue's respawn location to "
                        + (int) Respawn.blueSpawn.getX() + ", " + (int) Respawn.blueSpawn.getY() + ", " + (int) Respawn.blueSpawn.getZ()));

            //  else set the neutral spawn for players without a team 
            } else {

                Respawn.neutralSpawn = new Location(loc.getWorld(), loc.getX() + .5, loc.getY() + 1, loc.getZ() + .5);
                player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO ,ChatColor.WHITE + "You set neutral's respawn location to "
                        + (int) Respawn.neutralSpawn.getX() + ", " + (int) Respawn.neutralSpawn.getY() + ", " + (int) Respawn.neutralSpawn.getZ()));

            }
        }
        return true;
    }
}
