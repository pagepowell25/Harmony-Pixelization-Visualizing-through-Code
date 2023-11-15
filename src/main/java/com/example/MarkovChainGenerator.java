
/*
 * Modified by Page Powell 
 * Class: Project 2 
 * Description: Creates the transition table and the markov chain  
 */

package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MarkovChainGenerator<E> extends ProbabilityGenerator<E>
{
	 //declares new ArrayList for the values found for the transition table 
		ArrayList<ArrayList<Float>> transitionTable = new ArrayList<ArrayList<Float>>(); //creates the 2D ArrayList 
		ProbabilityGenerator <E> genProb = new ProbabilityGenerator<>(); //creates instance of probability generator


	void train (ArrayList <E> newTokens){  //method creates the Transition Table 
		//need to add rows to transition table everytime you check each token
		
		int lastIndex = -1; //keeps track of the index of the previous token in the alphabet array 
		
		for(int i = 0; i < newTokens.size(); i++) { //loop goes through the tokens in the newTokens array 

			int tokenIndex = alphabet.indexOf(newTokens.get(i));  //keeps track of the token that is currently being checked 

			if(tokenIndex == -1) { //if the current token is not found in the alphabet array 

				tokenIndex = alphabet.size(); //sets the tokenIndex equal to the size of the alphabet array
				
				//adds new rows to transition table 
				ArrayList<Float> newRow = new ArrayList<Float>(); //adds a new row everytime there is a new token 

				for (int k = 0; k < alphabet.size(); k++) { //sets rows to 0, and makes length of rows the same as the alphabet array 
						newRow.add(0.0f); //sets row to 0 
					}
					transitionTable.add(newRow); //adds the new row to the transitionTable array (expands vertically)
					

				for(int j = 0; j < transitionTable.size(); j++ ){ //adds a new row to the transition table after evey loop 
					transitionTable.get(j).add(0.0f);
				}
				alphabet.add(newTokens.get(i));  //adds the new token to the alphabet array  

			}
			if(lastIndex > -1) {  //adds counts to the transitionTable for each token (when they are found)
				ArrayList<Float> newRow = transitionTable.get(lastIndex); //retrieves the correct row from the transition table 
				newRow.set(tokenIndex, newRow.get(tokenIndex)+1); //locates the correct column and then adds a count to that current index (tokenIndex)
			} 
			lastIndex = tokenIndex; 
			
			
		}

		genProb.train(newTokens);  //training my probability generator object 

	} //end of train 


	public float sumRow(ArrayList<Float> newRow){  //function that finds the total sum of a row 
			float sumOfRow = 0;
			for (int j = 0; j < newRow.size(); j++) { //loops through rows in transition table 
        	          sumOfRow += newRow.get(j);   //adds the values in each row to find the total
			}// end of for 
			return sumOfRow; //returns the sum of a row 
	}//end of sumRow 

		public E generate(E initToken)
		{
			float sum = 0;
			int initIndex = 0;
			
			initIndex = alphabet.indexOf(initToken); //finds the index of the init token from alphabet. maybe in a for loop?
			sumSymbols = sumRow(transitionTable.get(initIndex)); //find the specific row based on what the index of initToken is
			tokenCounts = transitionTable.get(initIndex);
					
			if (sumSymbols == 0){
				return genProb.generate(1).get(0); //returning an array
			}
			else{
				return super.generate(1).get(0);  //calls generate function from probability generator class
			}
			
		
			
		} //end of first generate function 

		ArrayList<E> generate(E initToken, int numberOfTokens){  //creates the markov chain, calls the previous generate 
			ArrayList<E> newNotes = new ArrayList<E>(); //array that stores the new notes created 
			for(int i = 0; i < numberOfTokens; i++){
				E genToken = generate(initToken); //generates a new token based on the pervious token 
				newNotes.add(genToken);  //adds the newly generated token to the array holding new notes 
				initToken = genToken; //the current token becomes the "previous token" 

			} //end of for loop for numberOfTokens 

			return newNotes;  //returns the array of new notes created 
			
		} //end of second generate function 
		

		ArrayList<E> generate(int numberOfTokens){ //starts the markov chain
			E firstToken = genProb.generate(1).get(0);  //calls generate function on the token by using the instance of probability generator 
			return generate(firstToken,numberOfTokens); //returns the array that the previous generate function produces 

	} //end of third generate function 
		
		

  	//nested convenience class to return two arrays from sortTransitionTable() method
	//students do not need to use this class
	protected class SortTTOutput
	{
		public ArrayList<E> symbolsListSorted;
		ArrayList<ArrayList<Float>> ttSorted;
	}

	//sort the symbols list and the counts list, so that we can easily print the probability distribution for testing
	//symbols -- your alphabet or list of symbols (input)
	//tt -- the unsorted transition table (input)
	//symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	//ttSorted -- the transition table that changes reflecting the symbols sorting to remain accurate  (output)
	public SortTTOutput sortTT(ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt)	{

		SortTTOutput sortArraysOutput = new SortTTOutput(); 
		
		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.ttSorted = new ArrayList<ArrayList<Float>>();
	
		//sort the symbols list
		Collections.sort(sortArraysOutput.symbolsListSorted, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});

		//use the current sorted list to reference the counts and get the sorted counts
		for(int i=0; i<sortArraysOutput.symbolsListSorted.size(); i++)
		{
			int index = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(i));
			sortArraysOutput.ttSorted.add(new ArrayList<Float>());
			for( int j=0; j<tt.get(index).size(); j++)
			{
				int index2 = symbols.indexOf(sortArraysOutput.symbolsListSorted.get(j));
				sortArraysOutput.ttSorted.get(i).add(tt.get(index).get(index2));
			}
		}

		return sortArraysOutput;

	}


	public ArrayList<ArrayList<Float>> findProbability() //finds the probability of an index in a specific row 
	{
		ArrayList<ArrayList<Float>> tt = new ArrayList<ArrayList<Float>>();
		for (int i = 0; i < transitionTable.size(); i++) {
    	    ArrayList<Float> newRow = transitionTable.get(i);  //retrieve each row from the transition table 
			double sumOfRow = 0;
			

			for (int j = 0; j < newRow.size(); j++) { //adds the values in each row to find the total 
        	          sumOfRow += newRow.get(j);
			}
			ArrayList<Float> row = new ArrayList(); 
			
			for(int j=0; j < newRow.size(); j++) {  //loops through each row
				
				double prob = 0;  //sets probability to 0 if there are no values in the row 

				if(sumOfRow != 0) {  //divides the number of times a token appears in the array by the total of values in a row 
					prob  = newRow.get(j)/sumOfRow;  //finds the probability that a certain token will appear 
				}
				row.add((float)prob);  //adds the probability to each row 
			}
			tt.add(row);  //adds the probability value into the transition table 

			
	} //end of 1st for
	
	return tt;
	
	} //end of findProbability()

	
	
	//this prints the transition table
	//symbols - the alphabet or list of symbols found in the data
	//tt -- the transition table of probabilities (not COUNTS!) for each symbol coming after another
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<ArrayList<Float>> tt)
	{
		//sort the transition table
		SortTTOutput sorted = sortTT(symbols, tt);
		symbols = sorted.symbolsListSorted;
		tt = sorted.ttSorted;

		System.out.println("-----Transition Table -----");
		
		// System.out.println(symbols);
		
		for (int i=0; i<tt.size(); i++)
		{
			System.out.print("["+symbols.get(i) + "] ");
			for(int j=0; j<tt.get(i).size(); j++)
			{
				// if(round == true)
				// {
					
					DecimalFormat df = new DecimalFormat("#.##");
					// System.out.print(df.format((double)tt.get(i).get(j) + " "));
				// }
				// else
				// {
					// System.out.print((double)tt.get(i).get(j) + " ");
					System.out.printf("%.2f ", tt.get(i).get(j));

				// }
			
			}
			System.out.println();
			


		}
		System.out.println();
		
		System.out.println("------------");
	} 

	
	public void printProbabilityDistribution(boolean round)
	{
		printProbabilityDistribution(round, alphabet, findProbability());  //prints the information in the transition table 
	
	}

}
