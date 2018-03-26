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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            setloc = true;
            Player player = (Player) commandSender;
            Location loc = player.getTargetBlock((Set< Material>)null,5).getLocation();
            Respawn.loc = new Location(loc.getWorld(), loc.getX() + .5, loc.getY() + 1, loc.getZ() + .5);
            player.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO ,ChatColor.WHITE + "You set your respawn location to "
                    + (int) Respawn.loc.getX() + ", " + (int) Respawn.loc.getY() + ", " + (int) Respawn.loc.getZ()));
        }
        return true;
    }
}
