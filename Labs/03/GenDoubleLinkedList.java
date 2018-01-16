import java.util.ArrayList;
import java.util.List;

public class GenDoubleLinkedList<Type> {

  ListNode head;
  ListNode current;

  GenDoubleLinkedList() {
    head = new ListNode();
    current = head;
  }

  @SafeVarargs
  GenDoubleLinkedList(Type... types) {
    head = new ListNode(types[0]);
    head.prevLink = head;
    current = head;
    for (int i = 1; i < types.length; i++) {
      current.nextLink = new ListNode(types[i], current);
      goToNext();
    }
  }

  private class ListNode {
    Type data;
    ListNode nextLink = null;
    ListNode prevLink;

    ListNode() {
      prevLink = head; // strongly not recommended
    }

    ListNode(Type d) {
      prevLink = head; // not terribly recommended
      data = d;
    }

    ListNode(Type givenData, ListNode previous) { // this is the way!
      data = givenData;
      prevLink = previous;
    }

    ListNode(Type givenData, ListNode previous, ListNode next) { // if you want to go overboard
      data = givenData;
      prevLink = previous;
      nextLink = next;
    }

    @SuppressWarnings("unused")
    protected void setData(Type d) {
      data = d;
    }
  }

  public Type getDataAtCurrent() {
    return current.data;
  }

  public void setDataAtCurrent(Type data) {
    current.data = data;
  }

  public void goToNext() {
    current = current.nextLink;
  }

  public void goToPrev() {
    current = current.prevLink;
  }

  public void insertNodeAfterCurrent(Type data) {
    if (current.nextLink == null) {
      current.nextLink = new ListNode(data, current);
    } else {
      current.nextLink.prevLink = new ListNode(data, current, current.nextLink);
      current.nextLink = current.nextLink.prevLink;
    }
  }

  public void deleteCurrentNode() { // examples assume prevlink is 0, current is 1, next link is 2
    current.prevLink.nextLink = current.nextLink; // 0 links to 2
    current.nextLink.prevLink = current.prevLink; // 2 links backward to 0
    current = current.prevLink; // move current to 0
  }

  public void showList() {
    for (Type t : getList()) {
      System.out.println(t);
    }
  }
  
  public List<Type> getList() {
    current = head;
    List<Type> list = new ArrayList<Type>();

    while (current.nextLink != null) {
      list.add(getDataAtCurrent());
      goToNext();
    }

    list.add(getDataAtCurrent());

    return list;
  }

  public boolean inList(Type data) {
    return getList().contains(data);
  }

}
