package Minesweeper.app;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minesweeper {
    static int[][] mineArray = new int[10][10];
    static List<String> header = new ArrayList<>();
    static String[][] displayArray = new String[10][10];
    static Console cons = System.console();
    static String[] coordinatesString = {"0,1","0,-1","1,1","1,0","1,-1","-1,1","-1,0","-1,-1"};

    public static void main(String[] args) {
 
        // initialization
        header.add("  0 1 2 3 4 5 6 7 8 9");
        header.add("----------------------");
        Random randomGenerator = new Random();

        // Creating mine and display array
        for(int i = 0; i < mineArray.length; i++) {
            for(int j = 0; j < mineArray[i].length; j++) {
                mineArray[i][j] = 0;
                displayArray[i][j] = " ";
            }
        }

        // adding mines to random tiles
        for(int i = 0; i < 10; i++) {
            int x = randomGenerator.nextInt(10);
            int y = randomGenerator.nextInt(10);
            mineArray[x][y] = 1;
        }

        display();

        while(true){
            String[] coordinates = new String[2];
            int x, y;
            // checking for player input
            String input = cons.readLine("Enter coordinates e.g. 4,5 or input stop to end the game: ");
            // checking action to perform based on input
            if(input.equals("stop")) {
                break;
            } else if (input.length() == 3) {
                coordinates = input.split(",");
                x = Integer.parseInt(coordinates[0]); y = Integer.parseInt(coordinates[1]);
                // if no mine
                if(mineArray[x][y] == 0) {
                    checkBorder(x,y);
                    displayArray[x][y] = "X";
                    display();
                // if hit mine
                } else if (mineArray[x][y] == 1) {
                    displayArray[x][y] = "!";
                    displayAll();
                    System.out.println("You hit a mine! Game over.");
                    break;
                }
            // command to check mine positions for debug
            } else if (input.equals("check")) {
                checkMines();
            } else {
                System.out.println("Invalid input!");
            }
            // player wins if all tiles have been checked
            if (checkWin() == true); {
                System.out.println("You won!");
                break;
            }
        }
    }

    // prints out the board 
    public static void display() {
        for(String k : header)
            System.out.println(k);
        for(int i = 0; i < displayArray.length; i++) {
            System.out.print(i+"|");
            for(int j = 0; j < displayArray[i].length; j++) {
                System.out.print(displayArray[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println(header.get(1));
    }

    // checks the bordering tiles for the coordinate that player enters
    public static void checkBorder(int x, int y) {
        for(String s : coordinatesString) {
            String[] splitCoordinatesString = s.split(",");
            int row = Integer.parseInt(splitCoordinatesString[0]);
            int col = Integer.parseInt(splitCoordinatesString[1]);
            try{
                checkTile(x+row, y+col);
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    // calculating the number of mines in adjacent tiles
    public static void checkTile(int x, int y) {
        int numOfMines = 0;
        for(String s: coordinatesString) {
            String[] splitCoords = s.split(",");
            int row = Integer.parseInt(splitCoords[0]);
            int col = Integer.parseInt(splitCoords[1]);
            try {
                numOfMines += mineArray[x+row][y+col];
            } catch (IndexOutOfBoundsException e) {

            }
        }
        displayArray[x][y] = String.valueOf(numOfMines);
    }

    // check if there are any tiles still not checked
    public static boolean checkWin() {
        for(int i = 0; i < displayArray.length; i++) {
            for(int j = 0; j < displayArray[i].length; j++) {
                if(displayArray[i][j].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    // debug method to check mine position
    public static void checkMines() {
        for(int i = 0; i < mineArray.length; i++) {
            for(int j = 0; j < mineArray[i].length; j++) {
                System.out.print(mineArray[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    // if player hits a mine, reveal the position of all the mines
    public static void displayAll() {
        for(int i = 0; i < mineArray.length; i++) {
            for(int j = 0; j < mineArray[i].length; j++) {
                if(mineArray[i][j] == 1)
                    displayArray[i][j] = "O";
                else
                    displayArray[i][j] = " ";
            }
        }
        display();
    }
}
