package net.esiade.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.esiade.client.sprite.Individual;

public class StatisticsCore {
	
	private static int[] foodsEaten;
	
	public static void allIndividualsResult(ArrayList<Individual> individuals) {
		final DialogBox db = new DialogBox();
		String text = "All individuals: </br>";
		for(Individual i : individuals) 
			text = text.concat("Generation: " + i.getGeneration() + " Food: " + i.getFood() + " Velocity: " + i.getVelocity() + "</br>");
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				db.hide();
			}
		});
		db.setHTML(text);
		db.setAutoHideEnabled(true);
		db.add(close);
		db.center();
	}


	/**
	 * This function creates a popup, showing the generation number and the amount of food in an individual.
	 * @param i The individual for which to show statistics
	 */
	public static void randomIndividualResult(ArrayList<Individual> individuals) {
		final DialogBox db = new DialogBox();
		Individual i = individuals.get(Random.nextInt(individuals.size()));
		db.setHTML("Generation: " + i.getGeneration() + "</br>Food: " + i.getFood());
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				db.hide();
			}
		});
		db.setAutoHideEnabled(true);
		db.add(close);
		db.center();
	}
	
	public static int foodEaten(ArrayList<Individual> individuals) {
		int food = 0;
		for(Individual i : individuals)
			food=+i.getFood();
		return food;
	}
	
	public static void drawGraph() {
		
	}

}
