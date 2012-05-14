package net.esiade.client.sprite;

import net.esiade.client.Vector2D;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class MovingSprite extends Sprite{

	private Label dbgMsg = new Label("I get here");
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
		RootPanel.get().add(new Label("YESSS?!"));

		velocity.setY(0.0);
	}
	
	public void horizontalCollision() {
//		What should happen to the vectors if there is a
//		horizontal collision? Set the vectors to make the
//		individual go sideways.
		RootPanel.get().add(new Label("NOOO?!"));

		velocity.setX(0.0);
	}
	
	public double getSpeed() {
		return velocity.length();
	}
}