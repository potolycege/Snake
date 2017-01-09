package fr.dabsunter.snake.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dabsunter.snake.SnakePlugin;

public class Snake {
	
	private Game game;
	private ArrayList<SnakePart> snake = new ArrayList<>();
	private BlockFace direction = BlockFace.NORTH;
	private int size = 3;
	
	public Snake(Game game) {
		this.game = game;
		
		Block next = SnakePlugin.MAP_BASE.getBlock();
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				snake.add(new SnakePart(next, direction));
			} else {
				SnakePart part = new SnakePart(next);
				part.reverse();
				if (i%2 == 0)
					part.reverse();
				snake.add(part);
			}				
			next = next.getRelative(direction.getOppositeFace());
		}
		updateSize();
	}
	
	public int getSize() {
		return size;
	}
	
	public void setDirection(BlockFace direction) {
		this.direction = direction;
	}
	
	public ForwardResponse forward() {
		for (SnakePart part : snake)
			part.reverse();
		Block next = snake.get(0).getBlock().getRelative(direction);
		if (next.getType() != Material.AIR) {
			if (game.isApple(next)) {
				size++;
				updateSize();
				next.getWorld().playSound(
						next.getLocation(),
						Sound.ENTITY_GENERIC_EAT,
						150f, 1.5f);
				new BukkitRunnable() {

					@Override
					public void run() {
						game.placeApple();
					}
				}.runTaskLater(SnakePlugin.getInstance(), 100l);
			} else if (isQueue(next)) {
				return ForwardResponse.FAIL_SNAKE;
			} else {
				return ForwardResponse.FAIL_WALL;
			}
		}
		snake.add(0, new SnakePart(next, direction));
		if (snake.size() >= size)
			snake.remove(size).destroy();
		return ForwardResponse.SUCCESS;
	}
	
	private boolean isQueue(Block block) {
		for (SnakePart part : snake)
			if (part.getBlock().equals(block))
				return true;
		return false;
	}
	
	private void updateSize() {
		for (Player player : Bukkit.getOnlinePlayers())
			player.setLevel(size);
	}
	
	public static enum ForwardResponse {
		
		SUCCESS(true, null),
		FAIL_SNAKE(false, "Le serpent de :size blocs s'est mordu la queue"),
		FAIL_WALL(false, "Le serpent de :size blocs s'est prit un mur");
		
		private boolean success;
		private String message;
		
		private ForwardResponse(boolean success, String message) {
			this.success = success;
			this.message = message;
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public String getMessage() {
			return message;
		}
		
	}

}
