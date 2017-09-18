import java.io.IOException;			// for the dict
import java.nio.channels.Channels;			// if we need to download a file
import java.nio.channels.ReadableByteChannel;
import java.net.URL;						
import java.io.FileOutputStream; 
import java.net.URISyntaxException;			// because java insists
import java.nio.file.Path;		
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;			// only imports not dict-related
import java.util.List;
import java.util.Random;

class Data {
	static final String[] PICTURES = makePictures();
	static final int LIVES = 7;


	static final String INTRO = // don't touch
			"                                 Welcome to\n" + "                                 _____\n"
					+ "|      |      /\\      |\\    |   |     |      /\\    /\\          /\\     |\\    |\n"
					+ "|      |     /  \\     | \\   |   |     |     /  \\  /  \\        /  \\    | \\   |\n"
					+ "|______|    /____\\    |  \\  |   |   __     /    \\/    \\      /____\\   |  \\  |\n"
					+ "|      |   /      \\   |   \\ |   |     |   /            \\    /      \\  |   \\ |\n"
					+ "|      |  /        \\  |    \\|   |_____|  /              \\  /        \\ |    \\|";

	static final String WON = // don't touch this either

			"        _____                                 _____           /\\\n"
					+ "\\    / |     | |     |      \\              / |     | |\\    | |  |\n"
					+ " \\  /  |     | |     |       \\            /  |     | | \\   | |  |\n"
					+ "  \\/   |     | |     |        \\    /\\    /   |     | |  \\  | \\  /\n"
					+ "  /    |     | |     |         \\  /  \\  /    |     | |   \\ |  \\/\n"
					+ " /     |_____| |_____|          \\/    \\/     |_____| |    \\|  \\/";

	static final String LOST = // I mean it
			"        _____                       _____   ______  _____\n"
					+ "\\    / |     | |     |      |      |     | |       |\n"
					+ " \\  /  |     | |     |      |      |     | |       |\n"
					+ "  \\/   |     | |     |      |      |     | |_____  |_____\n"
					+ "  /    |     | |     |      |      |     |       | |       _\n"
					+ " /     |_____| |_____|      |_____ |_____| ______| |_____ |_|";

	
	private static final List<String> DICT = loadDict();
	private static final String DICT_PATH = "american-english-small";
	private Random rand;

	Data() {
		rand = new Random();
	}

	String randWord() {
		String w = DICT.get(rand.nextInt(DICT.size())).toLowerCase();
		if (!w.matches("[A-Za-z]*")) { // letters only
			return randWord();
		} else
			return w;
	}

	/**
	 * DICT_PATH is the filename of the dictionary. Dictionary must be have one word
	 * per line, but can be arbitrary characters (excluding \n). Words that contain
	 * characters that are not alphabetic will be discarded at runtime.
	 * Data.class.getResources works IF AND ONLY IF the dictionary is in the same
	 * directory as the CLASS file.
	 * Downloads a dictionary automatically if not present.
	 * Uses built-in dictionary if additional errors occur.
	 * 
	 * @return List<String> DICT
	 * @throws IOException
	 *             - File not found, permission denied, etc
	 * @throws URISyntaxException
	 *             - No clue
	 */
	private static final List<String> loadDict() {
		try {
			URL u = Data.class.getResource(DICT_PATH);
			if (u != null) {
				Path p = Paths.get(u.toURI());
				return Files.readAllLines(p);
			} else {
				System.out.println("Dict does not exist. Trying to load from a mirror.");
				URL internetDict = new URL(
						"https://raw.githubusercontent.com/jyn514/python-challenges/master/games/dict/american-english-small");
				ReadableByteChannel rbc = Channels.newChannel(internetDict.openStream());
				FileOutputStream fos = new FileOutputStream("american-english-small"); // string arg is the output file name
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // works for files <  2^63-1 bytes.
				fos.close();
				u = Data.class.getResource(DICT_PATH);
				Path p = Paths.get(u.toURI());
				return Files.readAllLines(p);
			}
		} catch (IOException | URISyntaxException e) { // i'm tired of dealing with this shit
			return makeLimitedDict();
		}
	}

	private static final List<String> makeLimitedDict() { // found these on google, they looked decent
		return Arrays.asList("accommodate", "afterthought", "allegiance", "aloft", "ancestor", "anticipation", "antics",
				"apparel", "ascend", "beckon", "brink", "catastrophe", "coax", "compassion", "complexion", "content",
				"courteous", "cringe", "derelict", "dignity", "distaste", "dormant", "elaborate", "endure", "enforce",
				"exertion", "expanse", "extraordinary", "foliage", "foremost", "frank", "function", "futile", "gaze",
				"glimmer", "glimpse", "grimace", "headstrong", "hesitate", "hoist", "immense", "imperceptibly",
				"indication", "inscription", "instinctive", "intent", "interior", "jar", "keepsake", "knack",
				"literacy", "lurch", "makeshift", "malicious", "massive", "meager", "melancholy", "merge", "mingle",
				"minuscule", "momentary", "nape", "nimble", "obstinate", "opt", "overwhelming", "pact", "pandemonium",
				"persuade", "phenomenal", "ponder", "quantity", "quaver", "quench", "radiant", "ravine", "recipient",
				"resentful", "satisfactory", "sensitive", "sentiment", "shudder", "sickly", "sleek", "solemn", "soothe",
				"stagger", "stern", "tantalize", "temptation", "transform", "unscrupulous", "vain", "vengeance",
				"violate", "vital", "vivid", "wistful", "yield", "zest");
	}

	private static final String[] makePictures() { // this looked nice at some point :(
		return new String[] {
				"\n" + "                +---+\n" + "                |   |\n" + "                    |\n"
						+ "                    |\n" + "                    |\n" + "                    |\n"
						+ "               ==========",
				"\n" + "                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "                    |\n" + "                    |\n" + "                    |\n"
						+ "               ==========",
				"\n" + "                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "                |   |\n" + "                    |\n" + "                    |\n"
						+ "               ==========",
				"\n" + "                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "               /|   |\n" + "                    |\n" + "                    |\n"
						+ "               ==========",
				"\n" + "                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "               /|\\  |\n" + // need to keep double backslash
						"                    |\n" + "                    |\n" + "               ==========",
				"\n" + "                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "               /|\\  |\n" + "               /    |\n" + "                    |\n"
						+ "               ==========",
				"                +---+\n" + "                |   |\n" + "                O   |\n"
						+ "               /|\\  |\n" + "               / \\  |\n" + "                    |\n"
						+ "               ==========",
				"\n" + "                +---+\n" + "                ^   |\n" + "                O   |\n"
						+ "               /|\\  |         GAME\n" + "               / \\  |         OVER\n"
						+ "                    |\n" + "               ==========" };
	}
}
