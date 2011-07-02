package net.milkbowl.combatevents.loot;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class CreatureDrop extends ItemStack {
	private int min;
	private int max;
	private double chance;
	private Random rand = new Random();

	public CreatureDrop (String dropInfo) {
		super(-1, 0, (short) -1);
		String[] dropData = dropInfo.split(":");
		if (dropData.length == 5) {
			try {
				super.setTypeId(Integer.parseInt(dropData[0]));
				super.setDurability(Short.parseShort(dropData[1]));
				this.min = Integer.parseInt(dropData[2]);
				this.max = Integer.parseInt(dropData[3]);
				this.chance = Double.parseDouble(dropData[4]);
			} catch (Exception e) {
			}
		}
	}

	public CreatureDrop(final int type, final int amount, final short damage, final int min, final int max, double chance) {
		super(type, amount, damage);
		this.min = min;
		this.max = max;
		this.chance = chance;
	}

	public CreatureDrop get() {
		return this;
	}

	public CreatureDrop getRandom(Random rand) {
		if (this.rand.nextInt(99) <= chance - 1) {
			super.setAmount(rand.nextInt(max-min) + min);
			return this;
		} else
			return null;
	}
	
	public boolean isValid() {
		if (super.getTypeId() == -1 && super.getDurability() == -1)
			return false;
		
		return true;
	}
	
	public String toString() {
		return super.getTypeId() + ":" + super.getDurability() + ":" + this.min + ":" + this.max + ":" + this.chance;
	}
}
