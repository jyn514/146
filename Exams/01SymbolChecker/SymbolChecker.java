/**
 * Copyright (c) 2018 Joshua Nelson
 * ALL RIGHTS RESERVED
 * @author Joshua Nelson
 * @version 0.0.1 (2018-03-02)
 */

public class SymbolChecker {

	public static void main(String[] args) {
		for (TEST t : TEST.values()) {
			if (wellFormed(t.expression) != t.result) {
				System.out.printf("ERROR: %s should be %b, instead labeled as %b%n",
						t.expression, t.result, !t.result);
			} else {
				System.out.printf("INFO: Expression %s is %b%n", t.expression, t.result);
			}
		}
	}

	public static boolean wellFormed(String expression) {
		final Stack<CHECK> stack = new DoubleLinkedListStack<>();
		boolean hasSymbol = false;
		for (char symbol : expression.toCharArray()) {
			if (symbol == '"' && stack.size() > 0 && stack.peek().equals(CHECK.QUOTE)) { // this took forever to figure out
				stack.pop();
				continue;
			}
			for (CHECK c : CHECK.values()) {
				if (c.opening == symbol) {
					stack.push(c);
					hasSymbol = true;
					break;
				} else if (c.closing == symbol && (stack.size() == 0 || !stack.pop().equals(c))) return false;
			}
			// not opening or closing symbol, ignore
		}
		return stack.size() == 0 && hasSymbol;
	}

	private enum TEST {
		one("()", true),
		two("<<<>>>", true),
		three("{(\"tacos\")}", true),
		four("if(pass == true){return \"yay!\";}", true),
		five("abcd", false),
		six("\"\"\"", false),
		seven("<(\")", false),
		eight(":-)", false),
		nine("<3", false),
		ten("(<{\"\"}>", false),
		eleven("<(>)", false);

		final String expression;
		final boolean result;
		private TEST(String expression, boolean result) {
			this.expression = expression;
			this.result = result;
		}
	}

	private enum CHECK {
		PARENTHESIS('(', ')'),
		BRACKET('<', '>'),
		BRACE('{', '}'),
		QUOTE('"', '"');
		
		final char opening, closing;
		private CHECK(char opening, char closing) {
			this.opening = opening;
			this.closing = closing;
		}
		
		public boolean equals(CHECK c) {
			return this.opening == c.opening && this.closing == c.closing;
		}
	}

}
