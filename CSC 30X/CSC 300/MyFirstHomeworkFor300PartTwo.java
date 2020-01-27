package algs11;

import java.util.Arrays;
import stdlib.*;

/**
 * This is a skeleton file for your homework. Edit the sections marked TODO. You
 * may also edit the function "main" to test your code.
 *
 * You must not change the declaration of any method. This will be true of every
 * skeleton file I give you.
 *
 * For example, you will get zero points if you change the line
 * <pre>
 *     public static double minValue (double[] list) {
 * </pre>
 * to something like
 * <pre>
 *     public static void minValue (double[] list) {
 * </pre>
 * or
 * <pre>
 *     public static double minValue (double[] list, int i) {
 * </pre>
 * 
 * Each of the functions below is meant to be SELF CONTAINED. This means that
 * you should use no other functions or classes. You should not use any HashSets
 * or ArrayLists, or anything else! In addition, each of your functions should go
 * through the argument array at most once. The only exception to this
 * removeDuplicates, which is allowed to call numUnique and then go through the
 * array once after that.
 */
public class MyFirstHomeworkFor300PartTwo {

	/**
	 * numUnique returns the number of unique values in an array of doubles.
	 * Unlike the previous questions, the array may be empty and it may contain
	 * duplicate values. Also unlike the previous questions, you can assume the
	 * array is sorted.
	 *
	 * Your solution must go through the array exactly once. Your solution must
	 * not call any other functions. Here are some examples (using "=="
	 * informally):
	 *
	 * <pre>
	 *     0 == numUnique(new double[] { })
	 *     1 == numUnique(new double[] { 11 })
	 *     1 == numUnique(new double[] { 11, 11, 11, 11 })
	 *     8 == numUnique(new double[] { 11, 11, 11, 11, 22, 33, 44, 44, 44, 44, 44, 55, 55, 66, 77, 88, 88 })
	 *     8 == numUnique(new double[] { 11, 22, 33, 44, 44, 44, 44, 44, 55, 55, 66, 77, 88 })
	 * </pre>
	 */
	public static int numUnique (double[] list) {
		
		int sum = 0;
		double holder = 0;
		double a = 0;
		for (int i = 0; i < list.length; i++) {
			if (list.length != 0) {
				a = list[i];
				if (a == holder) {
					sum -= 1;
				}
				holder = a;
				sum++;
			}
		}

		return sum;
	}

	
	

	/**
	 * removeDuplicates returns a new array containing the unique values in the
	 * array. There should not be any extra space in the array --- there should
	 * be exactly one space for each unique element (Hint: numUnique tells you
	 * how big the array should be). You may assume that the list is sorted, as
	 * you did for numUnique.
	 *
	 * Your solution may call numUnique, but should not call any other
	 * functions. After the call to numUnique, you must go through the array
	 * exactly one more time. Here are some examples (using "==" informally):
	 *
	 * <pre>
	 *   new double[] { }
	 *     == removeDuplicates(new double[] { })
	 *   new double[] { 11 }
	 *     == removeDuplicates(new double[] { 11 })
	 *     == removeDuplicates(new double[] { 11, 11, 11, 11 })
	 *   new double[] { 11, 22, 33, 44, 55, 66, 77, 88 }
	 *     == removeDuplicates(new double[] { 11, 11, 11, 11, 22, 33, 44, 44, 44, 44, 44, 55, 55, 66, 77, 88, 88 })
	 *     == removeDuplicates(new double[] { 11, 22, 33, 44, 44, 44, 44, 44, 55, 55, 66, 77, 88 })
	 * </pre>
	 */
	public static double[] removeDuplicates(double[] list) {

		int sum = numUnique(list);
		int size = list.length;
		double[] arr = new double[sum];
		double holder = 0;
		double a = 0;
		int count = 0;

		if (size == 0) {
			return list;
		}
		for (int i = 0; i < size; i++) {
			a = list[i];

			if (a != holder) {
				arr[count] = a;
				count++;
			}

			holder = a;
		}

		return arr;

	}

