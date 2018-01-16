
public class Process {
  public String name;
  private double completionTime; // should only be modified through methods
  public static final double MAX_PROC_TIME = 15.0;

  public Process() {
    name = "YouUsedTheDefaultConstructor";
    // time between 0 and 10 ** 10
    completionTime = Math.random() * MAX_PROC_TIME;
  }
  
  public Process(String givenName, double randTime) {
    name = givenName;
    completionTime = randTime;
  }
  
  public String toString() {
    return String.format("Name: %s; completes in %f seconds.", name, completionTime);
  }

  public double getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(double d) {
    completionTime = d;
  }

}
