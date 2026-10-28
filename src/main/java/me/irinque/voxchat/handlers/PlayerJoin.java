package me.irinque.voxchat.handlers;

import me.irinque.voxchat.Vox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!Vox.instance.getConfig().getBoolean("join.enable", true)) {
            return;
        }

        String message = Vox.instance.getConfig().getString("join.message", "");
        event.setJoinMessage(message.replace("{player}", event.getPlayer().getDisplayName()));
    }
}
