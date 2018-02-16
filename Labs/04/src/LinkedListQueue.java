
public class LinkedListQueue<T> implements Queue<T> {
  private ListNode head;
  private ListNode tail;

  private class ListNode {
    T data;
    ListNode link;

    @SuppressWarnings("unused")
    ListNode() {
      data = null;
      link = null;
    }

    ListNode(T givenData) {
      data = givenData;
      link = null;
    }
    
    @SuppressWarnings("unused")
    ListNode(T givenData, ListNode node) {
      data = givenData;
      link = node;
    }
  }

  public void enqueue(T data) {
    if (head == null) {
      head = new ListNode(data);
      tail = head;
    } else {
      tail.link = new ListNode(data);
      tail = tail.link;
    }
  }
  
  public T dequeue() {
    T result = peek();
    head = head.link;
    return result;
  }
  
  public T peek() {
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
