package me.irinque.voxchat.commands;

import me.irinque.voxchat.Vox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

import java.util.Arrays;

public class Message implements CommandExecutor {
    private final Vox vox;

    public Message(Vox vox) {
        this.vox = vox;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!vox.getConfig().getBoolean("private-message.enable", true)) {
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        String playerMessage = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).strip();

        if (playerMessage.isEmpty()) {
            commandSender.sendMessage(vox.getConfig().getString(
                    "message.emptyMessage",
                    "Unable to send an empty message."
            ));
            return true;
        }

        Player playerReceiver = findOnlinePlayer(args[0]);

        if (playerReceiver == null) {
            commandSender.sendMessage(vox.getConfig().getString(
                    "message.receiverIsOffline",
                    "The player with this nickname is not online."
            ));
            return true;
        }

        String sign = vox.getConfig().getString("private-message.sign", "[✉]");
        String senderName = commandSender instanceof Player playerSender
                ? playerSender.getDisplayName()
                : commandSender.getName();
        String receiverMessage = formatMessage(
                vox.getConfig().getString(
                        "private-message.template-receiver",
                        "§b{sign} {sender} >> {message}"
                ),
                sign,
                senderName,
                playerReceiver.getDisplayName(),
                playerMessage
        );
        String senderMessage = formatMessage(
                vox.getConfig().getString(
                        "private-message.template-sender",
                        "§b{sign} {sender} -> {receiver} >> {message}"
                ),
                sign,
                senderName,
                playerReceiver.getDisplayName(),
                playerMessage
        );

        playerReceiver.sendMessage(receiverMessage);
        commandSender.sendMessage(senderMessage);

        if (vox.getConfig().getBoolean("private-message.sound", true)) {
            playerReceiver.playSound(
                    playerReceiver.getLocation(),
                    Sound.BLOCK_NOTE_BLOCK_BELL,
                    1.0f,
                    1.0f
            );
        }

        return true;
    }

    private Player findOnlinePlayer(String playerName) {
        for (Player player : vox.getServer().getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return player;
            }
        }

        return null;
    }

    private String formatMessage(
            String template,
            String sign,
            String senderName,
            String receiverName,
            String playerMessage
    ) {
        return template
                .replace("{sign}", sign)
                .replace("{sender}", senderName)
                .replace("{receiver}", receiverName)
                .replace("{message}", playerMessage);
    }
}
