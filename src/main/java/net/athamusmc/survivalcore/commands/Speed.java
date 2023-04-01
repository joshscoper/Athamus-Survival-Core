package net.athamusmc.survivalcore.commands;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {

    private final Core core;

    public Speed(Core core){
        this.core = core;
        core.getCommand("speed").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            Bukkit.getLogger().warning("Only players can execute that command!");
            return true;
        }
        Player sender = (Player) commandSender;
        Location location = sender.getLocation();
        PlayerData playerData = new PlayerData(core,sender);
        if (!sender.hasPermission("athamus.speed")){
            String noPerms = core.getLangManager().getFile().getString("en.general.no-permission");
            noPerms = StringBuilder.parsePlaceholder("prefix", noPerms, core);
            sender.sendMessage(noPerms);
            return true;
        }
        if (!(strings.length == 1)){
            String invalid = core.getLangManager().getFile().getString("en.general.invalid-input");
            invalid = StringBuilder.parsePlaceholder("prefix", invalid, core);
            sender.sendMessage(invalid);
            return true;
        }
        if (location.getY() - 1 > location.getWorld().getHighestBlockAt(location).getY()){
            float speed = Float.parseFloat(strings[0]);
            playerData.setSpeed("fly", speed);
            String message = core.getLangManager().getFile().getString("en.player.fly-speed");
            message = message.replaceAll("%fly-speed%", strings[0]);
            message = StringBuilder.parsePlaceholder("prefix", message,core);
            sender.sendMessage(message);
            return true;
        }
            float speed = Float.parseFloat(strings[0]);
            playerData.setSpeed("walk",speed);
            String message = core.getLangManager().getFile().getString("en.player.walk-speed");
            message = message.replaceAll("%walk-speed%", strings[0]);
            message = StringBuilder.parsePlaceholder("prefix", message,core);
            sender.sendMessage(message);
            return true;
    }
}
