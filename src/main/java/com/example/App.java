/*
 * Modified by Page Powell 
 * Class: Project 2 
 * Description: Implementation of the MarkovChain Generator with a new Train and Generate Function 
 */

package com.example;

//importing the JMusic stuff
import jm.music.data.*;
import jm.util.*;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import processing.core.*;



//make sure this class name matches your file name, if not fix.
public class App extends PApplet{

	static MelodyPlayer player; // play a midi sequence
	static MidiFileToNotes midiNotes; // read a midi file
	static int noteCount = 0;

	//make cross-platform
	static FileSystem sys = FileSystems.getDefault();

	//the getSeperator() creates the appropriate back or forward slash based on the OS in which it is running -- OS X & Windows use same code :) 
	static String filePath = "mid"  + sys.getSeparator() +  "ABBA_-_Gimme_Gimme_Gimme.mid"; // path to the midi file -- you can change this to your file
															// location/name

	DrawingShapes drawing = new DrawingShapes(this);

	public void keyPressed() {  //function to stop the code from running
        if (key == 's') { // Stop music and shapes when 's' is pressed
            player.reset(); // Stops the melody player
            noLoop(); // Stops the shapes from drawing 
        } else if (key == 'e') //exits processing if key pressed 
			exit(); // Exits Processing window 
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// run the unit tests
		int whichTest = Integer.parseInt(args[0]);

		PApplet.main("com.example.App");
		


		// setup the melody player
		// uncomment below when you are ready to test or present sound output
		// make sure that it is commented out for your final submit to github (eg. when
		// pushing)
		//setup();
		//playMelody();
		generateMelody();
		//testAndTrainMarkovChainGen();
		


		// uncomment to debug your midi file
		// this code MUST be commited when submitting unit tests or any code to github
		//playMidiFileDebugTest(filePath);
	}

	public static void testAndTrainMarkovChainGen() //Unit Test 1
	{
		MarkovChainGenerator<Double> rhythmgen = new MarkovChainGenerator<Double>(); //MarkovChain generator for creating rhythm values 
		MarkovChainGenerator<Integer> pitchgen = new MarkovChainGenerator<Integer>(); //MarkovChain generator for creating pitch values 

		rhythmgen.train(midiNotes.getRhythmArray()); //the train method is used to call rhythm values from transition table in Markov Chain Generator 
		pitchgen.train(midiNotes.getPitchArray()); //the train method is used to call pitch values from transition table in Markov Chain Generator 


		pitchgen.printProbabilityDistribution(false);  //prints probability distribution for pitches
		rhythmgen.printProbabilityDistribution(false); //prints probability distribution for rhythms 

	}
	

	public static void generateMelody() //Unit Test 2 
		{
			MarkovChainGenerator<Integer>pitchGen = new MarkovChainGenerator<Integer>(); //creates markov chain generator for the pitch 
			MarkovChainGenerator<Double>rhythmGen = new MarkovChainGenerator<Double>(); //creates markov chain generator for the rhythm

			pitchGen.train(midiNotes.getPitchArray());  //calls the train method and accesses the MIDI information from the file 
			rhythmGen.train(midiNotes.getRhythmArray()); //calls the train method and accesses the MIDI information from the file 


			MarkovChainGenerator<Integer>pitchTrain = new MarkovChainGenerator<Integer>(); //creates markov chain generator for the pitch 
			MarkovChainGenerator<Double>rhythmTrain = new MarkovChainGenerator<Double>(); //creates markov chain generator for the rhythm
           
		   
		    	// for (int i = 0; i < 100000; i++)  //produces 100000 melodies
            	// {
	
				 		ArrayList<Integer> pitches = pitchGen.generate(250); //creates a melody using an Arraylist of pitches
				 		pitchTrain.train(pitches); //calls the train function to the pitchTrain array (creates pitches)
				 		ArrayList<Double> rhythms = rhythmGen.generate(250); //creates a melody using an ArrayList of rhythms
				 		rhythmTrain.train(rhythms); //calls the train function to the rhythmTrain array (creates rhythms)
						
            	// }

					ArrayList song = pitchGen.generate(20); //produces 20 notes from pitches 
    					player.setMelody(pitches);  //outputs sound based on probability of pitches generated 
		   				player.setRhythm(rhythms); //produces 20 notes from rhythms 
						System.out.println(song);
				

				rhythmTrain.printProbabilityDistribution(true);  //prints the probability distribution of rhythms  
				pitchTrain.printProbabilityDistribution(true);  //prints the probability distribution of pitches 	


		} 	


	// doing all the setup stuff
	public void setup() {

		// playMidiFile(filePath); //use to debug -- this will play the ENTIRE file --
		// use ONLY to check if you have a valid path & file & it plays
		// it will NOT let you know whether you have opened file to get the data in the
		// form you need for the assignment

		midiSetup(filePath);
		generateMelody();
		background(127);

	}

	
	public void settings(){
		//size (700,500);
		fullScreen();
		
	}


	public void draw(){  //draws the shapes for each note 
		
		strokeWeight(10);
		
		
		Integer midiNoteNumber = playMelody(); //plays the MIDI file I have
		if(midiNoteNumber != -1){
			println(midiNoteNumber); //prints the notes of the MIDI file in terminal 
			drawing.drawShape(midiNoteNumber); //draws the shapes as the notes are being played
		}
	
	} //end of draw function

	

	// plays the midi file using the player -- so sends the midi to an external
	// synth such as Kontakt or a DAW like Ableton or Logic
	static public Integer playMelody() {

		assert (player != null); // this will throw an error if player is null -- eg. if you haven't called
									// setup() first

		if (!player.atEndOfMelody()) {
			return player.play(); // play each note in the sequence -- the player will determine whether is time
							// for a note onset
		}
			return -1;
	}

	// opens the midi file, extracts a voice, then initializes a melody player with
	// that midi voice (e.g. the melody)
	// filePath -- the name of the midi file to play
	static void midiSetup(String filePath) {

		// Change the bus to the relevant port -- if you have named it something
		// different OR you are using Windows
		player = new MelodyPlayer(100, "Bus 1"); // sets up the player with your bus.

		midiNotes = new MidiFileToNotes(filePath); // creates a new MidiFileToNotes -- reminder -- ALL objects in Java
													// must
													// be created with "new". Note how every object is a pointer or
													// reference. Every. single. one.

		// // which line to read in --> this object only reads one line (or ie, voice or
		// ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0); // this assumes the melody is midi channel 0 -- this is usually but not ALWAYS
									// the case, so you can try other channels as well, if 0 is not working out for
									// you.

		noteCount = midiNotes.getPitchArray().size(); // get the number of notes in the midi file

		assert (noteCount > 0); // make sure it got some notes (throw an error to alert you, the coder, if not)

		// sets the player to the melody to play the voice grabbed from the midi file
		// above
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	}

	static void resetMelody() {
		player.reset();

	}

	// this function is not currently called. you may call this from setup() if you
	// want to test
	// this just plays the midi file -- all of it via your software synth. You will
	// not use this function in upcoming projects
	// but it could be a good debug tool.
	// filename -- the name of the midi file to play
	static void playMidiFileDebugTest(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

}
