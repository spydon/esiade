/**
 * 
 */
package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.MovingSprite;
import net.esiade.client.sprite.Obstacle;
import net.esiade.client.sprite.Sprite;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;


/**
 * @author spydon
 *
 */
public class GraphicsCore {
	private Canvas canvas, canvasBuffer;
	private Context2d context, contextBuffer;
	//private int updateCounter = 0;
	private static final int WIDTH = 400, HEIGHT = 400;
	private static final int REFRESH_RATE = 40;
	private static final CssColor REDRAW_COLOR = CssColor.make("red");
    private ArrayList<Individual> individuals = new ArrayList<Individual>(0);
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(0);
    private ArrayList<Food> foods = new ArrayList<Food>(0);

    //private CollisionManager collisionManager;

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
//		contextBuffer.setFillStyle(REDRAW_COLOR);
//		contextBuffer.fillRect(0, 0, WIDTH, HEIGHT);

		RootPanel.get("canvasholder").add(canvas);
		//Vector2D test = new Vector2D(20,20);
		RootPanel.get().add(new Label("1"));
//		new Individual("http://www.opentk.com/files/ball.png", 
//				new Vector2D(), new Vector2D());
		individuals.add(new Individual(new Vector2D(200,200), new Vector2D(40,40)));
		individuals.add(new Individual(new Vector2D(200, 300), new Vector2D(10, 10)));
		individuals.add(new Individual(new Vector2D(300, 300), new Vector2D(7, 7)));
		
		foods.add(new Food(new Vector2D(100,100)));
//		//collisionManager = new CollisionManager(individuals, obstacles, foods);
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
		for(Individual individual : individuals)
			individual.draw(contextBuffer);
		
		for(Food food : foods)
			food.draw(contextBuffer);
		
	    context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}
}