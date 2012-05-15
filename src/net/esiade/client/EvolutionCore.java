package net.esiade.client;

import com.google.gwt.user.client.Random;

import net.esiade.client.sprite.Individual;



public class EvolutionCore {
	private int width, height;
	private int mRate, cRate;
	
	public EvolutionCore(int width, int height, int mRate, int cRate){
		this.width = width;
		this.height = height;
		this.mRate = mRate;
		this.cRate = cRate;
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
		Individual newI;
		newI = I2;
		int randomPoint1 = Random.nextInt(width*height);
		int randomPoint2 = Random.nextInt(width*height-randomPoint1);
		randomPoint2 +=randomPoint1;
		
		for(int y = randomPoint1 / width;y <= height; y++)
			for (int x = randomPoint1 % width; x <= width; x++)
				if (randomPoint1 != randomPoint2){
					newI.genome[x][y] = I1.genome[x][y];
					randomPoint2--;
				} else {	
					break;
				}
		return newI;
	}
	
	private Individual UniformCrossover(Individual I1, Individual I2){
		Individual newI;
		newI = I2;
		for(int y = 0;y<=height;y++)
			for(int x=0;x<=width;x++)
				if (Random.nextDouble() > 0.5)
					newI.genome[x][y] = I1.genome[x][y];

		return newI;
	}
	
	private Individual ArithmeticCrossover(Individual I1, Individual I2){
		return null;
	}
	
	
	private Individual Mutation(Individual I){
		for (int y = 0;y < height;y++)
			for (int y=0;y< width;y++)

	}
	
	
}