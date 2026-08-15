package dev.leonardkleber.plotly;

import org.bukkit.plugin.java.JavaPlugin;

import dev.leonardkleber.plotly.selection.SelectionManager;
import dev.leonardkleber.plotly.commands.FounderCommand;
import dev.leonardkleber.plotly.commands.SelectorCommand;
import dev.leonardkleber.plotly.files.FileManager;
import dev.leonardkleber.plotly.founder.FounderManager;
import dev.leonardkleber.plotly.listeners.PlayerListener;
import dev.leonardkleber.plotly.listeners.SelectionListener;
import dev.leonardkleber.plotly.messages.MessageManager;

public class Plotly extends JavaPlugin {
	private FileManager fileManager;
	private SelectionManager selectionManager;
	private FounderManager founderManager;
	private MessageManager messageManager;

	@Override
	public void onEnable() {
		fileManager = new FileManager(this);
		selectionManager = new SelectionManager();
		founderManager = new FounderManager(fileManager);
		messageManager = new MessageManager(fileManager);

		getServer().getPluginManager().registerEvents(new SelectionListener(selectionManager, messageManager), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(selectionManager), this);

		getCommand("founder").setExecutor(new FounderCommand(founderManager, messageManager));
		getCommand("selector").setExecutor(new SelectorCommand(founderManager, messageManager));
	}
}
