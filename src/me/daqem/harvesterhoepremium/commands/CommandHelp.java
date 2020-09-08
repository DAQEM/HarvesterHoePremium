package me.daqem.harvesterhoepremium.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHelp {

    public void helpCommand(CommandSender player, String[] args) {
        if (args.length == 0) {
            sendHelp(player);
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sendHelp(player);
        }
    }
    public void sendHelp(CommandSender player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b=============== &b&lHarvester&3&lHoe &b==============="));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGive HarvesterHoe to player:"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "/hh give [playername] [amount] [multiplier]"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bReload the config:"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "/hh reload"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b================================================"));
    }
}
