package net.esiade.client.sprite;

import net.esiade.client.Vector2D;


public abstract class MovingSprite extends Sprite{

	protected Vector2D velocity;
	
	public MovingSprite(String imageLocation, Vector2D position, Vector2D velocity) {
		super(imageLocation, position);
		this.velocity = velocity;
		//RootPanel.get().add(dbgMsg);
	}
	
	public void updatePos() {
//		Update the position to a new position with the vectors, 
//		collisions have been taken care of.
		position.add(velocity);
	}
	
	public void verticalCollision() {
//		What should happen to the vectors if there is a
//		horizontal collision? Set the vectors to make the
//		individual go sideways.
		velocity.setY(0.0);
	}
	
	public void horizontalCollision() {
//		What should happen to the vectors if there is a
//		horizontal collision? Set the vectors to make the
//		individual go sideways.
		velocity.setX(0.0);
	}
	
	public double getSpeed() {
		return velocity.length();
	}
}