package me.daqem.harvesterhoepremium.utils;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoe {

    public static ItemStack harvesterHoe(int amount, int multiplier) {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE, amount);
        ItemMeta itemMeta = hoe.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String all : HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getStringList("harvesterhoe.item-lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', all));
        }
        if (HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getBoolean("multipliers-enabled")) {
            if (multiplier > 0) {
                lore.add(ChatColor.translateAlternateColorCodes('&', HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getString("harvesterhoe.multiplier-text-color") + "Multiplier: " + multiplier + "x"));
            } else {
                lore.add(ChatColor.translateAlternateColorCodes('&', HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getString("harvesterhoe.multiplier-text-color") + "Multiplier: 1x"));
            }
        }
        itemMeta.setLore(lore);
        Glow glow = new Glow(70);
        itemMeta.addEnchant(glow, 1, true);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getString("harvesterhoe.item-name")));
        hoe.setItemMeta(itemMeta);
        return hoe;
    }
}