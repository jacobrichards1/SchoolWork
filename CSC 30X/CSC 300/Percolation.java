package algs15.perc;

import stdlib.*;
import algs15.*;

// Uncomment the import statements above.

// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to the UF data structure,
// except percolates(), which may make up to N calls to the UF data structure.
public class Percolation {
	
	int N;
	boolean[] open;
	WeightedUF full;
	int top = 0;
	int bottom;
	
	// TODO: more fields to add here
	public Percolation(int N) {
		this.N = N;
		this.open = new boolean[N*N];
		int total = N*N;
		bottom = total + 1;
		
		full = new WeightedUF(total + 2);
		for (int j = 0; j < total; j++)
		{
			open[j] = false;
		}
		
		for (int i = 0; i < N; i++)
		{
			full.union(total, i);
		}
	}
	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		
		int index = (i * N) + j;
		
			open[index] = true;
		
		if (i > 0 && isOpen(i-1, j))
		{
			full.union(index, (i-1)*N+j);
		}
		
		if (i < N-1 && isOpen(i+1, j))
		{
			full.union(index, (i+1)*N+j);	
		}
		
		if (j > 0 && isOpen(i, j - 1))
		{
			full.union(index, i*N+(j-1));
		}
		
		if (j < N-1 && isOpen(i, j + 1))
		{
			full.union(index, i*N+(j+1));
		}
		
		
		open[i*N+j] = true;
		// TODO: more to do here.
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return open[i*N+j];
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		
		if(isOpen(i,j))
		{
			if(full.connected(i*N+j, N*N))
				return true;
		}
		
		return false;
	}
	// does the system percolate?
	public boolean percolates() {
		for(int i = 0; i<N; i++)
		{
			if(isFull(N-1, i))
			{
				return true;
			}
		}
		return false;
	}
}