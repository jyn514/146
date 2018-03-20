
public class LinkedListQueue<T> implements Queue<T> {
  private ListNode head;
  private ListNode tail;

  private class ListNode {
    T data;
    ListNode link;

    ListNode(T givenData) {
      data = givenData;
    }
    
    @SuppressWarnings("unused")
    ListNode(T givenData, ListNode node) {
      data = givenData;
      link = node;
    }
  }

  public void enqueue(T data) {
    if (head == null) {
      tail = head = new ListNode(data);
    } else {
      tail = tail.link = new ListNode(data);
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
  
  void print() {
    System.out.println(this);
  }
}
