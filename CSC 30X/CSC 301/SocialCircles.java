
package csc301w19Homework;
import java.util.Arrays;

import algs41.Graph;
import algs41.GraphGenerator;
import stdlib.*;

/**
 * class socialCircles   version 1.0
 * 
 * computes several 'social circle' (graph) characteristics
 * Terms: the popularity of a vertex is simply its degree
 *        vertices are 'friends' if they are connected
 * 
 * toCompute:   (each characteristic is defined in the corresponding functions below)
 * 
 * numberOfTrios     
 * maxBalanceFactor                               
 * indirectPopularity
 * socialStatus
 * numberOfMostUnbalancedFriendships  
 *
 * There are two levels to this assignment.
 * B-Level
 *     Complete the methods marked ToDo.
 * A-Level
 *     Complete ToDo-A in the method:  
 * 
 * You may add additional instance methods to the SocialCircles class to faciliate completing the ToDos
 * You may NOT add any additional instance variables to the SocialCircles class
 * You may NOT change the Graph class.
 * You may use basic Java arrays, however you may NOT use any additional data structures without permission (e.g. queue)
 * 
 * Note: you are free to change main; we will test your class using a different driver.
 * Some test graphs (with answers) are included in main; you are encouraged to create additional test graphs
 * using the textbook file format ( see data/tinyG.txt  for an example)
 * OR create small simpleConnected graphs using the GraphGenerator class (view with graphViz?) and verify your functions
 * compute the correct values
 * 
 */


public class SocialCircles {
	private int numberOfTrios;                     // the results of the computations are stored
	private int[] indirectPopularity;			   // in these instance variables.
	private int maxBalanceFactor;                  // the values of the variables may be accessed by clients using
	private int numberOfMostUnbalancedFriendships; // the corresponding 'accessor' functions below.
	private int [] socialRank;					   

	// accessor functions
	public int getIndirectPopularity(int v) {	// 	   getIndirectPopularity of vertex v
		return indirectPopularity[v];
	}
	public int getNumberOfTrios() {
		return numberOfTrios;
	}
	public int getMaxBalanceFactor() {
		return maxBalanceFactor;
	}
	public int getNumberOfMostUnBlanancedFriendships() {
		return numberOfMostUnbalancedFriendships;
	}
	public int getSocialRank(int v) {    // get getSocialRank of vertex v
		return socialRank[v];
	}

	// ---end accessors

	/**
	 * degree
	 * 
	 * Suggestion.  copy the degree function (or see if you can write it from scratch) from the textbook.
	 *              you may find it a useful utility function for your functions
	 */

	/**
	 * CountNumberOfTrios
	 * 
	 * determine how many groups of 3 vertices (u,v,w) are directly to connected to each other
	 * Each group should be counted only one time.
	 * 
	 * the functions stores the computed result in the  numberOfTrios  instance variable
	 */
	private void countNumberOfTrios(Graph G) {
		int trio = 0;
		for (int t = 0; t < G.V(); t++) {
			for (int i : G.adj(t)) {
				for (int n : G.adj(t)) {
					if (i != n) {
						for (int test : G.adj(i)) {
							if (test == n)
								trio++;
						}
					}

				}
			}
		}

		numberOfTrios = trio / 6; // ToDo 1 fix this
	}
	
	/**
	 * determineIndirectPopularity
	 * 
	 * concept:  people accrue social status from the people they associate with.
	 * and while an individual may not be 'popular', they may achieve some level
	 * of popularity - indirectly - through the popularity of their friends. This
	 * is the notion of 'indirectPopularity'
	 * 
	 * for each vertex v, determine the maximum popularity of its friends
	 * 
	 *   the IndirectPopularity of a vertex with no friends is 0
	 *   
	 *   store the answer in indirectPopularity[v]
	 */

