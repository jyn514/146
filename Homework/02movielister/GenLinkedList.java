public abstract class GenLinkedList {
  ListNode head;
  ListNode current;
  ListNode previous;

  class ListNode {
    Object data = null;
    ListNode link = null;

    ListNode(Object d) {
      data = d;
    }

    ListNode() {
    }

  }

  GenLinkedList() {
    head = new ListNode();
    current = head;
  }

  GenLinkedList(Object o) {
    head = new ListNode(o);
    current = head;
  }

  GenLinkedList(Object... objects) {
    head = new ListNode(objects[0]);
    current = head;
    for (int i = 1; i < objects.length; i++) {
      append(objects[i]);
    }
  }

  private void append(Object o) {
    goToEnd();
    current.link = new ListNode(o);
  }

  void deleteCurrent() {
    previous.link = current.link;
    current = null; // send message to garbage collector
    goToPrevious();
  }

  Object getCurrent() {
    return current.data;
  }

  void goToNext() {
    if (current != null) {
      previous = current;
      current = current.link;
    }
  }

  void goToPrevious() { // dubious
    ListNode temp = previous;
    goToStart();
    while (current != temp) {
      goToNext();
    }
  }

  void goToStart() {
    current = head;
    previous = head;
  }

  void goToEnd() {
    while (current != null && current.data != null && current.link != null) {
      goToNext();
    }
  }

  void print() {
    System.out.println(getAll());
  }

  String getAll() { // will reset position in linked list to head
    goToStart();
    StringBuilder b = new StringBuilder();
    while (current.link != null) {
      b.append(getCurrent());
      b.append('\n');
      goToNext();
    }
    b.append(current.data);
    goToStart();
    return b.toString();
  }

}
