package me.daqem.harvesterhoepremium.commands;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.utils.HarvesterHoe;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandGive {

    private final HarvesterHoePremium plugin;

    public CommandGive(HarvesterHoePremium pl) {
        plugin = pl;
    }

    public void giveCommand(Player target, CommandSender sender, String[] args, int amount, int multiplier) {
        if (hasAvailableSlot(target)) {
            if (args.length == 4) {
                if (plugin.getConfig().getBoolean("multipliers-enabled")) {
                    if (StringUtils.isNumeric(args[3])) {
                        target.getInventory().addItem(HarvesterHoe.harvesterHoe(amount, multiplier));
                        if (amount >= 2) {
                            if (target.getName().equals(sender.getName())) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given " + amount + " HarvesterHoes with multiplier " + multiplier + "x to yourself.");
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given " + amount + " HarvesterHoes with multiplier " + multiplier + "x to " + target.getName() + ".");
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.targetplayer-given-color")) + "You have been given " + amount + " HarvesterHoes with multiplier " + multiplier + "x.");
                            }
                        } else if (amount == 1) {
                            if (target.getName().equals(sender.getName())) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given 1 HarvesterHoe with multiplier " + multiplier + "x to yourself.");
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given 1 HarvesterHoe with multiplier " + multiplier + "x to " + target.getName() + ".");
                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.targetplayer-given-color")) + "You have been given 1 HarvesterHoe with multiplier " + multiplier + "x.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + "Amount must be higher than 0");
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + args[3] + " is not a number");
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + "Multipliers are disabled in the config. Usage /hh give [playername] [amount]");
                }
            } else {
                target.getInventory().addItem(HarvesterHoe.harvesterHoe(amount, multiplier));
                if (amount >= 2) {
                    if (target.getName().equals(sender.getName())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given " + amount + " HarvesterHoes to yourself.");
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given " + amount + " HarvesterHoes to " + target.getName() + ".");
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.targetplayer-given-color")) + "You have been given " + amount + " HarvesterHoes.");
                    }
                }
                else if (amount == 1) {
                    if (target.getName().equals(sender.getName())) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given 1 HarvesterHoes to yourself.");
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.sender-given-color")) + "You have given 1 HarvesterHoe to " + target.getName() + ".");
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.targetplayer-given-color")) + "You have been given 1 HarvesterHoe.");
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + "Amount must be higher than 0");
                }
            }
        } else {
            target.getWorld().dropItem(target.getLocation(), HarvesterHoe.harvesterHoe(amount, multiplier));
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.inventory-full")));
        }
    }

    public boolean hasAvailableSlot(Player player){
        Inventory inv = player.getInventory();
        boolean check = false;
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                check = true;
                break;
            }
        }
        return check;
    }
}
