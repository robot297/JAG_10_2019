package week_9;

import javax.swing.*;
import java.util.LinkedList;

public class TicketGUI extends JFrame {
    
    
    private JPanel mainPanel;
    private JPanel addTicketPanel;
    private JPanel ticketListPanel;
    private JPanel searchPanel;
    private JPanel controlsPanel;
    private JTextField reporterTextField;
    private JComboBox priorityComboBox;
    private JButton addButton;
    private JList ticketList;
    private JTextField descriptionSearchTextBox;
    private JTextField idSearchTextBox;
    private JButton saveAndQuitButton;
    private JTextField descriptionTextField;
    private JButton searchDescriptionButton;
    private JButton searchIdButton;
    private JLabel ticketListStatusDescription;
    private JButton showAllTicketsButton;
    private JButton deleteSelectedButton;
    
    SupportTicketManagerWithGUI manager;
    
    TicketGUI(SupportTicketManagerWithGUI manager) {
        
        this.manager = manager;
        
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        LinkedList<Ticket> allTickets = manager.getAllTickets();
        
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
    
    
        private JLabel ticketListStatusDescription;
    
    
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
    
}
