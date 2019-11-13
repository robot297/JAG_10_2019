package week_10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by clara on 10/21/19.
 */

public class WishListGUITest {
    
    @Before
    public void useTempFiles() throws IOException {
        File tempDirectory = new File("temporary_directory_for_test_files");
    
        File tempVisited = File.createTempFile("test_visited", ".txt", tempDirectory);
        File tempWishlist = File.createTempFile("test_wishlist", ".txt", tempDirectory);
    
        Main.wishListFile = tempWishlist.getPath();
        Main.visitedFile = tempVisited.getPath();
    }
    
    
    @After
    public void useRealFiles() {
        Main.wishListFile = "wishlist.txt";
        Main.visitedFile = "visited.txt";
    }
    
    
    private class WishListGUINoDialog extends WishListGUI {
        
        WishListGUINoDialog(List<String> w, List<String> v) {
            super(w, v);
        }
        
        String lastMessage;
        
        @Override
        protected void showMessageDialog(String msg) {
            lastMessage = msg;
        }
    }
    
    
    @Test(timeout = 3000)
    public void testAddNewPlace() {
    
        WishListGUI gui = new WishListGUI(new LinkedList<>(), new LinkedList<>());
        gui.newPlaceNameTextField.setText("Hawaii");
        gui.addButton.doClick();
    
        assertEquals("After adding one place, there should be 1 place in the wishList JList", 1, gui.wishList.getModel().getSize());
        assertEquals("After adding one place, there should be 1 place in the wishList JList", "Hawaii", gui.wishList.getModel().getElementAt(0));
        
        assertEquals("Clear the text in the newPlaceNameTextField when the new place has been added.", "", gui.newPlaceNameTextField.getText());
    
        gui.newPlaceNameTextField.setText("New York");
        gui.addButton.doClick();
    
        String msg = "\"After adding two places, there should be 2 place in the wishList JList";
        assertEquals(msg, 2, gui.wishList.getModel().getSize());
        assertEquals(msg, "Hawaii", gui.wishList.getModel().getElementAt(0));
        assertEquals(msg, "New York", gui.wishList.getModel().getElementAt(1));
    
        assertEquals("Clear the text in the newPlaceNameTextField when the new place has been added.", "", gui.newPlaceNameTextField.getText());
        
    }
    
    
    @Test(timeout = 3000)
    public void testAddDuplicatePlaceShowsMessageDialog() {
    
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
        gui.newPlaceNameTextField.setText("Hawaii");
        gui.addButton.doClick();
    
        gui.newPlaceNameTextField.setText("Hawaii");
        gui.addButton.doClick();
        
        assertNotNull("Show a message dialog if user tries to add duplicate place.", gui.lastMessage);   // was the message dialog shown?
    
        assertEquals("Don't add duplicate place names to the wishList", 1, gui.wishList.getModel().getSize());
        assertEquals("Don't add duplicate place names to the wishList", "Hawaii", gui.wishList.getModel().getElementAt(0));
    
    }
    
    @Test(timeout = 3000)
    public void testAddDuplicatePlaceShowsMessageDialogCaseInsensitive() {
        
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
        gui.newPlaceNameTextField.setText("Hawaii");
        gui.addButton.doClick();
        
        gui.newPlaceNameTextField.setText("HAWAII");
        gui.addButton.doClick();
        
        String msg = "Don't add duplicate place names to the wishList. 'Hawaii' and 'HAWAII' and 'hAwAiI' are the same name.";
        assertEquals(msg, 1, gui.wishList.getModel().getSize());
        assertEquals(msg, "Hawaii", gui.wishList.getModel().getElementAt(0));
        
        assertNotNull("Show a message dialog if user tries to add duplicate place", gui.lastMessage);   // was the message dialog shown?
        
        gui.newPlaceNameTextField.setText("hAwAiI");
        gui.addButton.doClick();
    
        assertEquals(msg, 1, gui.wishList.getModel().getSize());
        assertEquals(msg, "Hawaii", gui.wishList.getModel().getElementAt(0));
    
        assertNotNull("Show a message dialog if user tries to add duplicate place", gui.lastMessage);   // was the message dialog shown?
    }
    
    
    @Test(timeout = 3000)
    public void testAddEmptyPlaceIsNotAdded() {
        
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
        gui.newPlaceNameTextField.setText("");
        gui.addButton.doClick();
        
        assertEquals("Do not add empty strings to the wishlist", 0, gui.wishList.getModel().getSize());
        assertNull("Ignore empty and blank strings. Do not show a message dialog if the user tries to enter an empty or blank string.", gui.lastMessage);   // no message dialog shown
    }
    
    
    @Test(timeout = 3000)
    public void testAddBlankPlaceIsNotAdded() {
        
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
        gui.newPlaceNameTextField.setText("    \n    \t    ");   // this is a blank string - only whitespace
        gui.addButton.doClick();
        
        assertEquals("Do not add blank strings (Strings that are only whitespace) to the wishlist. \nHint: the String class has trim() and isBlank() methods that may help.", 0, gui.wishList.getModel().getSize());
        assertNull("Ignore empty and blank strings. Do not show a message dialog if the user tries to enter an empty or blank string.", gui.lastMessage);   // no message dialog shown
        
    }
    
