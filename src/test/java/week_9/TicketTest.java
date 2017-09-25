//package week_9;
//
//import input.InputUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import test_utils.MockInput;
//import test_utils.PrintUtils;
//
//import java.lang.reflect.Field;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.NoSuchElementException;
//import java.util.TreeMap;
//
//import static org.easymock.EasyMock.anyString;
//import static org.easymock.EasyMock.expect;
//import static org.junit.Assert.*;
//import static org.powermock.api.easymock.PowerMock.mockStatic;
//import static org.powermock.api.easymock.PowerMock.replay;
//
//
//@RunWith(PowerMockRunner.class)  // Needed for PowerMock to record method calls
//@PrepareForTest(InputUtils.class)   // To enable InputUtils to be mocked
//public class TicketTest {
//
//
//    @Test
//    public void testAddTicketSorted() throws Exception  {
//
//        TicketStore store = new TicketStore();
//
//        // Test tickets with all different priorities
//        Ticket testPr1 = new Ticket("The server is on fire", 1, "reporter", new Date());
//        Ticket testPr5 = new Ticket("Mouse mat stolen", 5, "reporter", new Date());
//        Ticket testPr3 = new Ticket("Word needs updating", 3, "reporter", new Date());
//
//        //Add these tickets. Assert they are added with lowest priority first
//        store.add(testPr1); store.add(testPr5); store.add(testPr3);
//
//        LinkedList allTickets = store.getAllTickets();
//
//        assertEquals(allTickets.pop(), testPr1);
//        assertEquals(allTickets.pop(), testPr3);
//        assertEquals(allTickets.pop(), testPr5);
//
//
//        // Tickets with joint priorities should be sorted by date
//        store = new TicketStore();
//
//        Ticket testPr1_newer = new Ticket("The server is down", 1, "reporter", new Date(1500000000000L));
//        Ticket testPr1_older = new Ticket("Another server is down", 1, "reporter", new Date(1400000000000L));
//        Ticket testPr4 = new Ticket("Several mouse mats stolen", 4, "reporter", new Date());
//
//        //Add these tickets. Assert they are added with lowest priority first,
//        //and equal priorities sorted with oldest first
//        store.add(testPr1_newer); store.add(testPr1_older); store.add(testPr4);
//
//        allTickets = store.getAllTickets();
//
//        assertEquals(allTickets.pop(), testPr1_older);
//        assertEquals(allTickets.pop(), testPr1_newer);
//        assertEquals(allTickets.pop(), testPr4);
//
//    }
//
//
//
//    @Test
//    public void testPeekNextTicket() throws Exception {
//
//        TicketStore store = new TicketStore();
//
//        Ticket testPr4 = new Ticket("The server is dusty", 4, "1", new Date());
//        Ticket testPr2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
//        Ticket testPr3 = new Ticket("Mouse mat stolen", 3, "3", new Date());
//
//        //Add these tickets
//        store.add(testPr2); store.add(testPr4); store.add(testPr3);
//
//        // Most severe should be returned
//        assertEquals(store.peekNextTicket(), testPr2);
//
//    }
//
//
//    @Test
//    public void testCountTicketsInQueue() throws Exception {
//
//        TicketStore store = new TicketStore();
//
//        Ticket testPr4 = new Ticket("The server is dusty", 4, "1", new Date());
//        Ticket testPr2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
//        Ticket testPr3 = new Ticket("Mouse mat stolen", 3, "3", new Date());
//
//        //Add these tickets
//        store.add(testPr2); store.add(testPr4); store.add(testPr3);
//
//        // Most severe should be returned
//        assertEquals(store.ticketsInQueue(), 3);
//
//    }
//
//
//    @Test
//    public void testSearchDescription() throws Exception {
//
//        TicketStore store = new TicketStore();
//
//        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());
//        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());
//        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());
//        Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());
//
//        //Add these tickets
//
//        store.add(test1); store.add(test2); store.add(test3); store.add(test4);
//
//        // Search for 'server'. Should not be case sensitive, return test1 and test2
//
//        LinkedList<Ticket> results = store.searchByDescription("Server");
//        assertNotNull("Return a LinkedList of all Tickets whose description contains the search text. If no matches, return an empty list. Your search should not be case sensitive", results);
//        assertEquals(results.size(), 2);   // 2 results
//        assertTrue(results.contains(test1));
//        assertTrue(results.contains(test2));
//        assertFalse(results.contains(test3));
//        assertFalse(results.contains(test4));
//
//
//        // Search for something not in the list
//        results = store.searchByDescription("Powerpoint");
//        assertNotNull("Return a LinkedList of results. If no matches, return an empty list.", results);
//        assertEquals("", results.size(), 0);   // No results
//
//    }
//
//
//
//    @Test
//    public void testGetTicketByID() throws Exception {
//
//        TicketStore store = new TicketStore();
//
//        // Get the Ticket counter field and reset it
//        Field ticketCounter = Class.forName("week_7.ticket.Ticket").getDeclaredField("ticketIdCounter");
//        ticketCounter.setAccessible(true);
//        ticketCounter.set(null, 1);
//
//        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());    //1
//        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());  // 2
//        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());          // 3
//        Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());    //4
//
//        store.add(test1); store.add(test2); store.add(test3); store.add(test4);
//
//        assertEquals(store.getTicketById(4), test4);
//        assertEquals(store.getTicketById(3), test3);
//        assertEquals(store.getTicketById(2), test2);
//        assertEquals(store.getTicketById(1), test1);
//
//    }
//
//
//    @Test
//    public void testDeleteTicketByID() throws Exception {
//
//        TicketStore store = new TicketStore();
//
//        // Get the Ticket counter field and reset it
//        Field ticketCounter = Class.forName("week_7.ticket.Ticket").getDeclaredField("ticketIdCounter");
//        ticketCounter.setAccessible(true);
//        ticketCounter.set(null, 1);
//
//        Ticket test1 = new Ticket("The server is on fire", 1, "1", new Date());    //1
//        Ticket test2 = new Ticket("Server keeps rebooting", 2, "2", new Date());  // 2
//        Ticket test3 = new Ticket("Mouse mat stolen", 3, "3", new Date());          // 3
//        Ticket test4 = new Ticket("Critical security updates", 1, "3", new Date());    //4
//
//        store.add(test1); store.add(test2); store.add(test3); store.add(test4);
//
//        store.deleteTicketById(3);
//
//        LinkedList<Ticket> allTickets = store.getAllTickets();
//        assertEquals(allTickets.size(), 3);
//        assertFalse(allTickets.contains(test3));   // 3 has been deleted
//
//        assertTrue(allTickets.contains(test1));
//        assertTrue(allTickets.contains(test2));
//        assertTrue(allTickets.contains(test4));  // Others still present
//
//
//        // Delete non-existent ticket
//        store.deleteTicketById(3);
//        assertEquals(allTickets.size(), 3);   // List not changed
//
//        store.deleteTicketById(-1);
//        assertEquals(allTickets.size(), 3);   // List not changed
//
//        // Delete another
//
//        store.deleteTicketById(1);
//        assertEquals(allTickets.size(), 2);   // one less ticket
//        assertFalse(allTickets.contains(test1));   // 1 has been deleted
//
//        assertTrue(allTickets.contains(test2));
//        assertTrue(allTickets.contains(test4));  // Others still present
//
//
//        // Delete last two, should not be errors
//
//        store.deleteTicketById(2);
//        store.deleteTicketById(4);
//        assertEquals(allTickets.size(), 0);   // empty
//
//        // Delete non-existent ticket
//
//        store.deleteTicketById(1);   // no errors
//
//    }
//
//
//
//
//    /* *************** Test TicketUI Methods **********************/
//
//
//    @Test(timeout=2000)  // Only wait 2 seconds for this test to complete.
//    public void testPriorityInRange() throws Exception {
//
//        // TODO if you modify the signature of getNewTicketInfo, for example, to pass in a max and min priority, you'll need to modify this test too.
//
//        System.out.println("If this test times out, make sure you don't accept priorities outside the range 1-5. " +
//                "\nIf you've modified the signature of testPriorityInRange, you may also need to also modify the test.");
//
//        TicketUI ui = new TicketUI();
//
//        MockInput.setInputs("server", "reporter", "6");
//
//        Ticket t = null;
//
//        try{
//            t = ui.getNewTicketInfo();      // Out of range. This should NOT complete.
//        } catch (NoSuchElementException e) {
//            // if your method asks for input again, none is provided. So
//            // a NoSuchElementException is thrown. This is expected, so can ignore.
//        } catch (Exception e) {
//        }
//
//        assertNull(t);
//
//
//        MockInput.setInputs("server", "reporter", "0");
//
//        Ticket t2 = null;
//
//        try{
//            t2 = ui.getNewTicketInfo();      // Out of range. This should NOT complete.
//        } catch (NoSuchElementException e) {
//            // if your method asks for input again, none is provided. So
//            // a NoSuchElementException is thrown. This is expected, so can ignore.
//        } catch (Exception e) {
//        }
//
//        assertNull(t2);
//
//
//    }
//
//
//    @Test
//    public void testMenuPrintedFromTreeMap() throws Exception {
//
//        TreeMap<Integer, String> options = new TreeMap<>();
//        options.put(1, "Taco");
//        options.put(2, "Pizza");
//
//        TicketUI ui = new TicketUI();
//
//        PrintUtils.catchStandardOut();
//        MockInput.setInputs("2");
//        int option = ui.showMenuGetChoice(options);
//        assertEquals(2, option);
//        String out = PrintUtils.resetStandardOut();
//
//        String msg = "This test uses a TreeMap that should produce the menu" +
//                "\n1: Taco" +
//                "\n2: Pizza" +
//                "\nEnter your selection" +
//                "\n\nYour program created this menu" +
//                "\n" + out
//                + "\n If your code has changed the way the menu looks, you'll need to modify this test.";
//
//        assertTrue(msg, out.contains("1: Taco"));
//        assertTrue(msg, out.contains("2: Pizza"));
//
//    }
//
//
//    @Test
//    public void addedNewMenuItemsSearchByDescriptionDeleteByDescription() {
//
//        Question_3_Support_Ticket_Manager q3 = new Question_3_Support_Ticket_Manager();
//
//        MockInput.setInputs(q3.QUIT);
//
//        q3.manage();
//
//        PrintUtils.catchStandardOut();
//        String out = PrintUtils.resetStandardOut();
//
//        assertTrue("Ensure your menu has an option 'Search by description'. Use that exact text.", out.toLowerCase().contains("search by description"));
//        assertTrue("Ensure your menu has an option 'Delete by description'. Use that exact text.", out.toLowerCase().contains("delete by description"));
//
//    }
//
//    /* *********** Resolved Tickets in Files ********************/
//
//    @Test(timeout=3000)
//    public void saveAndRestoreTickets() throws Exception {
//
//        // Provide dummy return values from any input used
//        mockStatic(InputUtils.class);
//        expect(InputUtils.intInput(anyString())).andReturn(1);
//        expect(InputUtils.doubleInput(anyString())).andReturn(1.1);
//        expect(InputUtils.stringInput(anyString())).andReturn("1");
//        expect(InputUtils.yesNoInput(anyString())).andReturn(true);  // Hopefully this will catch an "Are you sure you want to quit?" message
//        replay(InputUtils.class);
//
//        Question_3_Support_Ticket_Manager q3 = new Question_3_Support_Ticket_Manager();
//
//        // Test tickets with all different priorities
//        Ticket test1 = new Ticket("Mouse mat stolen", 5, "reporter", new Date());
//        Ticket test2 = new Ticket("Word needs updating", 3, "reporter", new Date());
//
//        // Force these tickets into the store
//        Class managerClass = Class.forName("week_7.ticket.Question_3_Support_Ticket_Manager");
//        Field ticketStoreField = managerClass.getDeclaredField("ticketStore");
//        ticketStoreField.setAccessible(true);
//        TicketStore store = (TicketStore) ticketStoreField.get(q3);
//
//        store.add(test1);  store.add(test2);
//
//        q3.quitProgram();    // End program. These tickets should be saved to a file.
//
//        Question_3_Support_Ticket_Manager q3_relaunch = new Question_3_Support_Ticket_Manager();
//
//        // The tickets should have been read from a file, and be available.
//
//        TicketStore store_relaunch = (TicketStore) ticketStoreField.get(q3_relaunch);
//        LinkedList<Ticket> ticketList_relaunch = store_relaunch.getAllTickets();
//
//        assertEquals("There should be the same number of open tickets after your app is restarted. " +
//                "Save all open tickets to a file when app closes, read the tickets from the file when it restarts.", ticketList_relaunch.size(), 2);
//
//
//        Ticket read_1 = ticketList_relaunch.pop();
//        Ticket read_2 = ticketList_relaunch.pop();
//        String msg = "Read all data from the file to create a new ticket.  " +
//                "Wrote out \n%s\n and got \n%s\n back. Make sure all the data is the same as the original ticket.";
//        assertTrue(String.format(msg, test1, read_1), sameOpenTicket(ticketList_relaunch.pop(), test2) );
//        assertTrue(String.format(msg, test2, read_2), sameOpenTicket(ticketList_relaunch.pop(), test2) );
//
//    }
//
//
//    private boolean sameOpenTicket(Ticket t1, Ticket t2) throws Exception {
//        // Could override .equals in the Ticket class, but not guaranteed that student will implement extra fields
//        // Overriding .equals requires hashcode to be overriden too, and that's out of scope for this problem
//
//
//        if (t1.getTicketID() != t2.getTicketID()) { return false; }
//        if (!(t1.getDateReported().equals(t2.getDateReported())))  { return false; }
//        if (!(t1.getDescription().equals(t2.getDescription())))  { return false; }
//        if (!(t1.getReporter().equals(t2.getReporter())))  { return false; }
//
//        return true;
//
//    }
//
//
//    @Test
//    public void testManualChecksQuestion3() {
//        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
//                "\nThe instructor will check your work for Question 7 and assign the rest of the points");
//    }
//
//
//    @Test
//    public void testManualChecksQuestion4() {
//        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
//                "\nThe instructor will check your work for Question 7 and assign the rest of the points");
//    }
//
//
//    @Test
//    public void testManualChecksQuestion5() {
//        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
//                "\nThe instructor will check your work for Question 7 and assign the rest of the points");
//    }
//
//
//    @Test
//    public void testManualChecksQuestion6() {
//        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
//                "\nThe instructor will check your work for Question 7 and assign the rest of the points");
//    }
//
//
//    @Test
//    public void testManualChecksQuestion7() {
//        fail("This test is supposed to fail. Tests can't check every aspect of this program. " +
//                "\nThe instructor will check your work for Question 7 and assign the rest of the points");
//    }
//
//}