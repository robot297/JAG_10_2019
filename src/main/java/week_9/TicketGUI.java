package week_9;

import javax.swing.*;


public class TicketGUI extends JFrame {
    
    // TODO complete the tasks described in grades/Lab 9 Questions.md
    
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
    
    // Saving and quit
    protected JPanel controlsPanel;
    protected JButton saveAndQuitButton;
    
    // Deleting
    protected JButton deleteSelectedButton;
    
    
    // Messages for showing in ticketListStatusDescription
    // Use these instead of your own Strings, the tests expect you to use these variables
    static final String ALL_TICKETS = "Showing all tickets";
    static final String TICKETS_MATCHING_DESCRIPTION = "Tickets matching search description";
    static final String TICKET_MATCHING_ID = "Ticket matching ID";
    static final String NO_TICKETS_FOUND = "No matching tickets";
    static final String INVALID_TICKET_ID = "Invalid ticket ID";
    
    
    // A reference to the SupportTicketManagerWithGUI object
    // The GUI will call the methods in this class to add, search for, and delete tickets.
    // See example in quitProgram method.
    TicketProgram manager;
    
    
    TicketGUI(TicketProgram manager) {
        
        this.manager = manager;
        
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        
    }
    
    
    
    // Call this method to quit the program. The tests expect you to use it.
    protected void quitProgram() {
        manager.quitProgram();
        this.dispose();
    }
    
    
    // Use this method to show message dialogs displaying the message given.
    // Otherwise tests for code that shows alert dialogs will time out and fail.
    protected void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    // Use this method to show input dialogs asking the given question.
    // Otherwise tests for code that shows input dialogs will time out and fail.
    // If user presses the cancel button, this method will return null.
    protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    
    
}


