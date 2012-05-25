package net.esiade.client.sprite;

import net.esiade.client.Vector2D;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public abstract class Sprite {
	  public ImageElement image;
	  public Vector2D position;
	  
	  /**
	 * @param imageLocation An address to the picture representing the sprite.
	 * @param position The initial position of the sprite.
	 */
	public Sprite(String imageLocation, Vector2D position) {
		  ImageElement image = 
				  (ImageElement)new Image(imageLocation).getElement().cast();
		  this.image = image;
		  this.position = position;
	  }
	
	public void setImage(String imageLocation) {
		  this.image = (ImageElement)new Image(imageLocation).getElement().cast();
	}	  

	/**
	 * @return The x-coordinate of the sprite
	 */
	public double getX() {
		  return position.getX();
	  }
	  
	/**
	 * @return The y-coordinate of the sprite
	 */
	  public double getY() {
		  return position.getY();
	  }
	  
	  /**
	 * @return The width of the sprite
	 */
	public int getWidth() {
		  return image.getWidth();
	  }
	  
	  /**
	 * @return The width of the sprite
	 */
	  public int getHeight() {
		  return image.getHeight();
	  }
	  
	  /**
	 * @return The width of the sprite
	 */
	  public int getRadius() {
		  return getWidth()/2;
	  }
	  
	  /**
	 * @param context This thingy draws the picture, given a context.
	 */
	public void draw(Context2d context) {
		  context.save();
		  context.drawImage(image, getX(), getY());
		  context.restore();
	  }
}
