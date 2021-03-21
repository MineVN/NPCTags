package me.manaki.plugin.npctags.tag;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import java.util.List;

public class Tag {

    private String id;
    private int npcID;
    private List<String> tags;

    public Tag(String id, int npcID, List<String> tags) {
        this.id = id;
        this.npcID = npcID;
        this.tags = tags;
    }

    public String getID() {
        return id;
    }

    public int getNPCID() {
        return npcID;
    }

    public List<String> getTags() {
        return tags;
    }
}
