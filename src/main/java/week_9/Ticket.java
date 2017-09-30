package week_9;

import java.util.Date;

public class Ticket {
    
    private int priority;
    private String reporter; //Stores person or department who reported problem
    private String description;
    private Date dateReported;
    private String resolution;
    private Date dateResolved;
    
    //STATIC Counter - one variable, shared by all Ticket objects.
    //If any Ticket object modifies this counter, all Ticket objects will have the modified value
    //Make it private - only Ticket objects should have access
    private static int ticketIdCounter = 1;
    
    //The ID for each ticket - an instance variable. Each Ticket will have it's own ticketID variable
    private int ticketID;
    
    // You should have already done these tasks in the previous lab
    // TODO: tickets need to store the resolution date and a string describing the resolution
    // TODO implement your mechanism to ensure new tickets have a unique ID
    // TODO add any other methods you wrote in the previous lab and will need here
    
    
    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = ticketIdCounter;
        ticketIdCounter++;    // Increment ticketIDCounter so the next ticket's number is one higher
    }
    
    
    
    // TODO use this constructor to create a Ticket from existing Ticket data read from a file
    // Notice that it does not affect the static ticketIDCounter
    // Use the setNextID and getNextId method if you need to change the next ticketID that will
    // be generated, for example, if you are re-starting the program
    public Ticket(int id, String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = id;
    }
    
    
    public static int getNextId(){
        return ticketIdCounter;
    }
    
    public static void setNextId(int counter){
        ticketIdCounter = counter;
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
    }
    
    
    
    public String toString(){
        return("ID: " + this.ticketID + " Description: " + this.description + " Priority: " + 					this.priority + " Reported by: "
                + this.reporter + " Reported on: " + this.dateReported);
    }
    
    
}

