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

        if (playerMessage.startsWith("!") || !chatSettings.localChatEnabled()) {
            return;
        }

        event.setCancelled(true);

        UUID playerId = event.getPlayer().getUniqueId();
        Runnable sendMessage = () -> sendLocalMessage(playerId, playerMessage, chatSettings);

        if (event.isAsynchronous()) {
            vox.getServer().getScheduler().runTask(vox, sendMessage);
        } else {
            sendMessage.run();
        }
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
