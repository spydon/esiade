package net.esiade.client.sprite;

import com.google.gwt.user.client.Random;

import net.esiade.client.Esiade;
import net.esiade.client.EvolutionCore;
import net.esiade.client.Vector2D;


public class Individual extends MovingSprite {

	private int food = 10;
	private int veloCheck;
	private int counter = 0;
	private double mapTrust;
	private int starveRate;
	private int selfReproductionLimit;
	private int reproductionLimit;
	public Vector2D[][] genome;
	
	public Individual(Vector2D position, Vector2D velocity, Vector2D[][] genome, 
					int veloCheck, double mapTrust,	int starveRate, 
					int selfReproductionLimit, int reproductionLimit) {
		super("./individual.png", position, velocity);
		this.velocity = genome[(int)(position.x/Esiade.WIDTH*EvolutionCore.WIDTH)]
							[(int)(position.y/Esiade.HEIGHT*EvolutionCore.HEIGHT)];
		this.veloCheck = veloCheck;
		this.mapTrust = mapTrust;
		this.starveRate = starveRate;
		this.selfReproductionLimit = selfReproductionLimit;
		this.reproductionLimit = reproductionLimit;
		this.genome = genome;
	}
	
	public void updatePos() {
//		Update the position to a new position with the vectors, 
//		collisions have been taken care of.
		if(counter==veloCheck) {
			if(Random.nextDouble() <= mapTrust)
				velocity = genome[(int)(position.x/Esiade.WIDTH*EvolutionCore.WIDTH)]
								[(int)(position.y/Esiade.HEIGHT*EvolutionCore.HEIGHT)];
			else
				velocity = new Vector2D();
			counter = 0;
//		} else if(position.x == 0 && position.y == y){
//			velocity = new Vector2D();
//			counter = 0;
		} else {
			counter++;
		}
		position.add(velocity);
	}

	public boolean eat() {
		food++;
		if(food >= selfReproductionLimit)
			return true;
		return false;
	}
	
	public Individual clone() {
		return new Individual(position, velocity, genome, veloCheck, mapTrust, starveRate, selfReproductionLimit, reproductionLimit);
	}
	
	public int getFood() {
		return food;
	}
	
	public int getReproductionLimit() {
		return reproductionLimit;
	}

	public void starve() {
		food--;
	}

	public int getStarveRate() {
		return starveRate;
	}

}