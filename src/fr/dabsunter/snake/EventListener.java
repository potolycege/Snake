package fr.dabsunter.snake;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.dabsunter.snake.game.Game;

public class EventListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Game game = SnakePlugin.getInstance().getGame();
		if (game != null && e.getAction().name().startsWith("RIGHT_CLICK")) {
			if (e.getClickedBlock().equals(SnakePlugin.BUTTON_UP))
				game.getSnake().setDirection(SnakePlugin.DIRECTION_UP);
			else if (e.getClickedBlock().equals(SnakePlugin.BUTTON_DOWN))
				game.getSnake().setDirection(SnakePlugin.DIRECTION_DOWN);
			else if (e.getClickedBlock().equals(SnakePlugin.BUTTON_LEFT))
				game.getSnake().setDirection(SnakePlugin.DIRECTION_LEFT);
			else if (e.getClickedBlock().equals(SnakePlugin.BUTTON_RIGHT))
				game.getSnake().setDirection(SnakePlugin.DIRECTION_RIGHT);
		}
	}

}
