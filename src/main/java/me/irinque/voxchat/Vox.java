package me.irinque.voxchat;

import me.irinque.voxchat.commands.Message;
import me.irinque.voxchat.commands.VoxChat;
import me.irinque.voxchat.config.ChatSettings;
import me.irinque.voxchat.handlers.PlayerDeath;
import me.irinque.voxchat.handlers.PlayerJoin;
import me.irinque.voxchat.handlers.PlayerMessage;
import me.irinque.voxchat.handlers.PlayerQuit;
import me.irinque.voxchat.tabcompleters.MessageTabCompleter;
import me.irinque.voxchat.tabcompleters.VoxChatTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Vox extends JavaPlugin {
    public static Vox instance;
    private volatile ChatSettings chatSettings = ChatSettings.defaults();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        refreshChatSettings();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerMessage(this), this);

        getCommand("message").setExecutor(new Message(this));
        getCommand("message").setTabCompleter(new MessageTabCompleter(this));
        getCommand("voxchat").setExecutor(new VoxChat(this));
        getCommand("voxchat").setTabCompleter(new VoxChatTabCompleter());

        getLogger().info("[VoxChat] Plugin is ready");
    }

    @Override
    public void onDisable() {
        if (instance == this) {
            instance = null;
        }

        getLogger().info("[VoxChat] Plugin is off");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        refreshChatSettings();
    }

    public ChatSettings getChatSettings() {
        return chatSettings;
    }

    private void refreshChatSettings() {
        chatSettings = ChatSettings.from(getConfig());
    }
}
