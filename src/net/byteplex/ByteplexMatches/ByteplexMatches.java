package net.byteplex.ByteplexMatches;

import org.bukkit.plugin.java.JavaPlugin;

public class ByteplexMatches extends JavaPlugin {

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    @Override
    public void onDisable(){

    }
}
