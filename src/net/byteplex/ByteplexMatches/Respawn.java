package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import static net.byteplex.ByteplexMatches.ByteplexMatches.blueTeam;
import static net.byteplex.ByteplexMatches.ByteplexMatches.redTeam;
import static net.byteplex.ByteplexMatches.SetSpawnLocation.setloc;


public class Respawn implements Listener {
    public static Location loc;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Player attacker = (Player) e.getDamager();

                if (victim.getHealth() - e.getFinalDamage() < 1) {
                    e.setCancelled(true);
                    doDeath(victim, attacker);
                    teamKills(victim, attacker);
                }

            } else if (e.getDamager() instanceof Projectile) {
                ProjectileSource src = ((Projectile) e.getDamager()).getShooter();
                if (src instanceof Player) {
                    Player attacker = (Player) src;
                    if (victim.getHealth() - e.getFinalDamage() < 1) {
                        e.setCancelled(true);
                        doDeath(victim, attacker);
                        teamKills(victim,attacker);
                    }
                }
            }
        }
    }

    public void doDeath(Player victim, Player attacker) {
        ItemStack killerItem = attacker.getInventory().getItemInMainHand();


        // calculate distance between attacker and victim
        int playerDistance = (int) attacker.getLocation().distance(victim.getLocation());

        String itemName;
        // if the item has special data
        if (killerItem.hasItemMeta()) {
            ItemMeta meta = killerItem.getItemMeta();

            // check if it has an "anvil'd" name
            if (meta.hasDisplayName()) {
                itemName = killerItem.getItemMeta().getDisplayName();

                // no anviled name
            } else {
                itemName = killerItem.getType().name().toLowerCase().replaceAll("_", " ");
                itemName = itemName.substring(0, 1).toUpperCase() + itemName.substring(1, itemName.length());
            }
            // no special data
        } else {
            itemName = killerItem.getType().name().toLowerCase().replaceAll("_", " ");
            itemName = itemName.substring(0, 1).toUpperCase() + itemName.substring(1, itemName.length());
        }

        if (itemName.equalsIgnoreCase("air")) {
            itemName = "Fists";
        }


        Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO,
                ChatColor.BLUE + victim.getName()
                        + ChatColor.WHITE + " has been killed by "
                        + ChatColor.RED + attacker.getName()
                        + ChatColor.WHITE + " with "
                        + ChatColor.GREEN + itemName
                        + ChatColor.WHITE + " from "
                        + ChatColor.RED + playerDistance + ((playerDistance == 1) ? " block " : " blocks ") + ChatColor.WHITE + "away."));

        if (setloc){
            victim.teleport(this.loc);
        } else{
            victim.teleport(victim.getWorld().getSpawnLocation());
        }
        victim.setHealth(20.0);
        victim.setFoodLevel(20);
    }

    public void teamKills(Player victim, Player attacker){
        int redKills = 0;
        int blueKills = 0;
        if(redTeam.contains(attacker.getName()) && blueTeam.contains(victim.getName())){
            redKills++;
            Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Red team kills: " + ChatColor.RED + redKills + " Blue team kills: " + ChatColor.BLUE + blueKills));
        } else if(redTeam.contains(victim.getName()) && blueTeam.contains(attacker.getName())){
            blueKills++;
            Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Red team kills: " + ChatColor.RED + redKills + " Blue team kills: " + ChatColor.BLUE + blueKills));
        }
        else{
            Bukkit.broadcastMessage("this is not right" + redTeam.get(0) + blueTeam.get(0));

        }
    }

}

