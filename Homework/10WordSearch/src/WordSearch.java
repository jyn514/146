package src;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.TreeSet;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toCollection;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class  WordSearch {
	private static final short MIN_SIZE = 2, MAX_SIZE = 6; // size of words
	private static final Path DEFAULT_DICTIONARY = get("dict.txt");
	private static final Character[][] DEFAULT_WORD_SEARCH = toGrid(
			"R A H J M\n" +
			"Y U W W K\n" +
			"R X N F M\n" +
			"Q G E E B\n" +
			"E O A P E");

	public static void main(String[] args) {
		try {
			final TreeSet<String> WORDS = new TreeSet<>(readAllLines(DEFAULT_DICTIONARY));
			System.out.println(Arrays.toString(allWords(DEFAULT_WORD_SEARCH, WORDS)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] allWords(Character[][] grid, TreeSet<String> dictionary) {
		Graph<Character> graph = new Graph<>();
		TreeSet<Character> letters = new TreeSet<>();

		for (Character[] row : grid) for (Character letter : row) {
				graph.addVertex(letter);
				letters.add(letter);
		}

		// remove any words which contain a letter not present in the grid; reduces search space from 25144 to 1616
		dictionary = dictionary.parallelStream() // it's in memory anyway, may as well
				 .map(String::toUpperCase)
				 .filter(s -> s.chars().allMatch(c -> letters.contains((char) c)))
				 .collect(toCollection(TreeSet::new));

		for (int vertex = 0; vertex < graph.size(); vertex++) {
			int currX = vertex % grid.length, currY = vertex / grid.length;
			for (int y = currY - 1; y <= currY + 1; y++) {
				for (int x = currX - 1; x <= currX + 1; x++) {
					if (isValid(y, grid.length) && isValid(x, grid.length) && !(y == currY && x == currX)) {
						graph.addEdge(vertex, y * grid.length + x);
					}
				}
			}
		}

		return doubleDepthFirstSearch(graph, dictionary);
	}

	public static String[] doubleDepthFirstSearch(Graph<Character> graph, TreeSet<String> dictionary) {
		TreeSet<String> result = new TreeSet<>();
		for (Graph<Character>.Vertex v : graph.depthFirstSearch()) {
			search(dictionary, new TreeSet<>(), result, "", v);
		}
		return result.parallelStream().filter(s -> s.length() >= MIN_SIZE).toArray(String[]::new);
	}

	private static void search(TreeSet<String> dictionary, TreeSet<Graph<Character>.Vertex> marked, TreeSet<String> words, String currentWord, Graph<Character>.Vertex currentNode) {
		if (currentWord.length() > MAX_SIZE) return;
		marked.add(currentNode);
		if (dictionary.contains(currentWord)) words.add(currentWord);
		else if (dictionary.parallelStream().noneMatch(s -> s.startsWith(currentWord))) { // TODO: take advantage of TreeSet
			return;
		}
		for (Graph<Character>.Edge e : currentNode.neighbors) if (e.to != null) {
			search(dictionary, new TreeSet<>(marked), words, currentWord + e.to.data, e.to); // use copy so paths don't overlap
		}
	}

	private static Character[][] toGrid(String input) {
		// I had a brain fart, https://stackoverflow.com/a/13129853
		String[] rows = input.split("\n");
		Character[][] grid = new Character[rows.length][];
		for (int i = 0; i < rows.length; i++) {
			grid[i] = Arrays.stream(rows[i].split(" "))
			                .map(s -> s.toUpperCase().charAt(0))
			                .toArray(Character[]::new);
		}
		return grid;
	}

	private static boolean isValid(int i, int size) {
		return i >= 0 && i < size;
	}

/*	private static class AlphabetTree {
		private static final char NULL = '\0', ASCII_A = 'A';
		private static final int M = 26;

 		final Node root = new Node(NULL);

 		/*public Node add(char c) {
 			return root.add(c);
		}

		public void add(String s) {
			add(s.toCharArray());
		}

		public void add(char[] array) {
			add(array, 0, root);
		}

		public void add(char[] array, int index, Node startNode) {
			if (index >= array.length) return;
			Node next = startNode.add(array[index++]);
			add(array, index, next);
		}

		public List<String> words(Collection<String> dictionary) {
			List<String> list = new ArrayList<>();
			addWords(dictionary, list, root, "");
			return list;
		} /

		/**
		 * Recursive helper function
		 /
		private void addWords(Collection<String> dictionary, List<String> words, Node currentNode, String currentWord) {
			if (currentWord.length() >= MIN_SIZE && currentWord.length() <= MAX_SIZE && dictionary.contains(currentWord)) {
				words.add(currentWord);
			}	else if (dictionary.parallelStream().noneMatch(s -> s.startsWith(currentWord))) {
				return;
			}
 			for (Node n : currentNode.children) {
				if (n != null) {
					addWords(dictionary, words, n, currentWord + n.key);
				}
			}
		}

		void addVertices(Graph<Character>.Vertex start, Node current, List<Graph<Character>.Vertex> marked, int depth) {
			if (marked.contains(start) || depth < 1) return;
			marked.add(start);
			for (Graph<Character>.Edge e : start.neighbors) {
				addVertices(e.to, current.add(e.to.data), marked, depth - 1);
			}
		}

		class Node {
			final char key;
			final Node[] children = new Node[M];

			Node(char key) {
				this.key = key;
			}

			Node add(char c) { // recursive - don't have to restart each time
				assert c >= ASCII_A && c <= 'Z': "Must be uppercase ASCII for now";
				int i = (int) c - ASCII_A;
				if (children[i] == null) children[i] = new Node(c);
				return children[i];
			}
		}
	}*/
}
