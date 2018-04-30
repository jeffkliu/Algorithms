/*----------------------------------------------------------------
 *  Author:        Jeffrey Liu
 *  Written:       4/28/2018
 *  Last updated:  4/28/2018
 *
 *  Compilation:   javac-algs4 Perlocation.java
 *  Execution:     java Perlocation
 *  
 *
 *----------------------------------------------------------------*/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.*;
import java.lang.*;

public class Perlocation {

	private static int[] grid; //grid-creation
	private static int storeVar; // stores n
	private static HashMap<Integer, Boolean> openGrid; //grid to map openness

	
/**
 *   Creates n*n grid 
 *
 *   Throws a NullPointerException if a is null.
 */
	public Perlocation(int n){
		if (n <= 0)
			throw new IllegalArgumentException("input must be greater than 0");
		grid = new int[n*n];
		storeVar = n;
		openGrid = new HashMap<Integer, Boolean>();
		for(int i = 0; i < grid.length; i++){
			openGrid.put(i, false);
		}
	}

	// open site( row, col) if it is not open already
	public void open(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar) 
			throw new IndexOutOfBoundsException("row index i out of bounds");
		openGrid.replace((row-1)*5 + (col - 1), true);
	}

	//is site (row, col) open?
	public boolean isOpen(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		return openGrid.get((row-1)*5 + (col - 1));
	}

	//is site (row, col) full?
	public boolean isFull(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (openGrid.get((row-1)*5 + (col - 1)) == false){
			return true;
		}
		return false;
	}

	// number of open sites
	public int numberOfOpenSites(){
		int counter = 0;
		for(int i = 0; i < grid.length; i++){
			if (openGrid.get(i) == true)
				counter++;
		}
		return counter;
	}

	// does the system percolate?
	public boolean percolates(){
		return false;
	}

	//test client
	public static void main(String[] args){
		Perlocation test1 = new Perlocation(5);
		System.out.println(grid.length);
	}
}