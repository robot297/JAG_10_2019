package week_10;

import javax.swing.*;


public class TicketGUI extends JFrame {
    
    // TODO complete the tasks described in Lab 10 Questions.md
    
    protected JPanel mainPanel;
    
    // Components for adding tickets
    protected JPanel addTicketPanel;
    protected JTextField descriptionTextField;
    protected JTextField reporterTextField;
    protected JComboBox priorityComboBox;
    protected JButton addButton;
    
    // Components for displaying ticket list
    protected JPanel ticketListPanel;
    protected JList<Ticket> ticketList;
    protected JLabel ticketListStatusDescription;
    
    // Components for searching
    protected JPanel searchPanel;
    protected JTextField descriptionSearchTextBox;
    protected JTextField idSearchTextBox;
    protected JButton searchDescriptionButton;
    protected JButton searchIdButton;
    protected JButton showAllTicketsButton;
    
    // Quit button
    protected JPanel controlsPanel;
    protected JButton quitButton;
    
    // Deleting
    protected JButton deleteSelectedButton;
    
    
    // Messages for showing in ticketListStatusDescription
    // TODO Use these instead of your own Strings, the tests expect you to use these constants
    static final String ALL_TICKETS = "Showing all tickets";
    static final String TICKETS_MATCHING_DESCRIPTION = "Tickets matching search description";
    static final String TICKET_MATCHING_ID = "Ticket matching ID";
    static final String NO_TICKETS_FOUND = "No matching tickets";
    static final String INVALID_TICKET_ID = "Invalid ticket ID";
    
    
    // A reference to the TicketProgram object
    // The GUI will call the methods in this class to add, search for, and delete tickets.
    // See example in quitProgram method.
    private TicketProgram controller;
    
    
    TicketGUI() {
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // TODO your code here.
        //  Recommended: create methods for different tasks so this constructor isn't gigantic.
        
    }
    
    /* You don't need to modify this method. In the rest of your code, when you need to send
    a message to the TicketProgram controller, use this controller object. So if you need
    to add a new ticket, you'll create a new Ticket object, then ask the TicketProgram controller
    to add the new Ticket to the database with
    controller.newTicket(myNewTicket);
    */
    public void setController(TicketProgram controller) {
        this.controller = controller;
    }
    
    // Call this method to quit the program. The tests expect you to use it.
    protected void quitProgram() {
        controller.quitProgram();    // Ask the controller to quit the program.
    }
    
    // Use this method to show message dialogs displaying the message given.
    // Otherwise tests for code that shows alert dialogs will time out and fail.
    // Don't modify this method.
    protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    // Use this method to show input dialogs asking the given question.
    // Otherwise tests for code that shows input dialogs will time out and fail.
    // If user presses the cancel button, this method will return null.
    // Don't modify this method.
    protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    
}


