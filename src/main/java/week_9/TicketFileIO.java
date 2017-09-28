package week_9;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class TicketFileIO {
    
    
    /** Save the list of tickets to the file with the given name */
    public void saveTickets(LinkedList<Ticket> ticketList, String fileName, boolean append) {
        // TODO implement this method or copy your code from Lab 7
    }
    
    
    /** Read a file, turn the data into Ticket objects, and return a list of Ticket objects */
    public LinkedList<Ticket> loadTickets(String fileName) {
        return null;  // TODO implement this method or copy your code from Lab 7
    }
    
    
    /* You may find this method useful to generate the date part of the resolved ticket filename */
        public String todaysDateToFileNameString() {
        SimpleDateFormat filenameFormatter = new SimpleDateFormat("MMMM_dd_yyyy");
        Date date = new Date();   //defaults to today, right now
        String s = filenameFormatter.format(date); // s will be in the format "September_28_2017"
        return s;
    }

    
    
}
