package me.daqem.harvesterhoepremium.listeners;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.utils.GetMultiplierFromLore;
import me.daqem.harvesterhoepremium.utils.HarvesterHoe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.UUID;

public class InteractChangeMode implements Listener {

    private final HarvesterHoePremium plugin;

    GetMultiplierFromLore getMultiplierFromLore = new GetMultiplierFromLore();

    public InteractChangeMode(HarvesterHoePremium pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerInteractChangeMode(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        List<UUID> toggle = plugin.getToggle();
        if (plugin.getConfig().getString("mode").equalsIgnoreCase("both")) {
            if (player.getInventory().getItemInHand().getType().equals(Material.DIAMOND_HOE)) {
                if (player.getInventory().getItemInHand().getItemMeta().getLore() != null) {
                    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                            event.getAction() == Action.RIGHT_CLICK_AIR) &&
                            player.getItemInHand().isSimilar(HarvesterHoe.harvesterHoe(1, getMultiplierFromLore.getMultiplier(player)))) {
                        toggle(player, toggle);
                    }
                } else {
                    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                            event.getAction() == Action.RIGHT_CLICK_AIR) &&
                            player.getItemInHand().isSimilar(HarvesterHoe.harvesterHoe(1, 0))) {
                        toggle(player, toggle);
                    }
                }
            }
        }
    }

    private void toggle(Player player, List<UUID> toggle) {
        if (!toggle.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.collect-mode")));
            toggle.add(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sell-mode")));
            toggle.remove(player.getUniqueId());
        }
    }
}
