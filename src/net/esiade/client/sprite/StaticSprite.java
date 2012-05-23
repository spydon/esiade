package net.esiade.client.sprite;

import net.esiade.client.Vector2D;

import com.google.gwt.canvas.dom.client.Context2d;


public abstract class StaticSprite extends Sprite {
	public static double spawnRate = 0.0;
	
	/**
	 * @param image The path to a picture of the sprite
	 * @param position The starting position of the sprite (top-left corner of the sprite at this point)
	 */
	public StaticSprite(String image, Vector2D position) {
		super(image, position);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.esiade.client.sprite.Sprite#draw(com.google.gwt.canvas.dom.client.Context2d)
	 */
	public void draw(Context2d context) {
		context.save();
		context.drawImage(image, position.getX(), position.getY());
		context.restore();
	}	
}