	private void determineIndirectPopularity(Graph G) {
		int max = 0;
		int temp = 0;
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				temp = G.degree(w);
				if (temp > max)
					max = temp;
			}
			indirectPopularity[v] = max;
			max = 0;// ToDo 2 fix this
		}
	}
	/**
	 * socialRank
	 * 
	 * for each vertex v, determine how many vertices have degree higher than v
	 *     "how many people are more popular than v?"
	 *     store the answer in socialRank[v] 
	 */
	private void determineSocialRank(Graph G) {
		int counter = 0;
		for (int v = 0; v < G.V(); v++) {
			int curDegree = G.degree(v);
			for (int w = 0; w < G.V(); w++) {
				if (v != w) {
					if (G.degree(w) > curDegree)
						counter++;
				}
			}
			socialRank[v] = counter;
			counter = 0;
			// ToDo 3 fix this
		}
	}
	/**
	 * blanancedFriendships
	 * 
	 * say the 'balanceFactor' of a friendship is the difference between the two friends'
	 *          popularities.  If two friends have the same popularity, then the balanceFactor would
	 *          be 0.  If one of the friends had N total friends and the other had 1, then the
	 *          balanceFactor would be N-1 - it would be a 'lopsided' friendship
	 *                            
	 *         the maxBalanceFactor for a graph is the largest balanceFactor for the graph
	 *         (note it would always be >=0)
	 * 
	 * B-Level:
	 *     determine the maximum balanceFactor for the graph.
	 *     store the answer in the maxBalanceFactor instance variable
	 * 
	 * A-Level
	 * 
	 *      determine the number of 'friendships' for which the balanceFactor is largest for the graph
	 *      store the answer in the numberOfMostUnbalancedFriendships instance variable
	 *      
	 *      this level is optional.  if you choose NOT to complete it, simply leave the assignment 
	 *      statement to   numberOfMostUnbalancedFriendships    as given below.
	 * 
	 * Example:  if all vertices have the same number of friends, then all popularites would be the same
	 *           so all balanceFactors would be 0 -  the maxBalanceFactor would be 0
	 *
     *           A-Level:   since all balanceFactors are the same, 
     *                      all friendships would have the maximum balanceFactor
	 *           
	 * Example:  if one vertex 'a' was connected to 5 other vertices (b,c,d,e,f) and there were no other
	 *           edges, then a's popularity would be 5, all the others would be 1.  The balanceFactor for
	 *           all friendships would be  4.  the maxBalanceFactor would be 4
	 *           
	 *           A-Level:   numberOfMostUnbalancedFriendships would be 5.
	 * 
	 */
	private void blanancedFriendships(Graph G) {
		int max = 0;
		for (int u = 0; u < G.V(); u++) {
			for (int v : G.adj(u)) {
					if(G.degree(u) > G.degree(v) && G.degree(u) - 1 > max)
						max = G.degree(u) - 1;
					else if(G.degree(u) < G.degree(v) && G.degree(v) - 1 > max)
						max = G.degree(v) - 1;
				
			}
		}

	    maxBalanceFactor = max;   // ToDo 4  fix this
	    
	    int counter = 0;
	    
	    for (int v = 0; v < G.V(); v++) {
	    	for (int t : G.adj(v))
	    	{
	    		if(G.degree(t)-G.degree(v) == max)
	    			counter++;
	    	}
		}
		
		numberOfMostUnbalancedFriendships = counter;    // toDo 5  optiona A Level  fix this 

	}

	// the constructor  instantiates all instance variables and
	//     calls methods to compute their values for the input graph G
	//     nothing for you to change here

	public SocialCircles(Graph G) {
		indirectPopularity = new int[G.V()];
		socialRank = new int[G.V()];
		countNumberOfTrios(G);
		determineIndirectPopularity(G);
		determineSocialRank(G);
		blanancedFriendships(G);
	}

	// test client
	//
	// use this test client to test your SocialCircles class
	// to perform a test, select an input graph by commenting it  "in" and the others "out" below

	public static void main(String[] args) {

		// the answers for each graph given below are included in a comment in the right margin
		// in order:  # trios, average indirect popularity, average social status, maxBalanceFactor, number of mostUnbalanced Friendships

		In in = new In("data/tinyG.txt" );
		Graph G = GraphGenerator.fromIn (in);		            // 2; 2.92; 4.54; 3; 2
		//Graph G =GraphGenerator.complete(4);  			    // 4; 3.00; 0.00; 0; 6
		//Graph G = GraphGenerator.cycle(8);	                // 0; 2.00; 0.00; 0; 8
		//Graph G  = GraphGenerator.binaryTree(15);			    // 0; 3.00; 4.14; 2; 8
		//Graph G = completeBipartite(1,6);                     // 0; 5.29; 0.86; 5; 6
		
		//Graph G  = GraphGenerator.simpleConnected(50,200);   // answers may vary due to randomness
		                                                     // ballpark:  10-30; 8; 21; 7-9,

		//StdOut.println(G);       // uncomment to have the graph adj-list printed
		//G.toGraphviz ("g.png");  // uncomment to get a picture of the graph

		// create the SocialCircles instance using the graph G
		SocialCircles community = new SocialCircles(G);

		// display the results computed by the SocialCircles instance

		StdOut.format("The number of trios is         %d\n", community.getNumberOfTrios());
		double averageIP =0.0;
		
		for (int v=0; v < G.V(); v++) {
			averageIP += community.getIndirectPopularity(v);
		}
		averageIP /= G.V();
		StdOut.format("Average indirect Popularity:  %5.2f \n", averageIP);
		// determine the average social rank
		double averageSR=0.0;
		for (int v=0; v < G.V(); v++) {
			averageSR+= community.getSocialRank(v);
		}
		averageSR /= G.V();
		StdOut.format("Average social rank:          %5.2f \n", averageSR);
		StdOut.format("The maximum balance factor is  %d\n", community.getMaxBalanceFactor());

		StdOut.format("\nA Level: # of mostUnbalanced Friendships is %d\n", community.getNumberOfMostUnBlanancedFriendships() );
	}
	public static Graph completeBipartite(int m, int n) {
		Graph G = new Graph(n+m);
		for (int i=0; i < n; i++ )
			for (int j=n; j < n+m; j++) {
				G.addEdge(i, j);
			}
		return G;
	}
}

