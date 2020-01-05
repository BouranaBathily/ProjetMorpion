import java.util.*;
import java.io.*;
import java.math.*;

public class Player {
	
	public static final Random RANDOM = new Random();
	  public static void main(String args[]) {
	     Board b=new Board();
	     Scanner sc=new Scanner(System.in);
	     b.displayBoard();
	     System.out.println("Qui joue en premier:\n1. Ordinateur (X) / 2 User (0) :");
	     int choice=sc.nextInt();
	     if(choice==Board.PLAYER_X) {
	    	 Point p = new Point(RANDOM.nextInt(3),RANDOM.nextInt(3));
	    	 b.placeAMove(p, Board.PLAYER_X);
	    	 b.displayBoard();
	    }
	     while(!b.isGameOver()) {
	    	 boolean moveOK=true;
	    	 do {
	    		 if(!moveOK) {
	    			 System.out.println("Cellule déjà remplie");
	    		 }
	    		 b.takeHumanInput();
	    		 
	    	 }while(!moveOK);
	    	 b.displayBoard();
	    	 
	    	if(b.isGameOver())
	    		break;
	    	b.minimax(0, Board.PLAYER_X);
	    	System.out.println("Choix position ordi: "+b.computersMove);
	    	b.placeAMove(b.computersMove, Board.PLAYER_X);
	    	b.displayBoard();
	     
	     }
	     if(b.hasPlayerWon(Board.PLAYER_X))
	    	 System.out.println("Perdu");
	     else if (b.hasPlayerWon(Board.PLAYER_0))
	    	 System.out.println("Gagné!!");
	     else
	    	 System.out.println("Egalite!!!");
}
}
