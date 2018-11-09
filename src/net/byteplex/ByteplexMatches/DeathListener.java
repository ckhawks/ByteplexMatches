package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

//  DO WE EVEN NEED THIS CLASS


public class DeathListener extends Respawn implements Listener {

    //  makes sure that if the player dies from anything other than players that they get teleported to appropriate spawn
    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e) {
        Player p = (Player) e.getEntity();
        if (p.getHealth() - e.getFinalDamage() < 1){
            e.setCancelled(true);
            teleport(p);
            Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, p.getDisplayName() + " has died from natural causes"));
        }
    }
}