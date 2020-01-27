package algs13;
import stdlib.*;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

/**
 * This is a skeleton file for your homework.
 * Complete the functions below. 
 * You may also edit the function "main" to test your code.
 * 
 * You should not use any loops or recursions, except in "delete"
 * Delete may use one loop or recursive helper.
 *
 * You must not add fields or static variables.
 * As always, you must not change the declaration of any method.
 * Do not change any of the methods I have provided, such as "toString" and "check".
 */

public class MyDeque {
	Node first = null;
	Node last = null;
	int N = 0;

	static class Node {
		public Node() { }
		public double item;
		public Node next;
		public Node prev;
	}

	public MyDeque ()         { };
	public boolean isEmpty () { return N == 0; }
	public int size ()        { return N; }

	public void pushLeft (double item) {
		
		Node newFirst = new Node();
        newFirst.item = item;
        
        if ( first != null){
                newFirst.next = first;
                first.prev = newFirst;
        }
        first = newFirst;
        if (last == null) last = first;
        
        N++;
	}

	public void pushRight (double item) {
		
		 Node newLast = new Node();
         newLast.item = item;
         
         if (last != null){
                 newLast.prev = last;
                 last.next = newLast;
         }
         last = newLast;
         if (first == null) first = last;
         
         N++;

	}

	public double popLeft () {
		
		if (N == 0)
			throw new NoSuchElementException();
		
		Node n = first;
		first = first.next;

		if (first == null)
			last = null;
		else
			first.prev = null;

		N--;

		return n.item;

	}

	public double popRight () {
		if (N == 0)
			throw new NoSuchElementException();
		
		Node n = last;
		last = n.prev;
		
		if(last == null)
			first = null;
		else
			last.next = null;
		N--;
		return n.item;
	}

	/* The concat method should take the Nodes from "that" and add them to "this"
	 * After execution, "that" should be empty.
	 * See the tests in the main program.
	 *
	 * Do not use a loop or a recursive definition.
	 * This method should create no new Nodes;
	 * therefore it should not call pushLeft or pushRight.
	 */
	public void concat (MyDeque that) {

		if (that.first == null) {
			return;
		}

		if (this.first == null) {
			this.first = that.first;
			this.last = that.last;
			this.N = that.N;

		} 
		
		else {
			this.last.next = that.first;
			this.last.next.prev = this.last;
			this.last = that.last;
			this.N = this.N + that.N;

			that.first = null;
			that.last = null;
			that.N = 0;

		}
		

	}

	/* Delete should delete and return the kth element from the left (where k is between 0 and N-1).
	 * See the tests in the main program.
	 *
	 * You may use a loop or a recursive definition here.
	 * This method should create no new Nodes;
	 * therefore it should not call pushLeft or pushRight.
	 */
	public double delete (int k) {
		if (k < 0 || k >= N)
			throw new IllegalArgumentException();

		Node n = first;

		int count = 0;

		while (count < k) {
			n = n.next;
			count++;
		}

		double result = n.item;

		if (N == 1) {
			first = last = null;
		}

		else if (first == last) {
			n = null;
			first = n;
		}

		else if (n == first) {
			n = n.next;
			n.prev = null;
			first = n;
		}

		else if (n == last) {
			n = n.prev;
			n.next = null;
			last = n;
			
		} else {
			Node temp = n.prev;
			temp.next = temp.next.next;
			n = n.next;
			n.prev = temp;
		}

		N--;

		return result;

	}

	public MyDeque (String s) {
		String[] nums = s.split (" ");
		for (int i = nums.length-1; i >= 0; i--) {
			try { 
				pushLeft (Double.parseDouble (nums[i]));			
			} catch (NumberFormatException e) {	}
		}
	}
	public String toString () { 
		DecimalFormat format = new DecimalFormat ("#.###");
		StringBuilder result = new StringBuilder ("[ ");
		for (Node x = first; x != null; x = x.next) {
			result.append (format.format (x.item));
			result.append (" ");
		}
		result.append ("]");
		return result.toString ();
	}

