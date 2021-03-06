package me.mrCookieSlime.CSCoreLibPlugin;

import java.io.File;
import java.io.IOException;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Localization;
import me.mrCookieSlime.CSCoreLibPlugin.updater.Updater;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.mcstats.Metrics;

public class PluginUtils {

	private Plugin plugin;
	private int id;
	private Config cfg;
	private Localization local;
	
	/**
	 * Creates a new PluginUtils Instance for
	 * the specified Plugin
	 *
	 * @param  plugin The Plugin for which this PluginUtils Instance is made for
	 */ 
	public PluginUtils(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Creates a new PluginUtils Instance for
	 * the specified Plugin
	 *
	 * @param  plugin The Plugin for which this PluginUtils Instance is made for
	 */ 
	public PluginUtils(Plugin plugin, int id) {
		this(plugin);
		this.id = id;
	}
	
	/**
	 * Returns the specified ID from Curse
	 *
	 * @return      Plugin ID
	 */ 
	public int getPluginID() {
		return this.id;
	}
	
	/**
	 * Automatically sets up an Updater Service for you
	 *
	 * @param  id The ID of your Project
	 * @param  file The file of your Plugin (obtained by plugin.getFile() )
	 */ 
	public void setupUpdater(int id, File file) {
		this.id = id;
		if (plugin.getConfig().getBoolean("options.auto-update")) new Updater(plugin, file, id);
	}
	
	/**
	 * Automatically sets up an Updater Service for you
	 *
	 * @param  file The file of your Plugin (obtained by plugin.getFile() )
	 */ 
	public void setupUpdater(File file) {
		if (plugin.getConfig().getBoolean("options.auto-update")) new Updater(plugin, file, id);
	}
	
	/**
	 * Automatically sets up MC-Stats Metrics for you
	 */ 
	public void setupMetrics() {
		try {
			Metrics metrics = new Metrics(plugin);
			metrics.start();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Automatically sets up the messages.yml for you
	 */ 
	public void setupLocalization() {
		local = new Localization(plugin);
	}
	
	/**
	 * Automatically sets up the config.yml for you
	 */ 
	public void setupConfig() {
		FileConfiguration config = plugin.getConfig();
		config.options().copyDefaults(true);
		config.options().header("\nПлагин: " + plugin.getName() + ".\nАвторы: " + plugin.getDescription().getAuthors().get(0) + " и SoSeDiK в качестве локализатора." + "\n\nНе изменяйте конфиг, пока сервер работает,\nиначе могут произойти плохие вещи!\n\nЭтот плагин требует CS-CoreLib для работы!\nЕсли Вы ещё не установили его, то он будет\nавтоматически загружен для Вас.\n\nВ плагин встроена функция автообновления.\nЯ, SoSeDiK, намеренно рекомендую не включать её,\nчтобы не было проблем с переводом в будущем!\noptions -> auto-update: false");
		plugin.saveConfig();
		
		cfg = new Config(plugin);
	}
	
	/**
	 * Returns the previously setup Config
	 *
	 * @return      Config of this Plugin
	 */ 
	public Config getConfig() {
		return cfg;
	}
	
	/**
	 * Returns the previously setup Localization
	 *
	 * @return      Localization for this Plugin
	 */ 
	public Localization getLocalization() {
		return local;
	}
}
