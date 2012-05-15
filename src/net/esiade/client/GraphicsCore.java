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
	private final int REFRESH_RATE = 20;
	private int day = 0;
	private final CssColor REDRAW_COLOR = CssColor.make("red");
    private ArrayList<Individual> individuals;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Food> foods;
    private CollisionManager collisionManager;
    private Label l_day, l_ind, l_food, l_obs;

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
		
		l_day = new Label("Day: " + day);
		l_ind = new Label("Alive individuals: " + individuals.size());
		l_food = new Label("Food in environment: " + foods.size());
		l_obs = new Label("Obstacles in environment: " + obstacles.size());
		
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
		
		RootPanel.get("statisticsholder").add(l_day);
		RootPanel.get("statisticsholder").add(l_ind);
		RootPanel.get("statisticsholder").add(l_food);
		RootPanel.get("statisticsholder").add(l_obs);

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
		day++;
		contextBuffer.setFillStyle(CssColor.make("GREEN"));
		contextBuffer.fillRect(0, 0, WIDTH, HEIGHT);
		collisionManager.checkCollision();
		
		for(Individual i : individuals) {
			i.updatePos();
			i.draw(contextBuffer);
		}
		
		for(Food f : foods)
			f.draw(contextBuffer);
		
		for(Obstacle o : obstacles)
			o.draw(contextBuffer);
		
		if(day%10==0)
			for(Individual i : individuals) {
				i.starve();
				if(i.getHunger() <= 0)
					individuals.remove(i);
			}
		
		updateStatistics();
	    context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}
	
	private void updateStatistics() {
		l_day.setText("Day: " + day);
		l_ind.setText("Alive individuals: " + individuals.size());
		l_food.setText("Food in environment: " + foods.size());
		l_obs.setText("Obstacles in environment: " + obstacles.size());
	}
}