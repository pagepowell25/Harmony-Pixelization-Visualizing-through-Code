package com.example;

import processing.core.PApplet;

public class DrawingShapes {
    
    PApplet myProcessing;



    DrawingShapes(PApplet mainProgram){
        myProcessing = mainProgram;
    }


    public void drawShape(int pitches){  //draws shapes based on what note is played from the song 
        if(pitches % 12 == 0) { //or another number
            drawRectangle();
        } //end of first if statement 
        else if (pitches > 52) {
            // draw a another shape();
            drawCircle();
        }
        else if (pitches < 45) {
            drawTriangle();
        }
        else 
        {
             myProcessing.rect(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 200, 40);


        }

    } //end of else if 
    
    
    
    void drawRectangle(){ //method that is called to draw a rectangle 
        myProcessing.rect(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40, 40);
    }


    void drawCircle(){
        myProcessing.circle(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40);
    }

    void drawTriangle(){
        myProcessing.triangle(120, 300, 232, 80, 344, 300);

    }


}// end of class 
