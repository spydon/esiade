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
    private TextBox tb_ind, tb_food, tb_obs, tb_mutation, tb_crossover,
    				tb_matrix_x, tb_matrix_y, tb_width, tb_height, tb_velocitycheck,
    				tb_maptrust, tb_starve, tb_foodspawn, tb_selfrepr, tb_foodrepr;
    private ListBox lb_crossover;
    private Button run;
	public static int WIDTH = 500, HEIGHT = 500;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		makeSettingsUI();
	}
	
	private void run() {
		new EvolutionCore((int)getNumber(tb_matrix_x.getText()), (int)getNumber(tb_matrix_y.getText()), getNumber(tb_mutation.getText()), getNumber(tb_crossover.getText()), getType(lb_crossover.getValue(lb_crossover.getSelectedIndex())));
		Esiade.WIDTH = (int)getNumber(tb_width.getText());
		Esiade.HEIGHT = (int)getNumber(tb_height.getText());
		Food.spawnRate = getNumber(tb_foodspawn.getText());

		int numInd = (int)getNumber(tb_ind.getText());
		int numFood = (int)getNumber(tb_food.getText());
		int numObs = (int)getNumber(tb_obs.getText());
		RootPanel.get("settingsholder").clear();
		
		for(int x = 0; x < numInd; x++)
			individuals.add(new Individual(new Vector2D(WIDTH,HEIGHT), new Vector2D(), EvolutionCore.getRandomGenome(), 
											(int)getNumber(tb_velocitycheck.getText()), getNumber(tb_maptrust.getText()), 
											(int)getNumber(tb_starve.getText()), (int)getNumber(tb_selfrepr.getText()), 
											(int)getNumber(tb_foodrepr.getText())));
		
		for(int x = 0; x < numFood; x++)
			foods.add(new Food(new Vector2D(WIDTH,HEIGHT)));

		for(int x = 0; x < numObs; x++)
			obstacles.add(new Obstacle(new Vector2D(WIDTH,HEIGHT)));

		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods);
		new GraphicsCore(individuals, obstacles, foods, collisionManager);
	}
	
	private double getNumber(String parse) {
		double num = 0.0;
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
		tb_ind.setText("1");
		RootPanel.get("settingsholder").add(tb_ind);
		
		RootPanel.get("settingsholder").add(new Label("# of Food: "));
		tb_food = new TextBox();
		tb_food.setText("20");
		RootPanel.get("settingsholder").add(tb_food);
		
		RootPanel.get("settingsholder").add(new Label("Food spawnrate(0.0-1.0): "));
		tb_foodspawn = new TextBox();
		tb_foodspawn.setText("0.1");
		RootPanel.get("settingsholder").add(tb_foodspawn);
		
		RootPanel.get("settingsholder").add(new Label("# of food needed for reproduction: "));
		tb_foodrepr = new TextBox();
		tb_foodrepr.setText("10");
		RootPanel.get("settingsholder").add(tb_foodrepr);
		
		RootPanel.get("settingsholder").add(new Label("# of food needed for selfreproduction: "));
		tb_selfrepr = new TextBox();
		tb_selfrepr.setText("100");
		RootPanel.get("settingsholder").add(tb_selfrepr);
		
		RootPanel.get("settingsholder").add(new Label("# of Obstacles: "));
		tb_obs = new TextBox();
		tb_obs.setText("0");
		RootPanel.get("settingsholder").add(tb_obs);
		
		RootPanel.get("settingsholder").add(new Label("Mutationrate(0.0-1.0): "));
		tb_mutation = new TextBox();
		tb_mutation.setText("0.0");
		RootPanel.get("settingsholder").add(tb_mutation);
		
		RootPanel.get("settingsholder").add(new Label("Crossover rate(0.0-1.0): "));
		tb_crossover = new TextBox();
		tb_crossover.setText("0.8");
		RootPanel.get("settingsholder").add(tb_crossover);
		
		RootPanel.get("settingsholder").add(new Label("Maptrust rate(0.0-1.0): "));
		tb_maptrust = new TextBox();
		tb_maptrust.setText("0.9");
		RootPanel.get("settingsholder").add(tb_maptrust);
		
		RootPanel.get("settingsholder").add(new Label("Velocity check(1-10000)(Days): "));
		tb_velocitycheck = new TextBox();
		tb_velocitycheck.setText("10");
		RootPanel.get("settingsholder").add(tb_velocitycheck);
		
		RootPanel.get("settingsholder").add(new Label("Starve rate(1-10000)(Days): "));
		tb_starve = new TextBox();
		tb_starve.setText("200");
		RootPanel.get("settingsholder").add(tb_starve);

		RootPanel.get("settingsholder").add(new Label("Environment size(X,Y): "));
		tb_width = new TextBox();
		tb_width.setText("500");
		tb_width.setMaxLength(4);
		tb_width.setWidth("30px");
		tb_height = new TextBox();
		tb_height.setText("500");
		tb_height.setMaxLength(4);
		tb_height.setWidth("30px");
		RootPanel.get("settingsholder").add(tb_width);
		RootPanel.get("settingsholder").add(tb_height);
		
		RootPanel.get("settingsholder").add(new Label("Matrix size(X,Y): "));
		tb_matrix_x = new TextBox();
		tb_matrix_x.setText("25");
		tb_matrix_x.setMaxLength(4);
		tb_matrix_x.setWidth("30px");
		tb_matrix_y = new TextBox();
		tb_matrix_y.setText("25");
		tb_matrix_y.setMaxLength(4);
		tb_matrix_y.setWidth("30px");
		RootPanel.get("settingsholder").add(tb_matrix_x);
		RootPanel.get("settingsholder").add(tb_matrix_y);
		
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
