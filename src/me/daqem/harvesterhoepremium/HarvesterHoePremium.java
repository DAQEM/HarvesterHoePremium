package me.daqem.harvesterhoepremium;

import me.daqem.harvesterhoepremium.commands.MainCommand;
import me.daqem.harvesterhoepremium.listeners.BlockBreakSugarcane;
import me.daqem.harvesterhoepremium.listeners.InteractChangeMode;
import me.daqem.harvesterhoepremium.utils.AdvancedLicense;
import me.daqem.harvesterhoepremium.utils.Glow;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class HarvesterHoePremium extends JavaPlugin {

    public static Economy economy;
    private final List<UUID> toggle = new ArrayList<>();

    public void onEnable() {

        loadCommands();
        loadEvents();
        loadConfig();
        registerGlow();

        if(!new AdvancedLicense(this.getConfig().getString("license-key"), "http://daqem.com/advancedlicense/verify.php", this).register()) return;

        if (!setupEconomy()) {
            System.out.println("[HarvesterHoePremium] Vault not found, shutting down HarvesterHoePremium.");
            Bukkit.shutdown();
        } else {
            System.out.println("[HarvesterHoePremium] Vault found, starting plugin.");
        }
    }
    public void onDisable() {
    }

    public void loadCommands() {
        getCommand("hh").setExecutor(new MainCommand(this));
    }

    public void loadEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BlockBreakSugarcane( this), this);
        pm.registerEvents(new InteractChangeMode(this), this);
    }

    public List<UUID> getToggle() {
        return toggle;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        System.out.println("[HarvesterHoePremium] Config created");

    }
    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(70);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
