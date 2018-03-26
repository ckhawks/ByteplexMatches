package net.byteplex.ByteplexMatches.teams;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexMatches.ByteplexMatches;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Damage implements Listener {


    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Projectile && e.getDamager() instanceof Player) {
            Projectile a = (Projectile) e.getDamager();
            if (a.getShooter() instanceof Player){
                Player shooter = (Player) a.getShooter();
                if(shooter){
                    e.setCancelled(true);
                    shooter.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't hurt your team!"));
                }
            }

        }
    }
}
