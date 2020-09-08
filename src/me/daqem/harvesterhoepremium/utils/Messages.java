package me.daqem.harvesterhoepremium.utils;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.commands.CommandHelp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {


    CommandHelp commandHelp;
    private final HarvesterHoePremium plugin;

    public Messages(HarvesterHoePremium pl) {
        plugin = pl;
        commandHelp = new CommandHelp();
    }

    public void noPermissionOnCommandMessage(CommandSender player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission-on-command")));
    }
    public void commandNotFoundMessage(CommandSender sender, String[] args) {
        if (plugin.getConfig().getBoolean("redirect-to-helpcommand-when-command-not-found")) {
            commandHelp.sendHelp(sender);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command-not-found")));
        }
    }
    public void playerNotOnlineMessage(CommandSender player, String args) {
        String message = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.player-not-online"));
        message = message.replace("%player%", args);
        player.sendMessage(message);
    }
}
