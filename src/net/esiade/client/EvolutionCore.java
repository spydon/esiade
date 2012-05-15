package net.esiade.client;

import com.google.gwt.user.client.Random;

import net.esiade.client.sprite.Individual;

public class EvolutionCore {
	public static int WIDTH = 20, HEIGHT = 20;
	private int mRate, cRate;
	
	public EvolutionCore(int width, int height, int mRate, int cRate){
		EvolutionCore.WIDTH = width;
		EvolutionCore.HEIGHT = height;
		this.mRate = mRate;
		this.cRate = cRate;
	}
	
	public static void setDimensions(int width, int height) {
		EvolutionCore.WIDTH = width;
		EvolutionCore.HEIGHT = height;
	}

	public static enum CType {
		ONEPOINT, TWOPOINT, UNIFORM, ARITHMETIC
	}	
	
	public static Vector2D[][] getRandomGenome() {
		Vector2D[][] genome = new Vector2D[WIDTH][HEIGHT];
		for (int x = 0;x < WIDTH;x++)
			for (int y = 0; y < HEIGHT;y++)
				genome[x][y] = new Vector2D();
		return genome;
	}

	
	public static Individual Crossover(Individual I1, Individual I2, CType type) {
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

	private static Individual OnePointCrossover(Individual I1, Individual I2){
		Individual newI = I2;
		int randomPoint = Random.nextInt(WIDTH*HEIGHT);
		for(int y = 0;y <= HEIGHT; y++)
			for (int x = 0; x <= WIDTH; x++)
				if (randomPoint>0){
					newI.genome[x][y] = I1.genome[x][y];
					randomPoint--;
				} else {	
					break;
				}
		return newI;
	}
	
	private static Individual TwoPointCrossover(Individual I1, Individual I2){
		Individual newI;
		newI = I2;
		int randomPoint1 = Random.nextInt(WIDTH*HEIGHT);
		int randomPoint2 = Random.nextInt(WIDTH*HEIGHT-randomPoint1);
		randomPoint2 +=randomPoint1;
		
		for(int y = randomPoint1 / WIDTH;y <= HEIGHT; y++)
			for (int x = randomPoint1 % WIDTH; x <= WIDTH; x++)
				if (randomPoint1 != randomPoint2){
					newI.genome[x][y] = I1.genome[x][y];
					randomPoint2--;
				} else {	
					break;
				}
		return newI;
	}
	
	private static Individual UniformCrossover(Individual I1, Individual I2){
		Individual newI;
		newI = I2;
		for(int y = 0;y<=HEIGHT;y++)
			for(int x=0;x<=WIDTH;x++)
				if (Random.nextDouble() > 0.5)
					newI.genome[x][y] = I1.genome[x][y];

		return newI;

	}
	
	private static Individual ArithmeticCrossover(Individual I1, Individual I2){
		return null;
	}
	
	
	private static Individual Mutation(Individual I){
		for (int y = 0;y < HEIGHT;y++)
			for (int x=0;x< WIDTH;x++)
				break;
	return null;
	}
}
