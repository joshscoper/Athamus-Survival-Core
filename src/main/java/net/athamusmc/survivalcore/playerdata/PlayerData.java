package net.athamusmc.survivalcore.playerdata;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.files.FileManager;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerData {

    private final Core core;
    private final Player player;
    private final FileManager playerData;

    public PlayerData(Core core, Player player){
        this.core = core;
        this.player = player;
        this.playerData = core.getPlayerDataManager();
    }

    public void loadPlayerData(){
        player.setDisplayName(getNick());
        player.setGameMode(GameMode.valueOf(getGamemode()));
        player.setWalkSpeed(getSpeed("walk"));
        player.setFlying(getFlying());
        player.setInvulnerable(getGod());
        player.setFlySpeed(getSpeed("fly"));
        player.setInvisible(isVanished());
    }

    public String getGamemode(){
        return playerData.getFile().getString(player.getUniqueId().toString() + ".settings.gamemode");
    }

    public void setGamemode(String gamemode){
        player.setGameMode(GameMode.valueOf(gamemode.toUpperCase()));
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.gamemode", gamemode.toUpperCase());
        playerData.saveFile();
    }

    public boolean getFlying(){
        return playerData.getFile().getBoolean(player.getUniqueId() + ".settings.fly");
    }

    public void setFlying(boolean isFlying){
        player.setFlying(isFlying);
        playerData.getFile().set(player.getUniqueId() + ".settings.fly", isFlying);
        playerData.saveFile();
    }

    public void setVanish(Boolean isVanished){
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.vanish", isVanished);
        player.setInvisible(isVanished);
        playerData.saveFile();
    }

    public Boolean isVanished(){return playerData.getFile().getBoolean(player.getUniqueId().toString() + ".settings.vanish");}
    public Float getSpeed(String movement){return (Float) playerData.getFile().get(player.getUniqueId().toString() + ".settings." + movement.toLowerCase() + "-speed");}
    public void setSpeed(String movement, float speed){
        if (movement.equalsIgnoreCase("fly")){
            player.setFlySpeed(speed);
            playerData.getFile().set(player.getUniqueId().toString() + ".settings.fly-speed", speed);
        }
        if (movement.equalsIgnoreCase("walk")){
            player.setWalkSpeed(speed);
            playerData.getFile().set(player.getUniqueId().toString() + ".settings.walk-speed", speed);
        }
        playerData.saveFile();
    }

    public boolean getGod(){return playerData.getFile().getBoolean(player.getUniqueId().toString() + ".settings.god");}

    public void setGod(Boolean enabled){
        player.setInvulnerable(enabled);
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.god", enabled);
        playerData.saveFile();
    }

    public String getNick(){return playerData.getFile().getString(player.getUniqueId().toString() + ".settings.nickname");}

    public void setNick(String nick){
        player.setDisplayName(StringBuilder.formatString(nick));
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.nickname", nick);
        if (core.getConfigManager().getFile().getBoolean("settings.display-nickname-on-tablist")){
            player.setPlayerListName(nick);
        }
        playerData.saveFile();
    }

    public Boolean getPvP(){return playerData.getFile().getBoolean(player.getUniqueId().toString() + ".settings.pvp");}

    public void setPvP(Boolean enabled){
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.pvp", enabled);
        playerData.saveFile();
    }

    public Boolean getTP(){return playerData.getFile().getBoolean(player.getUniqueId().toString() + ".settings.tp");}

    public void setTP(Boolean enabled){
        playerData.getFile().set(player.getUniqueId().toString() + ".settings.tp", enabled);
        playerData.saveFile();
    }

    public int getBalance(){return playerData.getFile().getInt(player.getUniqueId().toString() + ".economy.balance");}

    public void setBalance(int newBalance){
        playerData.getFile().set(player.getUniqueId().toString() + ".economy.balance", newBalance);
        playerData.saveFile();
    }

    public void initPlayerData(){
        if (!playerData.fileExists()){playerData.setupFile();}
        FileConfiguration file = playerData.getFile();
        generatePlayerData(file);
    }

    public void generatePlayerData(FileConfiguration file){
        String path = player.getUniqueId().toString();

        //Settings
        file.createSection(path + ".settings.gamemode");
        file.createSection(path + ".settings.fly");
        file.createSection(path + ".settings.fly-speed");
        file.createSection(path + ".settings.walk-speed");
        file.createSection(path + ".settings.god");
        file.createSection(path + ".settings.nickname");
        file.createSection(path + ".settings.pvp");
        file.createSection(path + ".settings.tp");
        file.createSection(path + ".settings.vanish");
        //Economy
        file.createSection(path+".economy.balance");
        //Social
        file.createSection(path + ".social.friends.list");
        file.createSection(path + ".social.blocked");
        file.createSection(path + ".social.friends.invites.enabled");
        file.createSection(path + ".social.friends.invites.auto-accept");
        file.createSection(path + ".social.friends.invites.auto-deny");
        file.createSection(path + ".social.trading.enabled");
        file.createSection(path + ".social.trading.requests.auto-accept");
        file.createSection(path + ".social.trading.requests.auto-deny");
        file.createSection(path + ".social.chat.enabled");
        file.createSection(path + ".social.chat.filter.enabled");
        //Leveling
        file.createSection(path + ".levels.farming.level");
        file.createSection(path + ".levels.farming.xp");
        file.createSection(path + ".levels.farming.level");
        file.createSection(path + ".levels.farming.xp");
        file.createSection(path + ".levels.fishing.level");
        file.createSection(path + ".levels.fishing.xp");
        file.createSection(path + ".levels.mining.level");
        file.createSection(path + ".levels.mining.xp");
        file.createSection(path + ".levels.woodcutting.level");
        file.createSection(path + ".levels.woodcutting.xp");
        file.createSection(path + ".levels.smelting.level");
        file.createSection(path + ".levels.smelting.xp");
        file.createSection(path + ".levels.enchanting.level");
        file.createSection(path + ".levels.enchanting.xp");
        file.createSection(path + ".levels.swords.level");
        file.createSection(path + ".levels.swords.xp");
        file.createSection(path + ".levels.bows.level");
        file.createSection(path + ".levels.bows.xp");
        file.createSection(path + ".levels.axes.level");
        file.createSection(path + ".levels.axes.xp");
        file.createSection(path + ".levels.unarmed.level");
        file.createSection(path + ".levels.unarmed.xp");
        file.createSection(path + ".levels.endurance.level");
        file.createSection(path + ".levels.endurance.xp");
        file.createSection(path + ".levels.alchemy.level");
        file.createSection(path + ".levels.alchemy.xp");
        file.createSection(path + ".levels.bartering.level");
        file.createSection(path + ".levels.bartering.xp");

        //Settings
        file.set(path + ".settings.gamemode", player.getGameMode().toString());
        file.set(path + ".settings.fly", player.isFlying());
        file.set(path + ".settings.fly-speed", .1);
        file.set(path + ".settings.walk-speed", .1);
        file.set(path + ".settings.god", player.isInvulnerable());
        file.set(path + ".settings.nickname", player.getDisplayName());
        file.set(path + ".settings.pvp" , true);
        file.set(path + ".settings.tp", true);
        file.set(path + ".settings.vanish",false);

        //Economy
        file.set(path + ".economy.balance", core.getConfigManager().getFile().getInt("economy.starting-balance"));

        //Social
        file.set(path + ".social.friends.list", new ArrayList<String>());
        file.set(path + ".social.blocked", new ArrayList<String>());
        file.set(path + ".social.friends.invites.enabled", true);
        file.set(path + ".social.friends.invites.auto-accept", false);
        file.set(path + ".social.friends.invites.auto-deny", false);
        file.set(path + ".social.trading.enabled", true);
        file.set(path + ".social.trading.requests.auto-accept", false);
        file.set(path + ".social.trading.requests.auto-deny", false);
        file.set(path + ".social.chat.enabled", true);
        file.set(path + ".social.chat.filter.enabled", true);

        //Levels
        file.set(path + ".levels.farming.level", 1);
        file.set(path + ".levels.farming.xp", 0);
        file.set(path + ".levels.fishing.level", 1);
        file.set(path + ".levels.fishing.xp", 0);
        file.set(path + ".levels.mining.level", 1);
        file.set(path + ".levels.mining.xp", 0);
        file.set(path + ".levels.woodcutting.level", 1);
        file.set(path + ".levels.woodcutting.xp", 0);
        file.set(path + ".levels.smelting.level", 1);
        file.set(path + ".levels.smelting.xp", 0);
        file.set(path + ".levels.enchanting.level", 1);
        file.set(path + ".levels.enchanting.xp", 0);
        file.set(path + ".levels.swords.level", 1);
        file.set(path + ".levels.swords.xp", 0);
        file.set(path + ".levels.bows.level", 1);
        file.set(path + ".levels.bows.xp", 0);
        file.set(path + ".levels.axes.level", 1);
        file.set(path + ".levels.axes.xp", 0);
        file.set(path + ".levels.unarmed.level", 1);
        file.set(path + ".levels.unarmed.xp", 0);
        file.set(path + ".levels.endurance.level", 1);
        file.set(path + ".levels.endurance.xp", 0);
        file.set(path + ".levels.alchemy.level", 1);
        file.set(path + ".levels.alchemy.xp", 0);
        file.set(path + ".levels.bartering.level", 1);
        file.set(path + ".levels.bartering.xp", 0);

        playerData.saveFile();
    }


}
