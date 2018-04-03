package src;

/**
 * Copyright © (2018) Joshua Nelson
 * Licensed under the GNU Public License
 * Essentially, you may modify, copy, and distribute this code frequently,
 * but you must preserve this copyright notice and make any changes available as source code to all users.
 * Complete information available at https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class ProcessHeap extends ArrayMaxHeap<Process> {
	public ProcessHeap() {
		super(1024); // kB is nothing
	}

	public ProcessHeap(int size) {
		super(size, 2);
	}

	public ProcessHeap(int size, float loadFactor) {
		super(size, loadFactor);
	}

	public ProcessHeap(Process ... data) {
		this(data.length);
		for (Process t : data) add(t);
	}

	public ProcessHeap(float loadFactor, Process ... data) {
		this(data.length, loadFactor);
		for (Process t : data) add(t);
	}

	public void insert(Process p) { super.add(p); }
	public Process remove() { return super.pop(); }
	public void printHeap() { System.out.println(this); }
}
