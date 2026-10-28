package me.irinque.voxchat.tabcompleters;

import me.irinque.voxchat.Vox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageTabCompleter implements TabCompleter {
    private final Vox vox;

    public MessageTabCompleter(Vox vox) {
        this.vox = vox;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender commandSender,
            Command command,
            String alias,
            String[] args
    ) {
        if (args.length != 1) {
            return List.of();
        }

        String playerName = args[0];

        return vox.getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.regionMatches(true, 0, playerName, 0, playerName.length()))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }
}
