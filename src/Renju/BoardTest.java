package Renju;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {



    public static JButton[][] createButtons(){
        Board GameBoard = new Board(15);
        JButton[][] buttons = new JButton[15][15];
        for (int y = 0; y < 15; y++) {
            for(int x = 0; x < 15; x++){
                buttons[y][x] = new JButton(" ");
                buttons[y][x].setPreferredSize(new Dimension(60, 60));
                buttons[y][x].setBackground(Color.GRAY);

                int newY = y;
                int newX = x;

                //Adding the action to the buttons of the gameboard
                buttons[y][x].addActionListener(e -> GameBoard.modifyField(newY, newX, buttons));

                //Adding the buttons to the panel
                buttons[y][x].setVisible(true);
            }
        }

        return buttons;
    }

    //This tests the modifyField method
    @org.junit.jupiter.api.Test
    void modifyField() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(3, 6, buttons);
        assertEquals("B", GameBoard.PlayBoard.get(3).get(6));
        assertEquals(Color.BLACK, buttons[3][6].getBackground());

        GameBoard.modifyField(5, 11, buttons);
        assertEquals("W", GameBoard.PlayBoard.get(5).get(11));
        assertEquals(Color.WHITE, buttons[5][11].getBackground());
        GameBoard.cleanBoard(buttons);
    }

    //This tests the Save and Load method
    @org.junit.jupiter.api.Test
    void saveAndLoad() {
        JButton[][] buttons = createButtons();
        Board GameBoard = new Board(15);
        GameBoard.cleanBoard(buttons);
        GameBoard.modifyField(4, 11, buttons);
        GameBoard.modifyField(2, 6, buttons);
        GameBoard.modifyField(6, 7, buttons);
        GameBoard.modifyField(12, 14, buttons);

        GameBoard.Save(15);
        Board tempBoard;
        tempBoard = GameBoard;
        GameBoard.cleanBoard(buttons);
        GameBoard.Load(buttons);
        assertEquals(tempBoard, GameBoard);
        assertEquals(Color.WHITE, buttons[2][6].getBackground());
        GameBoard.cleanBoard(buttons);


    }

    //This tests the cleanBoard function
    @org.junit.jupiter.api.Test
    void cleanBoard() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(7, 13, buttons);
        GameBoard.cleanBoard(buttons);
        assertEquals(" ", GameBoard.PlayBoard.get(7).get(13));
        assertEquals(Color.GRAY, buttons[7][13].getBackground());
    }
}