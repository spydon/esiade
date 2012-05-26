package net.esiade.client;

import com.google.gwt.user.client.Random;

public class Poisson {
	private int lambda;
	private Vector2D position;
	
	/**
	 * @param lambda Lambda determines the variance of the distribution.
	 * @param position The expected value of the distribution
	 */
	public Poisson(int lambda, Vector2D position){
		this.lambda = lambda;
		this.position = position;
	}
	
	/**
	 * @return Get a poisson-distributed number.
	 */
	public int getNumber() {
		int k=0;
		double p=1;
		double L = Math.pow(Math.E, -lambda);
		while (p > L){
		         k += 1;
		         p = p*Random.nextDouble();
		}
		return k;
	}
	
	/**
	 * @return A vector to a position around the poisson distribution, with 20*(standard deviation).
	 */
	public Vector2D getVector(){
		Vector2D v = new Vector2D(position.x + 20*(getNumber()-lambda), position.y + 20*(getNumber()-lambda)); 
		while(v.x < 0 || v.x > Esiade.WIDTH-20 || v.y < 0 || v.y > Esiade.HEIGHT-20)
			v = new Vector2D(position.x + 20*(getNumber()-lambda), position.y + 20*(getNumber()-lambda));
		return v;
	}

	/**
	 * @param The position of the distribution is moved from its current position with this vector.
	 */
	public void changePosition(Vector2D move){
		position.add(move);
	}
	
	/**
	 * @param position The position of the distribution is moved to the point newPosition.
	 */
	public void setPosition(Vector2D position){
		this.position = position;
	}

	/**
	 * @param newLambda Change the lambda of the distribution.
	 */
	public void setLambda(int newLambda){
		lambda = newLambda;
	}
	
	/**
	 * @return Get the lambda-value of the distribution.
	 */
	public int getLambda(){
		return lambda;
	}
	
	/**
	 * @return Get the middle position of the distribution.
	 */
	public Vector2D getPosition() {
		return position;
	}
}
