package me.irinque.voxchat;

import org.bukkit.plugin.java.JavaPlugin;

public final class Vox extends JavaPlugin {
    public static Vox instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

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
