package my.project.gop.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage spriteSheet;
	
	public SpriteSheet() {
		// TODO Auto-generated constructor stub
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	public BufferedImage getTile(int xTile, int yTile, int width, int height){
		BufferedImage sprite = spriteSheet.getSubimage(xTile, yTile, width, height);
		return sprite;
	}
	
}
