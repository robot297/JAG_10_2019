package week_9;

import java.util.Date;

public class Ticket {

    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;

    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int ticketIdCounter = 1;

    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    // TODO 6: tickets need to store the resolution date and a string describing the resolution
    // Either add them to this class or create another class called ResolvedTicket - which
    // do you think is the better approach?
    
    
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;    // Increment ticketIDcounter so the next ticket's number is one higher
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
        return("ID: " + this.ticketID + " Issue: " + this.description + " Priority: " + 					this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
    
    
}

