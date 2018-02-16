class ProcessScheduler {
  private LinkedListQueue<Process> queue = new LinkedListQueue<Process>();
  private Process currentProcess;

  Process getCurrentProcess() {
    return currentProcess;
  }

  void addProcess(Process p) {
    if (currentProcess == null) {
      currentProcess = p;
    } else {
      queue.enqueue(p);
    }
  }

  void runNextProcess() {
    currentProcess = queue.dequeue();
  }

  void cancelCurrentProcess() {
    runNextProcess(); // no clue what the point of this method is
  }

  void printProcessQueue() {
    System.out.println(queue);
  }

}
