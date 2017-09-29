package week_9;

import org.junit.After;
import org.junit.Before;
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
        
            Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());    //1
            Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());  // 2
            Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());          // 3
            Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());    //4
        
            store.add(test1); store.add(test2); store.add(test3); store.add(test4);
        
            assertEquals(test4, store.getTicketById(4));
            assertEquals(test3, store.getTicketById(3));
            assertEquals(test2, store.getTicketById(2));
            assertEquals(test1, store.getTicketById(1));
        
            assertEquals(null, store.getTicketById(0));  // not valid
            assertEquals(null, store.getTicketById(-2));  // not valid
            assertEquals(null, store.getTicketById(200));  // doesn't exist valid
        
    }
    
    @Test
    public void testTicketStoreDeleteTicketByID() throws Exception {

        TicketStore store = new TicketStore();
        
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

    // TASK 1 Configure Combobox

    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testPriorityComboBoxConfigured() throws Exception {
    
        // empty ticket list

        TicketProgram program = new TicketProgram();
        program.startGUI();
        
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

    // TASK 2 Configure JList
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testTicketListConfigured() throws Exception {
        
        // set to single selection
        // Model uses Ticket objects
        
        TicketProgram program = new TicketProgram();
        
        //bypass the load tickets method
        program.startGUI();
        
        JList ticketList = program.ticketUI.ticketList;
                
        try {
            DefaultListModel model = (DefaultListModel) ticketList.getModel();
        } catch (ClassCastException cce) {
            fail("Create a DefaultListModel<Ticket> and use it as the ticketList data model. It should only store Ticket objects");
        }
        
        // Should be single selection mode
        int listSelectionMode = ticketList.getSelectionMode();
        
        assertEquals("ticketList should use the single selection mode, to only select one ticket at a time.",
                listSelectionMode, ListSelectionModel.SINGLE_SELECTION);
        
    }
    
    // TASK 3 Get all Data
    
    @Test(timeout=2000)
    public void testTicketListGetAllData() throws Exception {
        // shows example ticket list in correct order
        
        TicketStore testTicketStore = new TicketStore();
        
        Ticket test1 = new Ticket("Server keeps rebooting", 2, "user 1", new Date());
        Ticket test2 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());
        
        testTicketStore.add(test1); testTicketStore.add(test2);
        
        TicketProgram program = new TicketProgram();
        
        // use test ticket store
        program.ticketStore = testTicketStore;
        
        //bypass the load tickets method
        program.startGUI();
        
        JList ticketList = program.ticketUI.ticketList;
        
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
    
    // TASK 4 Add Ticket
    
    @Test(timeout=2000)
    public void testAddTicketToEmptyListValidData() throws Exception {
    
        System.out.println("If this test times out, ensure that you don't show any dialogs after clicking " +
                "the add button if the data is valid. If the ticket data is valid, add it directly to the JList.");
        
        String msg = "Ticket added to the list should have the same data as that entered into GUI. " +
                "The date need not be exactly the same but it must be close.";
        
        
        TicketGUI gui = createGUIWithEmptyTicketStore();
        
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
        
        Ticket actualTicket = gui.ticketList.getModel().getElementAt(0);
    
        System.out.println("Expected ticket =" + expected);
        System.out.println("Actual ticket created by the program =" + actualTicket);
        
        assertTrue(msg, sameOpenTicket(actualTicket, expected, 4000));
        assertEquals("Added one ticket, should be 1 in data store", 1, gui.manager.getAllTickets().size());
    
    
    
        // Example data for another ticket
        description = "Mouse mat fell off desk"; reporter = "Another User";
        priority = 1;
    
        Ticket expected2 = new Ticket(description, priority, reporter , new Date());
    
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
    
        // click add button
        gui.addButton.doClick();
    
        assertEquals("After adding 1 ticket, there should be two tickets in the JList", 2, gui.ticketList.getModel().getSize() );
    
        Ticket actualTicket2 = gui.ticketList.getModel().getElementAt(0);   // This one is way more urgent so should be at the top
        Ticket actualTicket1 = gui.ticketList.getModel().getElementAt(1);   // This one is way more urgent so should be at the top
    
        System.out.println("Created another ticket. Expected ticket =" + expected2);
        System.out.println("Actual ticket created by the program =" + actualTicket2);
        
        assertTrue(msg, sameOpenTicket(actualTicket1, expected, 4000));
        assertTrue(msg, sameOpenTicket(actualTicket2, expected2, 4000));
        
        assertEquals("Added two tickets, should be 2 in data store", 2, gui.manager.getAllTickets().size());
        
    }

    // TASK 4 Add Ticket
    
    @Test(timeout=2000)
    public void testAddTicketAlreadyPopulatedListValidData() throws Exception {
        
       TicketGUI gui = createGUIWithTestData();
        
        int originalSize = gui.manager.getAllTickets().size();
        
        // Example data for new ticket
        String description = "Server needs updating", reporter = "User";
        int priority = 4;
    
        Ticket expected = new Ticket(description, priority, reporter , new Date());
    
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
    
        // click add button
        gui.addButton.doClick();
    
        // Now ticket should be the first and only element in JList
        assertEquals("After adding 1 ticket to a list with 3 tickets in, there should be 4 tickets in the JList",
                4, gui.ticketList.getModel().getSize());
    
        Ticket actualTicket = gui.ticketList.getModel().getElementAt(2);    // New ticket should be at position 2
    
        System.out.println("Original test ticket list " + gui.manager.getAllTickets());
        System.out.println("Expected ticket =" + expected);
        System.out.println("Actual ticket created by the program =" + actualTicket);
    
        assertTrue("A new ticket with priority 4 to the test list should be added at position 2", sameOpenTicket(actualTicket, expected, 4000));
        assertEquals("A new ticket should be added to the data store.", originalSize + 1, gui.manager.getAllTickets().size());
    }
    
    // TASK 4 Add Ticket
    
    @Test(timeout=2000)
    public void testAddTicketInvalidData() throws Exception {
        
        TicketGUIMockDialog gui = createGUIWithEmptyTicketStore();
        
        // Example invalid data for new ticket
        invalidAddTicket(gui, "", "", null);
        invalidAddTicket(gui, "Mouse mat", "", null);
        invalidAddTicket(gui, "", "The User", null);
        invalidAddTicket(gui, "", "", 3);
        invalidAddTicket(gui, "Mouse mat", "User", null);
        invalidAddTicket(gui, "", "User", 2);
        invalidAddTicket(gui, "Mpuse", "", 3);
    
    }

    
    private void invalidAddTicket(TicketGUIMockDialog gui, String description, String reporter, Integer comboboxSelection) {
        // enter data - no selection for priority
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(comboboxSelection);
    
        // click add button
        gui.addButton.doClick();
    
        // should be alert dialog shown
    
        assertTrue(String.format("Show an alert dialog if the new ticket data if description= '%s' reporter= '%s' priority= %s", description, reporter, comboboxSelection),
                gui.checkAlertWasCalled());
    
        // Nothing in JList or ticket Store
        assertEquals("After attempting to add invalid ticket, there should be no tickets in the ticketList",
                0, gui.ticketList.getModel().getSize());
    
        assertEquals("If invalid data is entered, no ticket should be added to the ticketStore", 0, gui.manager.getAllTickets().size());
    
    }
    
    //TASK 5 search by id
    
    @Test(timeout=2000)
    public void testSearchById() throws Exception {
    
        TicketStore testTicketStore = new TicketStore();
    
        Ticket test2 = new Ticket("Server keeps rebooting", 2, "user 1", new Date());
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());
    
        testTicketStore.add(test2); testTicketStore.add(test3);
        TicketProgram program = new TicketProgram();
    
        program.ticketStore = testTicketStore;  // use test ticket store
        program.startGUI();  // bypass load from file
        
        TicketGUI gui = program.ticketUI;
        JList ticketList = gui.ticketList;
        
        // Search for ticket id = 2
        gui.idSearchTextBox.setText("2");
        gui.searchIdButton.doClick();
        
        // List should only show ticket 2
        
        Ticket expected = null;
        try {
            expected = (Ticket) ticketList.getModel().getElementAt(0);
        } catch (IndexOutOfBoundsException e) {
            fail("Create a data model for your JList. Search test data for ID 2, should be 1 match, this ticket should be shown in JList.");
        }
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test2, expected));
        assertEquals("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", 1, ticketList.getModel().getSize());
    
        // Update search status
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel",
                TicketGUI.TICKET_MATCHING_ID, gui.ticketListStatusDescription.getText());
        
    
        // Search for ticket 3, should now only show ticket 3 in the list
        
        gui.idSearchTextBox.setText("3");
        gui.searchIdButton.doClick();
    
        // List should now only show ticket 3
    
        expected = (Ticket )ticketList.getModel().getElementAt(0);
    
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test3, expected));
        assertEquals("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", 1, ticketList.getModel().getSize() );
    
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel",
                TicketGUI.TICKET_MATCHING_ID, gui.ticketListStatusDescription.getText());
    
    
        // Search for ticket id = 4, does not exist in list
    
        gui.idSearchTextBox.setText("4");
        gui.searchIdButton.doClick();
    
        // List should be empty
    
        assertEquals("After searching for ticket ID that does not exist, the JList should be empty",
                0, ticketList.getModel().getSize() );

        assertEquals("After searching for ID that doesn't exist, update the ticketListStatusDescription JLabel to " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.NO_TICKETS_FOUND, gui.ticketListStatusDescription.getText());
        
        // Searches for things that are not valid IDs
        invalidIDSearch(gui, "");
        invalidIDSearch(gui, "Pizza");
        invalidIDSearch(gui, "-2");    // negative integers are not valid
        
        // Click show all
        gui.showAllTicketsButton.doClick();
    
        assertEquals("Update the ticketListStatusDescription JLabel to " + TicketGUI.ALL_TICKETS,
                TicketGUI.ALL_TICKETS, gui.ticketListStatusDescription.getText());
        
        assertEquals("When show all tickets is clicked, show all tickets in the JList", 2, ticketList.getModel().getSize());
    
    }
    
    private void invalidIDSearch(TicketGUI gui, String searchText) {
        gui.idSearchTextBox.setText(searchText);
        gui.searchIdButton.doClick();
        // List should be empty, invalid message shown
        assertEquals("After searching for ticket ID \"" + searchText + "\" the JList should be empty",
                0, gui.ticketList.getModel().getSize() );
        assertEquals("After searching for invalid ID, update the ticketListStatusDescription JLabel to " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
    }
    
    //TASK 6 Search by description
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testSearchByDescription() {
        
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
        
        try {
    
            Ticket expected1 = (Ticket) ticketList.getModel().getElementAt(0);
            Ticket expected3 = (Ticket) ticketList.getModel().getElementAt(1);
    
            assertTrue("After searching for 'mouse', all matching tickets should be" +
                    " shown in the JList", sameOpenTicket(test1, expected1));
            assertTrue("After searching for 'mouse', all matching tickets should be" +
                    " shown in the JList", sameOpenTicket(test3, expected3));
        } catch(IndexOutOfBoundsException e) {
            fail("Create a data model for your JList. All matching tickets should be shown in JList after the search.");
        }
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
    
        // List should be empty, no ticket found message shown
        assertEquals("After searching for ticket description \"Powerpoint\" the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for description that does not match any tickets, update the ticketListStatusDescription JLabel to " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.NO_TICKETS_FOUND, gui.ticketListStatusDescription.getText());
    
        
        // Search for empty string, should return no tickets
        gui.idSearchTextBox.setText("");
        gui.searchIdButton.doClick();
        
        // List should be empty, no ticket found message shown
        assertEquals("After searching for ticket description with an empty string, the JList should be empty",
                0, ticketList.getModel().getSize() );
        assertEquals("After searching for ticket description with an empty string, " + TicketGUI.NO_TICKETS_FOUND,
                TicketGUI.INVALID_TICKET_ID, gui.ticketListStatusDescription.getText());
    
        
        // Click show all
    
        gui.showAllTicketsButton.doClick();
    
        assertEquals("Update the ticketListStatusDescription JLabel to " + TicketGUI.ALL_TICKETS,
                TicketGUI.ALL_TICKETS, gui.ticketListStatusDescription.getText());
        
        assertEquals("When show all tickets is clicked, show all tickets in the JList", 3, ticketList.getModel().getSize());
        
    }
    
    
    // TASK 7 Delete selected
    
    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
    public void testDeleteSelected()  {
       
        TicketGUIMockDialog gui = createGUIWithTestData();
        
        // delete ticket 2
        
        gui.ticketList.setSelectedIndex(2);
        
        // expect input dialog after this line, so set it up first
        
        String userInput = "User fixed their screensaver";
        gui.mockUserInput = userInput;
        
        gui.deleteSelectedButton.doClick();
        
        // should have an input dialog shown and returning the value given
        
        
        
        
        // On the back end, resolved tickets should contain this ticket
    }
    
    
    class TicketGUIMockDialog extends TicketGUI {
        TicketGUIMockDialog(TicketProgram t){ super(t);}
        
        private boolean wasAlertCalled = false;
        
        public boolean checkAlertWasCalled() {
            boolean copyToReturn = wasAlertCalled;
            wasAlertCalled = false;
            return copyToReturn;
        }
        
        @Override
        protected void showAlertDialog(String msg){
            // do nothing so the program does not show an alert dialog
            wasAlertCalled = true;
        }
        
        // Set this to force showInputDialog to return a particular value
        
        String mockUserInput;
        
        @Override
        protected String showInputDialog(String msg) {
            if (mockUserInput == null) { fail("you need to set the mockUserInput value before using this in a test. This is clara's fault, pls let her know. ");}
            String copyToReturn = new String(mockUserInput);
            mockUserInput = null;
            return copyToReturn;
        }
        
        
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
        
        fail();

    }

    ArrayList<String> testFileNames;
    
    @Before
    public void prepareToStoreTestFiles() {
        testFileNames = new ArrayList<>();
    }
    
    @After
    public void cleanupFles() {
        for (String file : testFileNames) {
            FileUtils.moveToTemporaryTestFolder(file);
        }
    }
    
    /* *********** Resolved Tickets in Files ********************/

    @Test(timeout=3000)
    public void saveAndRestoreTickets() throws Exception {
        
        // Do not overwrite student's ticket file
        
        /* Data:
        
        Ticket test1 = new Ticket("Server keeps rebooting", 1, "user 1", new Date());  ID 1
        Ticket test3 = new Ticket("Mouse stolen", 5, "user 2", new Date());   ID 3
        Ticket test2 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());   ID 2
        */
        
        String openTicketFile = FileUtils.uniqueFilename("support_ticket_gui");
        testFileNames.add(openTicketFile);
        TicketProgram.openTicketsFile = openTicketFile;
        
        TicketGUI gui = createGUIWithTestData();
        LinkedList<Ticket> originalTickets = gui.manager.getAllTickets();
        
        gui.saveAndQuitButton.doClick();   // This should invoke the save and quit method for the test Tickets.
        
        TicketProgram relaunchedProgram = new TicketProgram();
        relaunchedProgram.start();

        // The tickets should have been read from a file, and be available.

        LinkedList<Ticket> ticketList_relaunch = relaunchedProgram.getAllTickets();

        assertEquals("There should be the same number of open tickets after your app is restarted. " +
                "Save all open tickets to a file when app closes, read the tickets from the file when it restarts.",
                ticketList_relaunch.size(), originalTickets.size());

        // Same tickets? Should be in the same priority order


        for (int t = 0 ;  t < ticketList_relaunch.size() ; t++) {
            String msg = "Read all data from the file to create a new ticket list.  " +
                    "Wrote out \n%s\n and got \n%s\n back. Make sure all the data is the same as the original ticket.";
            Ticket expected = originalTickets.get(t);
            Ticket actual = ticketList_relaunch.get(t);

            assertTrue(String.format(msg, expected, actual), sameOpenTicket(expected, actual));
        }
        
        
    }
    
    
    @Test(timeout=3000)
    public void saveResolvedTicketsAppendSameDay() throws Exception {
    
        fail();
    }
    
    
    
    @Test(timeout=3000)
    public void testRestoreTicketsNewTicketsUniqueID() throws Exception {
    
        String openTicketFile = FileUtils.uniqueFilename("support_ticket_gui");
        testFileNames.add(openTicketFile);
        TicketProgram.openTicketsFile = openTicketFile;
    
        TicketGUI gui = createGUIWithTestData();
        LinkedList<Ticket> originalTickets = gui.manager.getAllTickets();
    
        gui.saveAndQuitButton.doClick();   // This should invoke the save and quit method for the test Tickets.
    
        TicketProgram relaunchedProgram = new TicketProgram();
        relaunchedProgram.start();
    
        // The tickets should have been read from a file, and be available.
    
        
        TicketGUI relaunchedGUI = relaunchedProgram.ticketUI;
        
        relaunchedGUI.descriptionTextField.setText("Powerpoint keeps crashing.");
        relaunchedGUI.priorityComboBox.setSelectedIndex(3);
        relaunchedGUI.reporterTextField.setText("The User");
 
        relaunchedGUI.addButton.doClick();
        
        //This ticket should have a new ID, positive, not zero, and different to any other in the list.
    
        try{
            LinkedList<Ticket> allTickets = relaunchedProgram.getAllTickets();
            // Should have 4 tickets now
            assertEquals("Three test tickets plus one new one should equal 4 tickets in the relaunched app.", 4, relaunchedProgram.getAllTickets().size());
    
            boolean found = false;
            
            for (Ticket t : allTickets) {
                if (t.getDescription().contains("Powerpoint")) {
                    // there's the new ticket
                    int newId = t.getTicketID();
                    assertTrue("If existing tickets have IDs 1, 2, 3, new ticket ID must be more than 4", newId >= 4);
                    
                }
            }
            
            assertTrue("Tried to add new ticket 'Powerpoint keeps crashing' after relaunching program, but did not find it in the ticket list.", found);
            
        } catch (Exception e) {
            fail("Attempting to relaunch program, read ticket store, and search by description to find new ticket; take first item from ticket list.\n" +
                    "Verify that new ticket are being created in the ticket store when the application is relaunched.");
        }
    }
    
    
    
    
    private TicketGUIMockDialog createGUIWithEmptyTicketStore() {
    
        TicketStore testTicketStore = new TicketStore();
        
        TicketProgram program = new TicketProgram();
        program.ticketStore = testTicketStore;     // use test ticket store
        //program.startGUI();   //bypass the load tickets method
    
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketUI = gui;
    
        return gui;
   
    }
    
    
    private TicketGUIMockDialog createGUIWithTestData() {
        
        TicketStore testTicketStore = new TicketStore();
        
        Ticket test2 = new Ticket("Server keeps rebooting", 1, "user 1", new Date());
        Ticket test4 = new Ticket("Mouse stolen", 5, "user 2", new Date());
        Ticket test3 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());
    
        testTicketStore.add(test2); testTicketStore.add(test3); testTicketStore.add(test4);
        
        TicketProgram program = new TicketProgram();
        program.ticketStore = testTicketStore;     // use test ticket store
        //program.startGUI();   //bypass the load tickets method
        
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketUI = gui;
        
        return gui;
    }
    
        
    private boolean sameOpenTicket(Ticket t1, Ticket t2)  {
        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
        
        return sameOpenTicket(t1, t2, 0);

    }
    
    
    private boolean sameOpenTicket(Ticket t1, Ticket t2, long acceptableMsDifferenceInDate)  {
        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
        
        if (t1.getTicketID() != t2.getTicketID()) { return false; }
        if (!(t1.getDescription().equals(t2.getDescription())))  { return false; }
        if (!(t1.getReporter().equals(t2.getReporter())))  { return false; }
        
        long dateDiff = Math.abs(t1.getDateReported().getTime() - t2.getDateReported().getTime());
        if (dateDiff > acceptableMsDifferenceInDate) {
            // Within 5 seconds
            return false;
        }
        
        return true;
        
    }
    

    
    
    @Test
    public void testManualChecksQuestion() {
        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
                "\nThe instructor will check your work and assign the rest of the points");
    }


}