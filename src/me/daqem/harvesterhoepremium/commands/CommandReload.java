package me.daqem.harvesterhoepremium.commands;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.utils.HarvesterHoe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandReload {

    private final HarvesterHoePremium plugin;

    public CommandReload(HarvesterHoePremium pl) {
        plugin = pl;
    }

    public void reloadCommand(CommandSender player) {
        plugin.reloadConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.reload")));
        if (plugin.getConfig().getString("mode").equals("sell")) {
            plugin.getToggle().clear();
        }
        if (plugin.getConfig().getString("mode").equals("collect")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                plugin.getToggle().add(onlinePlayer.getUniqueId());
            }
        }
    }
}
