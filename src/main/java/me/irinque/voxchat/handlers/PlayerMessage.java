package me.irinque.voxchat.handlers;

import me.irinque.voxchat.Vox;
import me.irinque.voxchat.config.ChatSettings;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMessage implements Listener {
    private final Vox vox;

    public PlayerMessage(Vox vox) {
        this.vox = vox;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        String playerMessage = event.getMessage();
        ChatSettings chatSettings = vox.getChatSettings();
        boolean isGlobalChat = playerMessage.startsWith("!") && chatSettings.globalChatEnabled();

        if (!isGlobalChat && (playerMessage.startsWith("!") || !chatSettings.localChatEnabled())) {
            return;
        }

        event.setCancelled(true);

        UUID playerId = event.getPlayer().getUniqueId();
        String message = isGlobalChat ? playerMessage.substring(1) : playerMessage;
        Runnable sendMessage = isGlobalChat
                ? () -> sendGlobalMessage(playerId, message, chatSettings)
                : () -> sendLocalMessage(playerId, message, chatSettings);

        if (event.isAsynchronous()) {
            vox.getServer().getScheduler().runTask(vox, sendMessage);
        } else {
            sendMessage.run();
        }
    }

    private void sendGlobalMessage(UUID playerId, String playerMessage, ChatSettings chatSettings) {
        Player playerSender = vox.getServer().getPlayer(playerId);

        if (playerSender == null) {
            return;
        }

        String worldPrefix = getWorldPrefix(playerSender.getWorld(), chatSettings.globalChatSign());
        String message = chatSettings.globalChatMessage()
                .replace("{world_prefix}", worldPrefix)
                .replace("{player}", playerSender.getDisplayName())
                .replace("{message}", playerMessage);

        for (Player playerReceiver : vox.getServer().getOnlinePlayers()) {
            playerReceiver.sendMessage(message);
        }
    }

    private String getWorldPrefix(World world, String sign) {
        return switch (world.getEnvironment()) {
            case NETHER -> "§c" + sign;
            case THE_END -> "§d" + sign;
            default -> "§a" + sign;
        };
    }

    private void sendLocalMessage(UUID playerId, String playerMessage, ChatSettings chatSettings) {
        Player playerSender = vox.getServer().getPlayer(playerId);

        if (playerSender == null) {
            return;
        }

        Location senderLocation = playerSender.getLocation();
        World world = playerSender.getWorld();
        int radius = chatSettings.localChatRadius();
        double radiusSquared = (double) radius * radius;
        List<Player> playerReceivers = new ArrayList<>();

        if (radius >= 0) {
            for (Player playerReceiver : world.getPlayers()) {
                if (playerReceiver.getUniqueId().equals(playerId)) {
                    continue;
                }

                if (playerReceiver.getLocation().distanceSquared(senderLocation) <= radiusSquared) {
                    playerReceivers.add(playerReceiver);
                }
            }
        }

        if (playerReceivers.isEmpty()) {
            String noReceivers = vox.getConfig().getString(
                    "message.noReceivers",
                    "Your message was not seen by anyone."
            );
            playerSender.sendMessage(noReceivers);
            return;
        }

        String message = chatSettings.localChatMessage()
                .replace("{player}", playerSender.getDisplayName())
                .replace("{message}", playerMessage);

        for (Player playerReceiver : playerReceivers) {
            playerReceiver.sendMessage(message);
        }

        playerSender.sendMessage(message);
    }
}
