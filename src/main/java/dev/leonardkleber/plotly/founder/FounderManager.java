package dev.leonardkleber.plotly.founder;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import dev.leonardkleber.plotly.files.FileManager;

public class FounderManager {
	private final FileManager fileManager;
	private final FileConfiguration config;

	public FounderManager(FileManager fileManager) {
		this.fileManager = fileManager;
		config = fileManager.get("founders.yml");
	}

	public List<String> getFounders() {
		return config.getStringList("founders");
	}

	public boolean isFounder(String player) {
		List<String> founders = config.getStringList("founders");
		return founders.contains(player);
	}

	public void addFounder(String player) {
		List<String> founders = config.getStringList("founders");

		if (!founders.contains(player)) {
			founders.add(player);
			config.set("founders", founders);
			fileManager.save("founders.yml", config);
		}
	}

	public void removeFounder(String player) {
		List<String> founders = config.getStringList("founders");

		if (founders.contains(player)) {
			founders.remove(player);
			config.set("founders", founders);
			fileManager.save("founders.yml", config);
		}
	}
}