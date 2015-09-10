package kaleidoscope;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * The View "observes" and displays what is going on in the Model. In this
 * program, the Model contains only a single polygon.
 * 
 * @author David Matuszek
 * @author Alifia Haidry
 * @author Yuetong Chi
 */
public class PolyListView extends JPanel implements Observer {

	/** This is what we will be observing. */
	ArrayList<ModelPoly> modelList;
	ArrayList<Color> colorList;
	Color myBackground; // creates new color for background
	Color color = new Color(250, 250, 250); // color of polygons

	/**
	 * Constructor.
	 * @param modelBall
	 * The Model whose working is to be displayed.
	 */
	PolyListView() {
		this.modelList = new ArrayList<ModelPoly>();
		this.colorList = new ArrayList<Color>();
		for (int i = 0; i < 10; i++) {
			colorList.add(setInitColor());
		}
	}

	/**
	 * Adds new model to the model list.
	 */
	public void addModelPoly(ModelPoly m) {
		modelList.add(m);
	}

	/**
	 * Sets random colors of polygons.
	 */
	public Color setInitColor() {
		int R = (int) (Math.random() * 256);
		int G = (int) (Math.random() * 256);
		int B = (int) (Math.random() * 256);
		color = new Color(R, G, B);
		return color;
	}

	/**
	 * Displays what is going on in the Model. Note: This method should NEVER be
	 * called directly; call repaint() instead.
	 * @param g
	 *  The Graphics on which to paint things.
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */

	@Override
	public void paint(Graphics g) {
		int xpoints[];
		int ypoints[];
		int reflections[][];
		g.setColor(myBackground);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < this.modelList.size(); i++) {
			g.setColor(colorList.get(i));
			reflections = (this.modelList.get(i)).getReflections();
			for (int j = 0; j < 8; j++) {
				xpoints = (this.modelList.get(i))
						.getXCoordinates(reflections[j][0]);
				ypoints = (this.modelList.get(i))
						.getYCoordinates(reflections[j][1]);
				g.fillPolygon(xpoints, ypoints, this.modelList.get(i)
						.getNPoints());
			}

		}
	}

	/**
	 * When an Observer notifies Observers (and this View is an Observer), this
	 * is the method that gets called.
	 * @param obs
	 * Holds a reference to the object being observed.
	 * @param arg
	 * If notifyObservers is given a parameter, it is received here.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object arg) {
		repaint();
	}

}
