package week_10;

import java.util.Date;

public class Ticket {
    
    /*
    * An enumeration is a structure that can only take one of
    * given set of constant values. In this case, a TicketStatus can only be
    * OPEN or RESOLVED.
    *
    * Examples of working with an enum
    *   TicketStatus status = TicketStatus.OPEN;   // Create a TicketStatus
    *   status = TicketStatus.RESOLVED;      // Modify a TicketStatus
    *
    * To modify a TicketStatus in a Ticket object
    *   Ticket.setStatus(TicketStatus.RESOLVED);  // change to RESOLVED
    *
    * To turn a TicketStatus into a String, for example, to write to a database, use the TicketStatus name() method
    *  String statusAsString = status.name();
    *
    * To turn a String into a TicketStatus, use TicketStatus.valueOf(string), example
    *
    *  TicketStatus statusFromString = TicketStatus.valueOf("CLOSED");       // OK - the String must be exact
    *  TicketStatus anotherStatusFromString = TicketStatus.valueOf("OPEN");   // OK - the String must be exact
    *  TicketStatus invalidFromString = TicketStatus.valueOf("closed");  // Error - not a valid constant in the enum
    */

    
    enum TicketStatus {
        OPEN, RESOLVED
    }
    
    private int ticketID;
    
    private int priority;
    private String reporter;    // Stores person or department who reported problem
    private String description;
    private Date dateReported;
    private long dateReportedTimeStamp;
    private String resolution;
    private Date dateResolved;
    private long dateResolvedTimeStamp;
    private TicketStatus status;
    
    // You should have already done these tasks in the previous lab
    // TODO: tickets need to store the resolution date and a string describing the resolution
    //  and modify your constructors as needed
    // TODO add any other methods you wrote in the previous lab and will need here
    
    
    // This constructor will be used to create a new, open ticket
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.dateReportedTimeStamp = date.getTime();
        this.status = TicketStatus.OPEN;
    }
    
    
    // TODO modify this constructor to create a Ticket from existing Ticket data read from a database
    //  it should be able to set your dateResolved value, resolution string, and status.
    //
    public Ticket(int id, String desc, int p, String rep, Date dateReported, String resolution, Date dateResolved, TicketStatus status) {
        
        this.ticketID = id;
        
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = dateReported;
    
        this.resolution = resolution;
        this.dateResolved = dateResolved;
        this.dateResolvedTimeStamp = dateResolved.getTime();
    
        this.status = status;
    }
    
    public int getTicketID() {
        return ticketID;
    }
    
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public String getReporter() {
        return reporter;
    }
    
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDateReported() {
        return dateReported;
    }
    
    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
        this.dateReportedTimeStamp = dateReported.getTime();
    }
    
    public long getDateReportedTimeStamp() {
        return dateReportedTimeStamp;
    }
    
    public String getResolution() {
        return resolution;
    }
    
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
    
    public Date getDateResolved() {
        return dateResolved;
    }
    
    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
        this.dateResolvedTimeStamp = dateResolved.getTime();
    }
    
    public long getDateResolvedTimeStamp() {
        return dateResolvedTimeStamp;
    }
    
    public TicketStatus getStatus() {
        return status;
    }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    
    public String toString(){
    
        // This is used by the GUI to display open tickets.
        return("ID: " + this.ticketID + " Description: " + this.description + " Priority: " + 					this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
    
    
}