	static void showError (String message) {
		Trace.draw ();
		StdOut.println (message);
		//throw new Error (); // stops execution
	}
	public static void checkInvariants (String message, MyDeque that) {
		int N = that.N;
		MyDeque.Node first = that.first;
		MyDeque.Node last = that.last;

		if (N < 0) throw new Error ();
		if (N == 0) {
			if (first != null || last != null) {
				showError (String.format ("%s: Expected first,last == null.", message));
			}
		} else {
			if (first == null || last == null) {
				showError (String.format ("%s: Expected first,last != null.", message));
			}
		}
		if (N > 0) {
			MyDeque.Node prev = null;
			MyDeque.Node current = first;
			for (int i = 0; i < N; i++) {
				if (current == null) {
					showError (String.format ("%s: Expected %d next nodes, but got less.", message, N));
				}
				if (current.prev != prev) { 
					showError (String.format ("%s: Broken prev link.", message));
				}
				prev = current;
				current = current.next;
			}
			if (current != null) {
				showError (String.format ("%s: Expected %d next nodes, but got more.", message, N));
			}
			MyDeque.Node next = null;
			current = last;
			for (int i = 0; i < N; i++) {
				if (current == null) {
					showError (String.format ("%s: Expected %d prev nodes, but got less.", message, N));
				}
				if (current.next != next) {
					showError (String.format ("%s: Broken next link.", message));
				}
				next = current;
				current = current.prev;
			}
			if (current != null) {
				showError (String.format ("%s: Expected %d prev nodes, but got more.", message, N));
			}
		}
	}
	private static void check (String message, MyDeque actual, String expected) {
		checkInvariants (message, actual);
		if (expected != null) {
			if (!expected.equals (actual.toString ())) {
				showError ("Expected \"" + expected + "\", got \"" + actual + "\"");
			}
		}
	}
	private static void check (String message, MyDeque actual, String expected, double dActual, double dExpected) {
		if (dExpected != dActual) {
			showError ("Expected \"" + dExpected + "\", got \"" + dActual + "\"");
		}
		check (message, actual, expected);
	}
	public static void main (String args[]) {
		Trace.drawStepsOfMethod ("main");
		Trace.run ();

		// Here are some tests to get you started.
		// You can edit this all you like.
		MyDeque d1, d2, d3;
		double k;

		////////////////////////////////////////////////////////////////////
		// push/pop tests
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left", d1, "[ 11 ]");
		d1.pushLeft (12);
		check ("left", d1, "[ 12 11 ]");
		d1.pushLeft (13);
		check ("left", d1, "[ 13 12 11 ]");
		k = d1.popLeft ();
		check ("left", d1, "[ 12 11 ]", k, 13);
		k = d1.popLeft ();
		check ("left", d1, "[ 11 ]", k, 12);
		k = d1.popLeft ();
		check ("left", d1, "[ ]", k, 11);

		d1 = new MyDeque ();
		d1.pushRight (11);
		check ("right", d1, "[ 11 ]");
		d1.pushRight (12);
		check ("right", d1, "[ 11 12 ]");
		d1.pushRight (13);
		check ("right", d1, "[ 11 12 13 ]");
		k = d1.popRight ();
		check ("right", d1, "[ 11 12 ]", k, 13);
		k = d1.popRight ();
		check ("right", d1, "[ 11 ]", k, 12);
		k = d1.popRight ();
		check ("right", d1, "[ ]", k, 11);
		
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left/right", d1, "[ 11 ]");
		d1.pushRight (21);
		check ("left/right", d1, "[ 11 21 ]");
		d1.pushLeft (12);
		check ("left/right", d1, "[ 12 11 21 ]");
		d1.pushRight (22);
		check ("left/right", d1, "[ 12 11 21 22 ]");
		k = d1.popLeft ();
		check ("left/right", d1, "[ 11 21 22 ]", k, 12);
		k = d1.popLeft ();
		check ("left/right", d1, "[ 21 22 ]", k, 11);
		k = d1.popLeft ();
		check ("left/right", d1, "[ 22 ]", k, 21);
		k = d1.popLeft ();
		check ("left/right", d1, "[ ]", k, 22);
		
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left/right", d1, "[ 11 ]");
		d1.pushRight (21);
		check ("left/right", d1, "[ 11 21 ]");
		d1.pushLeft (12);
		check ("left/right", d1, "[ 12 11 21 ]");
		d1.pushRight (22);
		check ("left/right", d1, "[ 12 11 21 22 ]");
		k = d1.popRight ();
		check ("left/right", d1, "[ 12 11 21 ]", k, 22);
		k = d1.popRight ();
		check ("left/right", d1, "[ 12 11 ]", k, 21);
		k = d1.popRight ();
		check ("left/right", d1, "[ 12 ]", k, 11);
		k = d1.popRight ();
		check ("left/right", d1, "[ ]", k, 12);

		////////////////////////////////////////////////////////////////////
		//  test exceptions
		////////////////////////////////////////////////////////////////////
		try {
			d1.popLeft ();
			showError ("Expected exception");
		} catch (NoSuchElementException e) {}
		try {
			d1.popRight ();
			showError ("Expected exception");
		} catch (NoSuchElementException e) {}

		////////////////////////////////////////////////////////////////////
		// concat tests (and more push/pop tests)
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.concat (new MyDeque ());
		check ("concat", d1, "[ ]");
		d1.pushLeft (11);
		d1.concat (new MyDeque ());
		check ("concat", d1, "[ 11 ]");

		d1 = new MyDeque ();
		d2 = new MyDeque ();
		d2.pushLeft (11);
		d1.concat (d2);
		check ("concat", d1, "[ 11 ]");

		d1 = new MyDeque ();
		d2 = new MyDeque ();
		d1.pushLeft (11);
		d1.pushLeft (12);
		d2.pushLeft (21);
		d2.pushLeft (22);
		d1.concat (d2);
		check ("concat", d1, "[ 12 11 22 21 ]");
		check ("concat", d2, "[ ]");
		
		d1 = new MyDeque ();
		for (int i = 10; i < 13; i++) { d1.pushLeft (i); checkInvariants ("left", d1); }
		for (int i = 20; i < 23; i++) { d1.pushRight (i); checkInvariants ("right", d1); }
		check ("concat", d1, "[ 12 11 10 20 21 22 ]");
		d2 = new MyDeque ();
		d1.concat (d2);
		check ("concat", d1, "[ 12 11 10 20 21 22 ]");
		check ("concat", d2, "[ ]");

		for (int i = 30; i < 33; i++) { d2.pushLeft (i); checkInvariants ("left", d2); }
		for (int i = 40; i < 43; i++) { d2.pushRight (i); checkInvariants ("right", d2); }
		check ("concat", d2, "[ 32 31 30 40 41 42 ]");

		d3 = new MyDeque ();
		d2.concat (d3);
		check ("concat", d2, "[ 32 31 30 40 41 42 ]");
		check ("concat", d3, "[ ]");

		d1.concat (d2);
		check ("concat", d1, "[ 12 11 10 20 21 22 32 31 30 40 41 42 ]");
		check ("concat", d2, "[ ]");
		for (int i = 0; i < 12; i++) { d1.popLeft (); checkInvariants ("left", d1); }
		////////////////////////////////////////////////////////////////////
		// delete tests
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.pushLeft (11);
		k = d1.delete (0);
		check ("delete", d1, "[ ]", k, 11);
		for (int i = 10; i < 20; i++) { d1.pushRight (i); checkInvariants ("right", d1); }
		k = d1.delete (0);
		check ("delete", d1, "[ 11 12 13 14 15 16 17 18 19 ]", k, 10);
		k = d1.delete (8);
		check ("delete", d1, "[ 11 12 13 14 15 16 17 18 ]", k, 19);
		k = d1.delete (4);
		check ("delete", d1, "[ 11 12 13 14 16 17 18 ]", k, 15);
		StdOut.println ("Finished tests");
	}
}
