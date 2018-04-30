/*----------------------------------------------------------------
 *  Author:        Jeffrey Liu
 *  Written:       4/28/2018
 *  Last updated:  4/29/2018
 *
 *  Compilation:   javac-algs4 PercolationStats 
.java
 *  Execution:     java Percolation n t
 *  n = n*n grids to create
 *  t = trials to run to get more accurate results
 *  
 *  We are getting averages, standard devs, and confidence intervals here.
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {

	private int grid;
	private int times;
	private double[] arr;
	private Percolation perc;

   public PercolationStats(int n, int trials){
   	if(n <= 0 || trials <= 0)
   		throw new IllegalArgumentException("nums greater than 0 please");

   		grid = n;
   		times = trials;
   		Stopwatch sw = new Stopwatch();
	   	arr = new double[times];
	   	int prod = grid * grid;

	   	for(int i = 0; i < times; i++){
	   		perc = new Percolation(grid);

	   		while(!perc.percolates()){
	   			int arg1 = StdRandom.uniform(1, grid+1);
	   			int arg2 = StdRandom.uniform(1, grid+1);
	   			perc.open(arg1, arg2);
	   			}
	   		arr[i] = (double)(perc.numberOfOpenSites())/prod;
	   	}
   }

 /**
 *   Calculates the mean based on number of trials and size of grid
 *
 *   
 */
   public double mean(){
 
   	return StdStats.mean(arr);

   }        

 /**
 *   Basic Standard Dev calculation
 */
   public double stddev(){
   	return StdStats.stddev(arr);
   }

/**
 *   Low end of confidence interval
 *
 *   
 */
   public double confidenceLo(){
   	return mean() - ((1.96*stddev()) / Math.sqrt(times));
   }                 

/**
 *   High end of confidence interval
 */
   public double confidenceHi(){
   	return mean() + ((1.96*stddev())/Math.sqrt(times));
   }                  

   public static void main(String[] args){

   	int firstArg = Integer.parseInt(args[0]);
   	int secondArg = Integer.parseInt(args[1]);
   	PercolationStats newstats = new PercolationStats(firstArg,secondArg);
   	System.out.println(newstats.mean());
   	System.out.println(newstats.stddev());
   	System.out.println(newstats.confidenceLo());
   	System.out.println(newstats.confidenceHi());

   
   }      

}