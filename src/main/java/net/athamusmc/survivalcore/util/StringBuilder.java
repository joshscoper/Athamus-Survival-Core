package net.athamusmc.survivalcore.util;

import net.athamusmc.survivalcore.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StringBuilder{


    public static String formatString(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String stripString(String input){
        return ChatColor.stripColor(input);
    }

    public static String parsePlaceholder(Object placeholder, String input, Core core){
        String output = input;
        if (placeholder instanceof String){
            if (((String) placeholder).equalsIgnoreCase("prefix")){
                output = output.replaceAll("%prefix%",  core.getLangManager().getFile().getString("en.prefix"));
                output = formatString(output);
            }
        }
        if (placeholder instanceof Player){
            output = output.replaceAll("%player%", ((Player) placeholder).getDisplayName());
            output = output.replaceAll("%gamemode%", ((Player) placeholder).getGameMode().toString());
            output = formatString(output);
        }
        if (placeholder instanceof ItemStack){
            output = output.replaceAll("%item%",((ItemStack) placeholder).getItemMeta().getDisplayName());
            output = output.replaceAll("%item-amount%", String.valueOf(((ItemStack) placeholder).getAmount()));
            output = formatString(output);
        }
        return output;
    }

}
