package net.esiade.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class DynamicsCore {
	public EType type;
	private ArrayList<Poisson> poissons;
	private Poisson[][] seasons = new Poisson[4][100];
	private int season = 0;

	public DynamicsCore(EType eType) {
		this.type = eType;
	}
	
	public static enum EType {
		SMALL, BIG, SEASONAL, STATIC
	}
	
	public void changeEnvironment(ArrayList<Poisson> poissons) {
		this.poissons = poissons;
		if (type == EType.BIG)
			bigChange();
		else if (type == EType.SMALL)
			smallChange();
		else if (type == EType.SEASONAL)
			seasonal();
	}

	private void seasonal() {
		if(season < 4)
			for(int x = 0; x<poissons.size(); x++)
				seasons[season][x] = new Poisson(poissons.get(0).getLambda(), new Vector2D(Esiade.WIDTH, Esiade.HEIGHT));

		for(int x = 0; x<poissons.size(); x++)
			poissons.set(x, seasons[season%4][x]);
		season++;			
	}

	private void smallChange() {
		for(Poisson p : poissons) {
			Vector2D change = p.getPosition();
			change.add(new Vector2D(20));
			p.setPosition(change);
		}
	}

	private void bigChange() {
		for(Poisson p : poissons)
			p.setPosition(new Vector2D(Esiade.WIDTH, Esiade.HEIGHT));
	}
}
