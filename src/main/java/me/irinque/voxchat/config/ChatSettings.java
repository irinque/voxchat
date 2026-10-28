package me.irinque.voxchat.config;

import org.bukkit.configuration.file.FileConfiguration;

public record ChatSettings(
        boolean globalChatEnabled,
        String globalChatSign,
        String globalChatMessage,
        boolean localChatEnabled,
        int localChatRadius,
        String localChatMessage
) {
    public static ChatSettings defaults() {
        return new ChatSettings(
                true,
                "●",
                "{world_prefix} §f{player} >> {message}",
                true,
                50,
                "§7{player} >> {message}"
        );
    }

    public static ChatSettings from(FileConfiguration config) {
        ChatSettings defaults = defaults();

        return new ChatSettings(
                config.getBoolean("global-chat.enable", defaults.globalChatEnabled()),
                config.getString("global-chat.sign", defaults.globalChatSign()),
                config.getString("global-chat.message", defaults.globalChatMessage()),
                config.getBoolean("local-chat.enable", defaults.localChatEnabled()),
                config.getInt("local-chat.radius", defaults.localChatRadius()),
                config.getString("local-chat.message", defaults.localChatMessage())
        );
    }
}
