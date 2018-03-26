package net.byteplex.ByteplexMatches.teams;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static net.byteplex.ByteplexMatches.ByteplexMatches.blueTeam;
import static net.byteplex.ByteplexMatches.ByteplexMatches.redTeam;

public class Damage implements Listener {


    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Projectile || e.getDamager() instanceof Player) {
            Projectile a = (Projectile) e.getDamager();
            if (a.getShooter() instanceof Player){
                Player shooter = (Player) a.getShooter();
                Player victim = (Player) e.getEntity();
                if(redTeam.contains(shooter.getName()) && redTeam.contains(victim.getName())){
                    e.setCancelled(true);
                    shooter.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't hurt your team!"));
                } else if(blueTeam.contains(shooter.getName()) && blueTeam.contains(victim.getName())){
                    e.setCancelled(true);
                    shooter.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't hurt your team!"));
                } else {
                    ;
                }
            }

        }
    }
}
