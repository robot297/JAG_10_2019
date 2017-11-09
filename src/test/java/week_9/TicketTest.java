package week_9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test_utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;


public class TicketTest {
    
    // Some example test data, created before tests run, and available to all test methods
    
    private Ticket test_id_1, test_id_2, test_id_3;
    private LinkedList<Ticket> testTickets = new LinkedList<>();
    private int testNextId;    // Will be based on the given test data.
    
    
    @Before
    public void printTimeoutMessage() {
        System.out.println("If your tests are timing out and failing, it may be because of unexpected dialogs. \n" +
                "Only show a message dialog or input dialog when requested\n" +
                "Use the TicketGUI showMessageDialog and showInputDialog methods to show dialogs\n" +
                "When the tests run, they will replace these methods with the test's version of the method \n" +
                "that will be used to provide example user responses.  " +
                "So if you call JOptionPane.showMessageDialog() directly in your code, the test will not be expecting it, \n" +
                "and have no way to enter data or dismiss the dialog, and will be stuck.");
    }
    
    
    @Before
    public void resetTicketIDCounter() {
        Ticket.setNextId(1);
    }
    
    
    @Before
    public void generateTestTickets() {
        
        Ticket.setNextId(1);
        
        test_id_1 = new Ticket("Server keeps rebooting", 1, "user 1", new Date());  // add in order so id = 1
        test_id_2 = new Ticket("Mouse stolen", 5, "user 2", new Date());     // ID = 2
        test_id_3 = new Ticket("Mouse mat stolen", 3, "user 2", new Date());    // ID = 3
        
        testTickets = new LinkedList<>();
        
        // These tickets will be expected to be stored in the program in the priority order test_id_1, test_id_3, test_id_2
        
        testTickets.add(test_id_1);
        testTickets.add(test_id_3);
        testTickets.add(test_id_2);
        
        testNextId = 4;
        
    }
    
    
    // Keep track of files used in tests, and move them to temp folder after test done.
    
    private ArrayList<String> testFileNames;
    
    @Before
    public void prepareToStoreTestFiles() {
        
        testFileNames = new ArrayList<>();
        
        String openTickets = FileUtils.uniqueFilename("test_open_tickets");
        TicketProgram.openTicketsFile = openTickets;
        testFileNames.add(openTickets);
        
        String resolvedTicketFilePrefix = FileUtils.uniqueFilename("test_resolved_tickets");
        TicketProgram.resolvedTicketsFilePrefix = resolvedTicketFilePrefix;
        // Expected filename should be something like resolvedTicketFile_September_08_2017.txt
        SimpleDateFormat format = new SimpleDateFormat("MMMM_dd_yyyy");
        Date date = new Date();
        String today = format.format(date);
        String expectedFileName = resolvedTicketFilePrefix + today + ".txt";
        
        testFileNames.add(expectedFileName);
        
        String ticketCounterFile = FileUtils.uniqueFilename("test_ticket_counter");
        TicketProgram.ticketCounterFile = ticketCounterFile;
        testFileNames.add(ticketCounterFile);
    }
    
    
    @After
    public void cleanupFiles() {
        for (String file : testFileNames) {
            FileUtils.moveToTemporaryTestFolder(file);
        }
    }
    
    
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
        
        store.add(test_id_1); store.add(test_id_2); store.add(test_id_3);
        
        assertEquals(test_id_1, store.getTicketById(1));
        assertEquals(test_id_2, store.getTicketById(2));
        assertEquals(test_id_3, store.getTicketById(3));
        
