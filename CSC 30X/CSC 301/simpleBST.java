package csc301w19Homework; // please do not change the package 
                           //     or restore before turning in

import algs13.Queue;
import stdlib.StdOut;

/**
 * Version 1.0
 * 
 * This homework is worth 24 points; it has two credit level options.
 * Option 1: complete ToDos 1-4    20/24 points 
 * Option 2: complete ToDos 1-5    24/24 points
 * 
 * effectively, option 1 has a maximum grade of B.  
 * To get a 100% score on the assignment you must complete option 2
 * 
 * You must not change the declaration of any method.
 * You must not change the Node class declaration
 * You may not add instance variables
 * You may not use any other java classes/algorithms without permission
 * 
 * You may create helper functions for Todo's 3, 4, and 5
 * 
 */


/**
 *  The simpleBST class represents a symbol table of
 *  generic key-value pairs using a binary search tree.  
 *  recursive versions of get and put have been renamed 
 *  rGet and rPut to facilitate testing of your non-recursive versions
 *  you may not use rPut or rGet as part of your 'toDo' solutions
 *  a size method is also provided which you may use 
 *  
 */
public class simpleBST<Key extends Comparable<Key>, Value> {
	private Node root;             // root of BST

	static boolean verbose = true;   // set to false to suppress positive test results
	private class Node {
		private Key key;           // key
		private Value val;         // associated data
		private Node left, right;  // left and right subtrees

		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
		}

