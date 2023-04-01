package net.athamusmc.survivalcore.commands;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodMode implements CommandExecutor {

    private final Core core;

    public GodMode(Core core){
        this.core = core;
        core.getCommand("godmode").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            Bukkit.getLogger().warning("Only players can execute that command!");
            return true;
        }
        Player sender = (Player) commandSender;
        PlayerData senderData = new PlayerData(core,sender);
        if (command.getLabel().equalsIgnoreCase("godmode")){
            if (sender.hasPermission("athamus.godmode")){
                if (senderData.getGod()){
                    senderData.setGod(false);
                    String disabled = core.getLangManager().getFile().getString("en.player.god-mode-disabled");
                    disabled = StringBuilder.parsePlaceholder("prefix", disabled,core);
                    sender.sendMessage(disabled);
                } else {
                    String enabled = core.getLangManager().getFile().getString("en.player.god-mode-enabled");
                    enabled = StringBuilder.parsePlaceholder("prefix", enabled,core);
                    sender.sendMessage(enabled);
                    senderData.setGod(true);
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
