package week_10;

import java.util.List;


/**
 * A data structure to store Tickets in memory as the program runs
 * Stores tickets in priority order, so tickets with priority 1 are at the start
 * If more than one ticket with same priority, oldest tickets are before newer tickets
 * Supports add, delete, search operations on the list of Tickets
 */


public class TicketStore {
    
    String dbURI;
    
    TicketStore(String databaseURI) {
        this.dbURI = databaseURI;
        
        /* TODO create the Ticket database. It should have these fields and constraints:
              rowID, an autogenerating ID  (remember SQLite can autogenerate this for you)
              priority, a number in the range 1-5, not null. Add constraints to prohibit priorities outside this range.
              description, text, not null
              dateReported, number, the long time value of a Date object, representing the date the ticket was created. Required.
              resolution, text. Not required.
              dateResolved, number, the long time value of a Date object, representing the date the ticket was marked as resolved. Not required.
              status, text, either "OPEN" or "RESOLVED". Required.
        */
        
    }
    
    
    /** Returns all tickets in the queue.
     *
     * @return All the tickets.
     */
    public List<Ticket> getAllOpenTickets() {
        
        // TODO Query database, get all tickets which have the status "OPEN"
        //  order the tickets by priority - tickets with priority = 1 first, priority = 5 last
        //  remember the database can order tickets for you
        //  Create Ticket object for each row in the ResultSet
        //  Return a List of these Ticket objects
        //  If there are no Tickets in the database, return an empty List.
        
        return null;
    }
    
    
    /** Add ticket to the database. */
    public void add(Ticket newTicket) {
        // TODO insert a new row in the database for this ticket.
        //  Write the data from the fields in the newTicket object.
    }
    
    
    /** Searches store for ticket with given ID.
     * @param id The ticket ID
     * @return The ticket with this ID, if found; null otherwise
     */
    public Ticket getTicketById(int id) {
       
        // TODO query the database to find the ticket with this id.
        //  if the ticket is found, then create a Ticket object and return it
        //  if the ticket is not found, return null.
        
        return null; // TODO replace with your code.
    }
    
    
    public void updateTicket(Ticket ticket) {
        // TODO Use the Ticket's ID to modify the row in the database with this ID
        //  modify row in the database to set the values contained in the Ticket object
    }
    
    
    /** Returns a list of tickets, with a description containing
     * the given String. The search is not case sensitive.
     * @param description Text to search for in Ticket descriptions
     * @return a list of matching Tickets. If no matches, return an empty list.
     */
    public List<Ticket> searchByDescription(String description) {
        // TODO search the database for all tickets that match the description
        //  If description is null, or a blank string, or empty string, return an empty list
        //  The search should be case-insensitive.
        //  The search should return partial matches.
        //  A search for "server" should return a ticket with description "The Windows Server is down"
        
        return null;  // TODO replace with your code.
    }
    
    
    /** Deletes by ticket ID.
     *  @return true if a ticket was found and deleted, false if a ticket with this ID is not in the queue */
    public boolean deleteTicketById(int deleteID) {
       
        // TODO Delete ticket with this ticket ID.
        //  return true if found and deleted.
        //  If there is not ticket with this ID, return false.
        
        return false;  // TODO replace with your code.
        
    }
    

}
