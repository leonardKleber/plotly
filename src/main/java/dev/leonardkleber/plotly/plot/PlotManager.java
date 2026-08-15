package dev.leonardkleber.plotly.plot;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import dev.leonardkleber.plotly.files.FileManager;
import dev.leonardkleber.plotly.models.Coordinates;

public class PlotManager {
	private final FileManager fileManager;
	private final FileConfiguration config;
	private final PlotValidator plotValidator;

	public PlotManager(FileManager fileManager) {
		this.fileManager = fileManager;
		this.config = fileManager.get("plots.yml");
		this.plotValidator = new PlotValidator(fileManager);
	}

	public int claimMainPlot(String founder, String world, Coordinates corner1, Coordinates corner2) {
		if (plotValidator.isSelectionAlreadyClaimed(world, corner1, corner2))
			return 1;

		if (!plotValidator.isNewPlotSizeWithinLimit(founder, world, corner1, corner2))
			return 2;

		if (plotValidator.doesFounderAlreadyHavePlot(founder)) {
			if (!plotValidator.isSelectionAdjacentToFoundersPlot(founder, world, corner1, corner2))
				return 3;
		}

		UUID id = UUID.randomUUID();

		String path = "main-plots." + id.toString();

		config.set(path + ".world", world);
		config.set(path + ".founder", founder);

		config.set(path + ".corner1.x", corner1.getX());
		config.set(path + ".corner1.z", corner1.getZ());

		config.set(path + ".corner2.x", corner2.getX());
		config.set(path + ".corner2.z", corner2.getZ());

		config.set(path + ".owners", new ArrayList<String>());
		config.set(path + ".sub-plots", new ArrayList<String>());

		fileManager.save("plots.yml", config);

		return 0;
	}

	public boolean isLocationInOtherPlayersPlot(String player, String world, int x, int z) {
		return plotValidator.isLocationInOtherPlayersPlot(player, world, x, z);
	}
}