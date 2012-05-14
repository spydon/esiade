package net.esiade.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import net.esiade.client.sprite.Food;
import net.esiade.client.sprite.Individual;
import net.esiade.client.sprite.Obstacle;
import net.esiade.client.sprite.Sprite;

public class CollisionManager {
	private ArrayList<Individual> individuals;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Food> foods;
	private Label dbgMsg = new Label();
	private final int WIDTH, HEIGHT;
	
	public CollisionManager(int WIDTH, int HEIGHT,
							ArrayList<Individual> individuals, 
							ArrayList<Obstacle> obstacles,
							ArrayList<Food> foods) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.individuals = individuals;
		this.obstacles = obstacles;
		this.foods = foods;
		RootPanel.get().add(dbgMsg);
	}
	
	public void checkCollision() {
		for(Individual i : individuals) {
			double iX = i.getX();
			double iY = i.getY();
			
			if(iX < 0 || iX+i.getWidth() > WIDTH) {
				i.position.setX((int)((iX+i.getWidth())/WIDTH)*(WIDTH-i.getWidth()));
				i.horizontalCollision();
			} else if(iY < 0 || iY+i.getHeight() > HEIGHT) {
				i.position.setY((int)((iY+i.getHeight())/HEIGHT)*(HEIGHT-i.getHeight()));
				i.verticalCollision();
			}
	
			for(Obstacle o : obstacles) {
				if(iX >= o.getX() && iX <= o.getX()+o.getWidth())
					i.verticalCollision();
				if(iY >= o.getY() && iY <= o.getY()+o.getHeight())
					i.horizontalCollision();
			}
			
			for(Food f : foods) {
				if(isCollision(i, f)) {
					i.eat();
					foods.remove(f);
				}
			}
		}
	}
	
	private boolean isCollision(Sprite b1, Sprite b2) {
	    double xd = b1.getX() - b2.getX();
	    double yd = b1.getY() - b2.getY();

	    double sumRadius = b1.getRadius() + b2.getRadius();
	    double sqrRadius = sumRadius * sumRadius;

	    double distSqr = (xd * xd) + (yd * yd);

	    if (distSqr <= sqrRadius)
	        return true;
	    
	    return false;
	}	
}