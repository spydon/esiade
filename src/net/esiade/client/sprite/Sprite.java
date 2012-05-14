package net.esiade.client.sprite;

import net.esiade.client.Vector2D;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Sprite {
	  public ImageElement image;
	  public Vector2D position;
	  
	  public Sprite(String imageLocation, Vector2D position) {
		  ImageElement image = 
				  (ImageElement)new Image(imageLocation).getElement().cast();
		  this.image = image;
		  this.position = position;
	  }
	  
	  public double getX() {
		  return position.getX();
	  }
	  
	  public double getY() {
		  return position.getY();
	  }
	  
	  public int getWidth() {
		  return image.getWidth();
	  }
	  
	  public int getHeight() {
		  return image.getHeight();
	  }
	  
	  public int getRadius() {
		  return getWidth()/2;
	  }
	  
	  public void draw(Context2d context) {
		  context.save();
		  context.drawImage(image, getX(), getY());
		  context.restore();
	  }
}
