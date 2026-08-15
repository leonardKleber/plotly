package dev.leonardkleber.plotly.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.leonardkleber.plotly.founder.FounderManager;
import dev.leonardkleber.plotly.messages.MessageManager;
import dev.leonardkleber.plotly.models.Coordinates;
import dev.leonardkleber.plotly.plot.PlotManager;
import dev.leonardkleber.plotly.selection.SelectionManager;

public class PlotCommand implements CommandExecutor {
    private final FounderManager founderManager;
    private final SelectionManager selectionManager;
    private final PlotManager plotManager;
    private final MessageManager messageManager;

    public PlotCommand(FounderManager founderManager, SelectionManager selectionManager, PlotManager plotManager, MessageManager messageManager) {
        this.founderManager = founderManager;
        this.selectionManager = selectionManager;
        this.plotManager = plotManager;
        this.messageManager = messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            messageManager.send(sender, "general.only-players");
            return true;
        }

        Player player = (Player) sender;

        if (!founderManager.isFounder(player.getName())) {
            messageManager.send(player, "general.no-permission");
            return true;
        }

        if (args.length == 0 || !args[0].equalsIgnoreCase("claim")) {
            messageManager.send(player, "plot.claim-usage");
            return true;
        }

        Coordinates selection1 = selectionManager.getSelection1(player);
        Coordinates selection2 = selectionManager.getSelection2(player);

        if (selection1 == null || selection2 == null) {
            messageManager.send(player, "plot.no-selection");
            return true;
        }

        int response = plotManager.claimMainPlot(player.getName(), player.getWorld().getName(), selection1, selection2);
        if (response == 0) messageManager.send(player, "plot.claim-success");
        else if (response == 1) messageManager.send(player, "plot.already-claimed");

        return true;
    }
}