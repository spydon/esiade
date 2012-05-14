package net.esiade.client.sprite;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import net.esiade.client.Vector2D;


public class Individual extends MovingSprite {

	private int hungerLevel = 10;
	public Vector2D[][] genome;
	
	public Individual(Vector2D position, Vector2D velocity, Vector2D[][] genome) {
		super("http://www.opentk.com/files/ball.png", position, velocity);
		this.genome = genome;
		// TODO Auto-generated constructor stub
	}

	public void eat() {
		hungerLevel++;
	}

}