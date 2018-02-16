
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
    setCompletionTime(time);
  }
  
  public String toString() {
    return String.format("Name: %s; completes in %f seconds.", name, completionTime);
  }

  public double getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(double d) {
	  if (d < 0) {
		  System.err.printf("WARNING: process given negative time %.3f. Using 0 instead%n", d);
		  completionTime = 0;
	  } else if (d > MAX_PROC_TIME) {
		  System.err.printf("WARNING: process given time %.3f greater than max time. "
				  +	"Using %d instead%n", d, MAX_PROC_TIME);
		  completionTime = MAX_PROC_TIME;
	  } else completionTime = d;
  }
}
