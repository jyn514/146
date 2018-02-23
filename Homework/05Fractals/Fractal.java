import java.applet.Applet;
import java.awt.*;

public class Fractal extends Applet {
	private static final long serialVersionUID = 641543232321L; // JApplet implements Serializable
	private Image image;

	public void init() {
		super.init();
		int width = getWidth(), height = getHeight();
		image = createImage(width, height + 1); // don't cut off at bottom of screen
		Graphics drawArea = image.getGraphics();
		drawArea.setColor(Color.BLACK);
		sierpinski(width / 2, height, height, drawArea);
	}

	public void paint(Graphics g) {
		Rectangle r = getBounds();
		g.drawImage(image.getScaledInstance(r.width, r.height, Image.SCALE_DEFAULT), 0, 0, null);
	}

	/**
	 * @param x x-coordinate of bottom point
	 * @param y y-coordinate of bottom point
	 * @param side length of side
	 * @param graphics sheet to draw on
	 */
	private static void drawUpsideDownTriangle(int x, int y, int side, Graphics graphics) {
		int height = heightOfTriangle(side);
		int[] xPoints = {
				x,
				x + side / 2,
				x - side / 2,
				x
		};
		int[] yPoints = {
			y, // original
			y - height, // bottom two
			y - height,
			y, // original again
		};
		graphics.drawPolyline(xPoints, yPoints, 4); // 4th point is original because java is EVIL
	}

	private static void sierpinski(int x, int y, int previousSide, Graphics graphics) {
		int side = previousSide / 2;
		drawUpsideDownTriangle(x, y, side, graphics);
		if (side > 1) {
			sierpinski(x - side/2, y, side, graphics); // bottom-left
			sierpinski(x, y - heightOfTriangle(side), side, graphics); // top
			sierpinski(x + side / 2, y, side, graphics); // bottom-right
		}
	}

	private static int heightOfTriangle(int side) {
		return (int) (Math.sqrt(5) * side / 2); // by pythagorean theorem; draw it out if you're not convinced
	}
}
