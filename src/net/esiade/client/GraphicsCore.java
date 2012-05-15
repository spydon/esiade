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
	public static int WIDTH, HEIGHT;
	private final int REFRESH_RATE = 40;
	private final CssColor REDRAW_COLOR = CssColor.make("red");
    private ArrayList<Individual> individuals;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Food> foods;
    private CollisionManager collisionManager;

	public GraphicsCore(int WIDTH, int HEIGHT,
						ArrayList<Individual> individuals,
						ArrayList<Obstacle> obstacles,
						ArrayList<Food> foods,
						CollisionManager collisionManager) {
		GraphicsCore.WIDTH = WIDTH;
		GraphicsCore.HEIGHT = HEIGHT;
		this.individuals = individuals;
		this.obstacles = obstacles;
		this.foods = foods;
		this.collisionManager = collisionManager;
		
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
		
		context = canvas.getContext2d();
		context.setFillStyle(REDRAW_COLOR);
		context.fillRect(0, 0, WIDTH, HEIGHT);
		contextBuffer = canvasBuffer.getContext2d();

		RootPanel.get("canvasholder").add(canvas);
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
		contextBuffer.setFillStyle(CssColor.make("GREEN"));
		contextBuffer.fillRect(0, 0, WIDTH, HEIGHT);
		collisionManager.checkCollision();
		
		for(Individual individual : individuals) {
			individual.updatePos();
			individual.draw(contextBuffer);	
		}
		
		for(Food food : foods)
			food.draw(contextBuffer);
		
		for(Obstacle o : obstacles)
			o.draw(contextBuffer);
		
	    context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}
}