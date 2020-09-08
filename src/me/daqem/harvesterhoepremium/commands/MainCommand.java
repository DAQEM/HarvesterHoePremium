package me.daqem.harvesterhoepremium.commands;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.utils.Messages;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class MainCommand implements CommandExecutor {

    Messages messages;
    CommandReload commandReload;
    CommandGive commandGive;
    CommandHelp commandHelp;
    private final HarvesterHoePremium plugin;

    public MainCommand(HarvesterHoePremium pl) {
        commandReload = new CommandReload(pl);
        commandGive = new CommandGive(pl);
        commandHelp = new CommandHelp();
        messages = new Messages(pl);
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.help")) {
                commandHelp.helpCommand(sender, args);
            } else {
                messages.noPermissionOnCommandMessage(sender);
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.reload")) {
                    commandReload.reloadCommand(sender);
                } else {
                    messages.noPermissionOnCommandMessage(sender);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.help")) {
                    commandHelp.helpCommand(sender, args);
                } else {
                    messages.noPermissionOnCommandMessage(sender);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("give")) {
                if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.give")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + "Usage: /hh give [playername] [amount] [multiplier]");
                } else {
                    messages.noPermissionOnCommandMessage(sender);
                }
                return true;
            } else {
                messages.commandNotFoundMessage(sender, args);
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.give")) {
                        commandGive.giveCommand(target, sender, args, 1, 0);
                    } else {
                        messages.noPermissionOnCommandMessage(sender);
                    }
                } else {
                    messages.playerNotOnlineMessage(sender, args[1]);
                }
            } else {
                messages.commandNotFoundMessage(sender, args);
            }
            return true;
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.give")) {
                        if (StringUtils.isNumeric(args[2])) {
                            int amount = Integer.parseInt(args[2]);
                            commandGive.giveCommand(target, sender, args, amount, 0);
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + args[2] + " is not a number");
                        }
                    } else {
                        messages.noPermissionOnCommandMessage(sender);
                    }
                } else {
                    messages.playerNotOnlineMessage(sender, args[1]);
                }
            } else {
                messages.commandNotFoundMessage(sender, args);
            }
            return true;
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (sender.hasPermission("harvesterhoepremium.admin") || sender.hasPermission("harvesterhoepremium.give")) {
                        int amount = Integer.parseInt(args[2]);
                        if (StringUtils.isNumeric(args[2])) {
                            if (StringUtils.isNumeric(args[3])) {
                                int multiplier = Integer.parseInt(args[3]);
                                commandGive.giveCommand(target, sender, args, amount, multiplier);
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + args[3] + " is not a number");
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) + ChatColor.RED + args[2] + " is not a number");
                        }
                    } else {
                        messages.noPermissionOnCommandMessage(sender);
                    }
                } else {
                    messages.playerNotOnlineMessage(sender, args[1]);
                }
            } else {
                messages.commandNotFoundMessage(sender, args);
            }
            return true;
        } else {
            messages.commandNotFoundMessage(sender, args);
            return true;
        }
        return true;
    }
}