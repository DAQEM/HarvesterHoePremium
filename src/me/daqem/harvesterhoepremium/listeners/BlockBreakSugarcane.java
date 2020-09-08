package me.daqem.harvesterhoepremium.listeners;

import com.google.common.collect.Iterables;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import me.daqem.harvesterhoepremium.HarvesterHoePremium;
import me.daqem.harvesterhoepremium.utils.GetMultiplierFromLore;
import me.daqem.harvesterhoepremium.utils.HarvesterHoe;
import me.daqem.harvesterhoepremium.utils.Messages;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BlockBreakSugarcane implements Listener {

    private final HarvesterHoePremium plugin;

    GetMultiplierFromLore getMultiplierFromLore = new GetMultiplierFromLore();

    public BlockBreakSugarcane(HarvesterHoePremium pl) {
        plugin = pl;
    }

    @EventHandler
    public void onBlockBreakSugarcane(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        try {
            if (!block.getType().equals(Material.SUGAR_CANE_BLOCK) ||
                    player.getInventory().getItemInHand().getType() != Material.DIAMOND_HOE ||
                    player.getInventory().getItemInHand().getType() == Material.DIAMOND_HOE &&
                            !player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', HarvesterHoePremium.getPlugin(HarvesterHoePremium.class).getConfig().getString("harvesterhoe.item-name")))) {
                return;
            }
        } catch (NullPointerException exception) {
            return;
        }
        if (getMultiplierFromLore.getMultiplier(player) != 0) {
            if (Objects.requireNonNull(player.getItemInHand()).isSimilar(HarvesterHoe.harvesterHoe(0, getMultiplierFromLore.getMultiplier(player)))) {
                if (player.hasPermission("harvesterhoepremium.admin") || player.hasPermission("harvesterhoepremium.use") || !plugin.getConfig().getBoolean("use-permission-needed")) {
                    event.setCancelled(true);
                    Collection<ItemStack> drops = block.getDrops();
                    if (drops.size() > 0) {
                        int multiplier = getMultiplierFromLore.getMultiplier(player);
                        double dropAmount = drops.stream().mapToInt(ItemStack::getAmount).sum();
                        int inventorySpace = getInventorySpace(player, Iterables.get(drops, 0));
                        List<UUID> toggle = plugin.getToggle();
                        double pricePerCane = plugin.getConfig().getDouble("price-per-cane");
                        double total = pricePerCane * dropAmount;
                        if (toggle.contains(player.getUniqueId())) {
                            sendActionbar(player, ChatColor.GREEN + "Collect mode");
                            for (int i = 0; multiplier == 0 || i < multiplier; i++) {
                                if (dropAmount <= inventorySpace) {
                                    player.getInventory().addItem(drops.toArray(new ItemStack[0]));
                                    caneStack(block, player);
                                    block.setType(Material.AIR);
                                } else {
                                    player.sendTitle(ChatColor.RED + "Inventory full!", "Drops will be sold!");
                                    HarvesterHoePremium.economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), total);
                                }
                            }
                        } else {
                            for (int i = 0; multiplier == 0 || i < multiplier; i++) {
                                sendActionbar(player, ChatColor.GREEN + "Sell mode");
                                HarvesterHoePremium.economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), total);
                            }
                        }
                        caneStack(block, player);
                        block.setType(Material.AIR);
                    }
                } else {
                    player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) +
                            org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission-on-use")));
                }
            }
        } else {
            if (Objects.requireNonNull(player.getItemInHand()).isSimilar(HarvesterHoe.harvesterHoe(0, 0))) {
                if (player.hasPermission("harvesterhoepremium.admin") || player.hasPermission("harvesterhoepremium.use") || !plugin.getConfig().getBoolean("use-permission-needed")) {
                    event.setCancelled(true);
                    Collection<ItemStack> drops = block.getDrops();
                    if (drops.size() > 0) {
                        double dropAmount = drops.stream().mapToInt(ItemStack::getAmount).sum();
                        int inventorySpace = getInventorySpace(player, Iterables.get(drops, 0));
                        List<UUID> toggle = plugin.getToggle();
                        double pricePerCane = plugin.getConfig().getDouble("price-per-cane");
                        double total = pricePerCane * dropAmount;
                        if (toggle.contains(player.getUniqueId())) {
                            sendActionbar(player, ChatColor.GREEN + "Collect mode");
                            if (dropAmount <= inventorySpace) {
                                player.getInventory().addItem(drops.toArray(new ItemStack[0]));
                                caneStack(block, player);
                                block.setType(Material.AIR);
                                return;
                            } else {
                                player.sendTitle(ChatColor.RED + "Inventory full!", "Drops will be sold!");
                                HarvesterHoePremium.economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), total);
                            }
                        } else {
                            sendActionbar(player, ChatColor.GREEN + "Sell mode");
                            HarvesterHoePremium.economy.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), total);
                        }
                        caneStack(block, player);
                        block.setType(Material.AIR);
                    }
                } else {
                    player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix")) +
                            org.bukkit.ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission-on-use")));
                }
            }
        }
    }

    private void caneStack(Block block, Player player) {
        Material material = block.getType();
        while (material == Material.SUGAR_CANE_BLOCK) {
            block = block.getRelative(0, 1, 0);
            material = block.getType();
            if (block.getType().equals(material)) {
                Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(block, player));
            }
        }
    }

    public int getInventorySpace(Player player, ItemStack item) {
        int freeSpace = 0;
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i; ) {
            ItemStack itemStack = arrayOfItemStack[b];
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                freeSpace += item.getMaxStackSize();
            } else if (itemStack.isSimilar(item)) {
                freeSpace += item.getMaxStackSize() - itemStack.getAmount();
            }
            b++;
        }
        return freeSpace;
    }

    public void sendActionbar(Player player, String message) {
        if (player == null || message == null) return;
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);

            Class<?> ppoc = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> packet = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            Object packetPlayOutChat;
            Class<?> chat = Class.forName("net.minecraft.server." + nmsVersion + (nmsVersion.equalsIgnoreCase("v1_8_R1") ? ".ChatSerializer" : ".ChatComponentText"));
            Class<?> chatBaseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");

            Method method = null;
            if (nmsVersion.equalsIgnoreCase("v1_8_R1")) method = chat.getDeclaredMethod("a", String.class);

            Object object = nmsVersion.equalsIgnoreCase("v1_8_R1") ? chatBaseComponent.cast(method.invoke(chat, "{'text': '" + message + "'}")) : chat.getConstructor(new Class[]{String.class}).newInstance(message);
            packetPlayOutChat = ppoc.getConstructor(new Class[]{chatBaseComponent, Byte.TYPE}).newInstance(object, (byte) 2);

            Method handle = craftPlayerClass.getDeclaredMethod("getHandle");
            Object iCraftPlayer = handle.invoke(craftPlayer);
            Field playerConnectionField = iCraftPlayer.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(iCraftPlayer);
            Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", packet);
            sendPacket.invoke(playerConnection, packetPlayOutChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
