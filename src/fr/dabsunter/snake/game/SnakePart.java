package fr.dabsunter.snake.game;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;

public class SnakePart {
	
	private Block block;
	
	public SnakePart(Block block) {
		this.block = block;
	}
	
	public SnakePart(Block block, BlockFace direction) {
		this.block = block;
		
		block.setType(Material.SKULL);
		Skull skull = (Skull) block.getState();
		skull.setSkullType(SkullType.DRAGON);
		skull.setRotation(direction);
		skull.update();
	}
	
	public Block getBlock() {
		return block;
	}
	
	@SuppressWarnings("deprecation")
	public void reverse() {
		byte data = DyeColor.GREEN.getWoolData();
		if (block.getType() != Material.STAINED_CLAY)
			block.setType(Material.STAINED_CLAY);
		else if (block.getData() == data)
			data = DyeColor.LIME.getWoolData();
		block.setData(data);
	}
	
	public void destroy() {
		block.setType(Material.AIR);
	}

}
