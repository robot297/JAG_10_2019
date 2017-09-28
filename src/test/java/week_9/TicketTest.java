package week_9;

import org.junit.Test;
import test_utils.FileUtils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;


public class TicketTest {

    /* *************  Don't break ticket store methods ******************** */

    @Test
    public void testTicketStoreAddTicketSorted() throws Exception  {

        TicketStore store = new TicketStore();

        // Test tickets with all different priorities
        Ticket testPr1 = new Ticket("The server is on fire", 1, "reporter", new Date());
        Ticket testPr5 = new Ticket("Mouse mat stolen", 5, "reporter", new Date());
        Ticket testPr3 = new Ticket("Word needs updating", 3, "reporter", new Date());

        //Add these tickets. Assert they are added with lowest priority first
        store.add(testPr1); store.add(testPr5); store.add(testPr3);

        LinkedList allTickets = store.getAllTickets();

        assertEquals(allTickets.pop(), testPr1);
        assertEquals(allTickets.pop(), testPr3);
        assertEquals(allTickets.pop(), testPr5);


        // Tickets with joint priorities should be sorted by date
        store = new TicketStore();

        Ticket testPr1_newer = new Ticket("The server is down", 1, "reporter", new Date(1500000000000L));
        Ticket testPr1_older = new Ticket("Another server is down", 1, "reporter", new Date(1400000000000L));
        Ticket testPr4 = new Ticket("Several mouse mats stolen", 4, "reporter", new Date());

        //Add these tickets. Assert they are added with lowest priority first,
        //and equal priorities sorted with oldest first
        store.add(testPr1_newer); store.add(testPr1_older); store.add(testPr4);

        allTickets = store.getAllTickets();

        assertEquals(allTickets.pop(), testPr1_older);
        assertEquals(allTickets.pop(), testPr1_newer);
        assertEquals(allTickets.pop(), testPr4);

    }
    
    @Test
    public void testTicketStorePeekNextTicket() throws Exception {

        TicketStore store = new TicketStore();

        Ticket testPr4 = new Ticket("The server is dusty", 4, "1", new Date());
        Ticket testPr2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
        Ticket testPr3 = new Ticket("Mouse mat stolen", 3, "3", new Date());

        //Add these tickets
        store.add(testPr2); store.add(testPr4); store.add(testPr3);

        // Most severe should be returned
        assertEquals(store.peekNextTicket(), testPr2);

    }
    
    @Test
    public void testTicketStoreCountTicketsInQueue() throws Exception {

        TicketStore store = new TicketStore();

        Ticket testPr4 = new Ticket("The server is dusty", 4, "1", new Date());
        Ticket testPr2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
        Ticket testPr3 = new Ticket("Mouse mat stolen", 3, "3", new Date());

        //Add these tickets
        store.add(testPr2); store.add(testPr4); store.add(testPr3);

        // Most severe should be returned
        assertEquals(store.ticketsInQueue(), 3);

    }
    
    @Test
    public void testTicketStoreSearchDescription() throws Exception {

        TicketStore store = new TicketStore();

        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());
        Ticket test4 = new Ticket("Critical security updates", 1, "4", new Date());

        //Add these tickets

        store.add(test1); store.add(test2); store.add(test3); store.add(test4);

        // Search for 'server'. Should not be case sensitive, return test1 and test2

        LinkedList<Ticket> results = store.searchByDescription("Server");
        assertNotNull("Return a LinkedList of all Tickets whose description contains the search text. If no matches, return an empty list. Your search should not be case sensitive", results);
        assertEquals(results.size(), 2);   // 2 results
        assertTrue(results.contains(test1));
        assertTrue(results.contains(test2));
        assertFalse(results.contains(test3));
        assertFalse(results.contains(test4));


