package net.esiade.client;

import java.util.ArrayList;
import java.util.HashMap;

import net.esiade.client.DynamicsCore.EType;
import net.esiade.client.EvolutionCore.CType;
import net.esiade.client.EvolutionCore.IType;
import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
    				tb_maptrust, tb_starve, tb_foodspawn, tb_foodstart, tb_selfrepr, 
    				tb_foodrepr, tb_scalespeed, tb_poisson, tb_lambda, tb_elitism,
    				tb_chance, tb_epochlength, tb_numImmigrants, tb_envepochs;
    private ListBox lb_reprtype, lb_crossover, lb_environment, lb_itype;
    private CheckBox cb_visiblematrix, cb_trighypermut;
    private Button run;
    private EvolutionCore evolutionCore;
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
		tb_foodstart = (TextBox)state.get("tb_foodstart");
		tb_selfrepr = (TextBox)state.get("tb_selfrepr");
		tb_foodrepr = (TextBox)state.get("tb_foodrepr");
		tb_scalespeed = (TextBox)state.get("tb_scalespeed");
		tb_poisson = (TextBox)state.get("tb_poisson");
		tb_lambda = (TextBox)state.get("tb_lambda");
		tb_elitism = (TextBox)state.get("tb_elitism");
		tb_chance = (TextBox)state.get("tb_chance");
		tb_epochlength = (TextBox)state.get("tb_epochlength");
		tb_numImmigrants = (TextBox)state.get("tb_numImmigrants");
		tb_envepochs = (TextBox)state.get("tb_envepochs");
		lb_itype = (ListBox)state.get("lb_itype");
		lb_reprtype = (ListBox)state.get("lb_reprtype");
		lb_crossover = (ListBox)state.get("lb_crossover");
		lb_environment = (ListBox)state.get("lb_environment");
		cb_visiblematrix = (CheckBox)state.get("cb_visiblematrix");
		cb_trighypermut = (CheckBox)state.get("cb_trighypermut");
		drawSettingsUI();
	}
	
	public void onModuleLoad() {
		makeSettingsUI();
	}
	
	private void run() {
		int matrixWidth = (int)getNumber(tb_matrix_x.getText());
		int matrixHeight = (int)getNumber(tb_matrix_y.getText());
		evolutionCore = new EvolutionCore(matrixWidth, matrixHeight, getNumber(tb_mutation.getText()), getNumber(tb_crossover.getText()), 
									getCType(lb_crossover.getValue(lb_crossover.getSelectedIndex())), (int)getNumber(tb_elitism.getText()), getNumber(tb_chance.getText()),
									(int)getNumber(tb_numImmigrants.getText()), getIType(lb_itype.getValue(lb_itype.getSelectedIndex())));
		Esiade.WIDTH = (int)getNumber(tb_width.getText());
		Esiade.HEIGHT = (int)getNumber(tb_height.getText());
		Food.spawnRate = getNumber(tb_foodspawn.getText());
		
		int numInd = (int)getNumber(tb_ind.getText());
		int numFood = (int)getNumber(tb_food.getText());
		int numObs = (int)getNumber(tb_obs.getText());
		int numPoisson = (int)getNumber(tb_poisson.getText());
		int lambda = (int)getNumber(tb_lambda.getText());
		int epochLength = (int)getNumber(tb_epochlength.getText());
		int changeEpoch = (int)getNumber(tb_envepochs.getText());
		boolean isEpochBased = lb_reprtype.getItemText(lb_reprtype.getSelectedIndex()).equals("Epoch based");
		boolean visibleMatrix = cb_visiblematrix.getValue();
		boolean trigHyperMut = cb_trighypermut.getValue();
		double scaleSpeed = getNumber(tb_scalespeed.getText());
		RootPanel.get("settingsholder").clear();
		
		for(int x = 0; x < numPoisson; x++)
			poissons.add(new Poisson(lambda, new Vector2D(WIDTH, HEIGHT)));
			
		double jumpLength = scaleSpeed*Math.sqrt((WIDTH/matrixWidth)*(HEIGHT/matrixHeight));
		
		for(int x = 0; x < numInd; x++)
			individuals.add(new Individual(new Vector2D(0.0,0.0), new Vector2D(jumpLength), evolutionCore.getRandomGenome(jumpLength), 
											(int)getNumber(tb_velocitycheck.getText()), getNumber(tb_maptrust.getText()), 
											(int)getNumber(tb_starve.getText()), (int)getNumber(tb_selfrepr.getText()), 
											(int)getNumber(tb_foodrepr.getText()), (int)getNumber(tb_foodstart.getText()),
											jumpLength, 1, evolutionCore));
		
		for(int x = 0; x < numFood; x++)
			foods.add(new Food(poissons.get(Random.nextInt(poissons.size())).getVector()));

		for(int x = 0; x < numObs; x++)
			obstacles.add(new Obstacle(new Vector2D(WIDTH,HEIGHT)));
		
		collisionManager = new CollisionManager(WIDTH, HEIGHT, individuals, obstacles, foods, evolutionCore);
		DynamicsCore dynamicsCore = new DynamicsCore(getEType(lb_environment.getItemText(lb_environment.getSelectedIndex())));
		saveState();
		new UpdateCore(individuals, obstacles, foods, poissons, collisionManager, dynamicsCore, evolutionCore, changeEpoch, epochLength, isEpochBased, visibleMatrix, trigHyperMut, state);
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
		state.put("tb_foodstart", tb_foodstart);
		state.put("tb_selfrepr", tb_selfrepr); 
		state.put("tb_foodrepr", tb_foodrepr); 
		state.put("tb_scalespeed", tb_scalespeed); 
		state.put("tb_poisson", tb_poisson); 
		state.put("tb_lambda", tb_lambda);
		state.put("tb_elitism", tb_elitism);
		state.put("tb_chance", tb_chance);
		state.put("tb_epochlength", tb_epochlength);
		state.put("tb_numImmigrants", tb_numImmigrants);
		state.put("tb_envepochs", tb_envepochs);
		state.put("lb_reprtype", lb_reprtype); 
		state.put("lb_crossover", lb_crossover); 
		state.put("lb_environment", lb_environment);
		state.put("lb_itype", lb_itype);
		state.put("cb_visiblematrix", cb_visiblematrix);
		state.put("cb_trighypermut", cb_trighypermut);
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
	
	private CType getCType(String parse) {
		if(parse.equals("One-point"))
			return CType.ONEPOINT;
		else if(parse.equals("Two-point"))
			return CType.TWOPOINT;
		else 
			return CType.UNIFORM;
	}
	
	private EType getEType(String parse) {
		if(parse.equals("Small changes"))
			return EType.SMALL;
		else if(parse.equals("Big changes"))
			return EType.BIG;
		else if(parse.equals("Seasonal"))
			return EType.SEASONAL;
		else
			return EType.STATIC;
	}
	
	private IType getIType(String parse) {
		if(parse.equals("Random")) 
			return IType.RANDOM;
		if(parse.equals("Elite")) 
			return IType.ELITE;
		else
			return IType.NONE;
	}
	
	private void makeSettingsUI() {
		tb_ind = new TextBox();
		tb_ind.setText("15");
		
		tb_food = new TextBox();
		tb_food.setText("0");
		
		tb_poisson = new TextBox();
		tb_poisson.setText("1");
		tb_lambda = new TextBox();
		tb_lambda.setText("3");
		
		tb_foodspawn = new TextBox();
		tb_foodspawn.setText("0.03");
		
		tb_foodstart = new TextBox();
		tb_foodstart.setText("9");
		
		tb_foodrepr = new TextBox();
		tb_foodrepr.setText("10");
		
		tb_selfrepr = new TextBox();
		tb_selfrepr.setText("100");
		
		tb_obs = new TextBox();
		tb_obs.setText("0");
		tb_obs.setReadOnly(true);
		tb_obs.setEnabled(false);
		
		tb_mutation = new TextBox();
		tb_mutation.setText("0.0");
		
		tb_crossover = new TextBox();
		tb_crossover.setText("0.8");
		
		tb_maptrust = new TextBox();
		tb_maptrust.setText("1.0");
		
		tb_velocitycheck = new TextBox();
		tb_velocitycheck.setText("10");
		
		tb_scalespeed = new TextBox();
		tb_scalespeed.setText("0.2");
		
		tb_starve = new TextBox();
		tb_starve.setText("200");
		
		tb_elitism = new TextBox();
		tb_elitism.setText("1");

		tb_chance = new TextBox();
		tb_chance.setText("0.5");

		tb_epochlength = new TextBox();
		tb_epochlength.setText("200");
		
		tb_numImmigrants = new TextBox();
		tb_numImmigrants.setText("0");
		
		tb_envepochs = new TextBox();
		tb_envepochs.setText("1000");

		tb_width = new TextBox();
		tb_width.setText("500");
		tb_width.setMaxLength(4);
		tb_width.setWidth("30px");
		tb_height = new TextBox();
		tb_height.setText("500");
		tb_height.setMaxLength(4);
		tb_height.setWidth("30px");
		
		tb_matrix_x = new TextBox();
		tb_matrix_x.setText("5");
		tb_matrix_x.setMaxLength(4);
		tb_matrix_x.setWidth("30px");
		tb_matrix_y = new TextBox();
		tb_matrix_y.setText("5");
		tb_matrix_y.setMaxLength(4);
		tb_matrix_y.setWidth("30px");
		
		lb_reprtype = new ListBox();
		lb_reprtype.addItem("Epoch based");
		lb_reprtype.addItem("Collision based");
		lb_reprtype.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String selected = lb_reprtype.getItemText(lb_reprtype.getSelectedIndex());
				if(selected.equals("Epoch based")) {
    				tb_starve.setEnabled(false);
    				tb_selfrepr.setEnabled(false); 
    				tb_foodrepr.setEnabled(false);
    				tb_foodstart.setEnabled(false);
    				cb_trighypermut.setEnabled(true);
    				tb_foodstart.setText("0");
    				tb_elitism.setEnabled(true);
    				tb_chance.setEnabled(true);
    				tb_epochlength.setEnabled(true);
				} else if(selected.equals("Collision based")) {
    				tb_starve.setEnabled(true);
    				tb_selfrepr.setEnabled(true); 
    				tb_foodrepr.setEnabled(true);
    				tb_elitism.setEnabled(false);
    				tb_chance.setEnabled(false);
    				tb_epochlength.setEnabled(false); 
    				cb_trighypermut.setEnabled(false);
    				tb_foodstart.setText("9");
				}
			}
		});
		tb_starve.setEnabled(false);
		tb_selfrepr.setEnabled(false); 
		tb_foodrepr.setEnabled(false);
		tb_foodstart.setEnabled(false);
		tb_foodstart.setText("0");
		
		lb_itype = new ListBox();
		lb_itype.addItem("Random");
		lb_itype.addItem("Elite");
		lb_itype.addItem("None");
		
		lb_crossover = new ListBox();
		lb_crossover.addItem("One-point");
		lb_crossover.addItem("Two-point");
		lb_crossover.addItem("Uniform");
		
		lb_environment = new ListBox();
		lb_environment.addItem("Small changes");
		lb_environment.addItem("Big changes");
		lb_environment.addItem("Seasonal");
		lb_environment.addItem("Static");

		cb_visiblematrix = new CheckBox();
		cb_trighypermut = new CheckBox();
		
		drawSettingsUI();
		
	}
	
	private void drawSettingsUI() {
		RootPanel.get("settingsholder").add(new Label("Reproduction type: "));
		RootPanel.get("settingsholder").add(lb_reprtype);
		
		RootPanel.get("settingsholder").add(new Label("# of start Individuals: "));
		RootPanel.get("settingsholder").add(tb_ind);

		RootPanel.get("settingsholder").add(new Label("# of start food: "));
		RootPanel.get("settingsholder").add(tb_food);
		
		RootPanel.get("settingsholder").add(new Label("# of start food in individuals: "));
		RootPanel.get("settingsholder").add(tb_foodstart);

		RootPanel.get("settingsholder").add(new Label("# of Poisson food distributions: "));
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
		
		RootPanel.get("settingsholder").add(new Label("Number elite individuals: "));
		RootPanel.get("settingsholder").add(tb_elitism);
		
		RootPanel.get("settingsholder").add(new Label("Chance of doing crossover with individual x*numberInList: "));
		RootPanel.get("settingsholder").add(tb_chance);
		
		RootPanel.get("settingsholder").add(new Label("How many days between reproduction(Epoch): "));
		RootPanel.get("settingsholder").add(tb_epochlength);

		RootPanel.get("settingsholder").add(new Label("Environment size(X,Y): "));
		RootPanel.get("settingsholder").add(tb_width);
		RootPanel.get("settingsholder").add(tb_height);
		
		RootPanel.get("settingsholder").add(new Label("Matrix size(X,Y): "));
		RootPanel.get("settingsholder").add(tb_matrix_x);
		RootPanel.get("settingsholder").add(tb_matrix_y);
		
		RootPanel.get("settingsholder").add(new Label("Number of immigrants per epoch: "));
		RootPanel.get("settingsholder").add(tb_numImmigrants);
		
		RootPanel.get("settingsholder").add(new Label("Type of immigrants: "));
		RootPanel.get("settingsholder").add(lb_itype);

		RootPanel.get("settingsholder").add(new Label("Crossover type: "));
		RootPanel.get("settingsholder").add(lb_crossover);

		RootPanel.get("settingsholder").add(new Label("Environment type: "));
		RootPanel.get("settingsholder").add(lb_environment);
		
		RootPanel.get("settingsholder").add(new Label("Changes every X epoch: "));
		RootPanel.get("settingsholder").add(tb_envepochs);
		
		RootPanel.get("settingsholder").add(new Label("Visible matrix: "));
		RootPanel.get("settingsholder").add(cb_visiblematrix);
		
		RootPanel.get("settingsholder").add(new Label("Triggered hypermutation: "));
		RootPanel.get("settingsholder").add(cb_trighypermut);

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
