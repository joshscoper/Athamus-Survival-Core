package net.athamusmc.survivalcore.listeners;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.files.FileManager;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private Core core;
    private FileManager playerDataFile;

    public PlayerListener(Core core){
        this.core = core;
        this.playerDataFile = core.getPlayerDataManager();
        Bukkit.getServer().getPluginManager().registerEvents(this, core);
    }

    private void initPlayerData(Player player){
        PlayerData playerData = new PlayerData(core, player);
        playerData.initPlayerData();
    }

    private void loadPlayerData(Player player){
        PlayerData playerData = new PlayerData(core,player);
        playerData.loadPlayerData();
    }

    //Join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player =event.getPlayer();
        player.setAllowFlight(true);
        //Player Data Section
        if (!playerDataFile.getFile().contains(player.getUniqueId().toString())){
            initPlayerData(player);
        }
        loadPlayerData(player);
    }

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            PlayerData playerData = new PlayerData(core, player);
            if (playerData.getGod()){
                event.setCancelled(true);
            }
        }
    }


}