        // Search for something not in the list
        results = store.searchByDescription("Powerpoint");
        assertNotNull("Return a LinkedList of results. If no matches, return an empty list.", results);
        assertEquals("", results.size(), 0);   // No results

    }

    @Test
    public void testTicketStoreGetTicketByID() throws Exception {

        TicketStore store = new TicketStore();

        // Get the Ticket counter field and reset it
        Field ticketCounter = Class.forName("week_7.ticket.Ticket").getDeclaredField("ticketIdCounter");
        ticketCounter.setAccessible(true);
        ticketCounter.set(null, 1);

        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());    //1
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());  // 2
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());          // 3
        Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());    //4

        store.add(test1); store.add(test2); store.add(test3); store.add(test4);

        assertEquals(store.getTicketById(4), test4);
        assertEquals(store.getTicketById(3), test3);
        assertEquals(store.getTicketById(2), test2);
        assertEquals(store.getTicketById(1), test1);

    }
    
    @Test
    public void testTicketStoreDeleteTicketByID() throws Exception {

        TicketStore store = new TicketStore();

        // Get the Ticket counter field and reset it
        Field ticketCounter = Class.forName("week_7.ticket.Ticket").getDeclaredField("ticketIdCounter");
        ticketCounter.setAccessible(true);
        ticketCounter.set(null, 1);

        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());    //1
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());  // 2
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());          // 3
        Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());    //4

        store.add(test1); store.add(test2); store.add(test3); store.add(test4);

        store.deleteTicketById(3);

        LinkedList<Ticket> allTickets = store.getAllTickets();
        assertEquals(allTickets.size(), 3);
        assertFalse(allTickets.contains(test3));   // 3 has been deleted

        assertTrue(allTickets.contains(test1));
        assertTrue(allTickets.contains(test2));
        assertTrue(allTickets.contains(test4));  // Others still present


        // Delete non-existent ticket
        store.deleteTicketById(3);
        assertEquals(allTickets.size(), 3);   // List not changed

        store.deleteTicketById(-1);
        assertEquals(allTickets.size(), 3);   // List not changed

        // Delete another

        store.deleteTicketById(1);
        assertEquals(allTickets.size(), 2);   // one less ticket
        assertFalse(allTickets.contains(test1));   // 1 has been deleted

        assertTrue(allTickets.contains(test2));
        assertTrue(allTickets.contains(test4));  // Others still present


        // Delete last two, should not be errors

        store.deleteTicketById(2);
        store.deleteTicketById(4);
        assertEquals(allTickets.size(), 0);   // empty

        // Delete non-existent ticket

        store.deleteTicketById(1);   // no errors

    }
    
    
    /* *************** Test TicketUI Methods **********************/


    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testPriorityComboBoxConfigured() throws Exception {
    
        // empty ticket list

        TicketProgram program = new TicketProgram();
        program.start();
        
        JComboBox priorityCombo = program.ticketUI.priorityComboBox;

        //priorityComboBox should have 1-5 in
        
        assertEquals("The priority combo box should have 5 items in ", 5, priorityCombo.getItemCount());
        
        String[] vals = { "1","2","3","4","5"};

        for (int x = 1 ; x <= 5 ; x++) {
            String comboItem = (String) priorityCombo.getModel().getElementAt(x);
            assertTrue("Combo box should contain 1-5 in that order. Found " + comboItem + " at position " + x,
                    comboItem.equals(vals[x]));
        }
    }
    
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testTicketListConfigured() throws Exception {
        // shows example ticket list in correct order
        // set to single selection
        
        TicketStore testTicketStore = new TicketStore();
    
        Ticket test1 = new Ticket("Server keeps rebooting", 2, "user 1", new Date());  // 2
        Ticket test2 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());          // 3
    
        testTicketStore.add(test1); testTicketStore.add(test2);
        
        TicketProgram program = new TicketProgram();
        
        // use test ticket store
        program.ticketStore = testTicketStore;
    
        //bypass the load tickets method
        program.startGUI();
        
        JList ticketList = program.ticketUI.ticketList;
    
        // Should be single selection mode
        int listSelectionMode = ticketList.getSelectionMode();
        
        assertEquals("ticketList should use the single selection mode, to only select on ticket at a time.",
                listSelectionMode, ListSelectionModel.SINGLE_SELECTION);
        
        // Should be two tickets in list, in the given order (server reboot then mouse mat)
        assertEquals("With a test TicketList of 2 tickets, there should be 2 tickets in your JList", 2, ticketList.getModel().getSize());
        
        try {
            Ticket ticket1 = (Ticket) ticketList.getModel().getElementAt(0);
            Ticket ticket2 = (Ticket) ticketList.getModel().getElementAt(1);
            
            String msg = String.format("Using a test ticket list of \n%s, \n%s. \n\nYour JList should show these tickets in this order. \nFound \n%s \n%s",
                    test1, test2, ticket1, ticket2);
                            
            assertTrue(msg, sameOpenTicket(ticket1, test1));
            assertTrue(msg, sameOpenTicket(ticket2, test2));
            
        } catch (ClassCastException cce) {
            fail("Your JList should be a list of Ticket objects");
        }
        
    }
    
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testAddTicketValidData() throws Exception {
    
        System.out.println("If this test times out, ensure that you don't show any dialogs after clicking " +
                "the add button. If the ticket data is valid, add it directly to the JList.");
        // enter data
        // validate
        // add
        // verify shows up in ticket list
    
        TicketProgram.openticketsFile = null;
        
        TicketProgram program = new TicketProgram();
        TicketGUI gui = program.ticketUI;
        
        // Example data for ticket
        String description = "Server needs updating", reporter = "User";
        int priority = 3;
        
        Ticket expected = new Ticket(description, priority, reporter , new Date());
        
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
        
        // click add button
        
        gui.addButton.doClick();
        
        // Now ticket should be the first and only element in JList
        
        assertEquals("After adding 1 ticket, there should be one ticket in the JList", 1, gui.ticketList.getModel().getSize() );
        
        Ticket actualTicket = (Ticket) gui.ticketList.getModel().getElementAt(0);
    
        System.out.println("Expected ticket =" + expected);
        System.out.println("Actual ticket created by the program =" + actualTicket);
    
        assertEquals("Ticket added to the list should have the same data as that entered into GUI", expected.getDescription(), actualTicket.getDescription());
        assertEquals("Ticket added to the list should have the same data as that entered into GUI", expected.getPriority(), actualTicket.getPriority());
        assertEquals("Ticket added to the list should have the same data as that entered into GUI", expected.getReporter(), actualTicket.getReporter());
        
        // Date should be close (within, for example, 3 seconds)
        
        assertEquals("Ticket added to the list should have the same data as that entered into GUI. " +
                "The date will not be exactly the same but should be within a few seconds.", expected.getDateReported().getTime(), actualTicket.getDateReported().getTime(), 3000);
    }
    
    
    
    @Test(timeout=2000)
    public void testSearchById() throws Exception {
    
        TicketStore testTicketStore = new TicketStore();
    
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "user 1", new Date());
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());
    
        testTicketStore.add(test2); testTicketStore.add(test3);
    
        TicketProgram program = new TicketProgram();
    
        // use test ticket store
        program.ticketStore = testTicketStore;
    
        //bypass the load tickets method
        program.startGUI();
        
        TicketGUI gui = program.ticketUI;
        JList ticketList = gui.ticketList;
        
        // Search for ticket id = 2
        gui.idSearchTextBox.setText("2");
        gui.searchIdButton.doClick();
        
        // List should only show ticket 2
    
        Ticket expected = (Ticket )ticketList.getModel().getElementAt(0);
        
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test2, expected));
        assertEquals("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", 1, ticketList.getModel().getSize() );
    
        // Update search status
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel", TicketGUI.TICKET_MATCHING_ID, gui.ticketListStatusDescription.getText());
        
    
        // Search for ticket 3, should now only show ticket 3 in the list
        
        gui.idSearchTextBox.setText("3");
        gui.searchIdButton.doClick();
    
        // List should now only show ticket 3
    
        expected = (Ticket )ticketList.getModel().getElementAt(0);
    
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test3, expected));
        assertEquals("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", 1, ticketList.getModel().getSize() );
    
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel", TicketGUI.TICKET_MATCHING_ID, gui.ticketListStatusDescription.getText());
    
    
        // Search for ticket id = 4, does not exist in list
    
        gui.idSearchTextBox.setText("4");
        gui.searchIdButton.doClick();
    
        // List should be empty
    
        assertEquals("After searching for ticket ID that does not exist, the JList should be empty",
                0, ticketList.getModel().getSize() );

        assertEquals("After searching for ID that doesn't exist, update the ticketListStatusDescription JLabel to " + gui.NO_TICKETS_FOUND,
                gui.NO_TICKETS_FOUND, gui.ticketListStatusDescription.getText());
    
    
    
        // Search for ticket id = "", empty string, does not exist in list
    
        gui.idSearchTextBox.setText("");
        gui.searchIdButton.doClick();
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket ID \"\" the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for invalid ID, update the ticketListStatusDescription JLabel to " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
    
        // Search for non-numerical ticket id = "pizza", does not exist in list
    
        gui.idSearchTextBox.setText("pizza");
        gui.searchIdButton.doClick();
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket ID \"pizza\" the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for invalid ID, update the ticketListStatusDescription JLabel to " + gui.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
    
        // Search for negative ticket id = "-2", does not exist in list
    
        gui.idSearchTextBox.setText("-2");
        gui.searchIdButton.doClick();
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket ID \"-2\" the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for invalid ID, update the ticketListStatusDescription JLabel to " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
        
    
        // Click show all
        
        gui.showAllTicketsButton.doClick();
    
        assertEquals("Update the ticketListStatusDescription JLabel to " + TicketGUI.ALL_TICKETS,
                TicketGUI.ALL_TICKETS, gui.ticketListStatusDescription.getText());
        
        assertEquals("When show all tickets is clicked, show all tickets in the JList", 2, ticketList.getModel().getSize());
    
    }
    
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testSearchByDescription() throws Exception {
        
        TicketStore testTicketStore = new TicketStore();
    
        Ticket test1 = new Ticket("Where is my mouse?", 1, "user 1", new Date());
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "user 1", new Date());
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());
    
        testTicketStore.add(test1); testTicketStore.add(test2); testTicketStore.add(test3);
        
        TicketProgram program = new TicketProgram();
    
        // use test ticket store
        program.ticketStore = testTicketStore;
    
        //bypass the load tickets method to start with no tickets
        program.startGUI();
        
        System.out.println("This test uses the following test tickets" + testTicketStore.getAllTickets());
        
        TicketGUI gui = program.ticketUI;
    
        JList ticketList = gui.ticketList;
    
        // Search for ticket text "mouse". Should return tickets with ID 1 and 3
    
        gui.descriptionSearchTextBox.setText("mouse");
        gui.searchDescriptionButton.doClick();
    
        // List should show ticket 1 and 3
    
        Ticket expected1 = (Ticket) ticketList.getModel().getElementAt(0);
        Ticket expected3 = (Ticket) ticketList.getModel().getElementAt(1);
    
        assertTrue("After searching for 'mouse', all matching tickets should be" +
                " shown in the JList", sameOpenTicket(test1, expected1));
        assertTrue("After searching for 'mouse', all matching tickets should be" +
                " shown in the JList", sameOpenTicket(test3, expected3));
    
        // Update search status
    
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel",
                TicketGUI.TICKETS_MATCHING_DESCRIPTION, gui.ticketListStatusDescription.getText());
    
    
        // Search for ticket "Server keeps rebooting", should now only show ticket 2 in the list
    
        gui.descriptionSearchTextBox.setText("Server keeps rebooting");
        gui.searchDescriptionButton.doClick();
    
        // List should now only show ticket 1
    
        Ticket expected2 = (Ticket )ticketList.getModel().getElementAt(0);
    
        assertTrue("After searching for ticket 'Server keeps rebooting' that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test2, expected2));
       
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel",
                TicketGUI.TICKETS_MATCHING_DESCRIPTION, gui.ticketListStatusDescription.getText());
    
    
        // Search for ticket text = "Powerpoint", does not exist in list
    
        gui.descriptionSearchTextBox.setText("Powerpoint");
        gui.searchDescriptionButton.doClick();
    
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket description \"Powerpoint\" the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for description that does not match any tickets, update the ticketListStatusDescription JLabel to " + gui.NO_TICKETS_FOUND,
                TicketGUI.NO_TICKETS_FOUND, gui.ticketListStatusDescription.getText());
    
        
        // Search for empty string, should return no tickets
        gui.idSearchTextBox.setText("");
        gui.searchIdButton.doClick();
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket description with an empty string, the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals(""After searching for ticket description with an empty string, " + gui.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
        
    
        // Click show all
    
        gui.showAllTicketsButton.doClick();
    
        assertEquals("Update the ticketListStatusDescription JLabel to " + gui.ALL_TICKETS,
                gui.ALL_TICKETS, gui.ticketListStatusDescription.getText());
        
        assertEquals("When show all tickets is clicked, show all tickets in the JList",
                3, ticketList.getModel().getSize());
    
    
    
    
    }
    
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testDeleteSelected() throws Exception {
        //get resolutuoin
    }
    
    
    
    
    @Test(timeout=10000)
    public void testEndToEnd() throws Exception {
        // Launch
        // Add ticket
        // ticket in list
        // add another ticket
        // ticket list correct & msg correct
        // search for a ticket by id
        // ticket list correct & msg correct
        // search for ticket by description
        // ticket list correct  & msg correct
        // delete one ticket
        // ticket list correct  & msg correct
        // add another ticket
        // ticket list correct & msg correct
        // Close
        // relaunch
        // ticket list correct & msg correct
        // search by id
        // search by description
        // select and delete one ticket
        // close and relaunch
        // ticketlist still correct & msg correct

    }

    
  
    /* *********** Resolved Tickets in Files ********************/

    @Test(timeout=3000)
    public void saveAndRestoreTickets() throws Exception {

        // Provide dummy return values from any input used
        // Do not overwrite user's ticket file
        
        TicketProgram.openticketsFile = FileUtils.uniqueFilename("support_ticket_gui");
        
        TicketProgram program = new TicketProgram();

        // Test tickets with  different priorities
        Ticket test1 = new Ticket("Mouse mat stolen", 5, "reporter", new Date());
        Ticket test2 = new Ticket("Word needs updating", 3, "reporter", new Date());

        // Force these tickets into the store
       
        program.addTicket(test1);  program.addTicket(test2);

        program.quitProgram();    // End program. These tickets should be saved to a file.
    
        TicketProgram relaunchedProgram = new TicketProgram();

        // The tickets should have been read from a file, and be available.

        LinkedList<Ticket> ticketList_relaunch = relaunchedProgram.getAllTickets();

        assertEquals("There should be the same number of open tickets after your app is restarted. " +
                "Save all open tickets to a file when app closes, read the tickets from the file when it restarts.", ticketList_relaunch.size(), 2);

        // Same tickets? Should be in the same priority order

        Ticket read_1 = ticketList_relaunch.pop();
        Ticket read_2 = ticketList_relaunch.pop();
        String msg = "Read all data from the file to create a new ticket list.  " +
                "Wrote out \n%s\n and got \n%s\n back. Make sure all the data is the same as the original ticket.";
        assertTrue(String.format(msg, test1, read_1), sameOpenTicket(ticketList_relaunch.pop(), test2) );
        assertTrue(String.format(msg, test2, read_2), sameOpenTicket(ticketList_relaunch.pop(), test2) );

    }
    
    
    @Test(timeout=3000)
    public void saveResolvedTickets() throws Exception {
    
        fail();
    }
        
        
        
        
        private boolean sameOpenTicket(Ticket t1, Ticket t2) throws Exception {
        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
        
        if (t1.getTicketID() != t2.getTicketID()) { return false; }
        if (!(t1.getDateReported().equals(t2.getDateReported())))  { return false; }
        if (!(t1.getDescription().equals(t2.getDescription())))  { return false; }
        if (!(t1.getReporter().equals(t2.getReporter())))  { return false; }

        return true;

    }


    @Test
    public void testManualChecksQuestion() {
        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
                "\nThe instructor will check your work and assign the rest of the points");
    }


}