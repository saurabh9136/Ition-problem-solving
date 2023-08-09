package com.hibernate.BitCoinRate;

import java.util.*;
public class CountOrchards {

	// implementing method which count connected trees
	 public static List<Integer> CountOrchardSize (char[][] orchards) {
		 
		 List<Integer> orchardSize = new ArrayList<>(); // taking a list of integers to store the size
		 
		 for(int row= 0; row<orchards.length; row++) { // iterate on matrix of orchards
			 
			 for(int col = 0; col<orchards[row].length; col++) {
				 if(orchards[row][col] == 'T') { // values of index is 'T' then we can start calculating the size vertical, horizontal, diagonal
					 int size = countConnectedTrees(orchards, row, col); // method return the size of connected trees trees 
					 orchardSize.add(size);
				 }
			 }
		 }
		 return orchardSize;
	 }
	 public static int countConnectedTrees(char[][] orchards,int row, int col) {
		 // base case
		 if(row < 0 || row >= orchards.length  || col < 0 || col >= orchards[row].length || orchards[row][col] != 'T'  ) {
			 return 0;
		 }
		 // because T is founded in this index
		 orchards[row][col] = 'O'; // Mark the current cell as visited
		 int size = 1; // Size of the current orchard
		 
		 // using recursion to repetitively move on each cell
		 size += countConnectedTrees(orchards, row-1, col); //up
		 size += countConnectedTrees(orchards, row+1, col); //down
		 size += countConnectedTrees(orchards, row, col-1); //left
		 size += countConnectedTrees(orchards, row, col+1); //right
		 size += countConnectedTrees(orchards,  row + 1, col + 1); // Check diagonal down-right
	     size += countConnectedTrees(orchards,  row - 1, col - 1); // Check diagonal up-left
	     size += countConnectedTrees(orchards,  row + 1, col - 1); // Check diagonal down-left
	     size += countConnectedTrees(orchards,  row - 1, col + 1); // Check diagonal up-right

		 
		 return size;
	 }
	
	public static void main(String[] args) {
		

		char orchards[][] = {
				{'T', 'T', 'O', 'O'},
				{'O', 'T', 'O', 'T'},
				{'O', 'O', 'O', 'T'},
				{'O', 'T', 'O', 'T'}
		};
		
		
		 List<Integer> orchardSizes = CountOrchardSize(orchards);
		 System.out.println("Sizes of Orchards: " + orchardSizes);
	}

}
