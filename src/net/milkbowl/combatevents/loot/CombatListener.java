package net.milkbowl.combatevents.loot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import net.milkbowl.combatevents.events.EntityKilledByEntityEvent;
import net.milkbowl.combatevents.listeners.CombatEventsListener;

public class CombatListener extends CombatEventsListener{

	CombatEventsLoot plugin;
	private Random rand = new Random();
	
	public CombatListener(CombatEventsLoot plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onEntityKilledByEntity(EntityKilledByEntityEvent event) {
		if (event.getKilled() instanceof Player )
			return;
		
		CreatureID creature = CreatureID.fromEntity(event.getKilled());

		if (creature != null) {
			ArrayList<ItemInfo> creatureDrop = plugin.getConfigManager().getDrop(event.getKilled().getWorld().getName(), creature);
			
			if (creatureDrop != null) {
				List<ItemStack> drops = event.getDrops();
				
				if (drops != null) {
					drops.clear();
				
					Iterator<ItemInfo> creatureDropIterator = creatureDrop.iterator();
					while (creatureDropIterator.hasNext()) {
						ItemInfo dropInfo = creatureDropIterator.next();

						if (dropInfo != null && dropInfo.isValid()) {
							if (dropInfo.itemID == 0) 
								continue;
							
							int quantity = getQuantity(dropInfo.min, dropInfo.max);
							if (quantity == 0)
								continue;
							
							if (dropInfo.chance == 100) {
								if (dropInfo.dataID == 0) {
									drops.add(new ItemStack(dropInfo.itemID, quantity));
								}
								else {
									MaterialData matData = new MaterialData(dropInfo.itemID, dropInfo.dataID);
									drops.add(matData.toItemStack(quantity));
								}
							}
							else {
								
								if (rand.nextInt(100) <= dropInfo.chance) {
									if (dropInfo.dataID == 0) {
										drops.add(new ItemStack(dropInfo.itemID, quantity));
									}
									else {
										MaterialData matData = new MaterialData(dropInfo.itemID, dropInfo.dataID);
										drops.add(matData.toItemStack(quantity));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private int getQuantity(int min, int max) {
		if (min < 0 || max < 0)
			return 1;
		else if (min == max)
			return max;
		else if (max < min)
			return max;
		else {
			return rand.nextInt(max-min) + min;
		}
	}
}
