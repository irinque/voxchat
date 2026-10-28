package me.irinque.voxchat.handlers;

import me.irinque.voxchat.Vox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!Vox.instance.getConfig().getBoolean("quit.enable", true)) {
            return;
        }

        String message = Vox.instance.getConfig().getString("quit.message", "");
        event.setQuitMessage(message.replace("{player}", event.getPlayer().getDisplayName()));
    }
}
