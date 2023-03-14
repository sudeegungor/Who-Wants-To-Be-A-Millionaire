package main;
import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import enigma.console.TextWindow;

import java.awt.Color;

public class Game {
   private enigma.console.Console console = Enigma.getConsole("Mouse and Keyboard");
   private TextWindow textWindow = console.getTextWindow();
   private TextMouseListener tmlis; 
   private KeyListener klis; 

   // ------ Standard variables for mouse and keyboard ------
   public int mousepr;          // mouse pressed?
   public int mousex, mousey;   // mouse text coords.
   public int keypr;   // key pressed?
   public int rkey;    // key   (for press/release)
   // ----------------------------------------------------
   
   
   Game() throws Exception {   // --- Constructor
	   
      // ------ Standard code for mouse and keyboard ------ Do not change
      tmlis=new TextMouseListener() {
         public void mouseClicked(TextMouseEvent arg0) {}
         public void mousePressed(TextMouseEvent arg0) {
            if(mousepr==0) {
               mousepr=1;
               mousex=arg0.getX();
               mousey=arg0.getY();
            }
         }
         public void mouseReleased(TextMouseEvent arg0) {}
      };
      console.getTextWindow().addTextMouseListener(tmlis);
    
      klis=new KeyListener() {
         public void keyTyped(KeyEvent e) {}
         public void keyPressed(KeyEvent e) {
            if(keypr==0) {
               keypr=1;
               rkey=e.getKeyCode();
            }
         }
         public void keyReleased(KeyEvent e) {}
      };
      console.getTextWindow().addKeyListener(klis);
      // ----------------------------------------------------

        
        /*int px=5,py=5;
        console.getTextWindow().output(px,py,'P');
        while(true) {
           if(mousepr==1) {  // if mouse button pressed
              console.getTextWindow().output(mousex,mousey,'#');  // write a char to x,y position without changing cursor position
              px=mousex; py=mousey;
              
              mousepr=0;     // last action  
           }
           if(keypr==1) {    // if keyboard button pressed
              if(rkey==KeyEvent.VK_LEFT) px--;   
              if(rkey==KeyEvent.VK_RIGHT) px++;
              if(rkey==KeyEvent.VK_UP) py--;
              if(rkey==KeyEvent.VK_DOWN) py++;
              
              char rckey=(char)rkey;
              //        left          right          up            down
              if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') console.getTextWindow().output(px,py,'P'); // VK kullanmadan test teknigi
              else console.getTextWindow().output(rckey);
              
              if(rkey==KeyEvent.VK_SPACE) {
                 String str;         
                 str=console.readLine();     // keyboardlistener running and readline input by using enter 
                 console.getTextWindow().setCursorPosition(5, 20);
                 console.getTextWindow().output(str);
              }
              
              keypr=0;    // last action  
           }
           Thread.sleep(20);
        }*/

   }
   
   //Clears the Enigma console screen. Not much different from Console.Clear() from C#
   //got it from Enigma source code
   public void clearScreen() {
	   // output spaces to clear the screen
	   // start with one space less than the screen size, so as not to induce scrolling
	   char[] buffer = new char[Math.max(0, textWindow.getColumns() * textWindow.getRows() - 1)];
	   Arrays.fill(buffer, ' ');
	   textWindow.setCursorPosition(0, 0);
	   textWindow.output(buffer, 0, buffer.length);
	   // this positional output does not cause scrolling
	   textWindow.output(textWindow.getColumns() - 1, textWindow.getRows() - 1, ' ');
	   // move cursor back to beginning
	   textWindow.setCursorPosition(0, 0);
   }
   
   public String readLine()
   {
	   return console.readLine();
   }
   
   public char readKey() throws InterruptedException
   {
	   this.keypr = 0;
	   
	   while(true)
	   {
		   if(this.keypr == 1)
		   {
			   this.keypr = 0;
			   return (char)rkey;
		   }
		   
		   Thread.sleep(20);
	   }
   }
   
   //checks if a key is pressed on the moment it is called
   //does not stop the process
   public boolean isKeyPressed()
   {
	   if(this.keypr == 1)
	   {
		   this.keypr = 0;
		   return true;
	   }
	   
	   return false;
   }
   
   //use VK constants in KeyEvent
   //checks if the keyEvent key is pressed on the moment it is called
   //does not stop the process
   public boolean isKeyPressed(int keyEvent)
   {
	   if(this.keypr == 1 && rkey == keyEvent)
	   {
		   this.keypr = 0;
		   return true;
	   }
	   
	   return false;
   }
   
   public void print(String output)
   {
	   textWindow.output(output);
   }
   
   public void println(String output)
   {
	   textWindow.output(output + "\n");
   }
   
   public void setCursorPosition(int x, int y)
   {
	   textWindow.setCursorPosition(x, y);
   }
   
   public int getCursorPosX()
   {
	   return textWindow.getCursorX();
   }
   
   public int getCursorPosY()
   {
	   return textWindow.getCursorY();
   }
   
   public void setTitle(String title)
   {
	   console.setTitle(title);
   }
   
}
