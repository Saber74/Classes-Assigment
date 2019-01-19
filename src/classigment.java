/*
Class Assigment 1
Nizar Alrifai
Due Date: Novemeber 12th 2018
ICS4U period 1
A gameword class that allows for multiple methods to be done to the contents of the object.
The contents of the object is a string that can be used to find the reverse or the gameword, its permutations
or check if it has an anagram with an other word, it can also be used to determine how many points could be gained if played
on a scrabble board at a specific position.
 */
import java.util.*;
public class classigment {
    public static void main(String[] args) {
        GameWord hi = new GameWord("CAT"); //creating a new object
        System.out.println(hi.toString()); //checking contents
        hi.reverse(); //checking reversing the gameword
        System.out.println(hi.anagram("TAC"));  //checking the anagram class
        System.out.println(hi.permutation()); //checking all permutations of the gameword hi
        System.out.println(hi.pointValue(0,0,GameWord.RIGHT)); //checking the points pergame word when used at a specific location
    }
}
class GameWord { //a class used to create gameword objects which are strings containing the letters that are currently available to the scrabble player.
    private String contents; //the main object that will be used throughout, is a string that the user gives
    public static final int RIGHT=1; //assigining direction right to 1
    public static final int DOWN=2; //assigning direction down to 2
    public static int[][] grid = {
            {4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4},
            {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0},
            {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0},
            {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1},
            {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
            {4, 0, 0, 1, 0, 0, 0, 3, 0, 0, 0, 1, 0, 0, 4},
            {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0},
            {1, 0, 0, 3, 0, 0, 0, 1, 0, 0, 0, 3, 0, 0, 1},
            {0, 0, 3, 0, 0, 0, 1, 0, 1, 0, 0, 0, 3, 0, 0},
            {0, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 3, 0},
            {4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4}}; //grid used for points and multipliers
    public GameWord(String l) {
        contents = l;
    } //assiging contents to the string from the user
    public void reverse() { //reverses the gameword using StringBuilder
        String reverseWord = new StringBuilder(contents).reverse().toString(); //method for reversing using stringbuilders
        System.out.println(reverseWord);
    }
    public boolean anagram(String otherword) { //checks if the anagram is possible by splitting the 2 strings into arrays, sorting them and checking if they are equal
        String[] arr = contents.split(""); //splitting the gameword
        String[] arr2 = otherword.split(""); //splitting the word we need to compare
        Arrays.sort(arr);
        Arrays.sort(arr2);
        if (Arrays.equals(arr, arr2)) { //if they are equal returns true
            return true;
        }
        return false;
    }
    public boolean anagram(GameWord otherword) {//overload method to allow to check a gameword to be used as the comparison
        String[] arr = contents.split("");
        String[] arr2 = otherword.contents.split("");
        Arrays.sort(arr);
        Arrays.sort(arr2);
        if (Arrays.equals(arr, arr2)) {
            return true;
        }
        return false;
    }
    public int pointValue(int x, int y, int direction) {
        String[] pointers = {"EAIONRLSUT", "DG", "BCMP", "FHVWY", "K", "JX", "QZ"}; //letters from each point section grouped as strings
        int[] nums = {1, 2, 3, 4, 5, 8, 10}; //points at the same indexes as the strings in pointers to 'link' the two
        int points = 0; //to calculate points user is to get
        int letterMultiplier = 1; //for letter multiplying bonus
        int wordMultipler = 1; //for word multiplying bonus
        for (int i = 0; i < contents.length(); i++) {//checks for direction then moves the grid that way for the upcoming letters
            int block = grid[x][y]; //starting position
            if (direction == RIGHT) { //if direction is to the right we add to x
                x+=1;
            }
            else if (direction == DOWN) { //if direction is down we add to y
                y+=1;
            }
            if (block == 1) { //letter and word multipliers, letter multipliers are per letter and don't stack they are identified as 1 and 2
                letterMultiplier = 2;
            }
            else if (block == 2) {
                letterMultiplier = 3;
            }
            if (block == 3) {//word multiplier that stack they are identified as 3 and 4 in the grid
                wordMultipler *= 2;
            }
            else if (block == 4) {
                wordMultipler *= 3;
            }
            String ch = Character.toString(contents.charAt(i)); //checks for the character in gameword
            for (int k = 0; k < pointers.length; k++) {
                String checker = pointers[k]; //to idenitify how many points the player gets accoring to what string in the array contains the char
                if (checker.contains(ch)) {
                    points += letterMultiplier * nums[k]; //applies letter multiplier
                    letterMultiplier=1; //restarts the multiplier
                }
            }
        }
        return points *= wordMultipler; //multiping the points by the word bonus
    }
    public ArrayList<String> permutation(){
        ArrayList<String> possible= new ArrayList<String>(); //arraylist to hold all possible permutations
        permutation("", contents,possible); //calling recursive function
        return possible;
    }
    private void permutation(String soFar, String left,ArrayList<String> perm) {
        if (left.length() == 0) { //if there is not anything left to work with
            if (perm.contains(soFar) == false) { //if the perm is not in the list add it
                perm.add(soFar);
            }
        }
        else {
            for (int i = 0; i < left.length() ; i++) //branching out and calling for different permutaions
                permutation(soFar + left.charAt(i),
                        left.substring(0, i) + left.substring(i+1,left.length()),perm);
        }
    }
    public String toString(){
        return String.format("contents:%s",contents);
    }//returns contents
}

