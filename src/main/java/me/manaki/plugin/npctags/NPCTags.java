package me.manaki.plugin.npctags;

import me.manaki.plugin.npctags.command.TagCommand;
import me.manaki.plugin.npctags.listener.TagListener;
import me.manaki.plugin.npctags.tag.Tags;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NPCTags extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.reloadConfig();
        this.getCommand("npctags").setExecutor(new TagCommand());

        Bukkit.getPluginManager().registerEvents(new TagListener(), this);
    }

    @Override
    public void onDisable() {
        Tags.removeAll();
    }

    @Override
    public void reloadConfig() {
        this.saveDefaultConfig();
        File file = new File(this.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        Tags.reload(config);
        Tags.reloadHolograms();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public static NPCTags get() {
        return JavaPlugin.getPlugin(NPCTags.class);
    }


}
