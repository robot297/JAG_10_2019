package week_9;

import java.util.Comparator;
import java.util.LinkedList;


/**
 * A data structure to store Tickets in memory as the program runs
 * Stores tickets in priority order, so tickets with priority 1 are at the start
 * If more than one ticket with same priority, oldest tickets are before newer tickets
 * Supports add, delete, search operations on the list of Tickets
 */


public class TicketStore {
    
    private LinkedList<Ticket> ticketQueue;
    
    private Comparator<Ticket> ticketComparator;
    
    TicketStore() {
        
        ticketQueue = new LinkedList<>();
        
        // This is to sort tickets with highest priority first
        // If two tickets have the same priority, oldest first 
        
        ticketComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket t1, Ticket t2) {
    
                //Joint equal priorities, sort with oldest first
                if (t1.getPriority() == t2.getPriority()){
                    return t1.getDateReported().compareTo(t2.getDateReported());
                }
                
                else {
                    // Sort with smallest priority number at the start
                    return t1.getPriority() - t2.getPriority();
                }
            }
        };
    }
    
    
    public void addAll(LinkedList<Ticket> tickets) {
        ticketQueue.addAll(tickets);
        ticketQueue.sort(ticketComparator);
    }
    
    
    /** Add ticket, and then sort list to keep the highest-priority at the top of the queue */
    public void add(Ticket newTicket) {
        ticketQueue.add(newTicket);
        ticketQueue.sort(ticketComparator);
    }
    
    
    /** Returns all tickets in the queue.
     *
     * @return All the tickets.
     */
    public LinkedList<Ticket> getAllTickets() {
        return ticketQueue;
    }
    
    
    /** Returns, but does not remove, the tickets from the top of the TicketQueue
     * @return the ticket at the top of the queue */
    public Ticket peekNextTicket() {
        return ticketQueue.peek();
    }
    
    
    /** How many currently open tickets in the list?
     * @return the number of open tickets */
    public int ticketsInQueue() {
        return ticketQueue.size();
    }
    
    
    
    /** Searches store for ticket with given ID.
     * @param id The ticket ID
     * @return The ticket with this ID, if found; null otherwise
     */
    public Ticket getTicketById(int id) {
        for (Ticket t : ticketQueue) {
            if (t.getTicketID() == id) {
                return t;
            }
        }
        
        return null; // If ticket with this ID is not found
    }
    
    
    
    /** Deletes by ticket ID.
     *  @return true if a ticket was found and deleted, false if a ticket with this ID is not in the queue */
    public boolean deleteTicketById(int deleteID) {
       
        //Loop over all tickets. Delete the one with this ticket ID
        
        for (Ticket ticket : ticketQueue) {
            if (ticket.getTicketID() == deleteID) {
                ticketQueue.remove(ticket);
                return true;
            }
        }
        
        // Not found? Return false
        return false;
        
    }
    
    /** Returns a list of tickets, with a description containing
     * the given String. The search is not case sensitive.
     * @param description Text to search for in Ticket descriptions
     * @return a list of matching Tickets. If no matches, return an empty list.
     */
    public LinkedList<Ticket> searchByDescription(String description) {
        //TODO problem 3, implement this method
        return null;
    }
    
    
}
