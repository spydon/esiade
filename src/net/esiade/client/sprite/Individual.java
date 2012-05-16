package net.esiade.client.sprite;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import net.esiade.client.Esiade;
import net.esiade.client.EvolutionCore;
import net.esiade.client.GraphicsCore;
import net.esiade.client.Vector2D;


public class Individual extends MovingSprite {

	private int hungerLevel = 10;
	public Vector2D[][] genome;
	
	public Individual(Vector2D position, Vector2D velocity, Vector2D[][] genome) {
		super("./individual.png", position, velocity);
		this.genome = genome;
		this.velocity = genome[(int)(position.x/Esiade.WIDTH*EvolutionCore.WIDTH)]
							[(int)(position.y/Esiade.HEIGHT*EvolutionCore.HEIGHT)];
	}
	
	public void updatePos() {
//		Update the position to a new position with the vectors, 
//		collisions have been taken care of.
		RootPanel.get().add(new Label("Position: " + position + " BigWH: " + Esiade.WIDTH + " " + Esiade.HEIGHT + " Small: " + EvolutionCore.WIDTH + " " + EvolutionCore.HEIGHT));
		velocity = genome[(int)(position.x/Esiade.WIDTH*EvolutionCore.WIDTH)]
						[(int)(position.y/Esiade.HEIGHT*EvolutionCore.HEIGHT)];
		//RootPanel.get().add(new Label("Velocity after: " + velocity));
		position.add(velocity);
	}

	public void eat() {
		hungerLevel++;
	}
	
	public int getHunger() {
		return hungerLevel;
	}

	public void starve() {
		hungerLevel--;
	}

}