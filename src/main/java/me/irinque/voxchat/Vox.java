package me.irinque.voxchat;

import org.bukkit.plugin.java.JavaPlugin;
import me.irinque.voxchat.handlers.PlayerJoin;
import me.irinque.voxchat.handlers.PlayerQuit;

public final class Vox extends JavaPlugin {
    public static Vox instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

        getLogger().info("[VoxChat] Plugin is ready");
    }

    @Override
    public void onDisable() {
        if (instance == this) {
            instance = null;
        }

        getLogger().info("[VoxChat] Plugin is off");
    }
}
