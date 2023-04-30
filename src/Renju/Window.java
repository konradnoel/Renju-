package Renju;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public abstract class Window extends JFrame implements ActionListener {

//The class of the GUI
    static void createWindow(int size, Board GameBoard) {
        //Creating the frame, and most of its components
        JFrame frame = new JFrame("Renju");
        JMenuBar MenuBar = new JMenuBar();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout(size, size));
        JButton[][] buttons = new JButton[size][size];

        JMenu Menu = new JMenu("Settings");
        JMenuItem Save = new JMenuItem("Save");
        JMenuItem Resize = new JMenuItem("Resize");
        JMenuItem Load = new JMenuItem("Load Previous Game");



        JComboBox<Object> JCB;
        Object[] ABC = new Object[16];
        int k = 0;
        for(int i = 5; i < 21; i++){
            ABC[k] = i;
            k++;
        }
        JCB = new JComboBox<>(ABC);

        //Setting and adding the actions to the buttons of the menubar
        Save.addActionListener(e -> GameBoard.Save(size));
        JComboBox<Object> finalJCB = JCB;
        Resize.addActionListener(e -> GameBoard.setRowLength((Integer) finalJCB.getSelectedItem(), frame));
        Load.addActionListener(e -> GameBoard.Load(buttons));

        //Adding the components to the menubar
        Menu.add(Save);
        Menu.add(Resize);
        Menu.add(Load);
        MenuBar.add(JCB, BoxLayout.X_AXIS);
        MenuBar.add(Menu);

        //Setting the frame
        frame.add(MenuBar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(60*size, 60*size));
        frame.setMaximumSize(new Dimension(1920, 1080));

        //Adding the buttons in grid layout
        for (int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++){
                buttons[y][x] = new JButton(" ");
                buttons[y][x].setPreferredSize(new Dimension(60, 60));
                buttons[y][x].setBackground(Color.GRAY);

                int newY = y;
                int newX = x;

                //Adding the action to the buttons of the gameboard
                buttons[y][x].addActionListener(e -> GameBoard.modifyField(newY, newX, buttons));

                //Adding the buttons to the panel
                buttons[y][x].setVisible(true);
                buttonPanel.add(buttons[y][x]);
            }
        }

        //Adding the panel to the frame
        frame.add(buttonPanel);

        //Display the window.
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    //This creates a pop-up window if there is a winner
    public static void finishWindow(String winner){
        //Creating the elements of the window

        JFrame endFrame = new JFrame("Game Over");
        JTextField text = new JTextField(winner);
        JTextField newgame = new JTextField("Would you like to play a new game?");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton yes = new JButton("yes");
        JButton no = new JButton("no");

        //Adding the actions to the buttons
        yes.addActionListener(e-> createWindow(15, new Board(15)));
        yes.addActionListener(e-> endFrame.dispose());
        no.addActionListener(e-> endFrame.dispose());

        //Setting the elements of the frame to be nice
        newgame.setEditable(false);
        text.setEditable(false);
        text.setHorizontalAlignment(JTextField.CENTER);
        newgame.setHorizontalAlignment(JTextField.CENTER);

        //Adding the elements to the frame
        endFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        endFrame.add(text, BorderLayout.NORTH);
        endFrame.add(newgame, BorderLayout.CENTER);
        endFrame.add(panel, BorderLayout.SOUTH);
        panel.add(yes);
        panel.add(no);

        //Finalizing the frame
        endFrame.setMinimumSize(new Dimension(600, 300));
        endFrame.setLocationRelativeTo(null);
        endFrame.pack();
        endFrame.setVisible(true);



    }
}