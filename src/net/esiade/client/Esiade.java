package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.EvolutionCore.CType;
import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Esiade implements EntryPoint {
    private ArrayList<Individual> individuals = new ArrayList<Individual>(0);
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(0);
    private ArrayList<Food> foods = new ArrayList<Food>(0);
    private CollisionManager collisionManager;
    private TextBox tb_ind, tb_food, tb_obs, tb_mutation, tb_crossover;
    private ListBox lb_crossover;
    private Button run;
	public final static int WIDTH = 500, HEIGHT = 500;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		makeSettingsUI();
	}
	
	private void run() {
		new EvolutionCore(WIDTH, HEIGHT, getNumber(tb_mutation.getText()), getNumber(tb_crossover.getText()), getType(lb_crossover.getValue(lb_crossover.getSelectedIndex())));
		EvolutionCore.setDimensions(WIDTH/20, HEIGHT/20);
		int numInd = (int)getNumber(tb_ind.getText());
		int numFood = (int)getNumber(tb_food.getText());
		int numObs = (int)getNumber(tb_obs.getText());
		RootPanel.get("settingsholder").clear();
		
		for(int x = 0; x < numInd; x++)
			individuals.add(new Individual(new Vector2D(WIDTH,HEIGHT), new Vector2D(), EvolutionCore.getRandomGenome()));
		
		for(int x = 0; x < numFood; x++)
			foods.add(new Food(new Vector2D(WIDTH,HEIGHT)));

		for(int x = 0; x < numObs; x++)
			obstacles.add(new Obstacle(new Vector2D(WIDTH,HEIGHT)));

		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods);
		new GraphicsCore(WIDTH, HEIGHT, individuals, obstacles, foods, collisionManager);
	}
	
	private double getNumber(String parse) {
		double num = 0;
		try {
			num = Double.parseDouble(parse);
		} catch(NumberFormatException nfe) {
			//Do nothing
		}
		return num;
	}
	
	private CType getType(String parse) {
		if(parse.equals("One-point")) {
			return CType.ONEPOINT;
		} else if(parse.equals("Two-point")) {
			return CType.TWOPOINT;
		} else {
			return CType.UNIFORM;
		}
	}
	
	private void makeSettingsUI() {
		RootPanel.get("settingsholder").add(new Label("# of Individuals: "));
		tb_ind = new TextBox();
		RootPanel.get("settingsholder").add(tb_ind);
		
		RootPanel.get("settingsholder").add(new Label("# of Food: "));
		tb_food = new TextBox();
		RootPanel.get("settingsholder").add(tb_food);
		
		RootPanel.get("settingsholder").add(new Label("# of Obstacles: "));
		tb_obs = new TextBox();
		RootPanel.get("settingsholder").add(tb_obs);
		
		RootPanel.get("settingsholder").add(new Label("Mutationrate(0.0-1.0): "));
		tb_mutation = new TextBox();
		RootPanel.get("settingsholder").add(tb_mutation);
		
		RootPanel.get("settingsholder").add(new Label("Crossover rate(0.0-1.0): "));
		tb_crossover = new TextBox();
		RootPanel.get("settingsholder").add(tb_crossover);
		
		RootPanel.get("settingsholder").add(new Label("Crossover type: "));
		lb_crossover = new ListBox();
		lb_crossover.addItem("One-point");
		lb_crossover.addItem("Two-point");
		lb_crossover.addItem("Uniform");
		RootPanel.get("settingsholder").add(lb_crossover);
		
		run = new Button("Run simulation!!!");
		run.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				run();
			}
		});
		RootPanel.get("settingsholder").add(new HTML("<br/>"));
		RootPanel.get("settingsholder").add(run);
		
		//RootPanel.get("settingsholder").add();
	}
}
