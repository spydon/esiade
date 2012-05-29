/**
 * 
 */
package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import java.util.HashMap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * @author spydon
 *
 */
public class UpdateCore {
	private Timer timer;
	private Canvas canvas, canvasBuffer;
	private Context2d context, contextBuffer;
	public static int WIDTH, HEIGHT;
	private final int REFRESH_RATE = 20;
	private int day = 0;
	private double fitness = 0.0;
	private int epochLength, changeEpoch;
	private int foodPerEpoch = 0;
	private boolean isEpochBased, visibleMatrix, trigHypMut;
	private final CssColor REDRAW_COLOR = CssColor.make("red");
    private ArrayList<Individual> individuals;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Food> foods;
	private ArrayList<Poisson> poissons;
    private CollisionManager collisionManager;
    private DynamicsCore dynamicsCore;
    private Label l_day, l_ind, l_food, l_obs, l_epoch, l_foodPerEpoch, l_fitness;
    private HashMap<String, Widget> state;
    private double[] fitnessHistory = {0.0,0.0,0.0,0.0};
    private double fitnessMed = 0.0;

	public UpdateCore(ArrayList<Individual> individuals,
						ArrayList<Obstacle> obstacles,
						ArrayList<Food> foods,
						ArrayList<Poisson> poissons,
						CollisionManager collisionManager, 
						DynamicsCore dynamicsCore,
						int changeEpoch,
						int epochLength,
						boolean isEpochBased,
						boolean visibleMatrix,
						boolean trigHypMut,
						HashMap<String, Widget> state) {
		UpdateCore.WIDTH = Esiade.WIDTH;
		UpdateCore.HEIGHT = Esiade.HEIGHT;
		this.dynamicsCore = dynamicsCore;
		this.individuals = individuals;
		this.obstacles = obstacles;
		this.foods = foods;
		this.poissons = poissons;
		this.collisionManager = collisionManager;
		this.epochLength = epochLength;
		this.changeEpoch = changeEpoch;
		this.isEpochBased = isEpochBased;
		this.visibleMatrix = visibleMatrix;
		this.trigHypMut = trigHypMut;
		this.state = state;
		
		l_day = new Label("Day: " + day);
		l_fitness = new Label("Fitness " + fitness);
		l_foodPerEpoch = new Label(foodPerEpoch + " foods eaten this epoch");
		l_epoch = new Label("Epoch: " + (int)(day/epochLength));
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
		
		Button randomInd = new Button("Random individual stats");
		randomInd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<Individual> individuals = getIndividuals();
				StatisticsCore.randomIndividualResult(individuals);
			}
		});
		
		Button allInd = new Button("All individuals stats");
		allInd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<Individual> individuals = getIndividuals();
				StatisticsCore.allIndividualsResult(individuals);
			}
		});
		
		Button settings = new Button("<- Settings");
		settings.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				timer.cancel();
				RootPanel.get("canvasholder").clear();
				RootPanel.get("settingsholder").clear();
				RootPanel.get("statisticsholder").clear();
				RootPanel.get().clear();
				new Esiade(getState());
			}
		});

		RootPanel.get("canvasholder").add(canvas);
		
		RootPanel.get("statisticsholder").add(settings);
		RootPanel.get("statisticsholder").add(randomInd);
		RootPanel.get("statisticsholder").add(allInd);
		RootPanel.get("statisticsholder").add(l_day);
		RootPanel.get("statisticsholder").add(l_fitness);
		RootPanel.get("statisticsholder").add(l_foodPerEpoch);
		RootPanel.get("statisticsholder").add(l_epoch);
		RootPanel.get("statisticsholder").add(l_ind);
		RootPanel.get("statisticsholder").add(l_food);
		RootPanel.get("statisticsholder").add(l_obs);

		onModuleLoad();
	}
	
	public HashMap<String, Widget> getState() {
		return state;
	}
	
	public void onModuleLoad() {
	    timer = new Timer() {
	        @Override
	        public void run() {
	          if(individuals.size() == 0)
	        	  this.cancel();
	          doUpdate();
	        }
	      };
	      timer.scheduleRepeating(REFRESH_RATE);
	}
	
	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	
	private void doUpdate() {
		day++;
		contextBuffer.setFillStyle(CssColor.make("BLACK"));
		contextBuffer.fillRect(0, 0, WIDTH, HEIGHT);
		int cellWidth = WIDTH/EvolutionCore.WIDTH;
		int cellHeight = HEIGHT/EvolutionCore.HEIGHT;
		
	    contextBuffer.setLineWidth(1);
	    contextBuffer.setStrokeStyle("RED");
	    if(visibleMatrix) {
			for(int x = cellWidth; x < WIDTH; x+=cellWidth) {
				contextBuffer.beginPath();
				contextBuffer.moveTo(x, 0);
				contextBuffer.lineTo(x, HEIGHT);
				contextBuffer.closePath();
				contextBuffer.stroke();		
			}
			
			for(int y = cellHeight; y < HEIGHT; y+=cellHeight) {
				contextBuffer.beginPath();
				contextBuffer.moveTo(0, y);
				contextBuffer.lineTo(WIDTH, y);
				contextBuffer.closePath();
				contextBuffer.stroke();	
			}
	    }
		
		if(!isEpochBased) {
			collisionManager.checkCollision(false);		
			for(Individual i : individuals) {
				i.updatePos();
				if(day%i.getStarveRate()==0)
					i.starve();
				if(i.getFood() <= 0) {
					if(individuals.size()==1)
						StatisticsCore.randomIndividualResult(individuals);
					individuals.remove(i);
				}
				i.draw(contextBuffer);
			}
		} else {
			collisionManager.checkCollision(true);
			for(Individual i : individuals) {
				i.updatePos();
				i.draw(contextBuffer);
			}
			if(day%epochLength==0) {
				foodPerEpoch = StatisticsCore.foodEaten(individuals);
				double fpe = foodPerEpoch;
				double foodSize = foods.size();
				fitness = fpe/(foodSize+fpe);

				fitnessHistory[(day%epochLength)%4] = fpe/(foodSize+fpe);
				double sum = 0;
				for(int x = 0; x < fitnessHistory.length; x++)
					sum+=fitnessHistory[x];
				double newFitnessMed = sum/fitnessHistory.length;
				
				if(0.8*newFitnessMed <= fitnessMed)
					EvolutionCore.setChangeMutation(0.1);
				else
					EvolutionCore.setChangeMutation(-0.02);
				
				fitnessMed = newFitnessMed;
				individuals = EvolutionCore.EpochReproduction(individuals);
			}
			foods.add(new Food(poissons.get(Random.nextInt(poissons.size())).getVector()));
		}
		
		if(day%changeEpoch == 0 && dynamicsCore.type != DynamicsCore.EType.STATIC)
			dynamicsCore.changeEnvironment(poissons);
		
		if(Random.nextDouble() <= Food.spawnRate)
			foods.add(new Food(poissons.get(Random.nextInt(poissons.size())).getVector()));
		
		for(Food f : foods)
			f.draw(contextBuffer);
		
		for(Obstacle o : obstacles)
			o.draw(contextBuffer);
		
		updateStatistics();
	    context.drawImage(contextBuffer.getCanvas(), 0, 0);
	}
	
	private void updateStatistics() {
		l_day.setText("Day: " + day);
		l_fitness.setText("Fitness: " + fitness);
		l_foodPerEpoch.setText(foodPerEpoch + " foods eaten this epoch");
		l_epoch.setText("Epoch: " + (int)(day/epochLength));
		l_ind.setText("Alive individuals: " + individuals.size());
		l_food.setText("Food in environment: " + foods.size());
		l_obs.setText("Obstacles in environment: " + obstacles.size());
	}
}