package net.esiade.client;

import java.util.ArrayList;

import net.esiade.client.EvolutionCore.CType;
import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.core.client.EntryPoint;
import java.util.HashMap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Esiade implements EntryPoint {
    private ArrayList<Individual> individuals = new ArrayList<Individual>(0);
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>(0);
    private ArrayList<Food> foods = new ArrayList<Food>(0);
    private ArrayList<Poisson> poissons = new ArrayList<Poisson>(0);
    private CollisionManager collisionManager;
    private HashMap<String, Widget> state = new HashMap<String, Widget>(0);
    private TextBox tb_ind, tb_food, tb_obs, tb_mutation, tb_crossover,
    				tb_matrix_x, tb_matrix_y, tb_width, tb_height, tb_velocitycheck,
    				tb_maptrust, tb_starve, tb_foodspawn, tb_selfrepr, tb_foodrepr,
    				tb_scalespeed, tb_poisson, tb_lambda;
    private ListBox lb_crossover, lb_environment;
    private Button run;
	public static int WIDTH = 500, HEIGHT = 500;

	/**
	 * This is the entry point method.
	 */
	public Esiade() {
		//Do nothing
	}
	
	public Esiade(HashMap<String, Widget> state) {
		tb_ind = (TextBox)state.get("tb_ind");
		tb_food = (TextBox)state.get("tb_food");
		tb_obs = (TextBox)state.get("tb_obs"); 
		tb_mutation = (TextBox)state.get("tb_mutation");
		tb_crossover = (TextBox)state.get("tb_crossover");
		tb_matrix_x = (TextBox)state.get("tb_matrix_x");
		tb_matrix_y = (TextBox)state.get("tb_matrix_y");
		tb_width = (TextBox)state.get("tb_width"); 
		tb_height = (TextBox)state.get("tb_height"); 
		tb_velocitycheck = (TextBox)state.get("tb_velocitycheck");
		tb_maptrust = (TextBox)state.get("tb_maptrust");
		tb_starve = (TextBox)state.get("tb_starve");
		tb_foodspawn = (TextBox)state.get("tb_foodspawn");
		tb_selfrepr = (TextBox)state.get("tb_selfrepr");
		tb_foodrepr = (TextBox)state.get("tb_foodrepr");
		tb_scalespeed = (TextBox)state.get("tb_scalespeed");
		tb_poisson = (TextBox)state.get("tb_poisson");
		tb_lambda = (TextBox)state.get("tb_lambda");
		lb_crossover = (ListBox)state.get("lb_crossover");
		lb_environment = (ListBox)state.get("lb_environment");
		drawSettingsUI();
//		makeSettingsUI();
	}
	
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
		int numPoisson = (int)getNumber(tb_poisson.getText());
		int lambda = (int)getNumber(tb_lambda.getText());
		double scaleSpeed = getNumber(tb_scalespeed.getText());
		RootPanel.get("settingsholder").clear();
		
		for(int x = 0; x < numPoisson; x++)
			poissons.add(new Poisson(lambda, new Vector2D(WIDTH, HEIGHT)));
			
		double jumpLength = scaleSpeed*Math.sqrt((WIDTH/EvolutionCore.WIDTH)*(HEIGHT/EvolutionCore.HEIGHT));
		
		for(int x = 0; x < numInd; x++)
			individuals.add(new Individual(new Vector2D(WIDTH,HEIGHT), new Vector2D(jumpLength), EvolutionCore.getRandomGenome(jumpLength), 
											(int)getNumber(tb_velocitycheck.getText()), getNumber(tb_maptrust.getText()), 
											(int)getNumber(tb_starve.getText()), (int)getNumber(tb_selfrepr.getText()), 
											(int)getNumber(tb_foodrepr.getText()), jumpLength, 1));
		
		for(int x = 0; x < numFood; x++)
			foods.add(new Food(poissons.get(Random.nextInt(poissons.size())).getVector()));

		for(int x = 0; x < numObs; x++)
			obstacles.add(new Obstacle(new Vector2D(WIDTH,HEIGHT)));
		
		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods);
		saveState();
		new GraphicsCore(individuals, obstacles, foods, poissons, collisionManager, state);
	}
	
	private void saveState() {
		state.put("tb_ind", tb_ind);
		state.put("tb_food", tb_food);
		state.put("tb_obs", tb_obs);
		state.put("tb_mutation", tb_mutation); 
		state.put("tb_crossover", tb_crossover); 
		state.put("tb_matrix_x", tb_matrix_x); 
		state.put("tb_matrix_y", tb_matrix_y); 
		state.put("tb_width", tb_width); 
		state.put("tb_height", tb_height); 
		state.put("tb_velocitycheck", tb_velocitycheck); 
		state.put("tb_maptrust", tb_maptrust); 
		state.put("tb_starve", tb_starve); 
		state.put("tb_foodspawn", tb_foodspawn); 
		state.put("tb_selfrepr", tb_selfrepr); 
		state.put("tb_foodrepr", tb_foodrepr); 
		state.put("tb_scalespeed", tb_scalespeed); 
		state.put("tb_poisson", tb_poisson); 
		state.put("tb_lambda", tb_lambda); 
		state.put("lb_crossover", lb_crossover); 
		state.put("lb_environment", lb_environment); 
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
		tb_ind = new TextBox();
		tb_ind.setText("15");
		
		tb_food = new TextBox();
		tb_food.setText("20");
		
		tb_poisson = new TextBox();
		tb_poisson.setText("3");
		
		tb_lambda = new TextBox();
		tb_lambda.setText("40");
		
		tb_foodspawn = new TextBox();
		tb_foodspawn.setText("0.005");
		
		tb_foodrepr = new TextBox();
		tb_foodrepr.setText("10");
		
		tb_selfrepr = new TextBox();
		tb_selfrepr.setText("100");
		
		tb_obs = new TextBox();
		tb_obs.setText("0");
		tb_obs.setReadOnly(true);
		tb_obs.setEnabled(false);
		
		tb_mutation = new TextBox();
		tb_mutation.setText("0.01");
		
		tb_crossover = new TextBox();
		tb_crossover.setText("0.8");
		
		tb_maptrust = new TextBox();
		tb_maptrust.setText("0.9");
		
		tb_velocitycheck = new TextBox();
		tb_velocitycheck.setText("10");
		
		tb_scalespeed = new TextBox();
		tb_scalespeed.setText("0.7");
		
		tb_starve = new TextBox();
		tb_starve.setText("200");

		tb_width = new TextBox();
		tb_width.setText("500");
		tb_width.setMaxLength(4);
		tb_width.setWidth("30px");
		tb_height = new TextBox();
		tb_height.setText("500");
		tb_height.setMaxLength(4);
		tb_height.setWidth("30px");
		
		tb_matrix_x = new TextBox();
		tb_matrix_x.setText("25");
		tb_matrix_x.setMaxLength(4);
		tb_matrix_x.setWidth("30px");
		tb_matrix_y = new TextBox();
		tb_matrix_y.setText("25");
		tb_matrix_y.setMaxLength(4);
		tb_matrix_y.setWidth("30px");
		
		lb_crossover = new ListBox();
		lb_crossover.addItem("One-point");
		lb_crossover.addItem("Two-point");
		lb_crossover.addItem("Uniform");
		
		lb_environment = new ListBox();
		lb_environment.addItem("Small changes");
		lb_environment.addItem("Big changes");
		lb_environment.addItem("Seasonal");
		drawSettingsUI();
		
	}
	
	private void drawSettingsUI() {
		RootPanel.get("settingsholder").add(new Label("# of Individuals: "));
		RootPanel.get("settingsholder").add(tb_ind);

		RootPanel.get("settingsholder").add(new Label("# of Food: "));
		RootPanel.get("settingsholder").add(tb_food);

		RootPanel.get("settingsholder").add(new Label("# of Poisson distributions: "));
		RootPanel.get("settingsholder").add(tb_poisson);

		RootPanel.get("settingsholder").add(new Label("Lambda for Poisson: "));
		RootPanel.get("settingsholder").add(tb_lambda);
		
		RootPanel.get("settingsholder").add(new Label("Food spawnrate(0.0-1.0): "));
		RootPanel.get("settingsholder").add(tb_foodspawn);

		RootPanel.get("settingsholder").add(new Label("# of food needed for reproduction: "));
		RootPanel.get("settingsholder").add(tb_foodrepr);

		RootPanel.get("settingsholder").add(new Label("# of food needed for selfreproduction: "));
		RootPanel.get("settingsholder").add(tb_selfrepr);

		RootPanel.get("settingsholder").add(new Label("# of Obstacles: (Disabled)"));
		RootPanel.get("settingsholder").add(tb_obs);

		RootPanel.get("settingsholder").add(new Label("Mutationrate(0.0-1.0): "));
		RootPanel.get("settingsholder").add(tb_mutation);

		RootPanel.get("settingsholder").add(new Label("Crossover rate(0.0-1.0): "));
		RootPanel.get("settingsholder").add(tb_crossover);

		RootPanel.get("settingsholder").add(new Label("Maptrust rate(0.0-1.0): "));
		RootPanel.get("settingsholder").add(tb_maptrust);

		RootPanel.get("settingsholder").add(new Label("Velocity check(1-10000)(Days): "));
		RootPanel.get("settingsholder").add(tb_velocitycheck);

		RootPanel.get("settingsholder").add(new Label("Scale maxspeed x*(length of 1 square): "));
		RootPanel.get("settingsholder").add(tb_scalespeed);

		RootPanel.get("settingsholder").add(new Label("Starve rate(1-10000)(Days): "));
		RootPanel.get("settingsholder").add(tb_starve);

		RootPanel.get("settingsholder").add(new Label("Environment size(X,Y): "));
		RootPanel.get("settingsholder").add(tb_width);
		RootPanel.get("settingsholder").add(tb_height);
		
		RootPanel.get("settingsholder").add(new Label("Matrix size(X,Y): "));
		RootPanel.get("settingsholder").add(tb_matrix_x);
		RootPanel.get("settingsholder").add(tb_matrix_y);
		
		RootPanel.get("settingsholder").add(new Label("Crossover type: "));
		RootPanel.get("settingsholder").add(lb_crossover);

		RootPanel.get("settingsholder").add(new Label("Environment type: "));
		RootPanel.get("settingsholder").add(lb_environment);

		run = new Button("Run simulation!!!");
		run.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				run();
			}
		});
		RootPanel.get("settingsholder").add(new HTML("<br/>"));
		RootPanel.get("settingsholder").add(run);
	}
}
