package net.esiade.client.sprite;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import net.esiade.client.Vector2D;

public class Individual extends MovingSprite {

	private int hungerLevel = 10;
	
	public Individual(Vector2D position, Vector2D velocity) {
		super("http://www.opentk.com/files/ball.png", position, velocity);
		RootPanel.get().add(new Label("3"));
		// TODO Auto-generated constructor stub
	}

	public void eat() {
		hungerLevel++;
	}

}