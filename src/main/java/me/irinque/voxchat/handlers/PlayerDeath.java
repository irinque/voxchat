package me.irinque.voxchat.handlers;

import me.irinque.voxchat.Vox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!Vox.instance.getConfig().getBoolean("death.enable", true)) {
            return;
        }

        String message = Vox.instance.getConfig().getString("death.message", "");
        String sign = Vox.instance.getConfig().getString("death.sign", "");
        String defaultMessage = event.getDeathMessage();

        if (defaultMessage == null) {
            defaultMessage = "";
        }

        message = message
                .replace("{player}", event.getEntity().getDisplayName())
                .replace("{sign}", sign)
                .replace("{default_message}", defaultMessage);

        event.setDeathMessage(message);
    }
}
