package net.milkbowl.combatevents.loot;

import java.util.ArrayList;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import net.milkbowl.combatevents.CombatEventsListener;
import net.milkbowl.combatevents.events.EntityKilledByEntityEvent;
import net.milkbowl.combatevents.Utility;

public class CombatListener extends CombatEventsListener{

	CombatListener() {
	}

	@Override
	public void onEntityKilledByEntityEvent(EntityKilledByEntityEvent event) {
		CreatureType cType = Utility.getCType(event.getKilled());
		//If we gots a valid creature and a valid player lets do some drops!
		if (event.getAttacker() instanceof Player && !cType.equals(null) ) {
			String worldName = event.getAttacker().getWorld().getName();
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
