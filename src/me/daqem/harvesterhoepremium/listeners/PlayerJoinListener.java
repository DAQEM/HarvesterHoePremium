package me.daqem.harvesterhoepremium.listeners;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final HarvesterHoePremium plugin;
    public PlayerJoinListener(HarvesterHoePremium pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getConfig().getString("mode").equals("collect")) {
            plugin.getToggle().add(event.getPlayer().getUniqueId());
        }
    }
}
