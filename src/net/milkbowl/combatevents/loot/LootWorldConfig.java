package net.milkbowl.combatevents.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.CreatureType;

public class LootWorldConfig {
	private Map<CreatureType, ArrayList<CreatureDrop>> creatureMap = new HashMap<CreatureType, ArrayList<CreatureDrop>>();
	
	LootWorldConfig() {
		
	}
	
	public Map<CreatureType, ArrayList<CreatureDrop>> getCreatureMap() {
		return creatureMap;
	}
	
	public void setCreature(CreatureType cType, ArrayList<CreatureDrop> cLoot) {
		creatureMap.put(cType, cLoot);
	}
	
	public ArrayList<CreatureDrop> getCreature(CreatureType cType) {
		return creatureMap.get(cType);
	}
	
}
