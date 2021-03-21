package me.manaki.plugin.npctags.command;

import me.manaki.plugin.npctags.NPCTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage("");
            sender.sendMessage("§a§lNPCTags by MankaiStep - MineVN");
            sender.sendMessage("§6/npctags reload: §7Reload plugin and holograms");
            sender.sendMessage("");
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            NPCTags.get().reloadConfig();
            sender.sendMessage("§aAll done!");
        }


        return false;
    }

}
