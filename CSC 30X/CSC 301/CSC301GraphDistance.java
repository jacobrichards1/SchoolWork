package csc301w19Homework;  

import algs41.Graph;
import algs41.GraphGenerator;
import algs13.Queue;
import stdlib.*;


//  Version 1.0 
//
//This is basically exercise 4.1.16 from the text
//   see the exercise and/or video overview for definitions and hints
//
//  The provided structure follows the design pattern illustrated
//  by the examples in 4.1
//
// you're free to add instance variables and other methods
// you should add in code to support bfs 
//	     feel free to grab and adapt this from the text and/or algs41
//  you might find q or stack to be useful, if so use
//  the versions from algs13
//
//  you shouldn't need (or use) anything else, ask me if not sure

// you must document your code to explain your approach
// If I can't follow what you're doing, you will get reduced (or no) credit

public class CSC301GraphDistance {

	int[] eccentricity;         // the eccentricity of each vertex
	int diameter;               // the diameter of the graph
	int radius;	                // the radius of the graph

	// this method just illustrates what throwing an exception might look like
	// you can delete this method
	private void anExampleMethod(Graph G) {
		boolean somethingUnpected;
		// pretend we did some investigation of G here
		somethingUnpected = true;

		if (somethingUnpected)
			throw new IllegalArgumentException("something bad happened");
	}

	//  The constructor will initiate all the calculations 
	//  and store the results in the instance variables above
	//
	public CSC301GraphDistance(Graph G) {

		this.eccentricity = new int[G.V()];
		int diameter = Integer.MIN_VALUE;   // this is not the class instance variable
		int radius = Integer.MAX_VALUE;     // this is not the class instance variable   -- see below
		
		// If G.V()==0, then the above values are correct 
		//   otherwise the code below should update them to the correct values for the graph G

		// If G is not connected, you should throw a new IllegalArgumentException()  
		// This will require that you traverse the graph starting from some node (say 0)

		// I suggest that you first get this to work for a connected graph
		// You can then adjust your code so that it throws an exception in the case that all nodes are not visited


		//anExampleMethod(G);   // just to illustrate calling a method that throws an exception
		// note the try-catch block in main
		// try running the program with this commented in/out
		// you can delete this call in your final version


		// TODO
		// add code here to compute the eccentricity of each vertex, the diameter, the radius 
		// you will probably want to create some methods(functions) and just call them from here
		
			
		testConnected(G, 0); //used to check if there are more than one connected component

		for (int i = 0; i < G.V(); i++) {
			this.eccentricity[i] = bfs(G, i); //sets the eccentricity of each vertex

			for (int otherVertex = 0; otherVertex < G.V(); otherVertex++) { //if the vertex is itself then it goes to next iteration of the loop
				if (otherVertex == i)
					continue;
			}
			if (eccentricity[i] > diameter) {//if eccentricity is bigger than the diameter, set the diameter to the current eccentricity
				diameter = eccentricity[i];
			}
			if (eccentricity[i] < radius) {//if radius is bigger than the eccentricity, set the radius to the current eccentricity
				radius = eccentricity[i];
			}
		}
		this.diameter = diameter;
		this.radius = radius;

		

	}

	private int bfs(Graph G, int s) { //bfs used for finding the eccentricity of the graph
		Queue<Integer> q = new Queue<>(); //most of the code copied from BreadthFirstPaths
		boolean[] marked = new boolean[G.V()];
		int[] distTo = new int[G.V()];

		marked[s] = true;
		distTo[s] = 0;
		q.enqueue(s);

		int eccentricity = 0;

		while (!q.isEmpty()) {
			int thisVertex = q.dequeue();
			for (int w : G.adj(thisVertex)) {
				if (!marked[w]) {
					distTo[w] = distTo[thisVertex] + 1;
					marked[w] = true;
					q.enqueue(w);

					if (distTo[w] > eccentricity) { //if the distance is greater than the eccentricity
						eccentricity = distTo[w];// set the eccentricity to the distance to w
					}
				}
			}
		}

		return eccentricity;
	}
	
	
	
	private void testConnected(Graph G, int s) { //BFS for testing if the there are connected components
		boolean isConnected = true; //most of same code for BFS but no distTo
		Queue<Integer> q = new Queue<>();
		boolean[] marked = new boolean[G.V()];

		marked[s] = true;
		q.enqueue(s);

		while (!q.isEmpty()) {
			int thisVertex = q.dequeue();
			for (int w : G.adj(thisVertex)) {
				if (!marked[w]) {
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}

		for (int i = 0; i < G.V(); i++) { //loop to check if marked is ever false
			if (!marked[i])
				isConnected = false;// if marked[i] is false there are at least 2 components and then change isConnected to false
		}

		if (!isConnected) //checks isConnected and throws a exception if isConnected is false
			throw new IllegalArgumentException("Graph is not connected");
	}
	
	// Do not change the following constant time methods
	public int eccentricity(int v) { return eccentricity[v]; }
	public int diameter()          { return diameter; }
	public int radius()            { return radius; }
	public boolean isCenter(int v) { return eccentricity[v] == radius; }

	public static void main(String[] args) {
		// ToDo   test your class with different graphs by commenting in/out graphs below    

		//Graph G = GraphGenerator.fromIn(new In("data/tinyG.txt")); // this is non-connected -- should throw an exception
		//Graph G = GraphGenerator.connected (10, 20, 2); // Random non-connected graph -- should throw an exception
		//Graph G = GraphGenerator.fromIn(new In("data/tinyCG.txt")); // diameter=2, radius=2, every node is a center
		//Graph G = GraphGenerator.binaryTree (10); // A complete binary tree
		//Graph G = GraphGenerator.path (6); // A path -- diameter=V-1
		Graph G = GraphGenerator.connected (20, 100); // Random connected graph

		//StdOut.println(G);       // comment in if you want to see the adj list
		//G.toGraphviz ("g.png");  // comment in if you want a png of the graph and you have graphViz installed

		//  nothing to change below here
		try {
			CSC301GraphDistance theGraph = new CSC301GraphDistance(G);
			for (int v = 0; v < G.V(); v++)
				StdOut.format ("eccentricity of %d: %d\n", v, theGraph.eccentricity (v));
			StdOut.format ("\ndiameter = %d\n\nradius = %d\n\n", theGraph.diameter(), theGraph.radius() );
			StdOut.format ("checking for centers... \n" );
			for (int i = 0; i < G.V(); i++) {
				if ( theGraph.isCenter(i))
					StdOut.format ("center=%d\n", i);
			}
			StdOut.format ("done. \n" );
		} 
		catch (IllegalArgumentException e) {
			StdOut.println( " Exception was caught: " + e.getMessage());
		}
	}
}
