package algs13;
import java.text.DecimalFormat;

import algs13.MyLinked1.Node;
import stdlib.*;

/* 
 * Complete the methods below.
 * All of these methods modify the list.
 * Use the function checkInvariants to ensure that your list is well-formed after you modify it.
 * Note that this list keeps track of the number of elements N.
 * It is important that N accurately reflect the length of the list. 
 * 
 * You may not add any fields to the node or list classes.
 * You may not add any methods to the node class.
 * You may not create any new nodes or other objects.
 * For example, you cannot create a new Stack or ArrayList.
 * 
 * Each function must be independent.  
 * For example, you cannot call delete to implement remove.
 * You MAY add private methods to the list class (helper functions for the recursion). 
 */
public class MyLinked2 {
	static class Node {
		public Node (double item, Node next) { this.item = item; this.next = next; }
		public double item;
		public Node next;
	}
	int N;
	Node first;

	// delete the kth element (where k is between 0 and N-1 inclusive)
	public void delete(int k) {
		if (k < 0 || k >= N)
			throw new IllegalArgumentException();

		Node n = first;

		if (k == 0) {
			first = n.next;
			N = N - 1;
			return;
		}

		for (int i = 0; n != null && i < k - 1; i++) {
			n = n.next;
		}

		n.next = n.next.next;
		N = N - 1;

	}

	// reverse the list "in place"...
	public void reverse() {

		Node prev = null;
		Node temp = first;

		while (temp != null) {
			Node next = temp.next;
			temp.next = prev;
			prev = temp;
			temp = next;
		}
		first = prev;

	}

	// remove all occurrences of item from the list
	public void remove(double item) {

		Node temp = first;
		Node prev = null;

		while (temp != null) {
			if (temp.item == item) {
				if (prev == null) {
					first = temp.next;
				} else {
					prev.next = temp.next;
				}
				N--;
			} else {
				prev = temp;
			}
			temp = temp.next;
		}

	}

	public static void main (String args[]) {
		//mainDebug ();
		mainRunTests ();
	}
	private static void mainDebug () {
		// Use this for debugging!
		// Add the names of helper functions if you use them.
		Trace.drawStepsOfMethod ("delete");
		Trace.drawStepsOfMethod ("reverse");
		Trace.drawStepsOfMethod ("remove");
		Trace.run (); 
		// TODO:  Put the test here you want to debug:
		testDelete (2, "11 21 31 41", "[ 11 21 41 ]");
	}
	private static void mainRunTests () {
		// Trace.run (); // uncomment this to get drawings in showError
		testDelete (0, "", "java.lang.IllegalArgumentException");
		testDelete(-1, "11 21 31", "java.lang.IllegalArgumentException");
		testDelete (3, "11 21 31", "java.lang.IllegalArgumentException");
		testDelete (0, "11", "[ ]");
		testDelete (0, "11 21 31 41", "[ 21 31 41 ]");
		testDelete (1, "11 21 31 41", "[ 11 31 41 ]");
		testDelete (2, "11 21 31 41", "[ 11 21 41 ]");
		testDelete (3, "11 21 31 41", "[ 11 21 31 ]");
		testDelete (0, "11 21 31 41 51", "[ 21 31 41 51 ]");
		testDelete (1, "11 21 31 41 51", "[ 11 31 41 51 ]");
		testDelete (2, "11 21 31 41 51", "[ 11 21 41 51 ]");
		testDelete (3, "11 21 31 41 51", "[ 11 21 31 51 ]");
		testDelete (4, "11 21 31 41 51", "[ 11 21 31 41 ]");
		testReverse ("", "[ ]");
		testReverse ("11", "[ 11 ]");
		testReverse ("11 21", "[ 21 11 ]");
		testReverse ("11 21 31", "[ 31 21 11 ]");
		testReverse ("11 21 31 41", "[ 41 31 21 11 ]");
		testReverse ("11 21 31 41 51", "[ 51 41 31 21 11 ]");
		testRemove (5, "", "[ ]");
		testRemove (5, "5", "[ ]");
		testRemove (5, "5 5", "[ ]");
		testRemove (5, "5 5 5", "[ ]");
		testRemove (5, "11", "[ 11 ]");
		testRemove (5, "11 21", "[ 11 21 ]");
		testRemove (5, "11 21 31", "[ 11 21 31 ]");
		testRemove (5, "5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 5 5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5 5 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 5 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 5 5 11 21 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 31 41 5 5 5", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "11 21 5 5 5 31 41", "[ 11 21 31 41 ]");
		testRemove (5, "5 5 5 11 5 5 21 5 31 5 5 41 5 5 5", "[ 11 21 31 41 ]");
		testRemove (5.1, "5.1 5.1 5.1 5 11 5.1 5.1 21 5.1 31 5.1 5.1 41 5.1 5.1 5.1", "[ 5 11 21 31 41 ]");
		StdOut.println ("Finished tests");
	}
	
	/* ToString method to print */
	public String toString () { 
		// Use DecimalFormat #.### rather than String.format 0.3f to leave off trailing zeroes
		DecimalFormat format = new DecimalFormat ("#.###");
		StringBuilder result = new StringBuilder ("[ ");
		for (Node x = first; x != null; x = x.next) {
			result.append (format.format (x.item));
			result.append (" ");
		}
		result.append ("]");
		return result.toString ();
	}

	/* Method to create lists */
	public static MyLinked2 of(String s) {
		int N = 0;
		Node first = null;
		String[] nums = s.split (" ");
		for (int i = nums.length-1; i >= 0; i--) {
			try { 
				double num = Double.parseDouble (nums[i]); 
				first = new Node (num, first);  
				N++;
			} catch (NumberFormatException e) {
				// ignore anything that is not a double
			}
		}
		MyLinked2 result = new MyLinked2 ();
		result.first = first;
		result.N = N;
		return result;
	}
	
	static void showError (String message) {
		Trace.draw ();
		StdOut.println (message);
		//throw new Error (); // stops execution
	}
	private static void checkInvariants (String message, MyLinked2 list) {
		MyLinked2.Node x = list.first;
		int N = list.N;
		for (int i = 0; i < N; i++) {
			if (x == null) {
				showError (String.format ("%s: Expected %d nodes, but got less.", message, N));
				return;
			}
			x = x.next;
		}
		if (x != null) {
			showError (String.format ("%s: Expected %d nodes, but got more.", message, N));
		}
	}
	private static void check (String message, MyLinked2 actual, String expected) {
		checkInvariants (message, actual);
		if (!expected.equals (actual.toString ())) {
			showError (String.format ("%s: expected=%s, actual=%s", message, expected, actual.toString ()));
		}
	}
	private static void testDelete (int k, String list, String expected) {
		MyLinked2 actual = MyLinked2.of (list);
		String message = String.format ("[ %s ].delete( %d )", list, k);
		try {
			actual.delete (k);
		} catch (Throwable e) {
			String exception = e.getClass ().getName ();
			if (! exception.equals (expected)) {
				e.printStackTrace (); // for debugging
				showError (String.format ("%s: expected=%s, actual=%s", message, expected, exception));
			}
			return;
		}
		check (message, actual, expected);
	}
	private static void testReverse (String list, String expected) {
		MyLinked2 actual = MyLinked2.of (list);
		actual.reverse ();
		String message = String.format ("[ %s ].reverse( )", list);
		check (message, actual, expected);
	}
	private static void testRemove (double item, String list, String expected) {
		MyLinked2 actual = MyLinked2.of (list);
		actual.remove (item);
		String message = String.format ("[ %s ].remove( %.2f )", list, item);
		check (message, actual, expected);
	}
}
