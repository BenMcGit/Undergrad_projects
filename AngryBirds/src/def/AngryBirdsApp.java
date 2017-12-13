package def;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

public class AngryBirdsApp extends ThreeButtons {
	ThreeButtonFrame win;

	Container cover;
	Oval path;

	// bird
	Image aBird;

	// path and bird
	int x_new;
	int y_new;
	double time;

	public AngryBirdsApp() {
		create_window();

		// Bird image and location
		aBird = new Image(0, 0, 50, 50, "bigAngrySmaller.png");
		win.add(aBird);

		// platform image and location
		Rectangle platform;
		platform = new Rectangle(0, 70, 50, 10);
		win.add(platform);

		// pigs location (arguments are found in the object movePigs)
		movePigs(0, 175 - 50, "piggy_01_small.png");
		movePigs(175, 350 - 50, "piggy_02_small.png");
		movePigs(350, 525 - 50, "piggy_03_small.png");
		movePigs(525, 700 - 50, "piggy_04_small.png");

		// calls my container method
		resetPoints();
		win.add(cover);
		win.repaint();
	}

	private void movePigs(double min, double max, String images) {

		// return a value between 0 and 1 and scale it
		double random_number = Math.random();
		double my_random_number = min + (random_number * (max - min));

		int convert = (int) my_random_number;

		Image piggy = new Image(0, 0, 50, 50, images); // create pigs
		piggy.setLocation(convert, 400);
		win.add(piggy);
	}

	// PLEASE DO NOT EDIT THIS CODE..
	private void create_window() {
		win = new ThreeButtonFrame("Angry Birds", 800, 600);
		win.setLayout(null);
		win.setVisible(true);
		win.getContentPane().setBackground(Color.white);
		win.setDefaultCloseOperation(3);
	}

	public void pathAction() {
		int velocity = win.getVelocity();

		double Time_step = win.getTimeStep();
		time = time + Time_step; // creates the new point on my "path"

		x_new = (int) (x_new + velocity * time); // equation to get the x value
		y_new = (int) (y_new + 4.9 * time * time); // equation to get the y
		path = new Oval(x_new, y_new, 10, 10); // create oval

		// adds the points on my path
		cover.add(path);
		cover.repaint();
		win.repaint();
	}

	public void birdAction() {
		aBird.setLocation(x_new, y_new);
		win.repaint();
	}

	public Container resetPoints() {
		cover = new Container();
		cover.setBounds(0, 0, 800, 600);
		cover.repaint();
		return cover;
	}

	public void clearAction() {
		x_new = 0;
		y_new = 0;
		aBird.setLocation(0, 0);
		time = 0.0;

		win.remove(cover);
		resetPoints();
		win.add(cover);
		win.repaint();
	}
}
