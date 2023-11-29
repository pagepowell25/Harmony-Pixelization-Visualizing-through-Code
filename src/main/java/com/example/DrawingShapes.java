package com.example;

import processing.core.PApplet;

public class DrawingShapes { //name of my new class is DrawingShapes
    
    PApplet myProcessing; //declares a variable named myProcessing of type PApplet.



    DrawingShapes(PApplet mainProgram){
        myProcessing = mainProgram; //defines my class, DrawingShapes in the package com.example
    }


    public void drawShape(int pitches){  //draws shapes based on what note is played from the song 
        if(pitches >= 68 && pitches <= 72) { //if the note token is between 68 or 72
            myProcessing.fill(255, 255, 0); //sets the color of the rectangle to yellow 
            drawRectangle(); //calls the function to draw a rectangle
        } //end of first if statement 
        else if (pitches >= 62 && pitches <= 64) { //if the note token is between 62 and 64
            myProcessing.fill(0, 0, 255); //sets the color of the circle to blue 
            drawCircle(); //calls the function to draw a circle
        }
        else if (pitches >= 65 && pitches <= 67 ) { //if the note token is between 65 and 67
            myProcessing.fill(0, 255, 0); //sets the color of the triangle to green
            drawTriangle(); //calls the function to draw a triangle
        }
        else if (pitches < 20 ) { //if the note token is below 20
            myProcessing.fill(255,200,200); //sets the color of the ellipse to white 
            drawEllipse(); //calls the function to draw a triangle
        }
        else //if the note is none of the above 
        {
            myProcessing.fill(0, 255, 255); //sets the color of the quad to cyan        
            drawQuad(); //calls the function to draw a quadrilateral

        }

    } //end of drawShape
     

    
    void drawRectangle(){ //method that is called to draw a rectangle 
        myProcessing.rect(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40, 40); //randomly places the shape somewhere on the screen
    }


    void drawCircle(){ //draws a circle 
        myProcessing.circle(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), 40);  //randomly places the shape somewhere on the screen
    }

    void drawTriangle(){  //draws a triangle 
    
    float triangleSize = 40; //sets the size of the triangle 
    float halfSize = triangleSize / 2; //sets the size of the triangle

    float x1 = myProcessing.random(myProcessing.width - triangleSize);  //randomly places the shape somewhere on the screen (first x coordinate)
    float y1 = myProcessing.random(myProcessing.height - triangleSize); //randomly places the shape somewhere on the screen (first y coordinate)

    float x2 = x1 + triangleSize; //sets second x coordinate of triangle
    float y2 = y1; //sets second y coordinate of triangle 

    float x3 = x1 + halfSize; //sets third x coordinate of triangle
    float y3 = y1 - myProcessing.sqrt(3) * halfSize; //sets third y coordinate of triangle 

    myProcessing.triangle(x1, y1, x2, y2, x3, y3); //calls the coordinates of each point of the triangle 

    }

    void drawEllipse() {  //draws an ellipse 
        float ellipseWidth = 60; //sets the width of the ellipse
        float ellipseHeight = 40; //sets the height of the ellipse 
        myProcessing.ellipse(myProcessing.random(myProcessing.width), myProcessing.random(myProcessing.height), ellipseWidth, ellipseHeight);  //randomly places the shape somewhere on the screen
      
    }

    void drawQuad(){ //draws a quad 
        float quadSize = 40; //sets the size of the quad
    
        float x1 = myProcessing.random(myProcessing.width - quadSize); // Add some randomness to the first x coordinate
        float y1 = myProcessing.random(myProcessing.height - quadSize); // Add some randomness to the first y coordinate
    
        float x2 = x1 + quadSize + myProcessing.random(10);  // Add some randomness to the width
        float y2 = y1 - myProcessing.random(10);  // Randomness to the y-coordinate
    
        float x3 = x1 + quadSize - myProcessing.random(10);  // Randomness to the width
        float y3 = y1 + quadSize + myProcessing.random(10);  // Add some randomness to the y-coordinate
    
        float x4 = x1 - myProcessing.random(10);  // Randomness to the x-coordinate
        float y4 = y1 + quadSize - myProcessing.random(10);  // Randomness to the y-coordinate
    
        myProcessing.quad(x1, y1, x2, y2, x3, y3, x4, y4); //calls the coordinates of each point of the quad
    }
    

}// end of class DrawingShapes
