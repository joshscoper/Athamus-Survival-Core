package net.athamusmc.survivalcore.commands;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {

    private Core core;

    public Fly(Core core){
        this.core = core;
        core.getCommand("fly").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            Bukkit.getLogger().warning("Only players can execute that command!");
            return true;
        }
        Player sender = (Player) commandSender;
        PlayerData senderData = new PlayerData(core,sender);
        if (command.getLabel().equalsIgnoreCase("fly")){
            if (sender.hasPermission("athamus.fly")){
                if (senderData.getFlying()){
                    senderData.setFlying(false);
                    Location ground = sender.getLocation();
                    ground.setY(ground.getWorld().getHighestBlockYAt(ground) + 1);
                    sender.teleport(ground);
                    String disabled = core.getLangManager().getFile().getString("en.player.fly-mode-disabled");
                    disabled = StringBuilder.parsePlaceholder("prefix", disabled,core);
                    sender.sendMessage(disabled);
                } else {
                    sender.playSound(sender.getLocation(), Sound.ENTITY_BAT_TAKEOFF,1,1);
                    String enabled = core.getLangManager().getFile().getString("en.player.fly-mode-enabled");
                    enabled = StringBuilder.parsePlaceholder("prefix", enabled,core);
                    sender.sendMessage(enabled);
                    senderData.setFlying(true);
                }
                return true;
            }
            String noPerms = core.getLangManager().getFile().getString("en.general.no-permission");
            noPerms = StringBuilder.parsePlaceholder("prefix", noPerms, core);
            sender.sendMessage(noPerms);
            return true;
        }
        return false;
    }
}
