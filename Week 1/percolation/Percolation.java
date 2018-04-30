/*----------------------------------------------------------------
 *  Author:        Jeffrey Liu
 *  Written:       4/28/2018
 *  Last updated:  4/28/2018
 *
 *  Compilation:   javac-algs4 Percolation
.java
 *  Execution:     java Percolation

 *  This program performs percolation on an n*n times
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import edu.princeton.cs.algs4.UF;
// import edu.princeton.cs.algs4.QuickFindUF;


public class Percolation {

	private boolean[] grid; //site to track openness
	private boolean[] fullgrid;
	private int storeVar; // stores n
	private WeightedQuickUnionUF unionFind; //algo used to union(), find(), connected()
	private WeightedQuickUnionUF backwash; // prevent backwash
	private int virtualTop; //top stored in grid[0]
	private int virtualBottom; //bottom stored in grid[storeVar - 1]
	private int openSites;
	
/**
 *   Creates n*n grid 
 *
 *   Throws a NullPointerException if a is null. This constructure also initializes grid for openness, fullgrid for
 * 	 fullness, virtualTop and virtualBottom 
 */
	public Percolation(int n){
		if (n <= 0)
			throw new IllegalArgumentException("input must be greater than 0");
		storeVar = n; 
		grid = new boolean[n*n + 2];  
		fullgrid = new boolean[n*n + 2];
		virtualTop = 0; 
		virtualBottom = grid.length-1; 
		grid[0] = true; 
		grid[grid.length-1] = true; 
		fullgrid[0] = true;
		unionFind = new WeightedQuickUnionUF(grid.length);
		backwash = new WeightedQuickUnionUF(n*n + 1);
		openSites = 0;

	}

	// open site( row, col) if it is not open already
	public void open(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar) 
			throw new IllegalArgumentException("row index i out of bounds");

		// System.out.println("\n We're opening again! ");
		int currIndex = ((row-1)*storeVar) + col;

		if (isOpen(row, col)) return;
		openSites++;
		grid[currIndex] = true;


		if (currIndex < storeVar+1 ){
			unionFind.union(currIndex, virtualTop);
			backwash.union(currIndex, virtualTop);
			fullgrid[currIndex] = true;
		}
		if ((currIndex < grid.length - 1) && (currIndex >= (grid.length - 1 - storeVar))){
			// System.out.println("Confirmed that bottom is connected");
			unionFind.union(currIndex, virtualBottom);
		}

		
		if(row + 1 <= storeVar && isOpen(row + 1, col)){
			if (!unionFind.connected(currIndex, currIndex+storeVar)) {
				unionFind.union(currIndex, currIndex+storeVar);
				backwash.union(currIndex, currIndex+storeVar);
			}
		}
		if(col + 1 <= storeVar && isOpen(row , col+1)){
			if (!unionFind.connected(currIndex, currIndex+1)) {
				unionFind.union(currIndex, currIndex+1);
				backwash.union(currIndex, currIndex+1);
			}
		}
		if(row - 1 > 0 && isOpen(row - 1, col)){
			if (!unionFind.connected(currIndex, currIndex-storeVar)){ 
				unionFind.union(currIndex, currIndex-storeVar);
				backwash.union(currIndex, currIndex-storeVar);
			}
		}
		if(col - 1  > 0 && isOpen(row , col-1)){
			if (!unionFind.connected(currIndex, currIndex-1)){
			 unionFind.union(currIndex, currIndex-1);
			 backwash.union(currIndex, currIndex-1);
			}
		}
	}

	//is site (row, col) open?
	public boolean isOpen(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar)
			throw new IllegalArgumentException("row index i out of bounds");

		return grid[(row-1)*storeVar + (col)];
	}

	//is site (row, col) full?
	public boolean isFull(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar) 
			throw new IllegalArgumentException("row index i out of bounds");

		int currIndex = ((row-1)*storeVar) + (col);

		if(backwash.connected(virtualTop, currIndex)){
			System.out.println("Filled in new " + row + "\t" + col);
			fullgrid[currIndex] = true;
		}

		return fullgrid[currIndex];
	}

	// number of open sites
	public int numberOfOpenSites(){ return openSites; }

	// does the system percolate?
	public boolean percolates(){
		return unionFind.connected(virtualBottom, virtualTop);
	}

	//test client
	public static void main(String[] args){
		Percolation test1 = new Percolation(5);
		test1.percolates();
	}
}