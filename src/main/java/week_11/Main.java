package week_11;

/** Launches the GUI, creates data store, connects GUI to data store. */

public class Main {
    
    
    public static void main(String[] args) {
    
        String databaseURI = DBConfig.dbURI;
    
        TicketProgram ticketProgram = new TicketProgram();
        TicketStore store = new TicketStore(databaseURI);
        TicketGUI gui = new TicketGUI();
    
    }
    
    
    public static void quit() {
        ticketGUI.dispose();
    }
    
    
}
