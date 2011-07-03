package net.milkbowl.combatevents.loot;

import java.util.ArrayList;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

import net.milkbowl.combatevents.CombatEventsListener;
import net.milkbowl.combatevents.events.EntityKilledByEntityEvent;

public class CombatListener extends CombatEventsListener{

	CombatListener() {
	}

	@Override
	public void onEntityKilledByEntityEvent(EntityKilledByEntityEvent event) {
		CreatureType cType = getCType(event.getKilled());
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

	public CreatureType getCType(LivingEntity cEntity) {
		if (cEntity instanceof Chicken)
			return CreatureType.CHICKEN;
		else if (cEntity instanceof Cow)
			return CreatureType.COW;
		else if (cEntity instanceof Creeper)
			return CreatureType.CREEPER;
		else if (cEntity instanceof Ghast)
			return CreatureType.GHAST;
		else if (cEntity instanceof Giant)
			return CreatureType.GIANT;
		else if (cEntity instanceof Pig)
			return CreatureType.PIG;
		else if (cEntity instanceof PigZombie)
			return CreatureType.PIG_ZOMBIE;
		else if (cEntity instanceof Sheep)
			return CreatureType.SHEEP;
		else if (cEntity instanceof Skeleton)
			return CreatureType.SKELETON;
		else if (cEntity instanceof Slime)
			return CreatureType.SLIME;
		else if (cEntity instanceof Spider)
			return CreatureType.SPIDER;
		else if (cEntity instanceof Squid)
			return CreatureType.SQUID;
		else if (cEntity instanceof Wolf)
			return CreatureType.WOLF;
		else if (cEntity instanceof Zombie)
			return CreatureType.ZOMBIE;
		else
			return null;
	}
}
