
public class LinkedListQueue<Type> {
  ListNode head;
  ListNode tail;

  private class ListNode {
    Type data;
    ListNode link;

    @SuppressWarnings("unused")
    ListNode() {
      data = null;
      link = null;
    }

    ListNode(Type givenData) {
      data = givenData;
      link = null;
    }
    
    @SuppressWarnings("unused")
    ListNode(Type givenData, ListNode node) {
      data = givenData;
      link = node;
    }
  }

  public void enqueue(Type data) {
    if (head == null) {
      head = new ListNode(data);
      tail = head;
    } else {
      tail.link = new ListNode(data);
      tail = tail.link;
    }
  }
  
  public Type dequeue() {
    Type result = peek();
    head = head.link;
    return result;
  }
  
  public Type peek() {
    return head.data;
  }
  
  public String toString() {
    if (head == null) {
      return "";
    }
    
    ListNode temp = head;
    StringBuilder result = new StringBuilder();
    
    while (temp != tail) {
      result.append(temp.data.toString());
      result.append('\n');
      temp = temp.link;
    }
    result.append(tail.data);
    
    return result.toString();
  }
  
  public void print() {
    System.out.println(toString());
  }
}
