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
				if (!isValidItem())
					super.setTypeId(-1);
				super.setDurability(Short.parseShort(dropData[1]));
				validateSubType();
				this.min = Integer.parseInt(dropData[2]);
				//Error check our min
				if (this.min < 0)
					this.min = 0;
				this.max = Integer.parseInt(dropData[3]);
				//Normalize our max
				if (this.max < this.min)
					this.max = this.min;
				this.chance = Double.parseDouble(dropData[4]);
				//Chance is between 0 and 100
				if (this.chance < 0)
					this.chance = 0;
				else if (this.chance > 100)
					this.chance = 100;
				
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

	public CreatureDrop getRandom() {
		if (!isValid())
			return null;
		if (this.rand.nextInt(99) <= chance - 1) {
			super.setAmount(rand.nextInt(max-min) + min);
			return this;
		} else
			return null;
	}
	
	public boolean isValid() {
		if (super.getTypeId() == -1)
			return false;
		
		return true;
	}
	
	public String toString() {
		return super.getTypeId() + ":" + super.getDurability() + ":" + this.min + ":" + this.max + ":" + this.chance;
	}
	
	private boolean isValidItem() {
		int item = super.getTypeId();
		if ( (item >= 1 && item <= 33) || item == 35 )
			return true;
		else if ( (item >= 37 && item <= 96) || (item >= 256 && item <= 359) )
			return true;
		else if (item == 2256 || item == 2257)
			return true;
		else
			return false;
	}
	
	private void validateSubType() {
		short subType = super.getDurability();
		int item = super.getTypeId();
		//0-2 = Saplings - Wood - Leaves - Tall Grass
		if ( item == 6 || item == 17 || item == 18 || item == 31) {
			if ( subType < 0 || subType > 2)
				super.setDurability((short) 0);
		}
		//0-3 = Slab - Double Slab
		else if (item == 43 || item == 44) {
			if (subType < 0 || subType > 3)
				super.setDurability((short) 0);
		}
		//0-15 = Wool - Dye
		else if (item == 35 || item == 351) {
			if (subType < 0 || subType > 15)
				super.setDurability((short) 0);
		}
		//If we aren't an invalid item then make sure our durability is 0;
		else if (item != -1)
			super.setDurability((short) 0);
	}
}
