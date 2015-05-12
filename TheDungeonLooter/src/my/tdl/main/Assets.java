package my.tdl.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {

	SpriteSheet blocks = new SpriteSheet();
	
	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	public static BufferedImage roof_1;
	public static BufferedImage glass_1;
	public static BufferedImage bfloor_1;

	public void init() {
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		stone_1 = blocks.getTile(0, 0, 16, 16);	
		wall_1 = blocks.getTile(32, 32, 16, 16);	
		roof_1 = blocks.getTile(32, 0, 16, 16);	
		glass_1 = blocks.getTile(32, 48, 16, 16);	
		bfloor_1 = blocks.getTile(16, 32, 16, 16);	
	}
	
	public static BufferedImage getStone_1() {
		return stone_1;
	}
	
	public static BufferedImage getWall_1() {
		return wall_1;
	}

	public static BufferedImage getRoof_1() {
		return roof_1;
	}

	public static BufferedImage getGlass_1() {
		return glass_1;
	}

	public static BufferedImage getBfloor_1() {
		return bfloor_1;
	}
	
}
