package kaleidoscope;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the Model class for a Kaleidoscope. It is an Observable,
 * which means that it can notifyObservers that something in the
 * model has changed, and they should take appropriate actions.
 * 
 * @author David Matuszek
 * @author Alifia Haidry
 * @author Yuetong Chi
 */

public class ModelPoly extends Observable {
	public final static int Poly_Size = 30;
	private int xPosition = 60;
	private int yPosition = 60;
	private int xLimit, yLimit;
	double xDelta;
	double yDelta;
	int npoints;
	private Timer timer;

	/**
	 * Sets the "walls" that the polygons should bounce off from.
	 * 
	 * @param xLimit The position (in pixels) of the wall on the right.
	 * @param yLimit The position (in pixels) of the floor.
	 */
	public void setLimits(int xLimit, int yLimit) {
		this.xLimit = xLimit - Poly_Size;
		this.yLimit = yLimit - Poly_Size;
		xPosition = Math.min(xPosition, xLimit);
		yPosition = Math.min(yPosition, yLimit);
	}

	/**
	 * Sets number of sides of polygon.
	 * @param n
	 */
	public void setNPoints(int n) {
		npoints = n;
	}

	/**
	 *  Gets number of sides of polygon.
	 * @return
	 */
	public int getNPoints() {
		return npoints;
	}

	/**
	 *  Sets displacement in x direction of polygon.
	 * @param x
	 */
	public void setxDelta(double x) {
		xDelta = x;
	}

	/**
	 *  Sets displacement in y direction of polygon.
	 * @param y
	 */
	public void setyDelta(double y) {
		yDelta = y;
	}

	/**
	 * @return The polygon X position.
	 */
	public int getX() {
		return xPosition;
	}

	/**
	 * 
	 * @return The width of the screen.
	 */
	public int getXLimit() {
		return xLimit;
	}

	/**
	 * 
	 * @return The height of the screen.
	 */
	public int getYLimit() {
		return yLimit;
	}

	/**
	 * @return The polygon Y position.
	 */
	public int getY() {
		return yPosition;
	}

	/**
	 * Tells the polygon to start moving. This is done by starting a Timer
	 * that periodically executes a TimerTask. The TimerTask then tells
	 * the polygon to make one "step."
	 */
	public void start() {
		timer = new Timer(true);
		timer.schedule(new Strobe(), 0, 80);
	}

	/**
	 * Tells the polygon to stop where it is.
	 */
	public void pause() {
		timer.cancel();
	}

	/**
	 * Tells the polygon to advance one step in the direction that it is moving.
	 * If it hits a wall, its direction of movement changes.
	 */
	public void makeOneStep() {
		// Do the work
		xPosition += xDelta;
		if (xPosition < 0 || xPosition >= xLimit) {
			xDelta = -xDelta;
			xPosition += xDelta;
		}
		yPosition += yDelta;
		if (yPosition < 0 || yPosition >= yLimit) {
			yDelta = -yDelta;
			yPosition += yDelta;
		}
		// Notify observers
		setChanged();
		notifyObservers();
	}

	/**
	 * @return array of x-coordinates of polygon.
	 */
	public int [] getXCoordinates(int x) {
		int xpoints[] = new int[npoints];

		for (int i = 0; i < npoints; i++) {
			xpoints[i] = (int) (x + this.Poly_Size
					* Math.cos(2 * i * Math.PI / this.npoints));
		}

		return xpoints;
	}

	/**
	 * @return array of y-coordinates of polygon.
	 */
	public int [] getYCoordinates(int y) {
		int ypoints[] = new int[npoints];

		for (int i = 0; i < npoints; i++) {
			ypoints[i] = (int) (y + this.Poly_Size
					* Math.sin(2 * i * Math.PI / this.npoints));
		}

		return ypoints;
	}

	/**
	 * @return coordinates for polygon reflections.
	 */
	public int [][] getReflections() {
		int [][] reflections = new int[8][2];

		reflections[0][0] = getX();
		reflections[0][1] = getY();

		reflections[1][0] = getX();
		reflections[1][1] = getYLimit() - getY() + Poly_Size;

		reflections[2][0] = getY();
		reflections[2][1] = getX();

		reflections[3][0] = getY();
		reflections[3][1] = getYLimit() - getX() + Poly_Size;

		reflections[4][0] = getXLimit() - getY() + Poly_Size;
		reflections[4][1] = getX();

		reflections[5][0] = getXLimit() - getX() + Poly_Size;
		reflections[5][1] = getY();

		reflections[6][0] = getXLimit() - getY() + Poly_Size;
		reflections[6][1] = getYLimit() - getX() + Poly_Size;

		reflections[7][0] = getXLimit() - getX() + Poly_Size;
		reflections[7][1] = getYLimit()- getY() + Poly_Size;

		return reflections;
	}

	/**
	 * Tells the model to advance one "step."
	 */
	private class Strobe extends TimerTask {
		@Override
		public void run() {
			makeOneStep();
		}
	}
}
