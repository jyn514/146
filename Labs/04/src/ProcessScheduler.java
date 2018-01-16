
public class ProcessScheduler {
  LinkedListQueue<Process> queue = new LinkedListQueue<Process>();
  Process currentProcess;

  public Process getCurrentProcess() {
    return currentProcess;
  }

  public void addProcess(Process p) {
    if (currentProcess == null) {
      currentProcess = p;
    } else {
      queue.enqueue(p);
    }
  }

  public void runNextProcess() {
    currentProcess = queue.dequeue();
  }

  public void cancelCurrentProcess() {
    runNextProcess(); // no clue what the point of this method is
  }

  public void printProcessQueue() {
    System.out.println(queue);
  }

}
