
class Process {
  public final String name;
  private double completionTime; // should only be accessed through methods

  public static final double MAX_PROC_TIME = 15.0;
  private static final String DEFAULT_NAME = "YouUsedTheDefaultConstructor";

  Process() {
    name = DEFAULT_NAME;
    // time between 0 and 10 ** 10
    completionTime = Math.random() * MAX_PROC_TIME;
  }
  
  Process(double time) {
	  name = DEFAULT_NAME;
	  setCompletionTime(time);
  }
  
  Process(String givenName, double time) {
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
