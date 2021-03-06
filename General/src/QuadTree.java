package src;

import java.util.Iterator;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class QuadTree<T> extends MWayTree<T> {

	private enum ORDER { // top left, top right, bottom left, bottom right
		FIRST, SECOND, THIRD, FOURTH
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}
}
