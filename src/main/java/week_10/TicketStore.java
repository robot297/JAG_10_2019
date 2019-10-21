package week_10;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
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
        
        /* TODO create the Ticket table.
             It should have these fields and constraints:
              rowID, an autogenerating ID  (remember SQLite can autogenerate this for you)
              priority, a number in the range 1-5, not null. Add constraints to prohibit priorities outside this range.
              description, text, not null
              dateReported, number, the long time value of a Date object, representing the date the ticket was created. Required.
              resolution, text. Not required.
              dateResolved, number, the long time value of a Date object, representing the date the ticket was marked as resolved. Not required.
              status, text, either "OPEN" or "RESOLVED". Required.
        */
        
        String sql = "CREATE DATABASE IF NOT EXISTS tickets (" +
                "description TEXT not null, " +
                "priority number not null check(priority>=1 AND priority<=5), " +
                "description text not null, " +
                "dateReported number not null, " +
                "resolution text, " +
                "dateResolved number, " +
                "status text check(resolution like 'RESOLVED' OR resolution like 'OPEN') " +
                ")";
        
        try (Connection c = DriverManager.getConnection(databaseURI);
        Statement statement = c.createStatement() ) {
    
            statement.execute(sql);
    
        } catch (SQLException e) {
            System.out.println("Error creating database because " + e.getMessage());
        }
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
        
//        return null;
    
        try (Connection connection = DriverManager.getConnection(dbURI);
             Statement statement = connection.createStatement()  ) {
        
            ResultSet resultSet = statement.executeQuery("SELECT rowid, * FROM tickets WHERE status = 'OPEN' ORDER BY priority");
        
            List<Ticket> tickets = new ArrayList<>();
        
            while (resultSet.next()) {
                int ticketID = resultSet.getInt("rowid");
    
                String description = resultSet.getString("description");
                int priority = resultSet.getInt("priority");
                String reporter = resultSet.getString("reporter");
                Date dateReported = new Date(resultSet.getLong("dateReported"));
                
                String resolution = resultSet.getString("resolution");
                
                long dateResolvedLong = resultSet.getLong("dateResolved");
                Date dateResolved = null;
                if (dateResolvedLong != 0) {
                    dateResolved = new Date(dateResolvedLong);
                }
                
                
                Ticket.TicketStatus status = Ticket.TicketStatus.valueOf(resultSet.getString("status"));
            
                Ticket ticket = new Ticket(ticketID, description, priority, reporter, dateReported, resolution, dateResolved, status);
                tickets.add(ticket);
            }
        
            return tickets;
        
        } catch (SQLException sqle) {
            throw new RuntimeException("Error fetching all movies", sqle);
        }
    }
    
    
    /** Add ticket to the database. */
    public void add(Ticket newTicket) {
        // TODO insert a new row in the database for this ticket.
        //  Write the data from the fields in the newTicket object.
        
        String sql = "INSERT INTO tickets values (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
         /*
             String sql = "CREATE DATABASE IF NOT EXISTS tickets (" +
                "priority number not null check(priority>=1 AND priority<=5), " +
                "description text not null, " +
                "dateReported number not null, " +
                "resolution text, " +
                "dateResolved number, " +
                "status text check(resolution like 'RESOLVED' OR resolution like 'OPEN') " +
                ")";
            */
         
         statement.setString(1, newTicket.getDescription());
         statement.setInt(2, newTicket.getPriority());
         statement.setString(3, newTicket.getDescription());
         statement.setLong(4, newTicket.getDateReportedTimeStamp());
         statement.setString(5, newTicket.getResolution());
         statement.setLong(6, newTicket.getDateResolvedTimeStamp());
         statement.setString(7, newTicket.getStatus().name());
         
         statement.executeUpdate();
         
        } catch (SQLException e) {
            System.out.println("Error adding ticket because " + e);
        }
        
        
    }
    
    private Ticket rowToTicket(ResultSet rs) throws SQLException {
        
        return new Ticket(
                rs.getInt("id"),
                rs.getString("description"),
                rs.getInt("priority"),
                rs.getString("reporter"),
                new Date(rs.getLong("dateReported")),
                rs.getString("resolution"),
                new Date(rs.getLong("dateResolved")),
                Ticket.TicketStatus.valueOf(rs.getString("status"))
        );
    }
    
    /** Searches store for ticket with given ID.
     * @param id The ticket ID
     * @return The ticket with this ID, if found; null otherwise
     */
    public Ticket getTicketById(int id) {
       
        // TODO query the database to find the ticket with this id.
        //  if the ticket is found, then create a Ticket object and return it
        //  if the ticket is not found, return null.
    
        String sql = "SELECT rowid, * from tickets where rowid = ?";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            boolean ticket = rs.next();
            if (ticket) {
                return rowToTicket(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error get by id because " + e.getMessage());
            return null;
        }
         //   return null; // TODO replace with your code.
    }
    
    
    public void updateTicket(Ticket ticket) {
        // TODO Use the Ticket's ID to modify the row in the database with this ID
        //  modify row in the database to set the values contained in the Ticket object
    
        String sql = "UPDATE tickets values (?, ?, ?, ?, ?, ?, ?) where rowid = ? ";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, ticket.getDescription());
            statement.setInt(2, ticket.getPriority());
            statement.setString(3, ticket.getDescription());
            statement.setLong(4, ticket.getDateReportedTimeStamp());
            statement.setString(5, ticket.getResolution());
            statement.setLong(6, ticket.getDateResolvedTimeStamp());
            statement.setString(7, ticket.getStatus().name());
            
            statement.setInt(8, ticket.getTicketID());
            
            statement.executeUpdate();
         
        
        } catch (SQLException e) {
            System.out.println("Error get by id because " + e.getMessage());
        }
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
    
        String sql = "SELECT rowid, * from tickets where upper(description) like = upper(?)";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement statement = connection.prepareStatement(sql)) {
        
            statement.setString(1, "%" + description + "%");
            ResultSet rs = statement.executeQuery();
    
            LinkedList<Ticket> tickets = new LinkedList<>();
            while (rs.next()) {
                tickets.add(rowToTicket(rs));
            }
            return tickets;
            
        } catch (SQLException e) {
            System.out.println("Error get by id because " + e.getMessage());
            return null;
        }
        //return null;  // TODO replace with your code.
    }
    
    
    /** Deletes by ticket ID.
     *  @return true if a ticket was found and deleted, false if a ticket with this ID is not in the queue */
    public boolean deleteTicketById(int deleteID) {
       
        
        // TODO Delete ticket with this ticket ID.
        //  return true if found and deleted.
        //  If there is not ticket with this ID, return false.
        
        //return false;  // TODO replace with your code.
    
        String sql = "DELETE from tickets where rowid = ?";
        try (Connection connection = DriverManager.getConnection(dbURI);
             PreparedStatement statement = connection.prepareStatement(sql)) {
        
            statement.setInt(1, deleteID);
            int rows = statement.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error get by id because " + e.getMessage());
           return false;
        }
        
    }
    

}
