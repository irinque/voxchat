package me.irinque.voxchat.commands;

import me.irinque.voxchat.Vox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoxChat implements CommandExecutor {
    private final Vox vox;

    public VoxChat(Vox vox) {
        this.vox = vox;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            return false;
        }

        if (!commandSender.hasPermission("voxchat.reload")) {
            commandSender.sendMessage(getMessage("permissionError"));
            return true;
        }

        vox.reloadConfig();
        commandSender.sendMessage(getMessage("pluginReloaded"));
        return true;
    }

    private String getMessage(String messageName) {
        String message = vox.getConfig().getString("message." + messageName, "");
        return "[Vox] " + message;
    }
}
