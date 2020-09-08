package me.daqem.harvesterhoepremium.utils;

import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class GetMultiplierFromLore {

    public int getMultiplier(Player player) {
        String arr = player.getInventory().getItemInHand().getItemMeta().getLore().get(player.getInventory().getItemInHand().getItemMeta().getLore().size() - 1);
        String[] arr2 = arr.split(" ", 2);
        String numberx = arr2[1];
        String number = removeLastCharacter(numberx);
        if (StringUtils.isNumeric(number)) {
            if (HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getBoolean("multipliers-enabled")) {
                return Integer.parseInt(number);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }
}

