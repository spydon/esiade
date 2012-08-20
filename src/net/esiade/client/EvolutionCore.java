package net.esiade.client;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import net.esiade.client.sprite.Individual;

/**
 * @author Lukas Klingsbo
 * 
 */
public class EvolutionCore {
	public int matrixWidth, matrixHeight, elitism, numImmigrants;
	private double mRate, cRate, chance;
	private CType type;
	private IType iType;
	private ArrayList<Individual> historicElites = new ArrayList<Individual>(0);

	/**
	 * @param matrixWidth
	 *            The width of the map matrix
	 * @param matrixHeight
	 *            The height of the map matrix
	 * @param mRate
	 *            The mutation rate
	 * @param cRate
	 *            The crossover rate
	 */
	public EvolutionCore(int matrixWidth, int matrixHeight, double mRate,
						 double cRate, CType type, int elitism, double chance,
						 int numImmigrants, IType iType) {
		this.matrixWidth = matrixWidth;
		this.matrixHeight = matrixHeight;
		this.mRate = mRate;
		this.cRate = cRate;
		this.type = type;
		this.iType = iType;
		this.elitism = elitism;
		this.chance = chance;
		this.numImmigrants = numImmigrants;
	}

	public static enum CType {
		ONEPOINT, TWOPOINT, UNIFORM
	}

	public static enum IType {
		NONE, ELITE, RANDOM
	}

	/**
	 * @return This function returns a genome with all vectors set at random.
	 */
	public Vector2D[][] getRandomGenome(double jumpLength) {
		Vector2D[][] genome = new Vector2D[matrixWidth][matrixHeight];
		for (int x = 0; x < matrixWidth; x++)
			for (int y = 0; y < matrixHeight; y++)
				genome[x][y] = new Vector2D(jumpLength);
		return genome;
	}

	/**
	 * This function will change the vector with the position (x,y) in the
	 * matrix between I1 and I2
	 * 
	 * @param i1
	 *            The first individual
	 * @param i2
	 *            The second individual
	 * @param x
	 *            The x-coordinate in the matrix
	 * @param y
	 *            The y-coordinate in the matrix
	 * @return
	 * 
	 */
	public void switchVectors(Individual i1, Individual i2, int x, int y) {
		Individual temp = i1.clone();
		i1.setGene(x, y, i2.getGenome()[x][y]);
		i2.setGene(x, y, temp.getGenome()[x][y]);
		//i1.setGenome(null);
//		RootPanel.get().add(
//				new Label("EqualInd t-1: "
//						+ (temp.getGenome() == i1.getGenome())));
//		RootPanel.get().add(
//				new Label("EqualInd t-2: "
//						+ (temp.getGenome() == i2.getGenome())));
	}

	public void collisionCrossover(Individual i1, Individual i2) {
		if (i1.getReproductionLimit() <= i1.getFood()
				&& i2.getReproductionLimit() <= i2.getFood())
			crossover(i1, i2);
	}

	/**
	 * This is a general function for crossover, this function redirects to the
	 * appropriate crossover subfunction. The subfunctions implement common
	 * crossover operations, but applied on vectors instead of bits.
	 * 
	 * @param i1
	 *            The first individual
	 * @param i2
	 *            The second individual
	 */
	public Individual[] crossover(Individual i1, Individual i2) {
		if (Random.nextDouble() < cRate) {
			if (type == CType.ONEPOINT)
				onePointCrossover(i1, i2);
			else if (type == CType.TWOPOINT)
				twoPointCrossover(i1, i2);
			else if (type == CType.UNIFORM)
				uniformCrossover(i1, i2);

			mutation(i1);
			mutation(i2);
			// i1.position = new
			// Vector2D(Esiade.WIDTH-i1.getWidth(),Esiade.HEIGHT-i1.getHeight());
			// i2.position = new
			// Vector2D(Esiade.WIDTH-i2.getWidth(),Esiade.HEIGHT-i2.getHeight());
			i1.resetFood();
			i2.resetFood();
			i1.increaseGen();
			i2.increaseGen();
		}
		Individual[] l = { i1, i2 };
		return l;
	}

