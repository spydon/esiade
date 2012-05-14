/**
 * 
 */
package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;


/**
 * @author spydon
 *
 */
public class GraphicsCore {
	private Canvas canvas, canvasBuffer;
	private Context2d context, contextBuffer;
	public static final int WIDTH = 800, HEIGHT = 800;
	private final int REFRESH_RATE = 40;
	private final CssColor REDRAW_COLOR = CssColor.make("red");
    private ArrayList<Individual> individuals = new ArrayList<Individual>(0);
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(0);
    private ArrayList<Food> foods = new ArrayList<Food>(0);
    private CollisionManager collisionManager;

	public GraphicsCore() {
		canvas = Canvas.createIfSupported();
		canvas.setWidth(WIDTH + "px");
		canvas.setHeight(HEIGHT + "px");
		canvas.setCoordinateSpaceWidth(WIDTH);
		canvas.setCoordinateSpaceHeight(HEIGHT);
		canvas.setVisible(true);
		
		canvasBuffer = Canvas.createIfSupported();
		canvasBuffer.setWidth(WIDTH + "px");
		canvasBuffer.setHeight(HEIGHT + "px");
		canvasBuffer.setCoordinateSpaceWidth(WIDTH);
		canvasBuffer.setCoordinateSpaceHeight(HEIGHT);
		//canvasBuffer.setVisible(false);
		
		context = canvas.getContext2d();
		context.setFillStyle(REDRAW_COLOR);
		context.fillRect(0, 0, WIDTH, HEIGHT);
		contextBuffer = canvasBuffer.getContext2d();

		RootPanel.get("canvasholder").add(canvas);
//		new Individual("http://www.opentk.com/files/ball.png", 
//				new Vector2D(), new Vector2D());
		EvolutionCore.setDimensions(WIDTH/20, HEIGHT/20);
		individuals.add(new Individual(new Vector2D(200,200), new Vector2D(-4,-2), EvolutionCore.getRandomGenome()));
		individuals.add(new Individual(new Vector2D(200, 300), new Vector2D(3, 3), EvolutionCore.getRandomGenome()));
		individuals.add(new Individual(new Vector2D(300, 300), new Vector2D(-2, 0), EvolutionCore.getRandomGenome()));
		
		foods.add(new Food(new Vector2D(100,100)));
		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods);
		//doUpdate();
		//context.drawImage((ImageElement)new Image("http://www.opentk.com/files/ball.png").getElement().cast(), 200, 200);
		RootPanel.get().add(new Label("2"));
		onModuleLoad();

	}
	
	public void onModuleLoad() {
	    final Timer timer = new Timer() {
	        @Override
	        public void run() {
	          doUpdate();
	        }
	      };
	      timer.scheduleRepeating(REFRESH_RATE);
	}
	
	private void doUpdate() {
		//if(updateCounter == 0)
			//collisionManager.checkCollision();
		//updateCounter = (updateCounter+1)%10;
		contextBuffer.setFillStyle(CssColor.make("GREEN"));
		contextBuffer.fillRect(0, 0, WIDTH, HEIGHT);
		collisionManager.checkCollision();
		
		for(Individual individual : individuals) {
			individual.updatePos();
			individual.draw(contextBuffer);	
		}
		
		for(Food food : foods)
			food.draw(contextBuffer);
		
	    context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}
}