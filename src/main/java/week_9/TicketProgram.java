package week_9;

import java.util.LinkedList;

/** Launches the GUI. Connects GUI to data store, and to TicketFileStorage. */

public class TicketProgram {
    
    
    TicketFileIO ticketFileIO;
    TicketStore ticketStore;
    TicketGUI ticketUI;
    
    
    static String openticketsFile = "opentickets.txt";
    
    
    public static void main(String[] args) {
        new TicketProgram().start();
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
        
        // Add the Ticket to the ticketStore
        ticketStore.add(newTicket);
    }
    
    
    // Search the ticket store for this ticket. Returns null if ticket not found.
    protected Ticket searchById(int ticketId) {
        Ticket ticket = ticketStore.getTicketById(ticketId);
        return ticket;
    }
    
    
    protected void resolveTicket(Ticket ticket) {
        // TODO 7
    }
    
    
    // Find and return a list of matching tickets. If nothing matches, the list will be empty.
    protected LinkedList<Ticket> searchByDescription(String searchTerm) {
        
        LinkedList<Ticket> matchingTickets = ticketStore.searchByDescription(searchTerm);
        return matchingTickets;
        
    }
    
    
    protected LinkedList<Ticket> getAllTickets() {
        LinkedList<Ticket> allTickets = ticketStore.getAllTickets();
        return allTickets;
    }
    
    
    protected void quitProgram() {
        
        // TODO task 8
        
    }
    
    
}
