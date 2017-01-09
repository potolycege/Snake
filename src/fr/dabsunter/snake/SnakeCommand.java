package fr.dabsunter.snake;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.dabsunter.snake.game.Game;

public class SnakeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1)
			return false;
		
		switch (args[0].toLowerCase()) {
		case "reload":
			reloadCommand(sender, args);
			return true;
		case "reset":
			resetCommand(sender, args);
			return true;
		case "start":
			startCommand(sender, args);
			return true;
		case "stop":
			stopCommand(sender, args);
			return true;
		}
		return false;
	}
	
	private void reloadCommand(CommandSender sender, String[] args) {
		SnakePlugin plugin = SnakePlugin.getInstance();
		plugin.reloadConfig();
		plugin.loadConfig();
		sender.sendMessage("Config de Snake a été rechargée avec succès");
	}
	
	private void resetCommand(CommandSender sender, String[] args) {
		SnakePlugin.resetMap();
	}
	
	private void startCommand(CommandSender sender, String[] args) {
		SnakePlugin.getInstance().setGame(new Game());
	}
	
	private void stopCommand(CommandSender sender, String[] args) {
		SnakePlugin plugin = SnakePlugin.getInstance();
		plugin.getGame().destroy();
		plugin.setGame(null);
	}

}
