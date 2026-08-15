package dev.leonardkleber.plotly.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileManager {
	private final JavaPlugin plugin;

	public FileManager(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public FileConfiguration get(String fileName) {
		File file = new File(plugin.getDataFolder(), fileName);

		if (!file.exists()) {
			plugin.saveResource(fileName, false);
		}

		return YamlConfiguration.loadConfiguration(file);
	}

	public void save(String fileName, FileConfiguration config) {
		File file = new File(plugin.getDataFolder(), fileName);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}