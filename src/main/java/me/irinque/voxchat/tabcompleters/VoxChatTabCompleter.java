package me.irinque.voxchat.tabcompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;
import java.util.Locale;

public class VoxChatTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(
            CommandSender commandSender,
            Command command,
            String alias,
            String[] args
    ) {
        if (args.length != 1 || !commandSender.hasPermission("voxchat.reload")) {
            return List.of();
        }

        String reloadCommand = "reload";

        if (reloadCommand.startsWith(args[0].toLowerCase(Locale.ROOT))) {
            return List.of(reloadCommand);
        }

        return List.of();
    }
}
