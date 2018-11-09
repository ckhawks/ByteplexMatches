package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexMatches.teams.TeamCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ByteplexMatches extends JavaPlugin {
   public static Map <UUID, String> teams_map = new HashMap<UUID, String>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new Respawn(), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getCommand("setloc").setExecutor(new SetSpawnLocation()); //sets new spawn locations
        this.getCommand("team").setExecutor(new TeamCommand());  // see what team the player is on 
    }
    @Override
    public void onDisable(){

    }
}
