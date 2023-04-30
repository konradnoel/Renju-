package Renju;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Board extends RuleCheck {
    //The size of the board, rowLength x rowLength
    private static int rowLength;
    //The data structure of the board, basically a 2D array.
    public List<List<String>> PlayBoard;
    //The boolean which tells whose turn it is
    boolean whiteTurn = false;

    //Constructor that fills up a board with empty fields with the designated size
    public Board(int size) {
        rowLength = size;
        PlayBoard = new ArrayList<>();
        for(int i = 0; i < rowLength; i++){ PlayBoard.add(new ArrayList<>()); }

        for (int y = 0; y < rowLength; y++) {
            for (int x = 0; x < rowLength; x++) {
                PlayBoard.get(y).add(" ");
            }
        }
    }

    //Modifies the color of the button/field clicked, while setting the status of the field as well
    public void modifyField (int y, int x, JButton[][] buttons) {
        //Checking if the field is occupied or not
        if(Objects.equals(PlayBoard.get(y).get(x), " ")){
            //If it is White's turn
            if(whiteTurn) {
                PlayBoard.get(y).set(x, "W");
                buttons[y][x].setBackground(Color.WHITE);
                whiteTurn = false;
                //Checks all directions if White has goten 5 consecutive fields in any of them
                if(RuleCheck.checkAll(PlayBoard, y, x, ("W"), 0, rowLength)) Window.finishWindow("White wins!");
            }
            //If it's Black's turn
            else {
                PlayBoard.get(y).set(x, "B");
                buttons[y][x].setBackground(Color.BLACK);
                whiteTurn = true;

                //Checking if Black has made any forbidden moves, like forks and overlining
                if(checkBlackForbiddens(PlayBoard, y, x, rowLength)) Window.finishWindow("This was a forbidden move, White wins!");

                //Checks all directions if Black has gotten 5 consecutive fields in any of them
                else if(RuleCheck.checkAll(PlayBoard, y, x, ("B"), 0, rowLength)) Window.finishWindow("Black wins!");
            }
        }
        //If the field is already taken
        else System.out.println("This field is already taken!");
    }


    //Saves the game with its current state into a file.
    public void Save(int rowLength){

        try {
            //Creating the streawriters and streams
            FileOutputStream File = new FileOutputStream("Data.txt");
            OutputStreamWriter Out  = new OutputStreamWriter(File);

            //Writing the size of the board
            Out.write(rowLength);

            //Writing out the fields
            for(int y = 0; y < rowLength; y++){
                for(int x = 0; x < rowLength; x++){
                    Out.write(PlayBoard.get(y).get(x));

                }
            }
            Out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Loading in a previous gamestate from the data file
    public void Load (JButton[][] buttonarray) {
        // Specifying the file to read from
        FileInputStream File;
        try {
            File = new FileInputStream("Data.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader In = new InputStreamReader(File);
        //Reading in the size of the board
        String in;
        int size;
        try {
            size = In.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //If there was an ongoing match, this cleans the board.
        cleanBoard(buttonarray);

        //Double cycle to read in for each "row" and element
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                try {
                    in = String.valueOf(In.read());
                    PlayBoard.get(y).add(in);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Setting the fields and button colors if it was an occupied field
                if (in.equals("66")) {
                    buttonarray[y][x].setBackground(Color.BLACK);
                    this.PlayBoard.get(y).set(x, "B");
                } else if (in.equals("87")) {
                    buttonarray[y][x].setBackground(Color.WHITE);
                    this.PlayBoard.get(y).set(x, "W");
                }
            }
        }
    }

    //Setting the size of the board. It is only called when you create a new board with a different size,
    // so it also creates a new window, and disposes of the previous game.
    public void setRowLength(int x, JFrame frame){
        rowLength = x;

        Window.createWindow(rowLength, new Board(rowLength));
        frame.dispose();

    }

    //This cleans the board
    public void cleanBoard(JButton[][] buttonarray){
        for(int y = 0; y < rowLength; y++) {
            for(int x = 0; x < rowLength; x++){
                PlayBoard.get(y).set(x, " ");
                buttonarray[y][x].setBackground(Color.GRAY);
            }
        }

    }


}








