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
    public static void main(String[] args) {

        //initialization
        header.add("  0 1 2 3 4 5 6 7 8 9");
        header.add("----------------------");
        Random randomGenerator = new Random();
        boolean stop = false;

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
                displayArray[i][j] = " |";
            }
        }

        display();

        while(!stop){
            String[] coordinates = new String[2];
            int x, y;
            String input = cons.readLine("Enter coordinates e.g. 4,5 or input stop to end the game: ");
            if(input.equals("stop"))
                stop = true;
            else if (input.length() == 3) {
                coordinates = input.split(",");
                x = Integer.parseInt(coordinates[0]); y = Integer.parseInt(coordinates[1]);
                if(mineArray[x][y] == 0) {
                    checkBorder(x,y);
                    displayArray[x][y] = "X|";
                    display();
                } else if (mineArray[x][y] == 1) {
                    displayArray[x][y] = "!|";
                    display();
                    System.out.println("You hit a mine! Game over.");
                    stop = true;
                }
            }
            else {
                System.out.println("Invalid input!");
            }
        }
    }

    public static void display() {
        for(String k : header)
            System.out.println(k);
        for(int i = 0; i < displayArray.length; i++) {
            System.out.print(i+"|");
            for(int j = 0; j < displayArray[i].length; j++) {
                System.out.print(displayArray[i][j]);
            }
            System.out.println();
        }
        System.out.println(header.get(1));
    }

    public static void checkBorder(int x, int y) {

        if((x-1) >= 0 && (y+1) < 10 && (y-1) >= 0) {
            check(x-1, y+1);
            if(check(x-1, y+1).equals("0"))
                checkBorder(x-1, y+1);
            check(x-1, y+0);
            if(check(x-1, y+0).equals("0"))
                checkBorder(x-1, y+0);
            check(x-1, y-1);
            if(check(x-1, y-1).equals("0"))
                checkBorder(x-1, y-1);
        }

        if(y+1 < 10 && y-1 >= 0) {
            check(x+0, y+1);
            if(check(x+0, y+1).equals("0"))
                checkBorder(x+0, y+1);
            check(x+0, y-1);
            if(check(x+0, y-1).equals("0"))
                checkBorder(x+0, y-1);
        }
        
        if(x+1 < 10 && y+1 < 10 && y-1 >= 0) {
            check(x+1, y+1);
            if(check(x+1, y+1).equals("0"))
                checkBorder(x+1, y+1);
            check(x+1, y+0);
            if(check(x+1, y+0).equals("0"))
                checkBorder(x+1, y+0);
            check(x+1, y-1);
            if(check(x+1, y-1).equals("0"))
                checkBorder(x+1, y-1);
        }
    }

    public static String check(int x, int y) {
        int numOfMines = 0;
        try {
            numOfMines = mineArray[x][y+1] +
                     mineArray[x][y-1] +
                     mineArray[x+1][y+1] +
                     mineArray[x+1][y+0] +
                     mineArray[x+1][y-1] +
                     mineArray[x-1][y+1] +
                     mineArray[x-1][y+0] +
                     mineArray[x-1][y-1];
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        
        if(numOfMines == 0)
            displayArray[x][y] = "X|";
        else
            displayArray[x][y] = String.valueOf(numOfMines+"|");


        return String.valueOf(numOfMines);
    }

}