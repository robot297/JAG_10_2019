# Lab 9: More GUI programs:  Suport Ticket application

This program adds a GUI to the command line Support Ticket application from a previous lab.

This program is a prototype to manage IT support tickets for a company. Users will call or email a helpdesk to report computer problems, and this program keeps a record of all current problems. 

The tickets are assigned a priority between 1-5.   
Tickets need to store the priority, a description of the Task, the person who reported it, and a ticket ID number.

1 is the most urgent (e.g. all servers down); 5 is the least (e.g. missing mouse mat). 

When a Ticket is resolved (fixed or become non-issue), the ticket is deleted and the reason for deleting the ticket is recorded. 

Your Ticket objects should be able to store another date; `resolvedDate`, the date the ticket was closed.
And, a String that documents why the ticket was closed – the fix or the resolution for the ticket. This String should be called `resolution`

For this Task, you will implement a GUI for the program. 

### Task 1: TicketGUI, Set up priorityComboBox

Configure priorityComboBox so it contains the choices 1, 2, 3, 4, 5.

### Task 2: Configure TicketGUI ticketList JList

Configure `ticketList` so it will be able to display a list of Ticket objects. Use generic types. The Ticket objects should be shown in priority order, most urgent first. TicketStore should already be returning Ticket lists in the correct order.

The selection model should be `SINGLE_SELECTION`.

`ticketList` will be able to show any list of Tickets, for example, all the open Tickets, or only Tickets that match a search.

### Task 3: Get all the open tickets for ticketList JList

Ensure that you have provided the methods to read in open Ticket data from `open_tickets.txt`. 

In TicketGUI, Use the manager object to request all the current open Tickets.
Configure the ticketList to display this list of open Ticket objects when the GUI first opens.


Add a listener to `showAllTicketsButton` to show all of the current open tickets in `ticketList`.  The user may click this after searching for tickets. 
 
### Task 4: Add Ticket

Implement the add ticket feature in TicketGUI. Add a listener to addButton which reads data from 
descriptionTextField and reporterTextField and priorityComboBox.  Ensure that all the data needed has been entered.   If so, create a new ticket and call `manager.addTicket(newTicket)` to request that the new ticket is added to the Ticket store. 

The ticketList JList should update to show all the Tickets, including the new Ticket. 

If data (reporter or description or priority) is missing, display a message dialog to warn the user. Do not create a new Ticket. 
Use the showAlertDialog in TicketGUI to show the message dialog. 
 
### Task 5: Search by ID

Implement a listener for the `searchIdButton`. When this button is clicked, read the text in `idSearchTextBox`. Verify data has been entered, and that it is a positive integer. 

If there is no data or the data is invalid (not an integer, or a negative integer) the `ticketList` should show an empty list, and set the `ticketListStatusDescription` JLabel to the String `INVALID_TICKET_ID`

If the ID is a positive integer then use `manager` to search for the matching ticket.

If a ticket with this ID is found, display it in the ticketList and set the ticketListStatusDescription to `TICKET_MATCHING_ID`

If a ticket with this ID is not found, `ticketList` should not show any Tickets and ticketListStatusDescription should be set to `NO_TICKETS_FOUND`

Do not show any alert dialogs. 

### Task 6: Search by Description

Implement a listener for the `searchDescriptionButton`. When this button is clicked, read the text in `descriptionSearchTextBox`. Verify data has been entered.

If there is no search data, `ticketList` should be empty, and set the `ticketListStatusDescription` JLabel to the String `NO_TICKETS_FOUND`

If there is search data, then use `manager` to search for the matching tickets.

If a tickets containing the description are found (remember the search should not be case sensitive), display all the matching Tickets in `ticketList` and set `ticketListStatusDescription` to `TICKETS_MATCHING_DESCRIPTION`

If no tickets matching this description are found, `ticketList` should not show any Tickets and ticketListStatusDescription should be set to `NO_TICKETS_FOUND`

Do not show any alert dialogs. 

### Task 7: Delete Ticket

To delete Tickets, the user will select one Ticket in `ticketList` and click the `deleteSelectedButton` button.

Add a listener to `deleteSelectedButton` that checks if a Ticket is selected. Show an alert dialog with an appropriate message if no Ticket is selected in `ticketList`.

If a Ticket is selected in `ticketList`, then use TicketGUI's `showInputDialog` method to show an JOptionPane input dialog. 

The user will be able to click the Cancel button, if they don't want to delete/resolve the ticket. You should not delete the selected Ticket. The `ticketList` should not change.

Or, the user will type in a resolution String and click the OK button. Do the following tasks:

* Use the String entered to set the resolution of the ticket. Also, set the date it was resolved. 
* Call manager's resolveTicket to remove this Ticket from the ticket store, and add it to the resolved tickets store. 
* Display an alert dialog with a confirmation message. 
* You'll need to implement TicketProgram's `resolveTicket(Ticket)` method.
* The GUI should update `ticketList` so the deleted Ticket is no longer in the list. Display all the remaining open Tickets. 
* `ticketListStatusDescription` should show the String `ALL_TICKETS`. 

### Task 8: Save and Quit 

Add a listener to `saveAndQuitButton`. This should call the `manager.quitProgram()` method, which will use TicketFileIO to to save all current open and resolved Tickets to files, as implemented in the previous lab.

You should be able to use the same code written in the previous version of this program.

The TicketGUI should close itself by calling `dispose()`.  This is the only task running so will cause the program to exit.
 
 You should not save tickets to file until the user quits the program.

### Task 9: Load Tickets on relaunch

When your program opens, it should read in open Ticket information from `open_tickets.txt`, if this file exists. Your program should provide these to the GUI so all open Tickets are shown in `ticketList` when the program opens. 

You should be able to use the same code written in the previous version of this program.

You don't need to read in the previous resolved tickets. 

As before, what happens to ticket IDs when the program is closed and opened? Make sure they don't reset to 1 when the user restarts the program. Every ticket created should always have a unique positive integer ID, (excluding 0) no matter how many times the program is used*.   Save the counter information in a file with the name given by `TicketProgram.ticketCounterFile`.

You will need to create a second constructor for creating a tickets when the ID is already known. Make sure you don't break your mechanism for ensuring unique IDs. 

*Actually, you'll only be able to create approx 2 billion ticket IDs with this approach. That should be enough for now, although perhaps something to revisit in a future version.

Ensure that you include your mechanism for generating unique positive integer IDs for your Ticket objects. 
