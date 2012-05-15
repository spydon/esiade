package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Esiade implements EntryPoint {
    private ArrayList<Individual> individuals = new ArrayList<Individual>(0);
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(0);
    private ArrayList<Food> foods = new ArrayList<Food>(0);
    private CollisionManager collisionManager;
	public final static int WIDTH = 800, HEIGHT = 800;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EvolutionCore.setDimensions(WIDTH/20, HEIGHT/20);
		individuals.add(new Individual(new Vector2D(200,200), new Vector2D(), EvolutionCore.getRandomGenome()));
		individuals.add(new Individual(new Vector2D(200, 300), new Vector2D(), EvolutionCore.getRandomGenome()));
		individuals.add(new Individual(new Vector2D(300, 300), new Vector2D(), EvolutionCore.getRandomGenome()));
		
		foods.add(new Food(new Vector2D(100,100)));
		foods.add(new Food(new Vector2D(101,200)));
		foods.add(new Food(new Vector2D(100,300)));
		foods.add(new Food(new Vector2D(150,160)));
		foods.add(new Food(new Vector2D(500,600)));
		foods.add(new Food(new Vector2D(200,400)));
		foods.add(new Food(new Vector2D(120,600)));
		foods.add(new Food(new Vector2D(50,10)));
		foods.add(new Food(new Vector2D(10,10)));
		foods.add(new Food(new Vector2D(200,100)));
		foods.add(new Food(new Vector2D(360,100)));
		foods.add(new Food(new Vector2D(340,180)));
		foods.add(new Food(new Vector2D(80,190)));
		foods.add(new Food(new Vector2D(100,280)));
		foods.add(new Food(new Vector2D(20,430)));

		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods);		
		new GraphicsCore(WIDTH, HEIGHT, individuals, obstacles, foods, collisionManager);

	}
}
