package kaleidoscope;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kaleidoscope.PolyListView;

/**
 * The Controller sets up the GUI and handles all the controls (buttons, menu
 * items, etc.)
 * 
 * @author David Matuszek
 * @author Alifia Haidry
 * @author Yuetong Chi
 */
public class PolyListController extends JFrame {
	JPanel buttonPanel = new JPanel();
	JButton runButton = new JButton("Run");
	JButton stopButton = new JButton("Stop");
	JButton colorButton = new JButton("Change Background");
	JButton speedButton = new JButton("Increase Speed");
	JButton speedButton2 = new JButton("Reduce Speed");
	ModelPoly m, m1, m2, m3, m4, m5, m6, m7, m8, m9;
	PolyListView p;

	Timer timer;

	/**
	 * Runs the Kaleidoscope program.
	 * 
	 * @param args
	 *Ignored.
	 */
	public static void main(String[] args) {
		PolyListController c = new PolyListController();
		c.init();
		c.display();
	}

	/**
	 * Sets up communication between the components.
	 */
	private void init() {
		p = new PolyListView();
		m = new ModelPoly();
		this.setParameters(m, 4, 4, 5);
		p.modelList.add(m);
		
		m1 = new ModelPoly();
		this.setParameters(m1, 6, 3, 4);
		p.modelList.add(m1);
		
		m2 = new ModelPoly();
		this.setParameters(m2, 8, 7, 5);
		p.modelList.add(m2);
		
		m3 = new ModelPoly();
		this.setParameters(m3, 10, 5, 6);
		p.modelList.add(m3);
		
		m4 = new ModelPoly();
		this.setParameters(m4, 4, 3, 5);
		p.modelList.add(m4);
		
		m5 = new ModelPoly();
		this.setParameters(m5, 4, 9, 8);
		p.modelList.add(m5);
		
		m6 = new ModelPoly();
		this.setParameters(m6, 20, 4, 3);
		p.modelList.add(m6);
		
		m7 = new ModelPoly();
		this.setParameters(m7, 6, 6, 5);
		p.modelList.add(m7);
		
		m8 = new ModelPoly();
		this.setParameters(m8, 6, 8, 5);
		p.modelList.add(m8);
		
		m9 = new ModelPoly();
		this.setParameters(m9, 6, 2, 3);
		p.modelList.add(m9);
		
		// The model needs to give permission to be observed
		m.addObserver(p); 
		m1.addObserver(p);
		m2.addObserver(p);
		m3.addObserver(p);
		m4.addObserver(p);
		m5.addObserver(p);
		m6.addObserver(p);
		m7.addObserver(p);
		m8.addObserver(p);
		m9.addObserver(p);

	}

	/**
	 * Sets up parameters like # of sides and displacement for different
	 * polygons.
	 */
	private void setParameters(ModelPoly m, int n, double x, double y) {
		m.setNPoints(n);
		m.setxDelta(x);
		m.setyDelta(y);
	}

	/**
	 * Displays the GUI.
	 */
	private void display() {
		layOutComponents();
		attachListenersToComponents();
		setBounds(280, 50, 700, 650);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Arranges the various components in the GUI.
	 */
	private void layOutComponents() {
		setLayout(new BorderLayout());
		this.add(BorderLayout.SOUTH, buttonPanel);
		buttonPanel.add(runButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(colorButton);
		buttonPanel.add(speedButton);
		buttonPanel.add(speedButton2);
		stopButton.setEnabled(false);
		this.add(BorderLayout.CENTER, p);
	}

	/**
	 * Attaches listeners to the components, and schedules a Timer.
	 */
	private void attachListenersToComponents() {

		// The Run button tells the Model to start
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				for (int i = 0; i < p.modelList.size(); i++) {
					p.modelList.get(i).start();
				}
			}
		});

		// The Stop button tells the Model to pause
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				for (int i = 0; i < p.modelList.size(); i++) {
					p.modelList.get(i).pause();
				}
			}
		});

		// The Color button changes the background color.
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int R = (int) (Math.random() * 256);
				int G = (int) (Math.random() * 256);
				int B = (int) (Math.random() * 256);
				p.myBackground = new Color(R, G, B);
			}
		});

		// The Speed button increases the speed of polygons.
		speedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < p.modelList.size(); i++) {
					p.modelList.get(i).xDelta = (p.modelList.get(i).xDelta * 1.5);
					p.modelList.get(i).yDelta = (p.modelList.get(i).yDelta * 1.5);
				}

			}
		});
		
		// The Speed button reduces the speed of polygons.
				speedButton2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						for (int i = 0; i < p.modelList.size(); i++) {
							p.modelList.get(i).xDelta = (p.modelList.get(i).xDelta * 0.5);
							p.modelList.get(i).yDelta = (p.modelList.get(i).yDelta * 0.5);
						}

					}
				});

		// When the window is resized, the Model is given the new limits
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				for (int i = 0; i < p.modelList.size(); i++) {
					p.modelList.get(i).setLimits(p.getWidth(), p.getHeight());
				}
			}
		});
	}
}
