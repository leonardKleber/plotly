package dev.leonardkleber.plotly.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.leonardkleber.plotly.messages.MessageManager;
import dev.leonardkleber.plotly.models.Coordinates;
import dev.leonardkleber.plotly.selection.SelectionManager;

public class SelectionListener implements Listener {
	private final SelectionManager selectionManager;
	private final MessageManager messageManager;

	public SelectionListener(SelectionManager selectionManager, MessageManager messageManager) {
		this.selectionManager = selectionManager;
		this.messageManager = messageManager;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.LEFT_CLICK_BLOCK)
			return;
		if (event.getItem() == null || event.getItem().getType() != Material.STICK)
			return;

		event.setCancelled(true);

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		Coordinates coordinates = new Coordinates(block.getX(), block.getZ());

		int selection = selectionManager.select(player, coordinates);

		messageManager.send(player, "selector.selection", "%selection%", String.valueOf(selection), "%x%",
				String.valueOf(coordinates.getX()), "%z%", String.valueOf(coordinates.getZ()));
	}
}
