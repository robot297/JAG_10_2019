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
    
    
        // TODO complete the tasks described in grades/Lab 9 Questions.md

        
    }
    
    
    
    protected void quitProgram() {
        manager.quitProgram();
    }
    
    
    // Use this method to show Alert dialogs. Otherwise tests for
    // code that shows Alert Dialogs will time out and fail.
    protected void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    // Use this method to show input dialogs. Otherwise tests for
    // code that shows Alert Dialogs will time out and fail.
    
    // If user cancels, this will return null.
    protected String showInputDialog(String question) {
        return JOptionPane.showInputDialog(this, question);
    }
    
    
}


