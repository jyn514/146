package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class HuffmanCoding {
	public static HuffmanTree encode(String message) {
		Map<Float, Character> weights = weights(message);
		final HuffmanTree tree = new HuffmanTree();
		// TODO: build priority queue
		return tree;
	}

	public static String decode(HuffmanTree tree) {

	}

	public static Map<Float, Character> weights(CharSequence message) {
		LinkedMap<Character, Integer> counts = new LinkedMap<>();
		int len = message.length(); // make sure we don't modify length while iterating
		for (int i = 0; i < len; i++) {
			if (counts.contains(message.charAt(i))) {
				counts.put(message.charAt(i), counts.get(message.charAt(i)) + 1);
			}
		}
		Map<Float, Character> weights = new LinkedMap<>();
		counts.forEach(node ->
			weights.put(node.data / (float) len, node.key));
		return weights;
	}

	public static Map<Float, Character> weights(char[] message) {
		StringBuilder tmp = new StringBuilder();
		for (char c : message) tmp.append(c);
		return weights(tmp.toString());
	}

	// makes this easier to type
	private static class HuffmanTree extends LinkedMap<Character, CharSequence> {}
}
