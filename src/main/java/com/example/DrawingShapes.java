package com.example;

import processing.core.PApplet;

public class DrawingShapes {
    
    PApplet myProcessing;



    DrawingShapes(PApplet mainProgram){
        myProcessing = mainProgram;
    }


    public void drawShape(int pitches){  //draws shapes based on what note is played from the song 
        if(pitches % 12 == 0) { //if the note is a C or the number 60
            myProcessing.fill(255, 255, 0); //sets the color of the rectangle to yellow 
            drawRectangle();
        } //end of first if statement 
        else if (pitches >= 52 && pitches <= 65) { //if the note token is between 52 and 65
            myProcessing.fill(0, 0, 255); //sets the color of the circle to blue 
            drawCircle();
        }
        else if (pitches % 10 == 0 ) { //if the note token is between 21 and 50
            myProcessing.fill(0, 255, 0); //sets the color of the triangle to green
            drawTriangle();
        }
        else if (pitches < 20 ) { //if the note token is below 20
            myProcessing.fill(255,200,200); //sets the color of the ellipse to white 
            drawEllipse();
        }
        else //if the note is none of the above 
        {
            myProcessing.fill(0, 255, 255); //sets the color of the quan to cyan        
            drawQuad();

        }

    } //end of drawShape
     

    
    void drawRectangle(){ //method that is called to draw a rectangle 
        myProcessing.rect(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40, 40); //randomly places the shape somewhere on the screen
    }


    void drawCircle(){ //draws a circle 
        myProcessing.circle(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40);  //randomly places the shape somewhere on the screen
    }

    void drawTriangle(){  //draws a triangle 
    
        float triangleSize = 40;
    float halfSize = triangleSize / 2;

    float x1 = myProcessing.random(myProcessing.width - triangleSize);  //randomly places the shape somewhere on the screen
    float y1 = myProcessing.random(myProcessing.height - triangleSize); //randomly places the shape somewhere on the screen

    float x2 = x1 + triangleSize;
    float y2 = y1;

    float x3 = x1 + halfSize;
    float y3 = y1 - myProcessing.sqrt(3) * halfSize;

    myProcessing.triangle(x1, y1, x2, y2, x3, y3); //sets the coordinates of each point of the shape 

    }

    void drawEllipse() {  //draws an ellipse 
        float ellipseWidth = 60;
        float ellipseHeight = 40;
        myProcessing.ellipse(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), ellipseWidth, ellipseHeight);  //randomly places the shape somewhere on the screen
      
    }

    void drawQuad(){ //draws a quad 
        float quadSize = 40;
    
        float x1 = myProcessing.random(myProcessing.width - quadSize);
        float y1 = myProcessing.random(myProcessing.height - quadSize);
    
        float x2 = x1 + quadSize + myProcessing.random(10);  // Add some randomness to the width
        float y2 = y1 - myProcessing.random(10);  // Randomness to the y-coordinate
    
        float x3 = x1 + quadSize - myProcessing.random(10);  // Randomness to the width
        float y3 = y1 + quadSize + myProcessing.random(10);  // Add some randomness to the y-coordinate
    
        float x4 = x1 - myProcessing.random(10);  // Randomness to the x-coordinate
        float y4 = y1 + quadSize - myProcessing.random(10);  // Randomness to the y-coordinate
    
        myProcessing.quad(x1, y1, x2, y2, x3, y3, x4, y4);
    }
    

}// end of class 
