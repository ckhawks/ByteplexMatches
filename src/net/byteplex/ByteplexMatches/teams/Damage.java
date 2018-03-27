package net.byteplex.ByteplexMatches.teams;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import static net.byteplex.ByteplexMatches.ByteplexMatches.blueTeam;
import static net.byteplex.ByteplexMatches.ByteplexMatches.redTeam;

public class Damage implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player attacker = (Player) e.getDamager();
                Bukkit.getServer().broadcastMessage("Shooter name" + attacker.getName() + " victim name" + victim.getName());
                if (redTeam.contains(attacker.getName()) && redTeam.contains(victim.getName()) || blueTeam.contains(attacker.getName()) && blueTeam.contains(victim.getName())) {
                    e.setCancelled(true);
                    attacker.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't hurt your team!"));
                }
            }
        } else if (e.getDamager() instanceof Projectile){
            Player victim = (Player) e.getEntity();
            ProjectileSource src = ((Projectile) e.getDamager()).getShooter();
            if (src instanceof Player){
                Player attacker = (Player) src;
                if (redTeam.contains(attacker.getName()) && redTeam.contains(victim.getName()) || blueTeam.contains(attacker.getName()) && blueTeam.contains(victim.getName())) {
                    e.setCancelled(true);
                    attacker.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't hurt your team!"));
                }
            }
        }
    }

}
