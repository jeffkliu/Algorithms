/*----------------------------------------------------------------
 *  Author:        Jeffrey Liu
 *  Written:       4/28/2018
 *  Last updated:  4/28/2018
 *
 *  Compilation:   javac-algs4 Percolation
.java
 *  Execution:     java Percolation

 *  
 *
 *----------------------------------------------------------------*/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.UF;

public class Percolation {

	private static boolean[] grid; //grid-creation
	private static int storeVar; // stores n
	private UF unionFind; //algo used to union(), find(), connected()
	private static int virtualTop; //top stored in grid[0]
	private static int virtualBottom; //bottom stored in grid[storeVar - 1]
	private static int openSites;
	
/**f
 *   Creates n*n grid 
 *
 *   Throws a NullPointerException if a is null.
 */
	public Percolation(int n){
		if (n <= 0)
			throw new IllegalArgumentException("input must be greater than 0");
		storeVar = n; grid = new boolean[n*n + 2]; 
		virtualTop = 0; virtualBottom = grid.length-1; grid[0] = true; grid[grid.length-1] = true;
		unionFind = new UF(grid.length);
		openSites = 0;

	}

	// open site( row, col) if it is not open already
	public void open(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar) 
			throw new IndexOutOfBoundsException("row index i out of bounds");

		int currIndex = (row-1)*storeVar + (col);
		System.out.println("This is currIndex: " + currIndex);
		grid[currIndex] = true;

		openSites++; //opens site

		if (currIndex < storeVar+1){
			System.out.println("Confirmed that top is connected");
			unionFind.union(virtualTop, currIndex);
		}

		if ( (currIndex < grid.length - 1) && (currIndex >= (grid.length - 1 - storeVar)) ){
			System.out.println("Confirmed that bottom is connected");
			unionFind.union(virtualBottom, currIndex);
		}


		if(row + 1 <= storeVar && isOpen(row + 1, col)){
			System.out.println("Row: " + (row + 1) + "\tCol: " + col);
			if (!unionFind.connected(currIndex, currIndex+storeVar)) unionFind.union(currIndex, currIndex+storeVar);
		}
		if(col + 1 <= storeVar && isOpen(row , col+1)){
			System.out.println("Row: " + row  + "\tCol: " + (col +1));
			if (!unionFind.connected(currIndex, currIndex-1)) unionFind.union(currIndex, currIndex+1);
		}
		if(row - 1 > 0 && isOpen(row - 1, col)){
			System.out.println("Row: " + (row - 1) + "\tCol: " + col);
			if (!unionFind.connected(currIndex, currIndex-storeVar)) unionFind.union(currIndex, currIndex-storeVar);
		}
		if(col - 1  > 0 && isOpen(row , col-1)){
			System.out.println("Row: " + row  + "\tCol: " + (col - 1));
			if (!unionFind.connected(currIndex, currIndex-1)) unionFind.union(currIndex, currIndex-1);
		}

		System.out.println("This is the root of currentIndex: " + unionFind.find(currIndex));

	}

	//is site (row, col) open?
	public boolean isOpen(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		return grid[(row-1)*storeVar + (col)];
	}

	//is site (row, col) full?
	public boolean isFull(int row, int col){
		if (row <= 0 || col <= 0 || row > storeVar || col > storeVar)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		return isOpen(row,col) ? false : true;
	}

	// number of open sites
	public int numberOfOpenSites(){
		return openSites;
	}

	// does the system percolate?
	public boolean percolates(){

	/*
		for(int i = 1; i<storeVar+1; i++){
			if(grid[i]) unionFind.union(virtualTop, i);
			if(grid[grid.length-1-i]) unionFind.union(virtualBottom, grid.length-1-i);
		}
	*/

		return unionFind.connected(virtualTop,virtualBottom);
	}

	//test client
	public static void main(String[] args){
		Percolation test1 = new Percolation(5);
		test1.percolates();
	}
}