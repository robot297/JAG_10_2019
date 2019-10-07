package week_10;

import java.util.LinkedList;

/** Launches the GUI, creates data store, connects GUI to data store. */

public class Main {
    
    static String databaseURI = DBConfig.dbURI;
    
    static TicketStore ticketStore;
    static TicketGUI ticketGUI;
    
    TicketProgram ticketProgram = new TicketProgram(ticketGUI, ticketStore);
    
    
    public static void main(String[] args) {
        ticketStore = new TicketStore(databaseURI);
        ticketGUI = new TicketGUI();
        TicketProgram ticketProgram = new TicketProgram(ticketGUI, ticketStore);
    }
    
    
    public static void quit() {
        ticketGUI.dispose();
    }
    
    
}
