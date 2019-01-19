/*
Class Assigment 2
Nizar Alrifai
Due Date: Novemeber 12th 2018
ICS4U period 1
a checkerboard class with objects containing their own board which can can change using the
function move. Additional functions of the class includes checking how many pieces are on the board at a time
and drawing an ascii display of the current board of the object belonging to the class
 */
import java.util.ArrayList;
public class classassigment2 {
    public static void main(String args[]){

        CheckersBoard hi =new CheckersBoard();
        hi.display();
        CheckersBoard hi2 =new CheckersBoard();
        System.out.println(hi.count(CheckersBoard.BLACK));
        System.out.println(hi.move(0,1,0,1));
//        System.out.println(hi.move(0,3,1,4));
//        System.out.println(hi.move(1,2,1,2)); //captures a black piece
//        System.out.println(hi.count(CheckersBoard.BLACK));
//        System.out.println(hi.count(CheckersBoard.RED));
//        hi.display();
    }
}

class Pair { //my own class created to hold pairs to record coordiantes of captured pieces
    public int p1, p2;

    Pair(int p1, int p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
    class CheckersBoard { //class that contains
        public static final int BLACK=1; //assigning constants black is one and red is 2
        public static final int RED=2;
        private int[][] board; //the board that belongs to each object
        private int[][] firstboard = {
                {0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 2, 0, 2, 0, 2, 0},
                {0, 2, 0, 2, 0, 2, 0, 2},
                {2, 0, 2, 0, 2, 0, 2, 0}
        };

        public CheckersBoard() {
            board = firstboard;
        }

        private static boolean bol = false; //boolean to check if a move is valid starts at false and only changes if move is valid
        public boolean move(int x, int y, int x2, int y2) {
            int tempx = x; //creating a variable to keep original x and y intact
            int tempy = y;
            int i = 0;
            bol = false; //restarting boolean
            int col = board[y][x]; //checking colour
            if (col == BLACK) { //i is a variable to determine if the piece usually moves up or down the board
                i = 1;
            }
            else if (col == RED) {
                i = -1;
            }
            else return bol; //returns false as the initial position has no piece thus move is invalid
            if (x2 < 8 && x2 > -1 && y2 < 8 && y2 > -1) { //checks that final position is within bounds
                if (y + i == y2) { //checks that this is a simple move without capturing to an area that is empty
                    if (x + 1 == x2 || x - 1 == x2) {//if a move is normal then it updates the board and boolean
                        if (board[y2][x2] == 0) {
                            board[y2][x2] = col;
                            board[y][x] = 0;
                            bol = true;
                        }
                    }
                }
                else {//if it is not a simple move we can check if capturing is involved by using recursion
                    int counter = 0;
                    System.out.println("hi");
                    ArrayList<Pair> movelist = new ArrayList<Pair>();
                    move(x, y, x2, y2, col,  tempx, tempy,movelist,counter);
                }
            }
            return bol; //returns false as the move is not within bounds if not updated
        }

        public void move(int tempx, int tempy, int x2, int y2, int col, int x, int y,ArrayList<Pair> movelist,int count) {
            System.out.println("x "+tempx);
            System.out.println("y "+tempy);
            if (tempx==x2&&tempy==y2&&count<4&&bol==false) { //checks that the x and that are being updated reached the desired end point in under 3 moves
                board[y][x] = 0; //bol==false makes sure it only takes one path
//                System.out.println("hi2");
                board[y2][x2] = col;
                for (int i = 0; i < movelist.size(); i++) {//getting all captured pieces coordinates and making them empty(killed)
                    Pair pair = movelist.get(i);
                    int px = pair.p1;
                    int py = pair.p2;
                    if (px < 8 && px > -1 && py < 8 && py > -1) {
                        board[pair.p2][pair.p1] = 0;
                    }
                }

                bol = true; //if it passes the base case the move true
            }
            else {//recurivse portion
                if (tempx < 8 && tempy < 8 && tempy > -1 && tempx > -1) {//checks that temp values are still within board
                    if(tempx+1<8){//along with if below checks for an enemy piece to capture nearby
                        if(tempy+1<8&&col==BLACK){ //goes up as it is black and checks for red pieces to the right
                            if(board[tempy+1][tempx+1]==RED){
                                System.out.println("hei");
                                Pair move1 =new Pair(tempx + 1, tempy + 1);
                                movelist.add(move1);
                                move(tempx+2,tempy+2,x2,y2,col,x,y,movelist,count+=1);
                            }
                        }
                        else if(tempy-1>-1&&col==RED){
                            if(board[tempy-1][tempx+1]==BLACK){ //checks for black pieces to the right and goes down
                                Pair move1 = new Pair(tempx + 1, tempy - 1);
                                movelist.add(move1);
                                move(tempx+2,tempy-2,x2,y2,col,x,y,movelist,count+=1);
                            }
                        }
                    }
                    if(tempx-1>-1){
                        if(tempy+1<8&&col==BLACK){//checks for red peices to the left and up
                            if(board[tempy+1][tempx-1]==RED){
                                System.out.println("why");
                                Pair move1 = new Pair(tempx -1, tempy + 1);
                                movelist.add(move1);
                                move(tempx-2,tempy+2,x2,y2,col,x,y,movelist,count+=1);
                            }
                        }
                        else if(tempy-1>-1&&col==RED){
                            if(board[tempy-1][tempx-1]==BLACK){//checks for black peices down and to the left
                                Pair move1 = new Pair(tempx -1, tempy - 1);
                                movelist.add(move1);
                                move(tempx-2,tempy-2,x2,y2,col,x,y,movelist,count+=1);
                            }
                        }
                    }
                }
            }
        }
        public int count(int colour) {//counts how many pieces of a specified colour there is on the board
            int find=0;
            if(colour==BLACK){ //checks which colour we have to find
                find=BLACK;
            }
            else if (colour==RED){
                find=RED;
            }
            int counter = 0;
            for (int[] k : board) {
                for (int p : k) {//goes through the board and checks for the colour then adds to a counter
                    if (p == find) {
                        counter += 1;
                    }
                }
            }
            return counter;
        }

        public void display() {//displays crude ascii version of board
            for (int i = board.length; i > 0; i--) {//printing out the rows in accordance to the size of grid
                int[] togetcolour = board[i - 1];
                for (int j = 0; j < board.length; j++) {
                    if (j == board.length - 1) System.out.print("+---+");//prints out the extra + at the end of the line only to close the board
                    else {
                        System.out.print("+---");
                    }
                }
                System.out.println("");//leaving a blank for better appearance
                for (int p = 0; p < board.length + 1; p++) { //printing contens and coloums of grid
                    if (p < board.length) {//checks for colour and outputs a shape correspoding to it in the grid if grid[x][y]=0 then leaves a blank
                        int colour = 0;
                        if (p < board.length) colour = togetcolour[p];
                        String rb = " ";
                        if (colour == BLACK) {
                            rb = "X";
                        }
                        else if (colour == RED) {
                            rb = "O";
                        }
                        if (p != board.length) {
                            System.out.printf("| %s ", rb);
                        }
                    } else System.out.print("|   ");
                }
                System.out.println(""); //leaving a blank for it to look proper
            }
            System.out.println("+---+---+---+---+---+---+---+---+"); //outputs the closing line of the grid
        }
    }
