package fr.dabsunter.snake.game;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.dabsunter.snake.SnakePlugin;
import fr.dabsunter.snake.game.Snake.ForwardResponse;
import fr.dabsunter.snake.util.Title;

public class Game {
	
	private Random rand;
	private BukkitTask task;
	private Block apple;
	private Snake snake;
	
	public Game() {
		SnakePlugin.resetMap();
		this.snake = new Snake(this);
		this.rand = new Random();
		this.task = new GameRunnable().runTaskTimer(SnakePlugin.getInstance(), 60l, 15l);
		placeApple();
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public boolean isApple(Block apple) {
		return this.apple.equals(apple);
	}
	
	@SuppressWarnings("deprecation")
	public void placeApple() {
		int x1 = SnakePlugin.MAP_CORNER1.getBlockX();
		int x2 = SnakePlugin.MAP_CORNER2.getBlockX();
		int y1 = SnakePlugin.MAP_CORNER1.getBlockY();
		int y2 = SnakePlugin.MAP_CORNER2.getBlockY();
		int z1 = SnakePlugin.MAP_CORNER1.getBlockZ();
		int z2 = SnakePlugin.MAP_CORNER2.getBlockZ();
		
		ArrayList<Location> locs = new ArrayList<>();
		for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
			for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
				for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++)
					locs.add(new Location(SnakePlugin.MAP_BASE.getWorld(), x, y, z));
		
		Block apple;
		int id = rand.nextInt(locs.size());
		while ((apple = locs.get(id).getBlock()).getType() != Material.AIR)
			id = rand.nextInt(locs.size());
		
		apple.setType(Material.STAINED_CLAY);
		apple.setData(DyeColor.RED.getWoolData());
		this.apple = apple;
	}
	
	public void destroy() {
		task.cancel();
	}
	
	private class GameRunnable extends BukkitRunnable {
		
		@Override
		public void run() {
			ForwardResponse response = snake.forward();
			if (!response.isSuccess()) {
				Title title = new Title();
				title.setTitle("Game Over");
				title.setTitleColor(ChatColor.RED);
				title.setSubtitle(response.getMessage().replace(":size", String.valueOf(snake.getSize())));
				title.setSubtitleColor(ChatColor.RED);
				title.setFadeInTime(1);
				title.setStayTime(3);
				title.setFadeOutTime(1);
				title.broadcast();
				cancel();
			}
		}
	
	}

}
