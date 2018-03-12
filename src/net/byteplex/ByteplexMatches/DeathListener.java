package net.byteplex.ByteplexMatches;

import net.byteplex.ByteplexCore.util.ChatFormat;
import net.byteplex.ByteplexCore.util.ChatLevel;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player victim = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        ItemStack killerItem = killer.getInventory().getItemInMainHand();

        // calculate distance between attacker and victim
        int playerDistance = (int) killer.getLocation().distance(victim.getLocation());

        String itemName;
        // if the item has special data
        if(killerItem.hasItemMeta()){
            ItemMeta meta = killerItem.getItemMeta();

            // check if it has an "anvil'd" name
            if(meta.hasDisplayName()){
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

        e.setDeathMessage(ChatFormat.formatExclaim(ChatLevel.DEATH,
                ChatColor.BLUE + victim.getName()
                        + ChatColor.WHITE + " has been killed by "
                        + ChatColor.RED + killer.getName()
                        + ChatColor.WHITE + " with "
                        + ChatColor.GREEN  + itemName
                        + ChatColor.WHITE + " from "
                        + ChatColor.RED + playerDistance + " blocks " + ChatColor.WHITE + "away."));
    }
}
