package dev.leonardkleber.plotly.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.leonardkleber.plotly.selection.SelectionManager;

public class PlayerListener implements Listener {
	private final SelectionManager selectionManager;

	public PlayerListener(SelectionManager selectionManager) {
		this.selectionManager = selectionManager;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		selectionManager.init(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		selectionManager.remove(event.getPlayer());
	}
}
