package week_10;

import java.util.LinkedList;
import java.util.List;

/** Manages interactions between GUI and Ticket Store DB */

public class TicketProgram {
    
    TicketStore ticketStore;
    TicketGUI ticketGUI;
    
    TicketProgram(TicketGUI gui, TicketStore store) {
        ticketGUI = gui;
        ticketGUI.setController(this);
        ticketStore = store;
    }
    
    
    protected List<Ticket> loadTicketsFromTicketStore() {
        List<Ticket> openTickets = ticketStore.getAllOpenTickets();
        return openTickets;
    }
    
    
    protected void addTicket(Ticket newTicket) {
        ticketStore.add(newTicket);
    }
    
    
    // Search the ticket store for this ticket. Returns null if ticket not found.
    protected Ticket searchById(int ticketId) {
        Ticket ticket = ticketStore.getTicketById(ticketId);
        return ticket;
    }
    
    
    protected void updateTicket(Ticket ticket) {
        ticketStore.updateTicket(ticket);
    }
    
    
    // Find and return a list of matching tickets. If nothing matches, the list will be empty.
    protected List<Ticket> searchByDescription(String searchTerm) {
        List<Ticket> matchingTickets = ticketStore.searchByDescription(searchTerm);
        return matchingTickets;
    }
    
    
    protected boolean deleteTicket(Ticket ticket) {
        boolean deleted = ticketStore.deleteTicketById(ticket.getTicketID());
        return deleted;
    }
    
    
    protected void quitProgram() {
        Main.quit();
    }
    
}
