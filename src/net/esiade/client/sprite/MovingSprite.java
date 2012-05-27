package net.esiade.client.sprite;

import net.esiade.client.Vector2D;


public abstract class MovingSprite extends Sprite{

	protected Vector2D velocity;
	
	public MovingSprite(String imageLocation, Vector2D position, Vector2D velocity) {
		super(imageLocation, position);
		this.velocity = velocity;
		//RootPanel.get().add(dbgMsg);
	}
	
	/**
	 * Update the position to a new position with the vectors,	collisions have been taken care of.
	 */
	public void updatePos() {
		position.add(velocity);
	}
	
	/**
	 * If a sprite collides with the top or bottom border, set its velocity in that direction to 0. 
	 */
	public void verticalCollision() {
//		What should happen to the vectors if there is a
//		horizontal collision? Set the vectors to make the
//		individual go sideways.
		velocity = new Vector2D(velocity.x,0.0);
	}
	/**
	 * If a sprite collides with the left or right border, set its velocity in that direction to 0. 
	 */
	public void horizontalCollision() {
//		What should happen to the vectors if there is a
//		horizontal collision? Set the vectors to make the
//		individual go sideways.
		velocity = new Vector2D(0.0,velocity.y);
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	/**
	 * Get the speed of the sprite.
	 * @return the speed of the sprite.
	 */
	public double getSpeed() {
		return velocity.length();
	}
}