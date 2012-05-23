package net.esiade.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.esiade.client.sprite.Individual;

public class StatisticsCore {
	private static DialogBox db;


	/**
	 * This function creates a popup, showing the generation number and the amount of food in an individual.
	 * @param i The individual for which to show statistics
	 */
	public static void individualResult(Individual i) {
		db = new DialogBox();
		//VerticalPanel vp = new VerticalPanel();
		db.setHTML("Generation: " + i.getGeneration() + "</br>Food: " + i.getFood());
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				db.hide();
			}
		});
		//db.add(vp);
		db.add(close);
		db.center();
		db.show();
	}

}
