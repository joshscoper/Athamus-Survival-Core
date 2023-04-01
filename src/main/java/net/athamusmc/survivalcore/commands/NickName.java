package net.athamusmc.survivalcore.commands;

import net.athamusmc.survivalcore.Core;
import net.athamusmc.survivalcore.playerdata.PlayerData;
import net.athamusmc.survivalcore.util.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickName implements CommandExecutor{

    private final Core core;

    public NickName(Core core){
        this.core = core;
        core.getCommand("nickname").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            Bukkit.getLogger().warning("Only players can execute that command!");
            return true;
        }
        Player sender = (Player) commandSender;
        PlayerData senderData = new PlayerData(core, sender);
        //Setting Self Nickname
        if (command.getLabel().equalsIgnoreCase("nickname")){
            if (strings.length == 0){
                //Reset Message
                String reset = core.getLangManager().getFile().getString("en.command.nickname-reset");
                reset = StringBuilder.parsePlaceholder("prefix", reset,core);
                reset = StringBuilder.parsePlaceholder(sender,reset,core);
                sender.sendMessage(reset);
                senderData.setNick(sender.getName());
                return true;
            }
            if (strings.length == 1){
                if (sender.hasPermission("athamus.nick.self")) {
                    if (strings[0].length() > 16){
                        String exceeds = core.getLangManager().getFile().getString("en.command.nickname-too-long");
                        exceeds = StringBuilder.parsePlaceholder("prefix", exceeds,core);
                        sender.sendMessage(StringBuilder.formatString(exceeds));
                        return true;
                    }
                    senderData.setNick(StringBuilder.formatString(strings[0]));
                    String nick = core.getLangManager().getFile().getString("en.command.change-nickname");
                    nick = StringBuilder.parsePlaceholder("prefix", nick,core);
                    nick = StringBuilder.parsePlaceholder(sender,nick,core);
                    sender.sendMessage(nick);
                    return true;
                }
                String noPerms = core.getLangManager().getFile().getString("en.general.no-permission");
                noPerms = StringBuilder.parsePlaceholder("prefix", noPerms, core);
                sender.sendMessage(noPerms);
                return true;
            }
            if (strings.length == 2) {
                if (sender.hasPermission("athamus.nick.others")) {
                    Player target = Bukkit.getPlayer(strings[0]);
                    if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                        String noPlayer = core.getLangManager().getFile().getString("en.command.player-not-found");
                        noPlayer = StringBuilder.parsePlaceholder("prefix", noPlayer,core);
                        sender.sendMessage(StringBuilder.formatString(noPlayer));
                        return true;
                    }
                    PlayerData targetData = new PlayerData(core, target);
                    if (StringBuilder.stripString(strings[1]).equalsIgnoreCase("reset")){
                     targetData.setNick(target.getName());
                        String reset = core.getLangManager().getFile().getString("en.command.nickname-reset");
                        reset = StringBuilder.parsePlaceholder("prefix", reset,core);
                        reset = StringBuilder.parsePlaceholder(sender,reset,core);
                        target.sendMessage(reset);
                        return true;
                    }
                    String nick = strings[1];
                    if (nick.length() > 16) {
                        String exceeds = core.getLangManager().getFile().getString("en.command.nickname-too-long");
                        exceeds = StringBuilder.parsePlaceholder("prefix", exceeds,core);
                        sender.sendMessage(StringBuilder.formatString(exceeds));
                        return true;
                    }
                    if (!(nick.length() > 0)) {
                        String invalid = core.getLangManager().getFile().getString("en.command.nickname-invalid");
                        invalid = StringBuilder.parsePlaceholder("prefix", invalid,core);
                        sender.sendMessage(StringBuilder.formatString(invalid));
                        return true;
                    }
                    targetData.setNick(StringBuilder.formatString(strings[1]));
                    String nickChange = core.getLangManager().getFile().getString("en.command.change-nickname");
                    nickChange = StringBuilder.parsePlaceholder("prefix", nickChange,core);
                    nickChange = StringBuilder.parsePlaceholder(target,nickChange,core);
                    target.sendMessage(nickChange);
                    String nickChangeOther = core.getLangManager().getFile().getString("en.command.change-nickname-other");
                    nickChangeOther = StringBuilder.parsePlaceholder("prefix", nickChangeOther,core);
                    nickChangeOther = StringBuilder.parsePlaceholder(target,nickChangeOther,core);
                    sender.sendMessage(nickChangeOther);
                    return true;
                }
                //No Permission
                String noPerms = core.getLangManager().getFile().getString("en.general.no-permission");
                noPerms = StringBuilder.parsePlaceholder("prefix", noPerms,core);
                sender.sendMessage(StringBuilder.formatString(noPerms));
                return true;
            }
        }
        return false;
    }
}