        assertEquals(null, store.getTicketById(0));  // not valid
        assertEquals(null, store.getTicketById(-2));  // not valid
        assertEquals(null, store.getTicketById(200));  // doesn't exist valid
        
    }
    
    @Test
    public void testTicketStoreDeleteTicketByID() throws Exception {
        
        TicketStore store = new TicketStore();
        
        store.add(test_id_1); store.add(test_id_2); store.add(test_id_3);
        
        store.deleteTicketById(2);
        
        assertEquals(store.getAllTickets().size(), 2);
        
        assertNull(store.getTicketById(2));   // 2 has been deleted
        assertNotNull(store.getTicketById(1));   // 1 still there
        assertNotNull(store.getTicketById(3));   // 3 still there
        
        
        // Delete already deleted non-existent ticket
        store.deleteTicketById(2);
        assertEquals(store.getAllTickets().size(), 2);
        
        // Delete non-existent ticket
        store.deleteTicketById(7);
        assertEquals(store.getAllTickets().size(), 2);
        
        // Another non-existent ticket
        store.deleteTicketById(-1);
        assertEquals(store.getAllTickets().size(), 2);
        
        // Delete another
        
        store.deleteTicketById(1);
        assertEquals(store.getAllTickets().size(), 1);
        
        assertNull(store.getTicketById(2));   // 2 has been deleted
        assertNull(store.getTicketById(1));   // 1 deleted
        assertNotNull(store.getTicketById(3));   // 3 still there
        
        
        // Delete last ticket, should not be errors
        
        store.deleteTicketById(3);
        assertEquals(store.getAllTickets().size(), 0);
        
        assertNull(store.getTicketById(2));   // 2 has been deleted
        assertNull(store.getTicketById(1));   // 1 deleted
        assertNull(store.getTicketById(3));   // 3 deleted there
        
        
        // Delete non-existent ticket from empty list
        
        store.deleteTicketById(1);   // no errors
        
    }
    
    
    /* *************** Test TicketUI Methods **********************/
    
    
    // TASK 1 Configure ComboBox
    
    @Test(timeout=10000)
    public void testPriorityComboBoxConfigured() throws Exception {
        
        TicketProgram program = new TicketProgram();
        program.start();   // don't need test tickets
        
        JComboBox priorityCombo = program.ticketGUI.priorityComboBox;
        
        //priorityComboBox should have 1-5 in
        
        assertEquals("The priority combo box should have 5 items in ", 5, priorityCombo.getItemCount());
        
        for (int x = 0 ; x < 5 ; x++) {
            try {
                Integer comboItem = (Integer) priorityCombo.getModel().getElementAt(x);
                assertTrue("Combo box should contain the integers 1-5 in that order. Found " + comboItem + " at position " + x,
                        comboItem.equals(x+1));
                
            } catch (ClassCastException cce) {
                fail("ComboBox should contain the integers 1-5, not Strings, or other data");
            }
        }
    }
    
    
    // TASK 2 Configure JList
    
    @Test(timeout=10000)
    public void testTicketListConfigured() throws Exception {
        
        // Check JList set to single selection
        // Model uses Ticket objects
        
        TicketProgram program = new TicketProgram();
        program.start();
        
        JList ticketList = program.ticketGUI.ticketList;
        
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
    
    @Test(timeout=10000)
    public void testTicketListGetAllData() throws Exception {
        
        // shows example ticket list in correct order
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, testNextId);
        program.startGUI();
        
        JList ticketList = program.ticketGUI.ticketList;
        
        // Should be three tickets in list, in the given order
        assertEquals("With a test TicketList of 3 tickets, there should be 3 tickets in your JList. Test list was " + testTickets,
                3, ticketList.getModel().getSize());
        
        
        expectedListSameAsJList(testTickets, ticketList);
        
    }
    
    
    
    // TASK 4 Add Ticket
    
    @Test(timeout=10000)
    public void testAddTicketToEmptyListValidData() throws Exception {
        
        String msg = "Ticket added to the list should have the same data as that entered into GUI. " +
                "The date need not be exactly the same but it must be within a couple of seconds.";
        
        TicketProgram program = new TicketProgram();
        program.setup(new LinkedList<>(), 1);   // force program to start with empty ticket list
        program.startGUI();
        TicketGUI gui = program.ticketGUI;
        
        // Example data for ticket
        String description = "Server needs updating", reporter = "User";
        int priority = 3;
        
        Ticket expected1 = new Ticket(1, description, priority, reporter, new Date());  // Does not increment ticket counter
        
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
        
        // click add button
        gui.addButton.doClick();
        
        // Now ticket should be the first and only element in JList
        assertEquals("After adding 1 ticket, there should be one ticket in the JList", 1, gui.ticketList.getModel().getSize() );
        
        Ticket actualTicket = gui.ticketList.getModel().getElementAt(0);
        
        System.out.println("Expected ticket = " + expected1);
        System.out.println("Actual ticket created by the program =" + actualTicket);
        
        assertTrue(msg, sameOpenTicket(actualTicket, expected1, 1000));
        assertEquals("Added one ticket, should be 1 in data store", 1, gui.manager.getAllTickets().size());
        
        
        // Example data for another ticket
        description = "Server on fire"; reporter = "Another User";
        priority = 1;  // very urgent
        
        Ticket expected2 = new Ticket(2, description, priority, reporter, new Date());
        
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
        
        // click add button
        gui.addButton.doClick();
        
        assertEquals("After adding a ticket, then another ticket, there should be two tickets in the JList", 2, gui.ticketList.getModel().getSize() );
        
        Ticket actualTicket2 = gui.ticketList.getModel().getElementAt(0);   // This one is more urgent so should be at the top
        Ticket actualTicket1 = gui.ticketList.getModel().getElementAt(1);
        
        System.out.println("Created another ticket. Expected ticket =" + expected2);
        System.out.println("Actual ticket created by the program =" + actualTicket2);
        
        assertTrue(msg, sameOpenTicket(actualTicket1, expected1, 4000));
        assertTrue(msg, sameOpenTicket(actualTicket2, expected2, 4000));
        
        assertEquals("Added two tickets, should be 2 in data store", 2, gui.manager.getAllTickets().size());
        
    }
    
    
    // TASK 4 Add Ticket
    
    @Test(timeout=10000)
    public void testAddTicketAlreadyPopulatedListValidData() throws Exception {
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, testNextId);
        program.startGUI();
        TicketGUI gui = program.ticketGUI;
        
        int originalSize = program.getAllTickets().size();
        
        // Example data for new ticket
        String description = "Server needs updating", reporter = "User";
        int priority = 4;
        
        Ticket expected = new Ticket(4, description, priority, reporter , new Date());   // Configure ID
        
        // enter data
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(priority);
        
        // click add button
        gui.addButton.doClick();
        
        // New ticket added to list at position 2
        assertEquals("After adding 1 ticket to a list with 3 tickets in, there should be 4 tickets in the JList",
                4, gui.ticketList.getModel().getSize());
        
        Ticket actualTicket = gui.ticketList.getModel().getElementAt(2);    // New ticket should be at position 2
        
        System.out.println("Original test ticket list " + gui.manager.getAllTickets());
        System.out.println("Expected ticket =" + expected);
        System.out.println("Actual ticket created by the program =" + actualTicket);
        
        assertTrue("A new ticket with priority 4 to the test list should be added at position 2", sameOpenTicket(actualTicket, expected, 2000));
        assertEquals("A new ticket should be added to the data store.", originalSize + 1, program.getAllTickets().size());
        
    }
    
    // TASK 4 Add Ticket
    
    @Test(timeout=10000)
    public void testAddTicketInvalidData() throws Exception {
        
        TicketProgram program = new TicketProgram();
        program.setup(new LinkedList<>(), 1);   // force program to start with empty ticket list
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);  // Replace GUI with mock dialog version
        program.ticketGUI = gui;
        
        // Example invalid data for new ticket. Asserts in assertFailAddInvalidTicket
        assertFailAddInvalidTicket(gui, "", "", null);
        assertFailAddInvalidTicket(gui, "Mouse mat", "", null);
        assertFailAddInvalidTicket(gui, "", "The User", null);
        assertFailAddInvalidTicket(gui, "", "", 3);
        assertFailAddInvalidTicket(gui, "Mouse mat", "User", null);
        assertFailAddInvalidTicket(gui, "", "User", 2);
        assertFailAddInvalidTicket(gui, "Mouse", "", 3);
        
    }
    
    
    // Checks various things about not adding invalid tickets.
    private void assertFailAddInvalidTicket(TicketGUIMockDialog gui, String description, String reporter, Integer comboboxSelection) {
        // enter data - no selection for priority
        gui.descriptionTextField.setText(description);
        gui.reporterTextField.setText(reporter);
        gui.priorityComboBox.setSelectedItem(comboboxSelection);
        
        // click add button
        gui.addButton.doClick();
        
        // should be message dialog shown
        assertTrue(String.format("Show a message dialog if the new ticket data if description= '%s' reporter= '%s' priority= %s", description, reporter, comboboxSelection),
                gui.checkMessageWasCalled());
        
        // Nothing in JList or ticket Store
        assertEquals("After attempting to add invalid ticket, there should be no tickets in the ticketList",
                0, gui.ticketList.getModel().getSize());
        
        assertEquals("If invalid data is entered, no ticket should be added to the ticketStore", 0, gui.manager.getAllTickets().size());
    }
    
    
    //TASK 5 search by id
    
    @Test(timeout=10000)
    public void testSearchById() throws Exception {
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, testNextId);
        program.startGUI();
        
        TicketGUI gui = program.ticketGUI;
        JList ticketList = gui.ticketList;
        
        // Search for ticket id = 2
        gui.idSearchTextBox.setText("2");
        gui.searchIdButton.doClick();
        
        // List should only show one ticket, ticket ID 2
        
        assertEquals("Checking size of ticket list. When a ticket is found with the given ID, show 1 ticket in the list: the ticket with matching ID",
                1, ticketList.getModel().getSize());
        
        Ticket expected = (Ticket) ticketList.getModel().getElementAt(0);
        
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test_id_2, expected));
        
        // Update search status
        assertEquals("After ticket found by ID, update the ticketListStatusDescription JLabel",
                TicketGUI.TICKET_MATCHING_ID, gui.ticketListStatusDescription.getText());
        
        
        // Search for ticket 3, should now only show ticket 3 in the list
        
        gui.idSearchTextBox.setText("3");
        gui.searchIdButton.doClick();
        
        // List should now only show ticket 3
        
        assertEquals("When a ticket is found with the given ID, show only that ticket in ticketList", 1, ticketList.getModel().getSize());
        
        expected = (Ticket) ticketList.getModel().getElementAt(0);
        
        assertTrue("After searching for ticket ID that exists, only that matching ticket should be" +
                " shown in the JList", sameOpenTicket(test_id_3, expected));
        
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
        
        assertEquals("When show all tickets is clicked, show all tickets in the JList", 3, ticketList.getModel().getSize());
        
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
    
    @Test(timeout=10000)
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
        
        TicketGUI gui = program.ticketGUI;
        
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
    
    @Test(timeout=10000)
    public void testDeleteSelectedButNoneSelected() {
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, testNextId);
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketGUI = gui;
        
        gui.ticketList.clearSelection();   // Unselect everything
        gui.deleteSelectedButton.doClick();
        
        // expect a message dialog to be shown
        assertTrue("Show a message dialog if the user clicks the delete selected " +
                "button without anything selected in the ticketList ", gui.wasMessageCalled);
        
    }
    
    // TASK 7 Delete selected, user cancel
    
    @Test(timeout=10000)
    public void testDeleteSelectedUserCancel() {
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, testNextId);
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketGUI = gui;
        
        DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) gui.ticketList.getModel();
        
        Ticket test3_fromStore = program.searchById(3);
        
        // delete ticket ID 3, expected to be at position 1
        gui.ticketList.setSelectedIndex(1);
        
        Ticket test3_model = gui.ticketList.getModel().getElementAt(1);
        
        System.out.println("model" + test3_model);
        System.out.println("store" + test3_fromStore);
        
        
        // It's the same ticket?
        assertTrue("Tickets in TicketList should be in the same order as ticket store", sameOpenTicket(test3_fromStore, test3_model));
        
        // Changed mind. Click cancel.
        
        // expect input dialog after clicking the deleteSelected button, so set it up first
        
        // If user clicks cancel the Input dialog returns null
        gui.mockUserInput = null;
        
        gui.deleteSelectedButton.doClick();
        // should have an input dialog shown and returning the value given
        
        // The GUI should NOT remove the ticket from the list
        assertEquals("If user cancels, there should still be three tickets in the list", 3, model.getSize());
        
        assertEquals("The first ticket should be ID 1", 1, model.get(0).getTicketID());
        assertEquals("The second ticket should be ID 3", 3, model.get(1).getTicketID());
        assertEquals("The third ticket should be ID 2", 2, model.get(2).getTicketID());
        
        // A message dialog should NOT been shown
        assertFalse("Do not delete or show a delete confirmation message dialog when delete was cancelled", gui.checkMessageWasCalled());
        
        // On the back end, resolved tickets should NOT contain this ticket
        LinkedList<Ticket> resolved = program.resolvedTicketStore.getAllTickets();
        assertEquals("No if delete operation cancelled, should be no tickets in the resolved ticket store", 0, resolved.size());
        
        // The ticket should NOT be resolved
        
        assertNull("If ticket delete cancelled, ticket should not be resolved", test3_model.getResolution());
        assertNull("If ticket delete cancelled, ticket should not be resolved", test3_model.getDateResolved());
        
        // And open tickets should still contain this ticket;
        String msg = "Delete the deleted ticket from the ticket store";
        assertNotNull(msg, program.searchById(1));
        assertNotNull(msg, program.searchById(2));
        assertNotNull(msg, program.searchById(3));
        assertEquals(msg, 3, program.getAllTickets().size());
        
    }
    
    // TASK 7 Delete selected
    
    @Test(timeout=10000)
    public void testDeleteSelected()  {
        
        TicketProgram program = new TicketProgram();
        
        program.setup(testTickets, testNextId);
        
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketGUI = gui;
        
        DefaultListModel<Ticket> model = (DefaultListModel<Ticket>) gui.ticketList.getModel();
        
        // delete ticket ID 3, at position 1
        gui.ticketList.setSelectedIndex(1);
        
        // expect input dialog after clicking the deleteSelected button, so set it up first
        
        String expectedUserInput = "Replaced mouse mat";   // This should end up as resolution field in Ticket
        gui.mockUserInput = expectedUserInput;
        
        Date expectedResolvedDate = new Date();
        gui.deleteSelectedButton.doClick();
        // should have an input dialog shown and returning the value given
        
        
        // The GUI should remove the ticket from the list
        assertEquals("After deleting one ticket from the list, there should be two remaining", 2, model.getSize());
        
        assertEquals("The first remaining ticket should be ID 1", 1, model.get(0).getTicketID());
        assertEquals("The second remaining ticket should be ID 2", 2, model.get(1).getTicketID());
        
        
        // A message dialog should have been shown
        assertTrue("Show a confirmation message dialog when ticket has been deleted", gui.checkMessageWasCalled());
        
        
        // On the back end, resolved tickets should contain this ticket
        LinkedList<Ticket> resolved = program.resolvedTicketStore.getAllTickets();
        assertEquals("The deleted ticket should be in the resolved ticket store", 1, resolved.size());
        Ticket resolvedTicket = resolved.get(0);
        
        assertTrue("The deleted ticket should be in the resolved ticket store", sameOpenTicket(resolvedTicket, test_id_3));
        assertEquals("The deleted ticket should save the resolution entered by the user", expectedUserInput, resolvedTicket.getResolution());
        assertEquals("The deleted ticket should save the resolution date, the date/time the ticket was resolved",
                expectedResolvedDate.getTime(), resolvedTicket.getDateResolved().getTime(), 3000);
        
        
        // And open tickets should not contain this ticket; just ID 1 and 2
        String msg = "Delete the deleted ticket from the ticket store";
        assertNotNull(msg, program.searchById(1));
        assertNotNull(msg, program.searchById(2));
        assertNull(msg, program.searchById(3));
        assertEquals(msg, 2, program.getAllTickets().size());
        
        
        // ****  Delete the ticket with ID  = 1 ****
        
        // delete ticket ID 1, at position 0
        gui.ticketList.setSelectedIndex(0);
        
        // expect input dialog after clicking the deleteSelected button, so set it up first
        
        expectedUserInput = "Replaced mouse and glued new mouse to table.";   // This should end up as resolution field in Ticket
        gui.mockUserInput = expectedUserInput;
        
        expectedResolvedDate = new Date();
        gui.deleteSelectedButton.doClick();
        
        // should have an input dialog shown and returning the value given
        
        // The GUI should remove the ticket from the list
        assertEquals("After deleting another ticket from the list, there should be one remaining", 1, model.getSize());
        assertEquals("The remaining ticket should be ID 2", 2, model.get(0).getTicketID());
        
        // A message dialog should have been shown
        assertTrue("Show a confirmation message dialog when ticket has been deleted", gui.checkMessageWasCalled());
        
        // On the back end, resolved tickets should contain this ticket
        resolved = program.resolvedTicketStore.getAllTickets();
        assertEquals("The deleted ticket should be the last ticket in the resolved ticket store", 2, resolved.size());
        
        resolvedTicket = resolved.get(1);
        
        assertTrue("The deleted ticket should be in the resolved ticket store", sameOpenTicket(resolvedTicket, test_id_1));
        
        assertEquals("The deleted ticket should save the resolution entered by the user", expectedUserInput, resolvedTicket.getResolution());
        assertEquals("The deleted ticket should save the resolution date, the date/time the ticket was resolved",
                expectedResolvedDate.getTime(), resolvedTicket.getDateResolved().getTime(), 3000);
        
        // And open tickets should not contain this ticket; just ID 2
        assertNull(msg, program.searchById(1));
        assertNotNull(msg, program.searchById(2));
        assertNull(msg, program.searchById(3));
        assertEquals(msg, 1, program.getAllTickets().size());
        
        
        
        // **** Delete last ticket  with ID = 2 ****
        
        // delete ticket ID 1, at position 0
        gui.ticketList.setSelectedIndex(0);
        
        // expect input dialog after clicking the deleteSelected button, so set it up first
        expectedUserInput = "Rebooted the server by accidentally tripping over the power cable and pulling it out.";   // This should end up as resolution field in Ticket
        gui.mockUserInput = expectedUserInput;
        
        expectedResolvedDate = new Date();
        gui.deleteSelectedButton.doClick();
        // should have an input dialog shown and returning the value given
        
        // The GUI should remove the ticket from the list
        assertEquals("After deleting another ticket from the list, there should be none remaining", 0, model.getSize());
        
        // A message dialog should have been shown
        assertTrue("Show a confirmation message dialog when ticket has been deleted", gui.checkMessageWasCalled());
        
        // On the back end, resolved tickets should contain this ticket
        resolved = program.resolvedTicketStore.getAllTickets();
        assertEquals("The deleted ticket should be the last of 3 tickets in the resolved ticket store", 3, resolved.size());
        resolvedTicket = resolved.get(2);
        
        
        assertTrue("The deleted ticket should be in the resolved ticket store", sameOpenTicket(resolvedTicket, test_id_2));
        assertEquals("The deleted ticket should save the resolution entered by the user", expectedUserInput, resolvedTicket.getResolution());
        assertEquals("The deleted ticket should save the resolution date, the date/time the ticket was resolved",
                expectedResolvedDate.getTime(), resolvedTicket.getDateResolved().getTime(), 3000);
        
        // And open tickets should not contain any tickets
        assertEquals(msg, 0, program.getAllTickets().size());
        
    }
    
    /// TASK 8 Save and Quit, tickets to file, available when program relaunches
    
    @Test(timeout=10000)
    public void saveAndRestoreTickets() throws Exception {
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, 1);
        program.startGUI();
        
        LinkedList<Ticket> originalTicketList = program.getAllTickets();
        
        program.ticketGUI.saveAndQuitButton.doClick();   // This should invoke the save and quit method for the test Tickets.
        
        // Now restart program
        TicketProgram relaunchedProgram = new TicketProgram();
        relaunchedProgram.start();   // program loads normally
        
        // The tickets should have been read from a file, and be available.
        LinkedList<Ticket> ticketList_relaunch = relaunchedProgram.getAllTickets();
        
        assertEquals("There should be the same number of open tickets after your app is restarted. " +
                        "Save all open tickets to a file when app closes, read the tickets from the file when it restarts.",
                ticketList_relaunch.size(), testTickets.size());
        
        // Same tickets? Should be in the same priority order
        
        for (int t = 0 ;  t < ticketList_relaunch.size() ; t++) {
            String msg = "Read all data from the file to create a new ticket list.  " +
                    "Wrote out \n%s\n and got \n%s\n back. Make sure all the data is the same as the original ticket.";
            Ticket expected = originalTicketList.get(t);
            Ticket actual = ticketList_relaunch.get(t);
            
            assertTrue(String.format(msg, expected, actual), sameOpenTicket(expected, actual, 2000));
        }
        
        // Add a new ticket
        relaunchedProgram.ticketGUI.descriptionTextField.setText("Excel is broken");
        relaunchedProgram.ticketGUI.reporterTextField.setText("Another User");
        relaunchedProgram.ticketGUI.priorityComboBox.setSelectedItem(4);
        relaunchedProgram.ticketGUI.addButton.doClick();
        relaunchedProgram.ticketGUI.saveAndQuitButton.doClick();
        
        // Expect to have 4 tickets
        LinkedList<Ticket> relaunchedListPlusOneMore = relaunchedProgram.getAllTickets();
        
        // Save and close again
        relaunchedProgram.ticketGUI.saveAndQuitButton.doClick();
        
        // Should be a total of 4 tickets - not 3, not 7
        
        TicketProgram relaunch2 = new TicketProgram();
        relaunch2.start();
        
        // Same tickets as relaunchedList
        // Expect to have 4 tickets
        LinkedList<Ticket> relaunchedList2 = relaunch2.getAllTickets();
        
        assertEquals("There should be the same number of open tickets after your app is restarted. " +
                        "Save all open tickets to a file when app closes, read the tickets from the file when it restarts.",
                relaunchedListPlusOneMore.size(), relaunchedList2.size());
        
        // Same tickets? Should be in the same priority order
        for (int t = 0 ;  t < relaunchedList2.size() ; t++) {
            String msg = "Read all data from the file to create a new ticket list. " +
                    "Wrote out \n%s\n and got \n%s\n back. Make sure all the data is the same as the original ticket.";
            Ticket expected = relaunchedListPlusOneMore.get(t);
            Ticket actual = relaunchedList2.get(t);
            
            assertTrue(String.format(msg, expected, actual), sameOpenTicket(expected, actual));
        }
        
        
        
        
    }
    
    // TASK 9 Save resolved tickets
    
    @Test(timeout=10000)
    public void saveResolvedTicketsAppendSameDay() throws Exception {
        
        String[] resolutions = {
                "Turned it off and back on again",
                "Bribed user to keep quiet with donuts",
                "Glued mouse mat to desk"
        };
        
        TicketProgram program = new TicketProgram();
        program.setup(testTickets, 3);
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketGUI = gui;
        
        // Expected filename: resolvedTicketFile_September_08_2017.txt
        SimpleDateFormat format = new SimpleDateFormat("MMMM_dd_yyyy");
        Date date = new Date();
        String today = format.format(date);
        
        String expectedFileName = TicketProgram.resolvedTicketsFilePrefix + today + ".txt";
        
        // resolve first two tickets
        gui.mockUserInput = resolutions[0];
        gui.ticketList.setSelectedIndex(0);
        gui.deleteSelectedButton.doClick();
        gui.mockUserInput = resolutions[1];
        gui.ticketList.setSelectedIndex(0);
        gui.deleteSelectedButton.doClick();
        
        gui.saveAndQuitButton.doClick();   // This should invoke the save and quit method for the test Tickets.
        
        // Something in the file?
        File f = new File(expectedFileName);
        
        assertTrue(f.exists());
        
        long fileSize = f.length();
        
        assertTrue(fileSize > 0);
        
        assertTrue("The resolved ticket should be written to a file", FileUtils.fileContainsText(f, resolutions[0]));
        assertTrue("The resolved ticket should be written to a file", FileUtils.fileContainsText(f, resolutions[1]));
        
        // Relaunch
        
        // Resolve last ticket
        
        program = new TicketProgram();
        program.start();  // let it read from file
        gui = new TicketGUIMockDialog(program);  // replace gui
        program.ticketGUI = gui;
        
        // resolve first two tickets
        gui.mockUserInput = resolutions[2];
        gui.ticketList.setSelectedIndex(0);
        gui.deleteSelectedButton.doClick();
        
        gui.saveAndQuitButton.doClick();
        
        f = new File(expectedFileName);
        
        assertTrue(f.exists());
        
        long newFileSize = f.length();
        
        assertTrue(newFileSize > 0);
        
        assertTrue("The resolved ticket should be written to a file", FileUtils.fileContainsText(f, resolutions[0]));
        assertTrue("The resolved ticket should be written to a file", FileUtils.fileContainsText(f, resolutions[1]));
        assertTrue("The resolved ticket should be written to a file", FileUtils.fileContainsText(f, resolutions[2]));
        
    }
    
    
    @Test(timeout=10000)
    public void testRestoreTicketsNewTicketsUniqueID() throws Exception {


        TicketProgram program = new TicketProgram();
        
        program.setup(testTickets, testNextId);
        TicketGUIMockDialog gui = new TicketGUIMockDialog(program);
        program.ticketGUI = gui;
        
        
        gui.saveAndQuitButton.doClick();   // This should invoke the save and quit method for the test Tickets.
        
        TicketProgram relaunchedProgram = new TicketProgram();
        relaunchedProgram.start();
        
        // The tickets should have been read from a file, and be available.
        
        TicketGUI relaunchedGUI = relaunchedProgram.ticketGUI;
        
        relaunchedGUI.descriptionTextField.setText("Powerpoint keeps crashing.");
        relaunchedGUI.priorityComboBox.setSelectedIndex(3);
        relaunchedGUI.reporterTextField.setText("The User");
        
        relaunchedGUI.addButton.doClick();
        
        System.out.println(Ticket.getNextId());
        
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
                    System.out.println(t);
                    assertTrue("If existing tickets have IDs 1, 2, 3, new ticket ID must be 4 or greater", newId >= 4);
                    found = true;
                }
            }
            
            assertTrue("Tried to add new ticket 'Powerpoint keeps crashing' after relaunching program, but did not find it in the ticket list.", found);
            
        } catch (Exception e) {
            fail("Attempting to relaunch program, read ticket store, and search by description to find new ticket; take first item from ticket list.\n" +
                    "Verify that new ticket are being created in the ticket store when the application is relaunched.");
        }
    }
    
    
    private class TicketGUIMockDialog extends TicketGUI {
        TicketGUIMockDialog(TicketProgram t){ super(t);}
        
        private boolean wasMessageCalled = false;
        
        boolean checkMessageWasCalled() {
            boolean copyToReturn = wasMessageCalled;
            wasMessageCalled = false;
            return copyToReturn;
        }
        
        @Override
        protected void showMessageDialog(String msg){
            // do nothing so the program does not show a message dialog, but record that the method was invoked.
            wasMessageCalled = true;
        }
        
        // Set this to force showInputDialog to return a particular value. Do this before the inputDialog is expected to be called.
        String mockUserInput;
        
        @Override
        protected String showInputDialog(String msg) {
            return mockUserInput;
        }
        
    }
    
    
    
    private boolean sameOpenTicket(Ticket t1, Ticket t2)  {
        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
        return sameOpenTicket(t1, t2, 0);
    }
    
    
    private boolean sameOpenTicket(Ticket t1, Ticket t2, long acceptableMsDifferenceInDate)  {
        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
        // If either or both tickets are null, return false ( because one of the things is not an open ticket)
        
        if (t1 == null || t2 == null)  {return false;}
        
        if (t1.getTicketID() != t2.getTicketID()) { return false; }
        if (!(t1.getDescription().equals(t2.getDescription())))  { return false; }
        if (!(t1.getReporter().equals(t2.getReporter())))  { return false; }
        
        long dateDiff = Math.abs(t1.getDateReported().getTime() - t2.getDateReported().getTime());
        return dateDiff <= acceptableMsDifferenceInDate;
        
    }
    
    
    private void expectedListSameAsJList(LinkedList<Ticket> testTickets, JList ticketList) {
        
        for ( int t = 0 ; t < testTickets.size() ; t++) {
            
            Ticket expected = testTickets.get(t);
            try {
                Ticket actual = (Ticket) ticketList.getModel().getElementAt(t);
                assertTrue(String.format("Element %d of the GUI ticket list expected to be %s but was %s", t, expected.toString(), actual.toString()),
                        sameOpenTicket(expected, actual, 4000));
                
            } catch (ClassCastException cce) {
                fail("Your JList model should contain only Ticket objects.");
            }
        }
    }
    
    
}