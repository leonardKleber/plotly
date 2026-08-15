package dev.leonardkleber.plotly.selection;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import dev.leonardkleber.plotly.models.Coordinates;

public class SelectionManager {
	private final Map<String, Coordinates[]> selections = new HashMap<String, Coordinates[]>();

	public void init(Player player) {
		selections.put(player.getName(), new Coordinates[2]);
	}

	public void remove(Player player) {
		selections.remove(player.getName());
	}

	public Coordinates getSelection1(Player player) {
		return selections.get(player.getName())[0];
	}

	public Coordinates getSelection2(Player player) {
		return selections.get(player.getName())[1];
	}

	public int select(Player player, Coordinates position) {
		Coordinates[] selection = selections.get(player.getName());

		if (selection[0] == null) {
			selection[0] = position;
			return 1;
		} else if (selection[1] == null) {
			selection[1] = position;
			return 2;
		} else {
			selection[0] = position;
			selection[1] = null;
			return 1;
		}
	}
}
