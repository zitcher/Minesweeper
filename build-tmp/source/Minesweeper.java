import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {

TheBoard b1;

//Declare and initialize NUM_ROWS and NUM_COLS = 20
public class TheBoard{
	public MSButton[][] buttons = new MSButton[20][20]; //2d array of minesweeper buttons
	public ArrayList <MSButton> bombs; //ArrayList of just the minesweeper buttons that are mined

	public TheBoard(){
		for(int i = 0; i<buttons.length; i++){
			for(int a = 0; a<buttons[i].length; a++){
				buttons[i][a] = new MSButton(i*(25),a*(25));
			}
		}
	}

	public void checkClick(){
		for(MSButton[] array: buttons){
			for(MSButton c : array){
				c.setPressed();
			}
		}
	}
	public void checkFlag(){
		for(MSButton[] array: buttons){
			for(MSButton c : array){
				c.setFlag();
			}
		}
	}
	public void drawBoard(){
		for(MSButton[] array: buttons){
			for(MSButton c : array){
				c.hoverOff();
				c.checkHover();
				c.drawButton();
			}
		}
	}
}

public class MSButton{
	int theLength = 25;
	int x;
	int y;
	String theText="5";
	boolean isPressed = false;
	boolean isFlagged = false;
	boolean isHover = false;
	int c = color(224, 224, 224);
	int str = color(0, 0, 0);
	//instantiates at x and y
	public MSButton(int x, int y){
		this.x=x;
		this.y=y;
	}

	//checks if mouse is on button
	private boolean checkMouse(){
		if(mouseX>x && mouseY>y && mouseX<x+theLength && mouseY<y+theLength)
			return true;
		return false;
	}

	//sets button's text
	public void setText(String theText){
		this.theText = theText;
	}

	//sets button's color
	public void setColor(int r, int g, int b){
		this.c = color(r, g, b);
	}

	//set's button's border color
	public void setStroke(int r, int g, int b){
		this.str = color(r, g, b);
	}

	//draws button
	public void drawButton(){
		//draws rect
		stroke(1);
		if(!isPressed && !isHover){
			setColor(224,224,224);
			setStroke(0,0,0);
		}

		fill(c);
		stroke(str);
		rect(x,y,theLength,theLength);

		//draws text
		if(isPressed){
			fill(255);
			textSize(15);
			text(theText,x+12,y+12);
		}
		if(isFlagged){
			noStroke();
			fill(139,69,19);
			rect(x+7, y+12, 4, 12);
			fill(165,42,42);
			rect(x+7, y+6, 9, 6);
		}
	}

	public void checkHover(){
		if(checkMouse() && !isPressed){
			isHover = true;
			setColor(135,135,135);
			setStroke(200,200,200);
		}
	}

	public void hoverOff(){
		isHover = false;
	}

	public void setPressed(){
		if(checkMouse() && !isPressed){
			isPressed = true;
			isFlagged=false;
			setColor(0,0,0);
			setStroke(25,0,255);
		}
	}
	public void setFlag(){
		if(checkMouse() && !isPressed && !isFlagged){
			isFlagged=true;
		} else if(checkMouse() && !isPressed && isFlagged){
			isFlagged=false;
		}
	}
}

public void setup (){
    size(500, 500);
    textAlign(CENTER,CENTER);
    //rectMode(CENTER);   
    b1=new TheBoard(); 
}


public void draw (){
    background(0);
    b1.drawBoard();
}

public void mousePressed() {
  if (mouseButton == LEFT) {
    b1.checkClick();
  } else if (mouseButton == RIGHT) {
    b1.checkFlag();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
