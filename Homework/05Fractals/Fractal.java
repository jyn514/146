/*
 * Copyright © 2018 Joshua Nelson
 * Licensed under the GNU General Licence v3.
 * Essentially, you may modify, copy, and distribute this program,
 * but you must preserve this copyright and you must make all changes available to all users of the code.
 * Details available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public final class Fractal extends Applet {
	private static final long serialVersionUID = 614516141432L; // Applet implements serializable
	private Image image;  // would be final if compiler treated init as constructor

	public void init() {
		super.init();
		int width = 1920, height = 1080;
		image = createImage(width, height + 1); // don't cut off at bottom of screen
		sierpinski(width / 2, height, height, image.getGraphics());
	}

	public void paint(final Graphics g) {
		Rectangle r = getBounds();
		// resizable
		g.drawImage(image.getScaledInstance(r.width, r.height, Image.SCALE_DEFAULT), 0, 0, null);
	}

	/**
	 * @param x x-coordinate of bottom point
	 * @param y y-coordinate of bottom point
	 * @param side length of side
	 * @param graphics sheet to draw on
	 */
	private static void drawUpsideDownTriangle(final int x, final int y, final int side, final Graphics graphics) {
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
		graphics.drawPolyline(xPoints, yPoints, xPoints.length); // last point is original because java is EVIL
	}

	private static void sierpinski(final int x, final int y, final int previousSide, final Graphics graphics) {
		int side = previousSide / 2;
		drawUpsideDownTriangle(x, y, side, graphics);
		if (side > 1) {
			sierpinski(x - side / 2, y, side, graphics);  // bottom-left
			sierpinski(x, y - heightOfTriangle(side), side, graphics); // top
			sierpinski(x + side / 2, y, side, graphics); // bottom-right
		}
	}

	private static int heightOfTriangle(final int side) {
		return (int) (Math.sqrt(3) * side / 2); // by pythagorean theorem; draw it out if you're not convinced
		// three - it's a magic number youtube.com/watch?v=aU4pyiB-kq0
	}
}
