package week_9;

import java.util.Date;

public class Ticket {
    
    private int priority;
    private String reporter; //Stores person or department who reported problem
    private String description;
    private Date dateReported;
    
    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int ticketIdCounter = 1;
    
    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;
    
    // You should have already done these tasks in the previous lab
    // TODO: tickets need to store the resolution date and a string describing the resolution
    // TODO implement your mechanism to ensure new tickets have a unique ID
    // TODO add your constructor to create a Ticket from existing Ticket data read from a file
    // TODO add any other methods you wrote in the previous lab and will need here
    
    
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;    // Increment ticketIDCounter so the next ticket's number is one higher
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDateReported() {
        return dateReported;
    }
    
    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }
    
    
    
    protected int getPriority() {
        return priority;
    }
    
    public int getTicketID() {
        return ticketID;
    }
    
    public String getDescription() { return description; }
    
    public String toString(){
        return("ID: " + this.ticketID + " Description: " + this.description + " Priority: " + 					this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
    
    
}

