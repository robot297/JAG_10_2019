package week_9;

import java.util.LinkedList;

public class ResolvedTicketStore {
    
    LinkedList<Ticket> resolvedTickets;
    
    ResolvedTicketStore() {
        resolvedTickets = new LinkedList<Ticket>();
    }
    
    public void addTicket(Ticket t) {
        resolvedTickets.add(t);
    }
    
    public LinkedList<Ticket> getAllTickets() {
        return resolvedTickets;
    }
    
}
