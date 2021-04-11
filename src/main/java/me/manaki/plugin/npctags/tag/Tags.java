package me.manaki.plugin.npctags.tag;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.collect.Maps;
import me.manaki.plugin.npctags.NPCTags;
import net.citizensnpcs.api.CitizensAPI;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

public class Tags {

    private static final Map<String, Tag> tags = Maps.newHashMap();

    private static final Map<Integer, Hologram> currentTags = Maps.newHashMap();

    public static void reload(FileConfiguration config) {
        for (String id : config.getConfigurationSection("tag").getKeys(false)) {
            int npcID = config.getInt("tag." + id + ".npc");
            List<String> lines = config.getStringList("tag." + id + ".tags").stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
            tags.put(id, new Tag(id, npcID, lines));
        }
    }

    public static Tag get(String id) {
        return tags.getOrDefault(id, null);
    }

    public static Tag get(int npcID) {
        for (Tag tag : tags.values()) {
            if (tag.getNPCID() == npcID) return tag;
        }
        return null;
    }

    public static boolean isCreated(int npcID) {
        return currentTags.containsKey(npcID);
    }

    public static void create(int npcID) {

        Tag tag = get(npcID);
        if (tag == null) return;

        var npc = CitizensAPI.getNPCRegistry().getById(npcID);
        if (npc == null) return;

        var entity = npc.getEntity();
        entity.setCustomNameVisible(false);
        entity.setCustomName(null);
        List<String> lines = tag.getTags();

        var h = HologramsAPI.createHologram(NPCTags.get(), entity.getLocation().add(0, 2.4, 0));
        currentTags.put(npcID, h);

        for (String line : lines) {
            h.appendTextLine(line);
        }

        h.teleport(entity.getLocation().add(0, 2 + h.getHeight(), 0));
    }

    public static void remove(int npcID) {
        var h = currentTags.getOrDefault(npcID, null);
        if (h == null) return;
        h.delete();
        currentTags.remove(npcID);
    }

    public static void removeAll() {
        for (Hologram h : currentTags.values()) {
            h.delete();
        }
        currentTags.clear();
    }

    public static void reloadHolograms() {
        removeAll();
        for (Tag tag : tags.values()) {
            create(tag.getNPCID());
        }
    }
}
