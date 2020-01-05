import java.util.*;
import java.io.*;
import java.math.*;


class Board {
	public static final int NO_PLAYER=0;
	public static final int PLAYER_X=1;
	public static final int PLAYER_0=2;
	public static final int VALIDACTIONCOUNT=3;
    public Point computersMove;
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[VALIDACTIONCOUNT][VALIDACTIONCOUNT];

  

    public boolean isGameOver() {
        return (hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_0) || getAvailableStates().isEmpty());
    }

    public boolean hasPlayerWon(int player) {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player) 
        		|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
            return true;
        }
        for (int i = 0; i < VALIDACTIONCOUNT; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player))) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < VALIDACTIONCOUNT; ++i) {
            for (int j = 0; j <VALIDACTIONCOUNT; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public boolean placeAMove(Point point, int player) {
       if ( board[point.x][point.y] != NO_PLAYER)//player = 1 for X, 2 for O
    	   	return false;
       
       board[point.x][point.y] = player;
       return true;
    }

    void takeHumanInput() {
    	try {
        System.out.println("Choisis une ligne: ");
        int x = scan.nextInt();
        System.out.println("Choisis une colonne: ");
        int y = scan.nextInt();
        Point point = new Point(x, y);
        placeAMove(point, PLAYER_0);
    	} 
    	catch(Exception e) 
    	{
    		System.out.println("Erreur de saisie");
    	}
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < VALIDACTIONCOUNT; ++i) {
            for (int j = 0; j < VALIDACTIONCOUNT; ++j) {
            	String value ="?";
            	if(board[i][j]== PLAYER_X)
            		value="X";
            	else if (board[i][j]== PLAYER_0)
            		value="0";
                System.out.print(value + " ");
            }
            System.out.println();

        }
        System.out.println();
    }



    public int minimax(int depth, int turn) 
    {
        if (hasPlayerWon(PLAYER_X))
        	return 1;
        
        if (hasPlayerWon(PLAYER_0))
        	return -1;

        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) 
        	return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < pointsAvailable.size(); ++i)
        {
            Point point = pointsAvailable.get(i);
            if (turn == PLAYER_X) {
                placeAMove(point, PLAYER_X);
                int currentScore = minimax(depth + 1, PLAYER_0);
                max = Math.max(currentScore, max);

                if(depth == 0)
                	System.out.println("Score for position "+point+" = "+currentScore);
                if(currentScore >= 0)
                {
                	if(depth == 0) 
                		computersMove = point;
                }
                if(currentScore == 1)
                {
                	board[point.x][point.y] = NO_PLAYER; 
                	break;
                }
                if(i == pointsAvailable.size()-1 && max < 0)
                {
                	if(depth == 0)
                		computersMove = point;
                }
            } 
            else if (turn == PLAYER_0)
            {
                placeAMove(point, PLAYER_0);
                int currentScore = minimax(depth + 1, PLAYER_X);
                min = Math.min(currentScore, min);
                if(min == -1)
                {
                	board[point.x][point.y] = NO_PLAYER; 
                	break;
                }
            }
            board[point.x][point.y] = NO_PLAYER; //Reset this point
        }
        return turn == PLAYER_X ? max : min;
    }//end minimax
}
