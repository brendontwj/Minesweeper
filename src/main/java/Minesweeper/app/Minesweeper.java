package Minesweeper.app;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    static int[][] mineArray = new int[10][10];
    static List<String> header = new ArrayList<>();
    static String[][] displayArray = new String[10][10];
    static Console cons = System.console();
    static String[] coordinatesString = {"0,1,","0,-1","1,1","1,0","1,-1","-1,1","-1,0","-1,-1"};

    public static void main(String[] args) {

        //initialization
        header.add("  0 1 2 3 4 5 6 7 8 9");
        header.add("----------------------");
        Random randomGenerator = new Random();

        for(int i = 0; i < mineArray.length; i++) {
            for(int j = 0; j < mineArray[i].length; j++) {
                mineArray[i][j] = 0;
            }
        }

        for(int i = 0; i < 10; i++) {
            int x = randomGenerator.nextInt(10);
            int y = randomGenerator.nextInt(10);
            mineArray[x][y] = 1;
        }

        for(int i = 0; i < displayArray.length; i++) {
            for(int j = 0; j < displayArray[i].length; j++) {
                displayArray[i][j] = " ";
            }
        }

        display();

        while(true){
            String[] coordinates = new String[2];
            int x, y;
            String input = cons.readLine("Enter coordinates e.g. 4,5 or input stop to end the game: ");
            if(input.equals("stop")) {
                break;
            } else if (input.length() == 3) {
                coordinates = input.split(",");
                x = Integer.parseInt(coordinates[0]); y = Integer.parseInt(coordinates[1]);
                if(mineArray[x][y] == 0) {
                    checkBorder(x,y);
                    display();
                } else if (mineArray[x][y] == 1) {
                    displayArray[x][y] = "!";
                    display();
                    System.out.println("You hit a mine! Game over.");
                    break;
                }
            } else if (input.equals("check")) {
                checkMines();
            } else {
                System.out.println("Invalid input!");
            }
            // if (checkWin() == true); {
            //     System.out.println("You won!");
            //     break;
            // }
        }
    }

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

    public static void checkBorder(int x, int y) {
        for(String s : coordinatesString) {
            String[] splitCoordinatesString = s.split(",");
            int row = Integer.parseInt(splitCoordinatesString[0]);
            int col = Integer.parseInt(splitCoordinatesString[1]);
            try{
                check(x+row, y+col);
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    public static void check(int x, int y) {
        int numOfMines = 0;
        for(String s: coordinatesString) {
            String[] splitCoords = s.split(",");
            int row = Integer.parseInt(splitCoords[0]);
            int col = Integer.parseInt(splitCoords[1]);
            try {
                numOfMines += mineArray[row][col];
            } catch (IndexOutOfBoundsException e) {

            }
        }
        displayArray[x][y] = String.valueOf(numOfMines);
    }

    public static boolean checkWin() {
        for(int i = 0; i < displayArray.length; i++) {
            for(int j = 0; j < displayArray[i].length; j++) {
                if(displayArray[i][j].equals(" "));
                return false;
            }
        }
        return true;
    }

    public static void checkMines() {
        for(int i = 0; i < mineArray.length; i++) {
            for(int j = 0; j < mineArray[i].length; j++) {
                System.out.print(mineArray[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
