package week_9;

import java.util.LinkedList;

/** Launches the GUI. Connects GUI to data store, and to TicketFileStorage. */

public class TicketProgram {
    
    
    
    TicketFileIO ticketFileIO;
    TicketStore ticketStore;
    TicketGUI ticketGUI;
    ResolvedTicketStore resolvedTicketStore;
    
    
    static String openTicketsFile = "opentickets.txt";
    static String resolvedTicketsFilePrefix = "Resolved_Tickets_"; // for making a filename like  "Resolved_Tickets_September_28_2017.txt"
    
    
    public static void main(String[] args) {
        TicketProgram ticketProgram = new TicketProgram();
        
        ticketProgram.setup(null);
        ticketProgram.startGUI();
        
    }
    
    
    protected void setup(LinkedList<Ticket> injectedTestData) {
        
        createTicketStore();
        configureResolvedTickets();
        
        if (injectedTestData != null) {
            loadTicketsFromTicketStore(injectedTestData);
            // configureTicketIdGeneration()  // don't call, let this be the default start ID value of 1
        } else {
            loadTicketsFromTicketStore(null);
            configureTicketIdGeneration();
        }
    }
    
    
    
    // Do any TicketStore setup here
    protected void createTicketStore() {
        ticketStore = new TicketStore();
    }
    
    
    protected void loadTicketsFromTicketStore(LinkedList<Ticket> injectedTestData) {
    
        ticketFileIO = new TicketFileIO();
    
        if (injectedTestData != null) {
            ticketStore.addAll(injectedTestData);
        }
        
        else {
            LinkedList<Ticket> openTickets = ticketFileIO.loadTickets(openTicketsFile);
            ticketStore.addAll(openTickets);
        }
    }
    
    
    // Do any setup for your resolved tickets store here.
    protected void configureResolvedTickets() {
        resolvedTicketStore = new ResolvedTicketStore();
    }
    
    
    protected void configureTicketIdGeneration() {
        // TODO 7 If you need to do anything to assist generating unique ticket IDs, do it here.
    }
    
    
    
    protected void startGUI() {
        ticketGUI = new TicketGUI(this);
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
        
        // Use the static String resolvedTicketsFilePrefix = "Resolved_Tickets_";
        // for making a filename like  "Resolved_Tickets_September_28_2017.txt"
    
    
    }
    
    
}