	/**
	 * A test program, using private helper functions.  See below.
	 * To make typing tests a little easier, I've written a function to convert strings to arrays.  See below.
	 */
	public static void main (String[] args) {		
		// for numUnique: array must be sorted
		testNumUnique (0, "");
		testNumUnique (1, "11");
		testNumUnique (1, "11 11 11 11");
		testNumUnique (4, "11 21 31 41");
		testNumUnique (4, "11 11 11 21 31 31 31 31 41");
		testNumUnique (4, "11 21 21 21 31 41 41 41 41");
		testNumUnique (4, "11 11 21 21 21 31 31 41 41 41 41");
		testNumUnique (8, "11 11 11 11 21 31 41 41 41 41 41 51 51 61 71 81 81");
		testNumUnique (8, "11 21 31 41 41 41 41 41 51 51 61 71 81");
		testNumUnique (7, "11 11 11 11 21 31 41 41 41 41 41 51 51 61 71");
		testNumUnique (7, "11 21 31 41 41 41 41 41 51 51 61 71");
		testNumUnique (8, "-81 -81 -81 -81 -71 -61 -51 -51 -51 -51 -41 -41 -31 -21 -11 -11 -11");
		
		// for removeDuplicates: array must be sorted
		testRemoveDuplicates ("", "");
		testRemoveDuplicates ("11", "11");
		testRemoveDuplicates ("11", "11 11 11 11");
		testRemoveDuplicates ("11 21 31 41", "11 21 31 41");
		testRemoveDuplicates ("11 21 31 41", "11 11 11 21 31 31 31 31 41");
		testRemoveDuplicates ("11 21 31 41", "11 21 21 21 31 41 41 41 41");
		testRemoveDuplicates ("11 21 31 41", "11 11 21 21 21 31 31 41 41 41 41");
		testRemoveDuplicates ("11 21 31 41 51 61 71 81", "11 11 11 11 21 31 41 41 41 41 41 51 51 61 71 81 81");
		testRemoveDuplicates ("11 21 31 41 51 61 71 81", "11 21 31 41 41 41 41 41 51 51 61 71 81");
		testRemoveDuplicates ("11 21 31 41 51 61 71", "11 11 11 11 21 31 41 41 41 41 41 51 51 61 71");
		testRemoveDuplicates ("11 21 31 41 51 61 71", "11 21 31 41 41 41 41 41 51 51 61 71");
		testRemoveDuplicates ("-81 -71 -61 -51 -41 -31 -21 -11", "-81 -81 -81 -81 -71 -61 -51 -51 -51 -51 -41 -41 -31 -21 -11 -11 -11");
		StdOut.println ("Finished tests");
	}
	
	private static void testNumUnique (int expected, String list) {
		double[] aList = doublesFromString (list);
		int actual = numUnique (aList);
		if (! Arrays.equals (aList, doublesFromString (list))) {
			StdOut.format ("Failed numUnique([%s]): Array modified\n", list);
		}
		if (expected != actual) {
			StdOut.format ("Failed numUnique([%s]): Expecting (%d) Actual (%d)\n", list, expected, actual);
		}
	}
	private static void testRemoveDuplicates (String expected, String list) {
		double[] aList = doublesFromString (list);
		double[] actual = removeDuplicates (aList);
		if (! Arrays.equals (aList, doublesFromString (list))) {
			StdOut.format ("Failed removeDuplicates([%s]): Array modified\n", list);
		}
		double[] aExpected = doublesFromString (expected);
		// != does not do what we want on arrays
		if (! Arrays.equals (aExpected, actual)) {
			StdOut.format ("Failed removeDuplicates([%s]): Expecting (%s) Actual (%s)\n", list, Arrays.toString (aExpected), Arrays.toString (actual));
		}
	}
	
	
	/* A utility function to create an array of doubles from a string. */
	// The string should include a list of numbers, separated by single spaces.
	private static double[] doublesFromString (String s) {
		if ("".equals (s)) return new double [0]; // empty array is a special case
		String[] nums = s.split (" ");
		double[] result = new double[nums.length];
		for (int i = nums.length-1; i >= 0; i--) {
			try { 
				result[i] = Double.parseDouble (nums[i]); 
			} catch (NumberFormatException e) { 
				throw new IllegalArgumentException (String.format ("Bad argument \"%s\": could not parse \"%s\" as a double", s, nums[i]));
			}
		}
		return result;
	}
}