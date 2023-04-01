package net.athamusmc.survivalcore.commands;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    private final Core core;

    public Gamemode(Core core){
        this.core = core;
        core.getCommand("gamemode").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //InGame
        if (command.getLabel().equalsIgnoreCase("gamemode")) {
            if (commandSender instanceof Player) {
                Player sender = (Player) commandSender;
                if (sender.hasPermission("athamus.gamemode")) {
                    PlayerData playerData = new PlayerData(core, sender);
                    if (strings.length == 0) {
                        //Send help menu
                        return true;
                    }

                    //Change Self Gamemode
                    if (strings.length == 1) {
                        String input = strings[0];
                        input = input.toUpperCase();
                        switch (input){
                            case "SPEC":
                                input = "SPECTATOR";
                                break;
                            case "S":
                                input = "SURVIVAL";
                                break;
                            case "A":
                                input = "ADVENTURE";
                                break;
                            case "C":
                                input = "CREATIVE";
                                break;
                        }
                        if (GameMode.valueOf(input).equals(null)) {
                            String noGM = core.getLangManager().getFile().getString("en.command.gamemode-not-found");
                            noGM = StringBuilder.parsePlaceholder("prefix", noGM, core);
                            sender.sendMessage(noGM);
                            return true;
                        }
                        playerData.setGamemode(input.toUpperCase());
                        String message = core.getLangManager().getFile().getString("en.player.gamemode-change");
                        message = StringBuilder.parsePlaceholder("prefix", message, core);
                        message = StringBuilder.parsePlaceholder(sender, message, core);
                        commandSender.sendMessage(message);
                        return true;
                    }
                    String noPerms = core.getLangManager().getFile().getString("en.general.no-permission");
                    noPerms = StringBuilder.parsePlaceholder("prefix", noPerms, core);
                    sender.sendMessage(noPerms);
                    return true;
                }

                //Change Other Player's Gamemode
                if (sender.hasPermission("athamus.gamemode.others")) {
                    if (strings.length == 2) {
                        String target = strings[0];
                        String input = strings[1];
                        switch (input){
                            case "SPEC":
                                input = "SPECTATOR";
                                break;
                            case "S":
                                input = "SURVIVAL";
                                break;
                            case "A":
                                input = "ADVENTURE";
                                break;
                            case "C":
                                input = "CREATIVE";
                                break;
                        }
                        Player targetPlayer = Bukkit.getPlayer(target);
                        if (Bukkit.getOnlinePlayers().contains(targetPlayer)) {
                            if (targetPlayer.equals(sender)) {
                                return false;
                            }
                            PlayerData targetData = new PlayerData(core, targetPlayer);
                            targetData.setGamemode(input);
                            String senderMessage = core.getLangManager().getFile().getString("en.player.gamemode-change-other");
                            senderMessage = StringBuilder.parsePlaceholder("prefix", senderMessage, core);
                            senderMessage = StringBuilder.parsePlaceholder(targetPlayer, senderMessage, core);
                            String targetMessage = core.getLangManager().getFile().getString("en.player.gamemode-change");
                            targetMessage = StringBuilder.parsePlaceholder("prefix", targetMessage, core);
                            targetMessage = StringBuilder.parsePlaceholder(targetPlayer, targetMessage, core);
                            sender.sendMessage(senderMessage);
                            targetPlayer.sendMessage(targetMessage);
                            return true;
                        }
                        String invalidTarget = core.getLangManager().getFile().getString("en.command.player-not-found");
                        invalidTarget = StringBuilder.parsePlaceholder("prefix", invalidTarget, core);
                        invalidTarget = StringBuilder.parsePlaceholder(targetPlayer, invalidTarget, core);
                        sender.sendMessage(invalidTarget);
                        return true;
                    }
                }
            }
            //Console Sender
            if (commandSender instanceof ConsoleCommandSender) {
                if (strings.length == 2) {
                    String target = strings[0];
                    String input = strings[1];
                    switch (input){
                        case "SPEC":
                            input = "SPECTATOR";
                            break;
                        case "S":
                            input = "SURVIVAL";
                            break;
                        case "A":
                            input = "ADVENTURE";
                            break;
                        case "C":
                            input = "CREATIVE";
                            break;
                    }
                    Player targetPlayer = Bukkit.getPlayer(target);
                    if (Bukkit.getOnlinePlayers().contains(targetPlayer)) {
                        PlayerData targetData = new PlayerData(core, targetPlayer);
                        targetData.setGamemode(input);
                        String senderMessage = core.getLangManager().getFile().getString("en.player.gamemode-change-other");
                        senderMessage = StringBuilder.parsePlaceholder("prefix", senderMessage, core);
                        senderMessage = StringBuilder.parsePlaceholder(targetPlayer, senderMessage, core);
                        Bukkit.getServer().getLogger().info(StringBuilder.stripString(senderMessage));
                        String targetMessage = core.getLangManager().getFile().getString("en.player.gamemode-change");
                        targetMessage = StringBuilder.parsePlaceholder("prefix", targetMessage, core);
                        targetMessage = StringBuilder.parsePlaceholder(targetPlayer, targetMessage, core);
                        targetPlayer.sendMessage(targetMessage);
                        return true;
                    }
                    String invalidTarget = core.getLangManager().getFile().getString("en.command.player-not-found");
                    invalidTarget = StringBuilder.parsePlaceholder("prefix", invalidTarget, core);
                    invalidTarget = StringBuilder.parsePlaceholder(targetPlayer, invalidTarget, core);
                    invalidTarget = StringBuilder.stripString(invalidTarget);
                    Bukkit.getServer().getLogger().warning(invalidTarget);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
