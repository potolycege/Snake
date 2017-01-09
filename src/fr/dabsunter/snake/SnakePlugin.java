package fr.dabsunter.snake;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dabsunter.snake.game.Game;

public class SnakePlugin extends JavaPlugin {
	
	public static Location MAP_BASE;
	public static Location MAP_CORNER1;
	public static Location MAP_CORNER2;
	
	public static Block BUTTON_UP;
	public static Block BUTTON_DOWN;
	public static Block BUTTON_LEFT;
	public static Block BUTTON_RIGHT;
	
	public static BlockFace DIRECTION_UP;
	public static BlockFace DIRECTION_DOWN;
	public static BlockFace DIRECTION_LEFT;
	public static BlockFace DIRECTION_RIGHT;
	
	private static SnakePlugin instance;
	
	private Game game;
	
	public static SnakePlugin getInstance() {
		return instance;
	}
	
	public static void resetMap() {
		int x1 = MAP_CORNER1.getBlockX();
		int x2 = MAP_CORNER2.getBlockX();
		int y1 = MAP_CORNER1.getBlockY();
		int y2 = MAP_CORNER2.getBlockY();
		int z1 = MAP_CORNER1.getBlockZ();
		int z2 = MAP_CORNER2.getBlockZ();
		
		for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++)
			for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++)
				for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++)
					new Location(MAP_BASE.getWorld(), x, y, z).getBlock().setType(Material.AIR);
	}
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		getCommand("snake").setExecutor(new SnakeCommand());

		loadConfig();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void loadConfig() {
		MAP_BASE = new Location(Bukkit.getWorld(getConfig().getString("game-location.world")),
				getConfig().getDouble("game-location.x"),
				getConfig().getDouble("game-location.y"),
				getConfig().getDouble("game-location.z"));
		MAP_CORNER1 = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("game-location.corner1.x"),
				getConfig().getDouble("game-location.corner1.y"),
				getConfig().getDouble("game-location.corner1.z"));
		MAP_CORNER2 = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("game-location.corner2.x"),
				getConfig().getDouble("game-location.corner2.y"),
				getConfig().getDouble("game-location.corner2.z"));
		
		BUTTON_UP = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("buttons.up.x"),
				getConfig().getDouble("buttons.up.y"),
				getConfig().getDouble("buttons.up.z")).getBlock();
		BUTTON_DOWN = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("buttons.down.x"),
				getConfig().getDouble("buttons.down.y"),
				getConfig().getDouble("buttons.down.z")).getBlock();
		BUTTON_LEFT = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("buttons.left.x"),
				getConfig().getDouble("buttons.left.y"),
				getConfig().getDouble("buttons.left.z")).getBlock();
		BUTTON_RIGHT = new Location(MAP_BASE.getWorld(),
				getConfig().getDouble("buttons.right.x"),
				getConfig().getDouble("buttons.right.y"),
				getConfig().getDouble("buttons.right.z")).getBlock();

		DIRECTION_UP = BlockFace.valueOf(getConfig().getString("direction.up"));
		DIRECTION_DOWN = BlockFace.valueOf(getConfig().getString("direction.down"));
		DIRECTION_LEFT = BlockFace.valueOf(getConfig().getString("direction.left"));
		DIRECTION_RIGHT = BlockFace.valueOf(getConfig().getString("direction.right"));
	}

}
