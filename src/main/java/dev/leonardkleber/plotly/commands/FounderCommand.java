package dev.leonardkleber.plotly.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev.leonardkleber.plotly.founder.FounderManager;
import dev.leonardkleber.plotly.messages.MessageManager;

public class FounderCommand implements CommandExecutor {
	private final FounderManager founderManager;
	private final MessageManager messageManager;

	public FounderCommand(FounderManager founderManager, MessageManager messageManager) {
		this.founderManager = founderManager;
		this.messageManager = messageManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.isOp()) {
			messageManager.send(sender, "general.no-permission");
			return true;
		}

		if (args.length == 0) {
			messageManager.send(sender, "founder.usage");
			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {
			if (args.length != 1) {
				messageManager.send(sender, "founder.list-usage");
				return true;
			}

			List<String> founders = founderManager.getFounders();

			messageManager.send(sender, "founder.list-header");

			for (String founder : founders) {
				messageManager.send(sender, "founder.list-item", "%founder%", founder);
			}

			return true;
		}

		if (args[0].equalsIgnoreCase("add")) {
			if (args.length != 2) {
				messageManager.send(sender, "founder.add-usage");
				return true;
			}

			if (founderManager.isFounder(args[1])) {
				messageManager.send(sender, "founder.already-founder", "%founder%", args[1]);
				return true;
			}

			founderManager.addFounder(args[1]);
			messageManager.send(sender, "founder.now-founder", "%founder%", args[1]);

			return true;
		}

		if (args[0].equalsIgnoreCase("remove")) {
			if (args.length != 2) {
				messageManager.send(sender, "founder.remove-usage");
				return true;
			}

			founderManager.removeFounder(args[1]);
			messageManager.send(sender, "founder.no-founder", "%founder%", args[1]);

			return true;
		}

		messageManager.send(sender, "founder.usage");
		return true;
	}
}