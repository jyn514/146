package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class Process implements Comparable<Process> {
	private int priority;
	private double time;
	private String name;

	private void setPriority(int priority) throws IllegalArgumentException {
		if (priority < 1) throw new IllegalArgumentException("Priority must be greater than 0");
		this.priority = priority;
	}

	private void setTime(double time) throws IllegalArgumentException {
		if (time <= 0) throw new IllegalArgumentException("Time must be greater than 0");
		this.time = time;
	}

	public Process(String name, double time, int priority) {
		setPriority(priority);
		setTime(time);
		this.name = name;
	}

	@Override
	public String toString() {
		return "Process{" +
				"priority=" + priority +
				", time=" + time +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public int compareTo(Process process) {
		return priority - process.priority;
	}

	public double getTime() { return time; }
}
