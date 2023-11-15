/*
 * Page Powell
 * (Modified version of template created by Courtney Brown)
 * Class: ProbabliityGenerator
 * Description: calculates the probability that each specifc note will appear in the sequence 
 */


package com.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ProbabilityGenerator <E>
{
	ArrayList<E> alphabet = new ArrayList<E>();
	ArrayList<Float> tokenCounts = new ArrayList<Float>();
	double tokenCount = 0;
	float sumSymbols;

	void train(ArrayList<E> newTokens) //arraylist of the new tokens created 
	{
		
	for (int i = 0; i < newTokens.size(); i++ ){  //loop that goes through the array of new tokens 	
		
			int index = alphabet.indexOf(newTokens.get(i));  //sets index equal to the index of the alphabet array which the new tokens are being created from
	
			
		if(index == -1) {   
			index = alphabet.size();  //index equals the size of the alphabet array 
			alphabet.add(newTokens.get(i)); //adds the newTokens[i] to the alphabet array 
			
			tokenCounts.add((float) 0); //adds a 0 to the tokencounts/alphabetcounts array
			}
			
			Float newCount = tokenCounts.get(index)+1; //adds one value to the array tokenCounts 
			tokenCounts.set(index, newCount); //sets the new array newCount with the same info as original array tokenCounts

			
			tokenCount++;  //increase the token size 
			
					
		} 	

		sumSymbols = newTokens.size(); //keeps track of the total number of tokens 
			
	
	}

	 ArrayList<E> generate(float sum) //generate function that generates notes to play based on probability distribution 
	{
		
		ArrayList<Float> probabilityGen = new ArrayList<Float>(); //creates a new array for the probability distribution 
		for(int i = 0; i < tokenCounts.size(); i++) {  //loops through the array of tokens 
			Float probability = tokenCounts.get(i) / sumSymbols;  //keeps track of the probability of a certain token. Divides number of a specific token by total number of tokens. 
			probabilityGen.add(probability);  //adds the probability of a token to the array of probabilities for all tokens
		}
		System.out.println(probabilityGen);
		
		ArrayList<E> newNotes = new ArrayList<E>(); //keeps track of the notes that are produced from the probability generator and random generator 
		for(int i = 0; i < sum; i++){ //loop for creating 20 new notes 
			float rIndex = (float) Math.random(); //need to generate random tokens based on proabbility distribution  
			float prob = probabilityGen.get(0); //the probability of the token that is the position being checked 
			

			for(int j = 0; j < probabilityGen.size(); j++){ //loop that checks the probabilities that are in the array 
							

					if (rIndex < prob || j==probabilityGen.size()-1){ //if statement compares random probability to current probability by looking at the token probabilities in the probabilityGen array 
					

					newNotes.add(alphabet.get(j)); //the new note that is produced from matching it to its probabbility is retrieved from the alphabet array that contains the notes 
						break;
				 } 
				else {
					
				prob += probabilityGen.get(j+1);  //after reading the probability of a token and matching it to a specific note, moves to the next index to check 
				 }
				


			}
			

		}
		return newNotes; //returns the new notes produced and exits method
	}
	//need to create loop that compares random token  to tokens in alphabet 
	//newToken/tokenCount?
		
		


	//nested convenience class to return two arrays from sortArrays() method
	//students do not need to use this class
	protected class SortArraysOutput
	{
		public ArrayList<E> symbolsListSorted;
		public ArrayList<Float> symbolsCountSorted;
	}

	//sort the symbols list and the counts list, so that we can easily print the probability distribution for testing
	//symbols -- your alphabet or list of symbols (input)
	//counts -- the number of times each symbol occurs (input)
	//symbolsListSorted -- your SORTED alphabet or list of symbols (output)
	//symbolsCountSorted -- list of the number of times each symbol occurs inorder of symbolsListSorted  (output)
	public SortArraysOutput sortArrays(ArrayList<E> symbols, ArrayList<Float> counts)	{

		SortArraysOutput sortArraysOutput = new SortArraysOutput(); 
		
		sortArraysOutput.symbolsListSorted = new ArrayList<E>(symbols);
		sortArraysOutput.symbolsCountSorted = new ArrayList<Float>();
	
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
			sortArraysOutput.symbolsCountSorted.add(counts.get(index));
		}

		return sortArraysOutput;

	}
	
	//Students should USE this method in your unit tests to print the probability distribution
	//HINT: you can overload this function so that it uses your class variables instead of taking in parameters
	//boolean is FALSE to test train() method & TRUE to test generate() method
	//symbols -- your alphabet or list of symbols (input)
	//counts -- the number of times each symbol occurs (input)
	//sumSymbols -- the count of how many tokens we have encountered (input)
	public void printProbabilityDistribution(boolean round, ArrayList<E> symbols, ArrayList<Float> counts, double sumSymbols)
	{
		//sort the arrays so that elements appear in the same order every time and it is easy to test.
		SortArraysOutput sortResult = sortArrays(symbols, counts);
		ArrayList<E> symbolsListSorted = sortResult.symbolsListSorted;
		ArrayList<Float> symbolsCountSorted = sortResult.symbolsCountSorted;

		System.out.println("-----Probability Distribution-----");
		
		for (int i = 0; i < symbols.size(); i++)
		{
			if (round){
				DecimalFormat df = new DecimalFormat("#.##");
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: " + df.format((double)symbolsCountSorted.get(i) / sumSymbols));
			}
			else
			{
				System.out.println("Data: " + symbolsListSorted.get(i) + " | Probability: " + (double)symbolsCountSorted.get(i) / sumSymbols);
			}
		}
		
		System.out.println("------------");
	}

	public void printProbabilityDistribution(boolean round)
	{
		printProbabilityDistribution(round, alphabet, tokenCounts, sumSymbols);
	}

}