		/**
		 * this method is provided to facilitate testing
		 */
		public void buildString(StringBuilder s) {
			s.append(left == null ? '[' : '(');
			s.append(key + "," + val);
			s.append(right == null ? ']' : ')');
			if (left != null) left.buildString(s);
			if (right != null) right.buildString(s);
		}
	}
	/**
	 * This method is provided to facilitate testing
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		root.buildString(s);
		return s.toString();
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public simpleBST() {
		root = null;
	}

	/** size
	 * 
	 * return the size of the tree
	 */
	public int size() {
		return size( root);
	}
	private int size(Node x) {
		if ( x == null) return 0;
		return 1 + size(x.left)+size(x.right);
	}
	/** iokeys
	 * 
	 * Returns an Iterable containing the keys of the tree
	 * corresponding to an InOrder traversal
	 * 
	 * Hint: follow the approach outlined in class.
	 * use a helper function to carry out the traversal  
	 *   
	 *   
	 *   To Do 1
	 */
	public Iterable<Key> ioKeys() {
		Queue<Key> qok = new Queue<Key>();  // queue of keys
		if (root == null) return qok;
		ioHelper(root, qok);
		return qok;  //To do 1 complete this method
	}
	
	private void ioHelper(Node n, Queue<Key> q) {
		if (n == null)
			return;
		ioHelper(n.left, q);
		q.enqueue(n.key);
		ioHelper(n.right, q);

	}

	
	/** put
	 * 
	 * Inserts the specified key-value pair into the binary search tree, overwriting the old 
	 * value with the new value if the tree already contains the specified key.
	 * 
	 * ToDo 2   write a non-recursive implementation of put
	 */
	public void put(Key key, Value val) {

		Node n = new Node(key, val);
		if (root == null) {
			root = n;
			return;
		}

		Node parent = null, x = root;
		while (x != null) {
			parent = x;
			int res = key.compareTo(x.key);
			if (res < 0)
				x = x.left;
			else if (res > 0)
				x = x.right;
			else {
				x.val = val;
				return;
			}
		}

		int res = key.compareTo(parent.key);
		if (res < 0)
			parent.left = n;
		else
			parent.right = n;

		return; // To Do 2 complete this method
	}

	/** numNodesWithExactlyOneChild
	 * 
	 * Returns the number of nodes in the tree
	 * that have exactly one child
	 * 
	 * ToDo 3  
	 */
	public int numNodesWithExactlyOneChild() {
		if (root == null) return 0;
		
		return oneHelper(root);
	}
	
	private int oneHelper(Node n)
	{
		if (n == null) {
	        return 0;
	    } else if (n.left == null && n.right != null) {
	        return 1 + oneHelper(n.right);
	    } else if (n.left != null && n.right == null) {
	        return 1 + oneHelper(n.left);
	    } else {
	        return oneHelper(n.left) + oneHelper(n.right);
	    }
		
	}
	
	/** numberOfNodesAtDepth
	 * 
	 * Returns the number of nodes with depth == d
	 * Precondition: none 
	 * 
	 * param:  d  the depth to search for
	 * 
	 * hint:  use a recursive helper function
	 *      
	 * ToDo 4
	 */
	public int numNodesAtDepth(int d) {
		if (root == null) return 0;
		
		return depthHelper(root, d, 0); // ToDo 4  complete this method
	}
	
	private int depthHelper(Node n,int d, int h)
	{
		if (n == null) return 0;
		else if (h == d) return 1;
		return depthHelper(n.left, d, h+1) + depthHelper(n.right,d, h+1);
		
	}
	
	/**
	 * 
	 * delete
	 * 
	 * deletes the key (&value) from the table if the key is present
	 * using the the *dual* of the Hibbard approach from the text. That is, 
	 * for the two-child scenario, delete the node by replacing it 
	 * with it's predecessor (instead of its successor)
	 * 
	 * The functions:  max, deleteMax have been provided for you
	 * 
	 * ToDo 5:  implement a version of delete meeting the above spec
	 * 
	 * if you complete this toDo, comment out or delete the print statement, otherwise leave it in.
	 * 
	 */
	public void delete(Key key) {
		
		deleteHelp(root, key);
		//StdOut.println(" delete not implemented ");
		return;  // ToDo 5  complete this method
	}
	
	private Node deleteHelp(Node x, Key k) {
		if (x == null)
			return null;
		int res = k.compareTo(x.key); // does k come before x.key?
		if (res < 0)
			x.left = deleteHelp(x.left, k);
		else if (res > 0)
			x.right = deleteHelp(x.right, k);
		else // found it
		{

			// case 2

			if (x.right != null && x.left != null) {
				if (x == root) {
					Node t = x;
					x = max(x.left);
					x.left = deleteMax(t.left);
					x.left = t.left;
					x.right = t.right;
					root = x;
					return root;
				}
				Node t = x;
				x = max(x.left);
				x.left = deleteMax(t.left);
				x.left = t.left;
				x.right = t.right;
				return x;
			}

			// case 1
			if (x.left == null) {
				if (x == root) {
					root = root.right;
					return null;
				}
				return x.right;
			}

			else if (x.right == null) {
				if (x == root) {
					root = root.left;
					return null;
				}
				return x.left;
			}

			// case 0
			if (x.left == null && x.right == null) {
				if (x == root) {
					root = null;
					return null;
				}
				return null;
			}

		}

		return x;
	}
	
	/** max
	 * 
	 * return the node in the subtree x with the maximum key  ( right most node)
	 * 
	 * this is a method to help implement the delete method
	 */
	private Node max(Node x) {
		if ( x.right == null) return x;
		return max(x.right);
	}

	/** deleteMax
	 * 
	 * return the subtree x with the node with maximum key deleted
	 * 
	 * 	this is a method to help implement the delete method
	 */
	private Node deleteMax(Node x) {
		if ( x.right == null) return x.left;
		x.right = deleteMax(x.right);
		return x;
	}


	/*****************************************************
	 * 
	 * Utility functions 
	 */

	public Value rGet(Key key) {
		return rGet(root, key);
	}
	private Value rGet(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return rGet(x.left, key);
		else if (cmp > 0) return rGet(x.right, key);
		else              return x.val;
	}
	public void rPut(Key key, Value val) {
		if (key == null) throw new NullPointerException("first argument to put() is null");
		root = rPut(root, key, val);
	}

	private Node rPut(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = rPut(x.left,  key, val);
		else if (cmp > 0) x.right = rPut(x.right, key, val);
		else              x.val   = val;
		return x;
	}

	/* main calls all the testing functions */

	public static void main(String[] args)
	{
		// comment in/out calls to focus on a given ToDo method  				
		iokeysTests();
		putTests();
		numNodesWithExactlyOneChildTests();
		numNodesAtDepthDTests();
		deleteTests();
	}

	/* ioKeysTests
	 * 
	 * parameters to testIoKeys
	 * 1:  String of keys to add to symbol table in order left-to-right
	 * 2:  expected result
	 */
	public static void iokeysTests() {
		testIoKeys("","");
		testIoKeys("HCADMLZ","ACDHLMZ");   
		testIoKeys("ABCDEFG","ABCDEFG");   
		testIoKeys("GFEDCBA","ABCDEFG");  
		testIoKeys("MABC","ABCM");      
		testIoKeys("MGLJK","GJKLM"); 
		testIoKeys("CBADE","ABCDE");   
		StdOut.println("----------- ioKeys tests completed");
	}

	/* putTests
	 * 
	 *  parameters to testPut
	 * 1:  String of keys to add to a symbol table in order left-to-right
	 * 2:  String of vals corresponding to above keys
	 * 3,4:  key,value to 'put'
	 * 5,6:  String of keys,values expected in resulting table in order left-to-right
	 */
	public static void putTests() {
		testPut("CA","31","C","3","CA","31");  // put existing key-val pair, no change
		testPut("HCADMJZGL","123456789","L","9","HCADMJZGL","123456789");    // put existing key-val pair, no change
		testPut("HCADMJZGL","123456789","L","0","HCADMJZGL","123456780");    // update value  in Leaf
		testPut("HCADMJZGL","123456789","C","0","HCADMJZGL","103456789");    // update value  in middle	
		testPut("HCADMJZGL","123456789","B","0","HCABDMJZGL","1230456789");  // Add new kv in middle
		testPut("ABCEFG","123567","D","4","ABCEDFG","1235467");  // straight line right, add new kv in middle
		StdOut.println("----------- put tests  completed");
	}


	/* 	numNodesWithExactlyOneChildTests
	 * 
	 *  parameters to 	testNumNodesWithExactlyOneChild
	 * 1:  String of keys & values to add to a symbol table in order left-to-right
	 * 2:  expected number of nodes with exactly one child
	 */
	public static void numNodesWithExactlyOneChildTests() {
		testNumNodesWithExactlyOneChild("C", 0);  
		testNumNodesWithExactlyOneChild("CA", 1);
		testNumNodesWithExactlyOneChild("BAC", 0);
		testNumNodesWithExactlyOneChild("ABC", 2);
		testNumNodesWithExactlyOneChild("BACD", 1);
		testNumNodesWithExactlyOneChild("CBADE", 2);
		testNumNodesWithExactlyOneChild("DBACFEG", 0);
		testNumNodesWithExactlyOneChild("EAIDFBHCG", 6);
		StdOut.println("----------- numNodesWithExactlyOneChild tests  completed");
	}

	/* 	numNodesAtDepth Tests
	 * 
	 *  parameters to testnumNodesAtDepth
	 * 1:  String of keys & values to add to a symbol table in order left-to-right
	 * 2:  depth to check for
	 * 2:  expected number of nodes at the specified depth
	 */
	public static void numNodesAtDepthDTests() {
		testnumNodesAtDepth("",0, 0);  		// empty tree has no nodes at any depth
		testnumNodesAtDepth("C",0, 1);  
		testnumNodesAtDepth("BAC",0, 1); 
		testnumNodesAtDepth("ABCDEFG",3, 1);
		testnumNodesAtDepth("DBACFEG",1, 2);
		testnumNodesAtDepth("DBACFEG",2, 4);
		testnumNodesAtDepth("DBACFEG",3, 0);
		testnumNodesAtDepth("EAIDFBHCG",4, 2);

		StdOut.println("----------- numNodesAtDepthD tests  completed");
	}
	/** deleteTests
	 * 
	 * parameters to testDelete
	 * 1:  String of keys to add to a symbol table in order left-to-right
	 * 2:  String of vals corresponding to above keys
	 * 3:  key to be deleted
	 * 4,5:  String of keys,values expected in resulting table in order left-to-right
	 */
	public static void deleteTests() {

		testDelete("CA","31","C","A","1");
		testDelete("CAD","314","D","CA","31");
		testDelete("ABCDEFG","1234567","G","ABCDEF","123456");  // straight line right
		testDelete("ABCDEFG","1234567","D","ABCEFG","123567");  // straight line right
		testDelete("GFEDCBA","7654321","A","GFEDCB","765432");  // straight line left
		testDelete("GFEDCBA","7654321","D","GFECBA","765321");  // straight line left
		testDelete("HCADMJZ","1234567", "H", "DCAMJZ","423567"); // delete root
		testDelete("HCADMJZGL","123456789", "M", "HCADLJZG","12349678"); // delete M
		testDelete("MJALBCD","1234567","J", "MDALBC","173456"); // delete J
		StdOut.println("----------- delete tests  completed");
	}

	/** from
	 * builds a BST using the author's version of put
	 */
	public static simpleBST<String, String> from(String keys, String vals) {
		if ( keys.length() != vals.length()) 
			throw new IllegalArgumentException("array sizes do not match");

		simpleBST<String,String> abst = new simpleBST<String, String>();
		for (int i=0; i < keys.length(); i++) {
			abst.rPut(keys.substring(i, i+1),vals.substring(i,i+1));
		}
		return abst;
	}
	/*************************************** testing functions ******************************/

	/**
	 *  testIokeys 
	 * param keys: all substrings of keys of length 1 are added to the ST
	 * param expected:  a string containing the correct inOrder ordering
	 */
	public static void testIoKeys( String keys,  String expected) {

		// create and populate the table from the input string keys
		simpleBST<String,String> aTree = from(keys,keys);

		//  do the test
		Iterable<String> actual = aTree.ioKeys();
		//report result
		String actualString = new String();
		for (String s : actual) actualString += s;
		if ( actualString.equals(expected)) { // test passes
			if (verbose)
				StdOut.format("ioKeys: Correct  Tree Keys [ %s ] Answer: %s\n", keys, expected);
		}
		else
			StdOut.format("ioKeys: *Error*  Tree Keys [ %s ] expected: %s  actual: %s\n", 
					keys, expected, actualString);
	}

	/**
	 *  testNumNodesWithExactlyOneChild 
	 * param keys: all substrings of keys of length 1 are added to the ST
	 * param expected:  the correct result for this input string
	 */
	public static void testNumNodesWithExactlyOneChild( String keys, int expected ) {

		// create and populate the table from the input string 
		simpleBST<String,String> aTree = from(keys,keys);
		//  do the test
		int actual = aTree.numNodesWithExactlyOneChild();

		if ( actual == expected)  {// test passes
			if (verbose)
				StdOut.format("testNumNodesWithExactlyOneChild: Correct   Keys: [ %s ]   actual: %d\n", keys, actual);
		}
		else
			StdOut.format("testNumNodesWithExactlyOneChild: *Error*   Keys: [ %s ]   expected: %d  actual: %d\n", keys, expected, actual);
	}
	/**
	 *  testnumNodesAtDepth 
	 * param keys: all substrings of keys of length 1 are added to the ST
	 * param theDepth:  the depth to check for
	 * param expected:  the correct result for this input string and depth
	 */
	public static void testnumNodesAtDepth( String keys, int theDepth, int expected ) {

		// create and populate the table from the input string 
		simpleBST<String,String> aTree = from(keys,keys);
		//  do the test
		int actual = aTree.numNodesAtDepth(theDepth);

		if ( actual == expected)  {// test passes
			if (verbose)
				StdOut.format("testnumNodesAtDepthD: Correct   Keys: [ %s ]   actual: %d\n", keys, actual);
		}
		else
			StdOut.format("testnumNodesAtDepthD: *Error*   Keys: [ %s ]   expected: %d  actual: %d\n", keys, expected, actual);
	}



	/**
	 *  testPut 
	 * param keys: all substrings of keys of length 1 are added to the ST
	 * param vals: corresponding values for keys
	 * param pKey, pVal:   the key-value pair to be inserted into the ST
	 * param exKeys, exVals:  strings contained the expected ST contents after inserting pKey,pVal
	 * 
	 * this test not check for inserting a null value
	 */

	public static void testPut( String keys, String vals, String pKey, 
			String pVal,String exKeys, String exVals ) {

		// create and populate the table from the input string 
		simpleBST<String,String> testTree = from(keys,vals);
		simpleBST<String,String> expectedTree = from(exKeys,exVals);

		//  do the test
		testTree.put(pKey,pVal);

		String actual = testTree.toString();
		String expected = expectedTree.toString();
		if ( actual.equals(expected) ) {  // test passes 
			if (verbose) 
				StdOut.format("testPut: Correct Keys:[%s] Vals:[%s] \n                 Put:(%s,%s) Result: %s\n", 
						keys, vals, pKey, pVal, actual);
		} else
			StdOut.format("testPut: *Error*  Keys:[%s] Vals:[%s] \n                Put:(%s,%s) Result: %s\n",  
					keys, vals, pKey, pVal, actual);
	}

	/**
	 *  testDelete 
	 * param keys: all substrings of keys of length 1 are added to the ST
	 * param vals: corresponding values for keys
	 * param key:   the key to be deleted
	 * param exKeys, exVals:  strings contained the expected ST contents after deleting key
	 * 
	 */
	public static void testDelete( String keys, String vals, String key, String exKeys, String exVals ) {

		// create and populate the table from the input string 
		simpleBST<String,String> testTree = from(keys,vals);
		simpleBST<String,String> expectedTree = from(exKeys,exVals);
		//  do the test
		testTree.delete(key);
		String actual = testTree.toString();
		String expected = expectedTree.toString();
		if ( actual.equals(expected) ) { // test passes
			if (verbose)
				StdOut.format("testDelete: Correct  Keys:[ %s ]  Vals:[%s] \n           Delete:%s Result: %s\n", 
						keys, vals, key, actual);
		}else
			StdOut.format("testDelete: *Error*  Keys:[ %s ]  Vals:[%s] \n           Delete:%s Result: %s\n",  
					keys, vals, key, actual);
	}


}