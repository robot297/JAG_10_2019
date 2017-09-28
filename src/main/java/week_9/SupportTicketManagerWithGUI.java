package week_9;


import java.util.ArrayList;
import java.util.LinkedList;

/** Launches the GUI. Connects GUI to data store, and to TicketFileStorage. */

public class SupportTicketManagerWithGUI {
    
    
    TicketFileIO ticketFileIO;
    TicketStore ticketStore;
    TicketGUI ticketUI;
    
    
    static String openticketsFile = "opentickets.txt";
    
    
    public static void main(String[] args) {
        new SupportTicketManagerWithGUI().start();
    }
    
    public void start() {
     //   loadTickets();
        startGUI();
    }
    
    public void loadTickets() {
        
        TicketFileIO ticketFileIO = new TicketFileIO();
        LinkedList<Ticket> openTickets = ticketFileIO.loadTickets(openticketsFile);
        
        TicketStore ticketStore = new TicketStore();
        ticketStore.addAll(openTickets);
        
    }
    
    public void startGUI() {
        TicketGUI gui = new TicketGUI(this);
    }
    
    
    protected void addTicket(Ticket newTicket) {
        
        /* Ticket UI should call this method when user has added new ticket */
        
        // Add to the ticket store
        ticketStore.add(newTicket);
    }
    
    
    // Search the ticket store for this ticket. Returns null if ticket not found.
    protected Ticket searchById(int ticketId) {
        
        Ticket ticket = ticketStore.getTicketById(ticketId);
        return ticket;
        
    }
    
    
    // Delete ticket with given ID. Returns true if ticket found and deleted, false otherwise.
    protected boolean deleteById(int ticketId) {
        
        boolean deleted = ticketStore.deleteTicketById(ticketId);
        return deleted;
        
    }
    
    
    // Find and return a list of matching tickets. If nothing matches, the list will be empty.
    protected LinkedList<Ticket> searchByDescription(String searchTerm) {
        
        LinkedList<Ticket> matchingTickets = ticketStore.searchByDescription(searchTerm);
        return null;
        
    }


//    protected boolean deleteTicketByIssue(int id) {
//
//
//        }

    
    
    protected LinkedList<Ticket> getAllTickets() {
        
        LinkedList<Ticket> allTickets = ticketStore.getAllTickets();
        return allTickets;
        
    }
    
    
    protected void quitProgram() {
        
        //TODO Problem 6 use your new TicketFileIO class to save all open tickets to a file; save all resolved tickets to a separate file
        
        
    }
    
    
}
