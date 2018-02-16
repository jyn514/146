
public class DoubleLinkedListTester {

	public static void main(String[] args) {
		System.out.println("Double Linked List Tester");
		System.out.println("Create, insert, and move test");
		DoubleLinkedList<String> dList = new DoubleLinkedList<>();
		dList.setDataAtCurrent("1");
		dList.insert("2");
		dList.goToNext();
		dList.insert("3");
		dList.goToNext();
		dList.insert("4");
		dList.goToNext();
		dList.showList();
		
		System.out.println("Previous and Deletion Test");
		dList.goToPrev();
		dList.deleteCurrentNode();
		dList.showList();
		
		System.out.println("In list test");
		System.out.println(dList.inList("2"));
		System.out.println(dList.inList("3"));
		
		System.out.println("Test Done");
	}

}
