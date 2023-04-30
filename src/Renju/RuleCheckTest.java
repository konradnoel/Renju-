package Renju;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RuleCheckTest {

    //Creates a new 2D array of buttons, just for the cause of the tests
    public static JButton[][] createButtons() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = new JButton[15][15];
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
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

    //This tests if there is 5 consecutive same-colored fields to the right from the selected field
    @Test
    void checkRight() {
        JButton[][] buttons = createButtons();
        Board GameBoard = new Board(15);


        GameBoard.modifyField(9, 12, buttons);
        GameBoard.modifyField(0, 7, buttons);
        GameBoard.modifyField(9, 11, buttons);
        GameBoard.modifyField(0, 6, buttons);
        GameBoard.modifyField(9, 10, buttons);
        GameBoard.modifyField(0, 5, buttons);
        GameBoard.modifyField(9, 9, buttons);
        GameBoard.modifyField(0, 4, buttons);
        GameBoard.modifyField(9, 8, buttons);// completes the overline

        assertEquals(5, RuleCheck.checkRight(GameBoard.PlayBoard, 9, 8, "B", 0, 15));
    }

    //This tests if the horizontal line's both ends are open (empty)
    @Test
    void isOpenSide() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(2, 3, buttons);
        GameBoard.modifyField(0, 14, buttons);
        GameBoard.modifyField(1, 2, buttons);
        GameBoard.modifyField(0, 13, buttons);

        GameBoard.modifyField(2, 2, buttons);
        GameBoard.modifyField(0, 12, buttons);
        GameBoard.modifyField(3, 2, buttons);
        GameBoard.modifyField(0, 11, buttons);

        GameBoard.modifyField(2, 1, buttons); //This makes the 3x3 fork complete
        assertTrue(RuleCheck.isOpenSide(GameBoard.PlayBoard, 2, 2, 15));
    }

    //This tests if black has made an illegal for of the desired size
    @Test
    void checkFork() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(2, 3, buttons);
        GameBoard.modifyField(0, 14, buttons);
        GameBoard.modifyField(1, 2, buttons);
        GameBoard.modifyField(0, 13, buttons);

        GameBoard.modifyField(2, 2, buttons);
        GameBoard.modifyField(0, 12, buttons);
        GameBoard.modifyField(3, 2, buttons);
        GameBoard.modifyField(0, 11, buttons);

        GameBoard.modifyField(2, 1, buttons); //This makes the 3x3 fork complete
        assertTrue(RuleCheck.checkFork(GameBoard.PlayBoard, 2, 1, "B", 0, 15, 3));

    }

    //This checks if black has 6 or more fields in the same line, which is illegal
    @Test
    void checkOverline() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();

        GameBoard.modifyField(9, 14, buttons);
        GameBoard.modifyField(0, 9, buttons);
        GameBoard.modifyField(9, 13, buttons);
        GameBoard.modifyField(0, 8, buttons);
        GameBoard.modifyField(9, 12, buttons);
        GameBoard.modifyField(0, 7, buttons);
        GameBoard.modifyField(9, 11, buttons);
        GameBoard.modifyField(0, 6, buttons);
        GameBoard.modifyField(9, 10, buttons);
        GameBoard.modifyField(0, 5, buttons);
        GameBoard.modifyField(9, 9, buttons);
        GameBoard.modifyField(0, 4, buttons);
        GameBoard.modifyField(9, 8, buttons);// completes the overline


        assertTrue(RuleCheck.checkOverline(GameBoard.PlayBoard, 9, 15));
    }

    //This checks if there is 5 consecutive same-colored fields downwards from a cordinate
    @Test
    void checkDown() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(9, 10, buttons);
        GameBoard.modifyField(0, 0, buttons);
        GameBoard.modifyField(8, 10, buttons);
        GameBoard.modifyField(0, 1, buttons);
        GameBoard.modifyField(7, 10, buttons);
        GameBoard.modifyField(0, 2, buttons);
        GameBoard.modifyField(6, 10, buttons);
        GameBoard.modifyField(0, 3, buttons);
        GameBoard.modifyField(5, 10, buttons);

        assertEquals(5, RuleCheck.checkDown(GameBoard.PlayBoard, 5, 10, "B", 0, 15));
    }

    //This checks if the diagonal line's ends are open (empty)
    @Test
    void checkisOpenUpLeftDownRight() {
        Board GameBoard = new Board(15);
        JButton[][] buttons = createButtons();
        GameBoard.modifyField(9, 10, buttons);
        GameBoard.modifyField(0, 0, buttons);
        GameBoard.modifyField(8, 9, buttons);
        GameBoard.modifyField(0, 1, buttons);
        GameBoard.modifyField(7, 8, buttons);
        GameBoard.modifyField(0, 2, buttons);
        GameBoard.modifyField(6, 7, buttons);
        GameBoard.modifyField(0, 3, buttons);
        GameBoard.modifyField(5, 6, buttons);

        assertTrue(RuleCheck.isOpenUpLeftDownRight(GameBoard.PlayBoard, 7, 8, 15));

    }
}