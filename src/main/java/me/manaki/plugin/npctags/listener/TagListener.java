package me.manaki.plugin.npctags.listener;

import me.manaki.plugin.npctags.NPCTags;
import me.manaki.plugin.npctags.tag.Tags;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TagListener implements Listener {

    private boolean join = false;

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent e) {
        if (join) return;
        join = true;
        Tags.reloadHolograms();
    }

    @EventHandler
    public void onNPCreate(NPCCreateEvent e) {
        NPC npc = e.getNPC();
        int id = npc.getId();
        Bukkit.getScheduler().runTask(NPCTags.get(), () -> {
            Tags.create(id);
        });
    }

    @EventHandler
    public void NPCRemove(NPCRemoveEvent e) {
        NPC npc = e.getNPC();
        int id = npc.getId();
        Tags.remove(id);
    }

}
