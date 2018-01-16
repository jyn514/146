
public class LinkedListQueue<Type> {
  ListNode head;
  ListNode tail;
  protected int size = 0;

  private class ListNode {
    Type data;
    ListNode link;

    @SuppressWarnings("unused")
    ListNode() {}
    
    ListNode(Type givenData) {
      data = givenData;
      link = null;
    }
    
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
    size++;
  }
  
  public void insert(Type data) {
    head = new ListNode(data, head);
    size++;
  }
  
  public Type dequeue() {
    Type result = peek();
    head = head.link;
    size--;
    return result;
  }
  
  public Type peek() {
    return head.data;
  }
  
  public void clear() {
    head = null;
    tail = null;
    size = 0;
  }
  
  public boolean hasNext() {
    return size > 0;
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
