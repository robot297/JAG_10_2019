package week_10;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by clara on 10/21/19.
 */
public class WishListGUI extends JFrame {
    
    JPanel mainPanel;
    JList wishList;
    JList visitedList;
    JTextField newPlaceNameTextField;
    JButton addButton;
    JButton saveQuitButton;
    
    public WishListGUI(List<String> wishListPlaces, List<String> visitedPlaces) {
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(500, 500));
        getRootPane().setDefaultButton(addButton);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     
        // TODO finish this code. See Lab 10 Questions.md
        // TODO use methods to avoid writing all of your code in this constructor.
    
    }
    
    // Use this method to show message dialogs displaying the message given.
    // Otherwise tests for code that shows alert dialogs will time out and fail.
    // Don't modify this method.
    protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    
}