    @Test(timeout = 3000)
    public void testWishListHasRightClickMenu() {
    
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
    
        JPopupMenu menu = gui.wishList.getComponentPopupMenu();
        assertNotNull("Add a JPopupMenu as a component popup menu to the wishList", menu);
    
        MenuElement[] elements = menu.getSubElements();
        assertEquals("The wishlist menu should have two options.", 2, elements.length);
    
        JMenuItem visitedMenu = (JMenuItem) elements[0];
        assertEquals("The first wishlist menu item should have the text 'Visited!'.", "Visited!", visitedMenu.getText());
    
        JMenuItem deleteMenu = (JMenuItem) elements[1];
        assertEquals("The second wishlist menu item should have the text 'Delete'.", "Delete", deleteMenu.getText());
    }
    
    
    @Test(timeout = 3000)
    public void testVisitedListHasRightClickMenu() {
        
        WishListGUINoDialog gui = new WishListGUINoDialog(new LinkedList<>(), new LinkedList<>());
        
        JPopupMenu menu = gui.visitedList.getComponentPopupMenu();
        assertNotNull("Add a JPopupMenu as a component popup menu to the visited list", menu);
        
        MenuElement[] elements = menu.getSubElements();
        assertEquals("The visited list menu should have one option.", 1, elements.length);
        
        JMenuItem deleteMenu = (JMenuItem) elements[0];
        assertEquals("The first visited list menu item should have the text 'Delete'.", "Delete", deleteMenu.getText());
    }


    @Test(timeout = 3000)
    public void testSaveAndQuitSavesToFiles() throws Exception {
        
        List<String> wish = new LinkedList<>();
        wish.add("Toronto");
        wish.add("Iceland");
        
        List<String> visited = new LinkedList<>();
        visited.add("Machu Picchu");
        visited.add("Steamboat Springs");
        
        WishListGUINoDialog gui = new WishListGUINoDialog(wish, visited);
        
        gui.saveQuitButton.doClick();
        
        byte[] wishBytes = Files.readAllBytes(Path.of(Main.wishListFile));
        String actualWritten = new String(wishBytes).replace("\r", "").trim();
        String expectedWritten = String.join("\n", wish);
        assertEquals("Finish the Storage.java writeListToFile method to write all of the wishlist places to a file, one name per line.", expectedWritten, actualWritten);
    
    
        byte[] visitedBytes = Files.readAllBytes(Path.of(Main.visitedFile));
        actualWritten = new String(visitedBytes).replace("\r", "").trim();
        expectedWritten = String.join("\n", visited);
        assertEquals("Finish the Storage.java writeListToFile method to write all of the visited places to a file, one name per line", expectedWritten, actualWritten);
    }
    
    
    @Test(timeout = 3000)
    public void testAppStartsWithNoFiles() throws Exception {
        
        Main.wishListFile = "this file doesn't exist";
        Main.visitedFile = "also not a file";
        Main.main(null);
        
        String msg = "When the program first starts, there are no files to read. Catch any exceptions in Storage.java\n" +
                "readListFromFile method, and return empty lists. In the WishListGUI constructor, if the lists provided are\n" +
                "empty, then you don't need to add anything to the JList models. ";
        
        ListModel wishListModel = Main.gui.wishList.getModel();
        assertEquals(msg, 0, wishListModel.getSize());
        
        ListModel visitedModel = Main.gui.visitedList.getModel();
        assertEquals(msg, 0, visitedModel.getSize());
        
    }
    
    
    @Test(timeout = 3000)
    public void testLoadingFiles() throws Exception {
        
        List<String> wish = new LinkedList<>();
        wish.add("Toronto");
        wish.add("Iceland");
        String wishFileText = String.join("\n", wish);
        
        Files.write(Path.of(Main.wishListFile), wishFileText.getBytes());
        
        List<String> visited = new LinkedList<>();
        visited.add("Machu Picchu");
        visited.add("Steamboat Springs");
        visited.add("Auckland");
    
        String visitedFileText = String.join("\n", visited);
        Files.write(Path.of(Main.visitedFile), visitedFileText.getBytes());
        
        Main.main(null);
        
        ListModel wishListModel = Main.gui.wishList.getModel();
        
        String msg = "If wishlist.txt has two place names in, these should both be displayed in the wishList JList, \n" +
                "in the same order as they were read from the file.";
        
        assertEquals(msg, 2, wishListModel.getSize());
        assertEquals(msg, "Toronto", wishListModel.getElementAt(0));
        assertEquals(msg, "Iceland", wishListModel.getElementAt(1));
    
    
        msg = "If visited.txt has three place names in, all three should be displayed in the wishList JList, \n" +
                "in the same order as they were read from the file.";
        
        ListModel visitedModel = Main.gui.visitedList.getModel();
        assertEquals(msg, 3, visitedModel.getSize());
        assertEquals(msg, "Machu Picchu", visitedModel.getElementAt(0));
        assertEquals(msg, "Steamboat Springs", visitedModel.getElementAt(1));
        assertEquals(msg, "Auckland", visitedModel.getElementAt(2));
        
    }
    
    @Test(timeout = 3000)
    public void testNotAllCodeInTheWishListGUIConstructor() {
        fail("This test will always fail. It will be assessed by the instructor. \n" +
                "Write and call methods for different tasks in WishListGUI. Don't write all of your code in the constructor.");
    }
    
    
}