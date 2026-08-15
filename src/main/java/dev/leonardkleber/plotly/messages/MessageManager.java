package dev.leonardkleber.plotly.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import dev.leonardkleber.plotly.files.FileManager;

public class MessageManager {
	private final FileConfiguration config;

	public MessageManager(FileManager fileManager) {
		config = fileManager.get("messages.yml");
	}

	public String getPrefix() {
		return getPrimaryColor() + config.getString("prefix");
	}

	public ChatColor getPrimaryColor() {
		return ChatColor.valueOf(config.getString("colors.primary"));
	}

	public ChatColor getSecondaryColor() {
		return ChatColor.valueOf(config.getString("colors.secondary"));
	}

	public String getMessage(String key) {
		return getPrefix() + " " + getSecondaryColor() + config.getString("messages." + key);
	}

	public String getMessage(String key, String... placeholders) {
		String message = getMessage(key);
		for (int i = 0; i < placeholders.length; i += 2) {
			message = message.replace(placeholders[i], getPrimaryColor() + placeholders[i + 1] + getSecondaryColor());
		}
		return message;
	}

	public void send(CommandSender sender, String key) {
		sender.sendMessage(getMessage(key));
	}

	public void send(CommandSender sender, String key, String... placeholders) {
		sender.sendMessage(getMessage(key, placeholders));
	}
}
