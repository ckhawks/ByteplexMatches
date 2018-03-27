package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexMatches.teams.Damage;
import net.byteplex.ByteplexMatches.teams.TeamCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ByteplexMatches extends JavaPlugin {
   public static List<String> redTeam = new ArrayList<>();
   public static List<String> blueTeam = new ArrayList<>();

    @Override
    public void onEnable() {
        //this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new Respawn(), this);
        this.getCommand("setloc").setExecutor(new SetSpawnLocation());
        this.getCommand("team").setExecutor(new TeamCommand());
        this.getServer().getPluginManager().registerEvents(new Damage(), this);
    }

    @Override
    public void onDisable(){

    }
}
