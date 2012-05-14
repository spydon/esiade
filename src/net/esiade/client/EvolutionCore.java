package net.esiade.client;

import com.google.gwt.user.client.Random;

import net.esiade.client.sprite.Individual;



public class EvolutionCore {
	private int width, height;
		
	public EvolutionCore(int width, int height){
		this.width = width;
		this.height = height;
	}	

	public enum CType {
		ONEPOINT, TWOPOINT, UNIFORM, ARITHMETIC
	}	
	
	public Vector2D[][] getRandomGenome() {
		Vector2D[][] genome = new Vector2D[width][height];
		for (int x = 0;x < width;x++)
			for (int y = 0; y < height;y++)
				genome[x][y] = new Vector2D();
		return genome;
	}

	
	public Individual Crossover(Individual I1, Individual I2, CType type) {
		Individual I = null;
		if (type == CType.ONEPOINT)
			I = OnePointCrossover(I1, I2);
		else if (type == CType.TWOPOINT)
			I = TwoPointCrossover(I1, I2);
		else if (type == CType.UNIFORM)
			I = UniformCrossover(I1, I2);
		else if (type == CType.ARITHMETIC)	
			I = ArithmeticCrossover(I1, I2);
		
		return I;
	}

	private Individual OnePointCrossover(Individual I1, Individual I2){
		Individual newI;
		newI = I2;
		int randomPoint = Random.nextInt(width*height);
		for(int y = 0;y <= height; y++)
			for (int x = 0; x <= width; x++)
				if (randomPoint>0){
					newI.genome[x][y] = I1.genome[x][y];
					randomPoint--;
				} else {	
					break;
				}
		return newI;
	}
	
	private Individual TwoPointCrossover(Individual I1, Individual I2){
		return null;
	}
	
	private Individual UniformCrossover(Individual I1, Individual I2){
		return null;
	}
	
	private Individual ArithmeticCrossover(Individual I1, Individual I2){
		return null;
	}
	
	
	
	
}