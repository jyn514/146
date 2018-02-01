import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleLinkedListTest {
	private DoubleLinkedList<Integer> intTest;
	private DoubleLinkedList<String> stringTest;

	@Test
	void toStringTest() {
		intTest = new DoubleLinkedList<>(12, 15, 14, 13);
		assertEquals("12\n15\n14\n13\n", intTest.toString());
		intTest = new DoubleLinkedList<>(1, 2, 3, 4, 5, 6, 7);
		assertEquals("1\n2\n3\n4\n5\n6\n7\n", intTest.toString());
		stringTest = new DoubleLinkedList<>("hi there", "my name is josh");
		assertEquals("hi there\nmy name is josh\n", stringTest.toString());
	}

	@Test
	void goTo() {
		intTest = new DoubleLinkedList<>(4, 1, 6, 2);
		intTest.goTo(6);
		assertEquals(6, (int) intTest.getDataAtCurrent());
		stringTest = new DoubleLinkedList<>("this","is","a","list");
		stringTest.goTo("is");
		assertEquals("is",stringTest.getDataAtCurrent());
	}

	@Test
	void setData() {
		goTo();
		intTest.setDataAtCurrent(2);
		assertEquals(2, (int) intTest.getDataAtCurrent());
	}

	@Test
	void append() {
		intTest = new DoubleLinkedList<>(1, 5, 3);
		intTest.append(4);
		intTest.goTo(4);
		assertEquals(4, (int) intTest.getDataAtCurrent());
		intTest.append(4, 3, 1, 7, 9);
		intTest.goTo(9);
		assertEquals(9, (int) intTest.getDataAtCurrent());
	}

	@Test
	void insert() {
		intTest = new DoubleLinkedList<>(1, 2, 3);
		intTest.insert(4);
		assertEquals(4, (int) intTest.getDataAtCurrent());
		intTest.insert(1,2,65,13,51);
		assertEquals(51, (int) intTest.getDataAtCurrent());
	}

	@Test
	void deleteCurrent() {
		intTest = new DoubleLinkedList<>(3,1,6);
		intTest.deleteCurrentNode();
		assertEquals(1, (int) intTest.getDataAtCurrent());
	}

	@Test
	void iterator() {
		intTest = new DoubleLinkedList<>(1,6,4,2,2);
		intTest.iterator().forEachRemaining(System.out::print);
	}
}