	public ArrayList<Individual> epochReproduction(
			ArrayList<Individual> individuals) {
		ArrayList<Individual> elites = new ArrayList<Individual>(0);
		Collections.sort(individuals);

		// Vector2D v = new Vector2D(Esiade.WIDTH-individuals.get(0).getWidth(),
		// Esiade.HEIGHT-individuals.get(0).getHeight());
		Vector2D v = new Vector2D(5, 5);

		for (Individual i : individuals) {
			i.resetFood();
			i.position = v.clone();
		}

		Individual hE = individuals.get(0).clone();
		hE.resetFood();
		historicElites.add(hE);

		for (int x = 0; x < elitism; x++) {
			Individual e = individuals.get(x).clone();
			e.position = new Vector2D(0.0, 0.0);
			elites.add(e);
		}

		ArrayList<Individual> newIndividuals = new ArrayList<Individual>(0);
		int z = 0;
		while (newIndividuals.size() < individuals.size()) {
			Individual i = individuals.get(z);
			z++;
			for (Individual j : individuals)
				if ((!i.equals(j) && Random.nextDouble() < chance)
						|| individuals.get(individuals.size() - 1).equals(j)) {
					Individual i2 = i.clone();
					Individual j2 = j.clone();
					crossover(i2, j2);
					newIndividuals.add(i2);
					newIndividuals.add(j2);
					break;
				}
		}
		if (newIndividuals.size() > individuals.size())
			newIndividuals.remove(newIndividuals.size() - 1);

		for (int x = 0; x < individuals.size(); x++)
			individuals.set(x, individuals.get(x));

		for (int x = 1; x <= numImmigrants; x++) {
			if (iType == IType.RANDOM) {
				Individual immigrant = individuals.get(individuals.size()
						- elites.size() - x);
				immigrant.position = v.clone();
				immigrant.setGenome(getRandomGenome(immigrant.getJumpLength()));
				immigrant.setImage("./immigrant.png");
			} else if (iType == IType.ELITE) {
				Individual e = getRandomElite();
				e.position = v.clone();
				individuals.set(individuals.size() - elites.size() - x, e);
			}
		}

		int x = 1;
		for (Individual e : elites) {
			e.resetFood();
			e.position = v.clone();
			e.setImage("./elite.png");
			individuals.set(individuals.size() - x, e);
			x++;
		}

		return individuals;
	}

	public Individual getRandomElite() {
		int number = Random.nextInt(historicElites.size());
		Individual elite = historicElites.get(number).clone();
		elite.setImage("./immigrant.png");
		return historicElites.get(number).clone();
	}

	public Individual selfReproduction(Individual i) {
		Individual i2 = i.clone();
		i2.increaseGen();
		// i2.position = new Vector2D(WIDTH, HEIGHT);
		i.resetFood();
		i2.resetFood();
		i2.position.add(new Vector2D(10));
		mutation(i2);
		return i2;
	}

	/**
	 * One point crossover. The two individuals will be modified to two new
	 * individuals, in a 1-point crossover fashion.
	 * 
	 * @param I1
	 *            The first individual
	 * @param I2
	 *            The second individual
	 */
	private void onePointCrossover(Individual I1, Individual I2) {
		int randomPoint = Random.nextInt(matrixWidth * matrixHeight);
		for (int y = 0; y < matrixHeight; y++)
			for (int x = 0; x < matrixWidth; x++)
				if (randomPoint > 0) {
					switchVectors(I1, I2, x, y);
					randomPoint--;
				} else {
					break;
				}
	}

	/**
	 * One point crossover. The two individuals will be modified to two new
	 * individuals, in a 2-point crossover fashion.
	 * 
	 * @param I1
	 *            The first individual
	 */
	private void twoPointCrossover(Individual I1, Individual I2) {
		int randomPoint1 = Random.nextInt(matrixWidth * matrixHeight);
		int randomPoint2 = Random.nextInt(matrixWidth * matrixHeight
				- randomPoint1);

		for (int y = randomPoint1 / matrixWidth; y < matrixHeight; y++)
			for (int x = randomPoint1 % matrixWidth; x < matrixWidth; x++)
				if (randomPoint2 >= 0) {
					switchVectors(I1, I2, x, y);
					randomPoint2--;
				} else {
					break;
				}
	}

	/**
	 * One point crossover. The two individuals will be modified to two new
	 * individuals, in a uniform crossover fashion.
	 * 
	 * @param i1
	 *            The first individual
	 * @param i2
	 *            The second individual
	 */
	private void uniformCrossover(Individual i1, Individual i2) {
		Individual temp1 = i1.clone();
		Individual temp2 = i2.clone();
		for (int y = 0; y < matrixHeight; y++)
			for (int x = 0; x < matrixWidth; x++)
				switchVectors(i1, i2, x, y);
		// if (Random.nextDouble() > 0.5)
		// SwitchVectors(i1, i2, x, y);
		// RootPanel.get().add(new Label("Height: " + HEIGHT +
		// " Width: " + WIDTH + " X: " + x + " Y: " + y));
	}

	/**
	 * This function will change a vector to a random new vector, with the
	 * probability set as mutation rate, see mRate.
	 * 
	 * @param I
	 *            The individual to mutate.
	 */
	private void mutation(Individual i) {
		for (int y = 0; y < matrixHeight; y++)
			for (int x = 0; x < matrixWidth; x++)
				if (Random.nextDouble() < mRate)
					i.getGenome()[x][y] = new Vector2D(i.getJumpLength());
	}

	public void setChangeMutation(double change) {
		if (mRate + change < 1 && mRate + change > 0)
			mRate += change;
	}

	public int getMatrixHeight() {
		return matrixHeight;
	}

	public int getMatrixWidth() {
		return matrixWidth;
	}
}
