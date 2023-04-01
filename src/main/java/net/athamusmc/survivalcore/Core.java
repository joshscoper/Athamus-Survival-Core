package net.athamusmc.survivalcore;

import net.athamusmc.survivalcore.commands.*;
import net.athamusmc.survivalcore.files.FileManager;
import net.athamusmc.survivalcore.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    //Init Files
    private FileManager configManager;
    private FileManager langManager;
    private FileManager playerDataManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        initFiles();
        registerCommands();
        registerEvents();

        //Startup Message
        Bukkit.getServer().getLogger().info("Athamus Survival Core Loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //Shutdown Message
        Bukkit.getServer().getLogger().info("Athamus Survival Core Unloaded!");
    }

    private void initFiles(){
        configManager = new FileManager(this, "config.yml");
        langManager = new FileManager(this, "lang.yml");
        playerDataManager = new FileManager(this, "player-data.yml");
        configManager.setupFile();
        langManager.setupFile();
        playerDataManager.setupFile();
    }

    private void registerEvents(){
        new PlayerListener(this);
    }

    private void registerCommands(){
        new Gamemode(this);
        new GodMode(this);
        new Fly(this);
        new NickName(this);
        new Speed(this);
    }

    public FileManager getConfigManager() {
        return configManager;
    }

    public FileManager getLangManager() {
        return langManager;
    }

    public FileManager getPlayerDataManager() {
        return playerDataManager;
    }
}
