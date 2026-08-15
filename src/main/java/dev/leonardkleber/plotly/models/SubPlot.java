package dev.leonardkleber.plotly.models;

public class SubPlot {
	private final String owner;
	private final Coordinates corner1;
	private final Coordinates corner2;

	public SubPlot(String owner, Coordinates corner1, Coordinates corner2) {
		this.owner = owner;
		this.corner1 = corner1;
		this.corner2 = corner2;
	}
}