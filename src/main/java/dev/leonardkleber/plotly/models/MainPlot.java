package dev.leonardkleber.plotly.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainPlot {
	private final UUID id;
	private final String world;
	private final String founder;
	private final Coordinates corner1;
	private final Coordinates corner2;
	private final List<String> owners;
	private final List<SubPlot> subPlots;

	public MainPlot(UUID id, String world, String founder, Coordinates corner1, Coordinates corner2) {
		this.id = id;
		this.world = world;
		this.founder = founder;
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.owners = new ArrayList<String>();
		this.subPlots = new ArrayList<SubPlot>();
	}

	public String getWorld() {
		return world;
	}
}