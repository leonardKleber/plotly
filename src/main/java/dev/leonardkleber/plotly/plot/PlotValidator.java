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

        if (plots == null) return false;

        for (String plotId : plots.getKeys(false)) {
            String path = "main-plots." + plotId;

            String plotWorld = config.getString(path + ".world");

            if (!world.equals(plotWorld)) continue;

            int plotMinX = Math.min(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));
            int plotMaxX = Math.max(config.getInt(path + ".corner1.x"), config.getInt(path + ".corner2.x"));

            int plotMinZ = Math.min(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));
            int plotMaxZ = Math.max(config.getInt(path + ".corner1.z"), config.getInt(path + ".corner2.z"));

            int selectionMinX = Math.min(corner1.getX(), corner2.getX());
            int selectionMaxX = Math.max(corner1.getX(), corner2.getX());

            int selectionMinZ = Math.min(corner1.getZ(), corner2.getZ());
            int selectionMaxZ = Math.max(corner1.getZ(), corner2.getZ());

            boolean overlaps =
                selectionMinX <= plotMaxX &&
                selectionMaxX >= plotMinX &&
                selectionMinZ <= plotMaxZ &&
                selectionMaxZ >= plotMinZ;

            if (overlaps) return true;
        }

        return false;
    }

    public boolean doesFounderAlreadyHavePlot(String founder) {
        FileConfiguration config = fileManager.get("plots.yml");
        
        ConfigurationSection plots = config.getConfigurationSection("main-plots");

        if (plots == null) return false;

        for (String plotId : plots.getKeys(false)) {
            String plotFounder = config.getString("main-plots." + plotId + ".founder");
            if (founder.equals(plotFounder)) return true;
        }

        return false;
    }

    public boolean isSelectionAdjacentToFoundersPlot() {
        return false;
    }

    public boolean isNewPlotSizeWithinLimit() {
        return false;
    }
}
