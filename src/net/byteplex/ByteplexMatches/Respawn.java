package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import net.byteplex.ByteplexMatches.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.byteplex.ByteplexMatches.ByteplexMatches.teams_map;

public class Respawn implements Listener {
    public static Location redSpawn;
    public static Location blueSpawn;
    public static Location neutralSpawn;
    private int redKills = 0;
    private int blueKills = 0;
    private Map<UUID, Timestamp> lastTeamkillComplain = new HashMap<>();

    public Respawn(){
        redSpawn = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
        blueSpawn = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
        neutralSpawn = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Player attacker = null;
        Player victim = null;

        // check that the victim entity is a player
        if (e.getEntity() instanceof Player) {
            victim = (Player) e.getEntity();

            // check that attacking entity is player
            if (e.getDamager() instanceof Player) {
                attacker = (Player) e.getDamager();

                // check if attacking entity is projectile (arrow, snowball, egg, fire charge, ghast fireball, etc)
            } else if (e.getDamager() instanceof Projectile) {
                ProjectileSource src = ((Projectile) e.getDamager()).getShooter();
                if (src instanceof Player) {
                    attacker = (Player) src;
                }
            }
        }

        // if there is a player attacker and victim after projectile checks
        if (attacker != null && victim != null) {

            // team kill check
            if (getTeam(attacker) == getTeam(victim)) {
                e.setCancelled(true);

                // don't display teamkill complaining message unless more than 3 seconds have passed since last teamkill attempt
                Timestamp t = new Timestamp(System.currentTimeMillis());
                Timestamp t2 = lastTeamkillComplain.get(attacker.getUniqueId());
                if (t2 != null) {
                    if (t.getTime() - t2.getTime() > 3000) {
                        attacker.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't attack your teammates!"));
                        lastTeamkillComplain.replace(attacker.getUniqueId(), t);
                    }
                } else {
                    attacker.sendMessage(ChatFormat.formatExclaim(ChatLevel.INFO, "Don't attack your teammates!"));
                    lastTeamkillComplain.put(attacker.getUniqueId(), t);
                }
            }

            // death check
            if (victim.getHealth() - e.getFinalDamage() < 1) {
                e.setCancelled(true);
                doDeath(victim, attacker);
                teamKills(victim, attacker);
            }
        }
    }


    //  return what team the player is on 
    private String getTeam(Player player) {
        if (teams_map.containsKey(player.getUniqueId())) {
            return teams_map.get(player.getUniqueId());
        }
        return null;
    }

    //  set a counter for the amount of kills each team has 
    private void teamKills(Player victim, Player attacker) {
        if (getTeam(attacker) != getTeam(victim)) {
            switch (getTeam(attacker)) {
                case "red":
                    redKills++;
                    break;
                case "blue":
                    blueKills++;
                    break;
            }
            Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, " -- TDM -- "));
            Bukkit.broadcastMessage(ChatFormat.formatExclaim(ChatLevel.INFO, ChatColor.RED + "Red: " + ChatColor.WHITE + redKills + ChatColor.GRAY + " | " + ChatColor.BLUE + "Blue: " + ChatColor.WHITE + blueKills));
        }
    }

    private void doDeath(Player victim, Player attacker) {
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

        //  teleport player when dead to team spawn 
        switch (getTeam(victim)) {
            case "red":
                victim.teleport(redSpawn);
                break;
            case "blue":
                victim.teleport(blueSpawn);
                break;
            default:
                victim.teleport(neutralSpawn);
                break;
        }
        victim.setHealth(20.0);
        victim.setFoodLevel(20);

        // set the players inventory with these items when spawn
        PlayerInventory i = victim.getInventory();
        i.clear();
        i.addItem(new ItemStack(Material.IRON_SWORD), new ItemStack(Material.BOW), new ItemStack(Material.WOOD, 64));
        i.setItem(10, new ItemStack(Material.ARROW, 64));
        i.setHelmet(new ItemStack(Material.LEATHER_HELMET));
        i.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        i.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        i.setBoots(new ItemStack(Material.LEATHER_BOOTS));

    }
}