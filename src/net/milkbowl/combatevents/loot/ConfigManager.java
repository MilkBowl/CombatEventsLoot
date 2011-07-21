package net.milkbowl.combatevents.loot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class ConfigManager {
	
	private final File configFile;
	private Configuration config;
	private CombatEventsLoot plugin;

	private final HashMap<String, HashMap<CreatureID, ArrayList<ItemInfo>>> worldDropTable;
	
	public ConfigManager(CombatEventsLoot plugin) {
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder() + File.separator + "general.yml");
		worldDropTable = new HashMap<String, HashMap<CreatureID, ArrayList<ItemInfo>>>();
		
		loadConfig();
	}
	
	public void loadConfig() {
		if (configFile.exists()) {	
			List<World> worlds = this.plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();

			config = new Configuration(configFile);
			config.load();
		
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();
				loadWorld(world.getName());
			}
		}
		else
			this.createConfig();
	}
	
	private void saveConfig() {
		config.save();

		CombatEventsLoot.log.info("[CombatEventsLoot] Config saved.");
	}
	
	public ArrayList<ItemInfo> getDrop(String worldName, CreatureID creature) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = worldDropTable.get(worldName);
		
		if (dropTable == null)
			dropTable = loadWorld(worldName);
		
		ArrayList<ItemInfo> drop = dropTable.get(creature);
		
		if (drop == null || drop.size() == 0)
			return null;
		else
			return drop;
	}
	
	public void setDrop(String worldName, CreatureID creature, String[] items) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
		ArrayList<String> itemDropsData = new ArrayList<String>();
		ArrayList<ItemInfo> itemDrops = new ArrayList<ItemInfo>();

		for (int i = 0; i < items.length; i++) {
			itemDrops.add(new ItemInfo(items[i]));
			itemDropsData.add(items[i]);
		}
		
		dropTable.put(creature, itemDrops);
		
		config.setProperty(worldName+"."+creature.getName(), itemDropsData);
		worldDropTable.put(worldName, dropTable);
	}
	
	private void createConfig() {
		List<World> worlds = plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();

		config = new Configuration(configFile);

		while (worldIterator.hasNext()) {
			World world = worldIterator.next();

			HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
			
			for (CreatureID creature : CreatureID.values()) {
				ArrayList<ItemInfo> creatureDrop = new ArrayList<ItemInfo>();
				dropTable.put(creature, creatureDrop);

				config.setProperty(world.getName()+"."+creature.getName(), creatureDrop);
			}
			
			worldDropTable.put(world.getName(), dropTable);
		}

		
		this.saveConfig();
	}
	
	private HashMap<CreatureID, ArrayList<ItemInfo>> loadWorld(String worldName) {
		HashMap<CreatureID, ArrayList<ItemInfo>> dropTable = new HashMap<CreatureID, ArrayList<ItemInfo>>();
		
		for (CreatureID creature : CreatureID.values()) {
			ArrayList<String> creatureDropData = (ArrayList<String>) config.getStringList(worldName+"."+creature.getName(), new ArrayList<String>());
			ArrayList<ItemInfo> creatureDrop = new ArrayList<ItemInfo>();
			Iterator<String> creatureDropDataIterator = creatureDropData.iterator();
			
			while (creatureDropDataIterator.hasNext()) {
				creatureDrop.add(new ItemInfo(creatureDropDataIterator.next()));
			}
			
			dropTable.put(creature, creatureDrop);
		}
		
		worldDropTable.put(worldName, dropTable);

		return dropTable;
	}
}
