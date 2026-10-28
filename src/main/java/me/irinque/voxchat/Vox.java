package me.irinque.voxchat;

import org.bukkit.plugin.java.JavaPlugin;
import me.irinque.voxchat.handlers.PlayerJoin;
import me.irinque.voxchat.handlers.PlayerQuit;
import me.irinque.voxchat.handlers.PlayerDeath;

public final class Vox extends JavaPlugin {
    public static Vox instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);

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
