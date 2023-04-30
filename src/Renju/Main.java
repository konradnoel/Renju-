package Renju;

public class Main{

    public static void main(String[] args) {
        //Creating a default gameboard with the traditional size
        Board GameBoard = new Board(15);

        //Creating a window for the default board.
        Window.createWindow(15, GameBoard);

    }

}