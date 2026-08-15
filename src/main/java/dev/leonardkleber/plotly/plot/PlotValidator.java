package dev.leonardkleber.plotly.plot;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import dev.leonardkleber.plotly.files.FileManager;
import dev.leonardkleber.plotly.models.Coordinates;

public class PlotValidator {
	private final FileManager fileManager;

	public PlotValidator(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	public boolean isSelectionAlreadyClaimed(String world, Coordinates corner1, Coordinates corner2) {
		FileConfiguration config = fileManager.get("plots.yml");

		ConfigurationSection plots = config.getConfigurationSection("main-plots");

		if (plots == null)
			return false;

		for (String plotId : plots.getKeys(false)) {
			String path = "main-plots." + plotId;

			String plotWorld = config.getString(path + ".world");

			if (!world.equals(plotWorld))
				continue;

			int plotMinX = Math.min(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));
			int plotMaxX = Math.max(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));

			int plotMinZ = Math.min(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));
			int plotMaxZ = Math.max(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));

			int selectionMinX = Math.min(corner1.getX(), corner2.getX());
			int selectionMaxX = Math.max(corner1.getX(), corner2.getX());

			int selectionMinZ = Math.min(corner1.getZ(), corner2.getZ());
			int selectionMaxZ = Math.max(corner1.getZ(), corner2.getZ());

			boolean overlaps = selectionMinX <= plotMaxX && selectionMaxX >= plotMinX && selectionMinZ <= plotMaxZ
					&& selectionMaxZ >= plotMinZ;

			if (overlaps)
				return true;
		}

		return false;
	}

	public boolean doesFounderAlreadyHavePlot(String founder) {
		FileConfiguration config = fileManager.get("plots.yml");

		ConfigurationSection plots = config.getConfigurationSection("main-plots");

		if (plots == null)
			return false;

		for (String plotId : plots.getKeys(false)) {
			String plotFounder = config.getString("main-plots." + plotId + ".founder");
			if (founder.equals(plotFounder))
				return true;
		}

		return false;
	}

	public boolean isSelectionAdjacentToFoundersPlot(String founder, String world, Coordinates corner1,
			Coordinates corner2) {
		FileConfiguration config = fileManager.get("plots.yml");
		ConfigurationSection plots = config.getConfigurationSection("main-plots");

		if (plots == null)
			return false;

		int selectionMinX = Math.min(corner1.getX(), corner2.getX());
		int selectionMaxX = Math.max(corner1.getX(), corner2.getX());

		int selectionMinZ = Math.min(corner1.getZ(), corner2.getZ());
		int selectionMaxZ = Math.max(corner1.getZ(), corner2.getZ());

		for (String plotId : plots.getKeys(false)) {
			String path = "main-plots." + plotId;

			if (!founder.equals(config.getString(path + ".founder")))
				continue;

			if (!world.equals(config.getString(path + ".world")))
				continue;

			int plotMinX = Math.min(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));
			int plotMaxX = Math.max(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));

			int plotMinZ = Math.min(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));
			int plotMaxZ = Math.max(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));

			boolean touchesTopOrBottom = (selectionMaxZ + 1 == plotMinZ || plotMaxZ + 1 == selectionMinZ)
					&& selectionMaxX >= plotMinX && selectionMinX <= plotMaxX;

			boolean touchesLeftOrRight = (selectionMaxX + 1 == plotMinX || plotMaxX + 1 == selectionMinX)
					&& selectionMaxZ >= plotMinZ && selectionMinZ <= plotMaxZ;

			if (touchesTopOrBottom || touchesLeftOrRight)
				return true;
		}

		return false;
	}

	public boolean isNewPlotSizeWithinLimit(String founder, String world, Coordinates corner1, Coordinates corner2) {
		FileConfiguration plotsConfig = fileManager.get("plots.yml");
		ConfigurationSection plots = plotsConfig.getConfigurationSection("main-plots");

		FileConfiguration config = fileManager.get("config.yml");
		int maxSize = config.getInt("plot.max-size-per-world");

		int width = Math.abs(corner1.getX() - corner2.getX()) + 1;
		int length = Math.abs(corner1.getZ() - corner2.getZ()) + 1;

		int size = width * length;

		if (size > maxSize)
			return false;

		int currentSize = 0;

		if (plots != null) {
			for (String plotId : plots.getKeys(false)) {
				String path = "main-plots." + plotId;

				String plotFounder = plotsConfig.getString(path + ".founder");
				String plotWorld = plotsConfig.getString(path + ".world");

				if (!founder.equals(plotFounder))
					continue;
				if (!world.equals(plotWorld))
					continue;

				int plotWidth = Math
						.abs(plotsConfig.getInt(path + ".corner1.x") - plotsConfig.getInt(path + ".corner2.x")) + 1;
				int plotLength = Math
						.abs(plotsConfig.getInt(path + ".corner1.z") - plotsConfig.getInt(path + ".corner2.z")) + 1;

				currentSize += plotWidth * plotLength;
			}
		}

		return currentSize + size <= maxSize;
	}
}
