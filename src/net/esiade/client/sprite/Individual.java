package net.esiade.client.sprite;

import net.esiade.client.EvolutionCore;
import net.esiade.client.GraphicsCore;
import net.esiade.client.Vector2D;


public class Individual extends MovingSprite {

	private int hungerLevel = 10;
	public Vector2D[][] genome;
	
	public Individual(Vector2D position, Vector2D velocity, Vector2D[][] genome) {
		super("/home/spydon/tmp/test.png", position, velocity);
		this.genome = genome;
		// TODO Auto-generated constructor stub
	}

	public void eat() {
		hungerLevel++;
	}
	
	public int getHunger() {
		return hungerLevel;
	}
	
	public Vector2D getVelocity() {
		return genome[((int)position.x/GraphicsCore.WIDTH)*EvolutionCore.WIDTH][((int)position.y/GraphicsCore.HEIGHT)*EvolutionCore.HEIGHT];
	}

}