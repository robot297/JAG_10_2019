package week_9;

import com.sun.codemodel.internal.JOp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class TicketGUI extends JFrame {
    
    
    protected JPanel mainPanel;
    
    
    // Components for adding tickets
    protected JPanel addTicketPanel;
    protected JTextField descriptionTextField;
    protected JTextField reporterTextField;
    protected JComboBox priorityComboBox;
    protected JButton addButton;
    
    // Components for displaying ticket list
    protected JPanel ticketListPanel;
    protected JList ticketList;
    protected JLabel ticketListStatusDescription;
    
    // Components for searching
    protected JPanel searchPanel;
    protected JTextField descriptionSearchTextBox;
    protected JTextField idSearchTextBox;
    protected JButton searchDescriptionButton;
    protected JButton searchIdButton;
    protected JButton showAllTicketsButton;
    
    // Saving and quit
    protected JPanel controlsPanel;
    protected JButton saveAndQuitButton;
    
    // Deleting
    protected JButton deleteSelectedButton;
    
    
    // Messages for showing in ticketListStatusDescription
    static final String ALL_TICKETS = "showing all tickets";
    static final String TICKETS_MATCHING_DESCRIPTION = "Tickets matching search description";
    static final String TICKET_MATCHING_ID = "Ticket matching ID";
    static final String NO_TICKETS_FOUND = "No matching tickets";
    static final String INVALID_TICKET_ID = "Invalid ticket ID";
    
    
    
    SupportTicketManagerWithGUI manager;
    
    // Messages to show in
    
    TicketGUI(SupportTicketManagerWithGUI manager) {
        
        this.manager = manager;
        
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
//        LinkedList<Ticket> allTickets = manager.getAllTickets();
        
        // TODO configure components
        
        /*
        * Set up the priorityComboBox to hold numbers 1-5
        *
        * Set up the JList to be single selection, and show the all the tickets in allTickets
         */
        
        // TODO configure event handlers
        
        /*
        
        addButton click event handler to add new Ticket and update list
        
        showAllTicketsButton should show all tickets in the ticketList
        
        searchDescriptionButton should read the text in descriptionSearchTextBox. Validate that some text is entered.  and
        ask manager to search for matching tickets. If tickets are found, display them in the allTickets list and update ticketListStatusDescription to say e.g. Tickets matching "powerpoint"
        If no tickets are found, display a empty list and update the message to say no tickets found.
        
        searchIdButton should read the text in idSearchTextBox. Validate that some text is entered, and is an integer.
        ask manager to search for matching ticket. If ticket is found, display it in the allTickets list and update ticketListStatusDescription to say e.g. Tickets matching "powerpoint"
        If no ticket is found, display a empty list and update the ticketListStatusDescription text to say no tickets found.
        
            
        saveAndQuitButton should call the quitProgram message in manager
    
    
        protected JLabel ticketListStatusDescription;
    
    
        deleteSelectedButton, if a ticket is selected in the JList, show a string input to get the resolution.
        Use confirms, manager should request that ticket is deleted. Update allTickets
        
      
        
        */
        
    }
    
    
    
    protected void addTicket() {
        
        // TODO call this method from the addButton event handler.
        // TODO Get ticket data from user interface,
        // TODO validate that all required data is entered
        // TODO create a new Ticket object
        
        manager.addTicket(null);  // TODO replace null with your new Ticket
        
        // TODO update list of all tickets
    }
    
    
    protected void searchById() {
        
       // TODO update list of tickets to just show the matching ticket, or a not found message.
        
    }
    
    
    protected void deleteById() {
        
        // Figure out ID and
        
    }
    
    
    protected void searchByIssue() {
        
        // TODO problem 3 implement this method.
        
    }
    
    
    
    protected void displayAllTickets() {
        // TODO Call this to update the allTickets JList
    }
    
    
    
    protected void quitProgram() {
        
        manager.quitProgram();
        
        //TODO Problem 6 use your new TicketFileIO class to save all open tickets to a file; save all resolved tickets to a separate file
    }
    
    //
    protected void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    
    // If user cancels, this will return null.
    protected String showUserInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    
    
}


