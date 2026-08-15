package dev.leonardkleber.plotly.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.leonardkleber.plotly.founder.FounderManager;
import dev.leonardkleber.plotly.messages.MessageManager;

public class SelectorCommand implements CommandExecutor {
	private final FounderManager founderManager;
	private final MessageManager messageManager;

	public SelectorCommand(FounderManager founderManager, MessageManager messageManager) {
		this.founderManager = founderManager;
		this.messageManager = messageManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			messageManager.send(sender, "general.only-players");
			return true;
		}

		Player player = (Player) sender;

		if (!player.isOp() && !founderManager.isFounder(player.getName())) {
			messageManager.send(sender, "general.no-permission");
			return true;
		}

		player.getInventory().addItem(new ItemStack(Material.STICK));
		messageManager.send(sender, "selector.on-reciept");

		return true;
	}
}