package week_10;

import org.assertj.swing.core.EmergencyAbortListener;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JListFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.After;
import org.junit.Test;

import javax.swing.*;
import java.util.LinkedList;


import static junit.framework.TestCase.assertEquals;

/**
 * Created by clara on 10/21/19.
 */
public class WishListMenuTest extends AssertJSwingJUnitTestCase {
    
    private FrameFixture window;
    private EmergencyAbortListener listener;
    
    @Override
    protected void onSetUp() {
        
        listener = EmergencyAbortListener.registerInToolkit();
        // Control + Shift + A stops the tests
        
        WishListGUI frame = GuiActionRunner.execute( () ->
                new WishListGUI(new LinkedList<>(), new LinkedList<>()));
        
        window = new FrameFixture(robot(), frame);
        window.show();
    }
    
    @After
    public void removeListener() {
        listener.unregister();
    }
    
    
    
    @Test(timeout = 20000)
    public void rightClickDeleteWishList() {
    
        window.textBox("newPlaceName").enterText("New York");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Tokyo");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Cairo");
        window.button("addButton").click();
        
        JListFixture wishlist = window.list("wishList");
    
        wishlist.showPopupMenuAt(1).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Delete".equals(jMenuItem.getText());
            }
        }).click();   // "Tokyo"
    
        wishlist.requireItemCount(2);
        
        assertEquals("New York", wishlist.item(0).value());
        assertEquals("Cairo", wishlist.item(1).value());
        
        window.list("visitedList").requireItemCount(0);
    
        
        wishlist.showPopupMenuAt(1).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Delete".equals(jMenuItem.getText());
            }
        }).click();   // "Cairo"
    
        wishlist.requireItemCount(1);
        assertEquals("New York", wishlist.item(0).value());
    }
    
    
    @Test(timeout = 20000)
    public void rightClickVisitPlaceWishList() {
        window.textBox("newPlaceName").enterText("New York");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Tokyo");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Cairo");
        window.button("addButton").click();
    
        JListFixture wishlist = window.list("wishList");
        
        wishlist.showPopupMenuAt(1).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Visited!".equals(jMenuItem.getText());
            }
        }).click();   // "Tokyo"
    
        wishlist.requireItemCount(2);
        assertEquals("New York", wishlist.item(0).value());
        assertEquals("Cairo", wishlist.item(1).value());
    
        window.list("visitedList").requireItemCount(1);
        assertEquals("Tokyo", window.list("visitedList").item(0).value());
        
        
        wishlist.showPopupMenuAt(0).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Visited!".equals(jMenuItem.getText());
            }
        }).click();   // "New York"
        
        wishlist.requireItemCount(1);
        assertEquals("Cairo", wishlist.item(0).value());
    
        window.list("visitedList").requireItemCount(2);
        assertEquals("Tokyo", window.list("visitedList").item(0).value());
        assertEquals("New York", window.list("visitedList").item(1).value());
        
    }
    
    @Test(timeout = 20000)
    public void rightClickDeleteVisitedList() {
    
        window.textBox("newPlaceName").enterText("New York");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Tokyo");
        window.button("addButton").click();
    
        window.textBox("newPlaceName").enterText("Cairo");
        window.button("addButton").click();
    
        JListFixture wishlist = window.list("wishList");
        JListFixture visitedList = window.list("visitedList");
        
    
        wishlist.showPopupMenuAt(1).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Visited!".equals(jMenuItem.getText());
            }
        }).click();   // "Tokyo"
    
    
        wishlist.showPopupMenuAt(0).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Visited!".equals(jMenuItem.getText());
            }
        }).click();   // "New York"
    
    
        visitedList.showPopupMenuAt(1).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Delete".equals(jMenuItem.getText());
            }
        }).click();   // "New York"
        
        visitedList.requireItemCount(1);
        assertEquals("Tokyo", window.list("visitedList").valueAt(0));
    
        
        visitedList.showPopupMenuAt(0).menuItem(new GenericTypeMatcher<>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem jMenuItem) {
                return "Delete".equals(jMenuItem.getText());
            }
        }).click();   // "Toyko"
        
        visitedList.requireItemCount(0);
        
    }
    
  
    

}
