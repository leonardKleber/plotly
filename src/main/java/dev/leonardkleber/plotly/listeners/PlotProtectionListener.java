package dev.leonardkleber.plotly.listeners;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.leonardkleber.plotly.messages.MessageManager;
import dev.leonardkleber.plotly.plot.PlotManager;

public class PlotProtectionListener implements Listener {
	private final PlotManager plotManager;
	private final MessageManager messageManager;

	private final Set<Material> protectedBlocks = new HashSet<Material>(Arrays.asList(Material.WOODEN_DOOR,
			Material.IRON_DOOR_BLOCK, Material.TRAP_DOOR, Material.FENCE_GATE, Material.LEVER, Material.STONE_BUTTON));

	public PlotProtectionListener(PlotManager plotManager, MessageManager messageManager) {
		this.plotManager = plotManager;
		this.messageManager = messageManager;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
				block.getZ())) {
			event.setCancelled(true);
			messageManager.send(player, "plot.no-build-permission");
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
				block.getZ())) {
			event.setCancelled(true);
			messageManager.send(player, "plot.no-build-permission");
		}
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked();

		if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
				block.getZ())) {
			event.setCancelled(true);
			messageManager.send(player, "plot.no-interact-permission");
		}
	}

	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockClicked();

		if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
				block.getZ())) {
			event.setCancelled(true);
			messageManager.send(player, "plot.no-interact-permission");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Block block = event.getClickedBlock();

		if (block == null)
			return;
		if (!protectedBlocks.contains(block.getType()))
			return;

		Player player = event.getPlayer();

		if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
				block.getZ())) {
			event.setCancelled(true);
			messageManager.send(player, "plot.no-interact-permission");
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (!(event.getPlayer() instanceof Player))
			return;

		Player player = (Player) event.getPlayer();

		if (event.getInventory().getHolder() instanceof BlockState) {
			BlockState holder = (BlockState) event.getInventory().getHolder();
			Block block = holder.getBlock();

			if (plotManager.isLocationInOtherPlayersPlot(player.getName(), player.getWorld().getName(), block.getX(),
					block.getZ())) {
				event.setCancelled(true);
				messageManager.send(player, "plot.no-container-permission");
			}
		}
	}
}