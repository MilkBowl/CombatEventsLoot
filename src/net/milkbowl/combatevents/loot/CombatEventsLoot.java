package net.milkbowl.combatevents.loot;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class CombatEventsLoot extends JavaPlugin {

	public static String plugName;
	public static Logger log = Logger.getLogger("Minecraft");


	public static Configuration wConfig;
	
	private ConfigManager configManager;
	
	private CombatListener combatListener;

	@Override
	public void onLoad() {
		plugName = "["+this.getDescription().getName()+"]";
	}

	@Override
	public void onDisable() {
		log.info(plugName + " - " + "disabled!");

	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();

		if (!setupDependencies()) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		configManager = new ConfigManager(this);


		PluginManager pm = this.getServer().getPluginManager();
		combatListener = new CombatListener(this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, combatListener, Priority.Normal, this);

		log.info(plugName + " - " + "v" + pdfFile.getVersion() + " by Sleaker is enabled!");

	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	private boolean setupDependencies() {
		return (this.getServer().getPluginManager().getPlugin("CombatEventsCore") != null);
	}
}
