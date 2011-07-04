package net.milkbowl.combatevents.loot;

import java.util.ArrayList;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import net.milkbowl.combatevents.Utility;

public class CombatListener extends EntityListener{

	CombatListener() {
	}

	
	public void onEntityDeath(EntityDeathEvent event) {
		CreatureType cType = Utility.getCType((LivingEntity) event.getEntity());
		//If we gots a valid creature and a valid player lets do some drops!
		if (!cType.equals(null) ) {
			String worldName = event.getEntity().getWorld().getName();
			ArrayList<CreatureDrop> drops = CombatEventsLoot.worldConfig.get(worldName).getCreature(cType);
			if (drops.isEmpty())
				return;
			else {
				event.getDrops().clear();
				for (CreatureDrop drop : drops) {
					event.getDrops().add(drop.getRandom());
				}
			}
		}

	}
}
