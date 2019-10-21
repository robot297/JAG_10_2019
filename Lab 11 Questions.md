# Lab 11: More GUI programs: Support Ticket Application

This program adds a GUI and database to the command line Support Ticket application from a previous lab.

This program is a prototype to manage IT support tickets for a company. Users will call or email a helpdesk to report computer problems. The technician will enter the information into this program, which will keep a record of all current problems. 

The tickets are assigned a priority between 1-5.   
Tickets need to store the priority, a description of the Task, the person who reported it, and a ticket ID number.

1 is the most urgent (e.g. all servers down); 5 is the least urgent (e.g. missing mouse mat). 

When a Ticket is resolved (fixed or become non-issue), the ticket is deleted and the reason for deleting the ticket is recorded. 

Your Ticket objects should be able to store another date; `resolvedDate`, the date the ticket was closed.
And, a String that documents why the ticket was closed – the fix or the resolution for the ticket. This String should be called `resolution`

For this lab, you will implement a GUI for the program. 

### Task 1: 

set up database 

### Task 1: 

set up ticket class


### Task 3: TicketGUI, Set up priorityComboBox

Configure `priorityComboBox` so it contains the choices 1, 2, 3, 4, 5.


### Task 2: Configure TicketGUI ticketList JList

Configure `ticketList` so it will be able to display a list of Ticket objects. Use generic types. The Ticket objects should be shown in priority order, most urgent first. TicketStore should already be returning Ticket lists in the correct order.

The selection model should be `SINGLE_SELECTION`.

`ticketList` will be able to show any list of Tickets, for example, all the open Tickets, or only Tickets that match a search.


### Task 3: Get all the open tickets for ticketList JList

Ensure that you have provided the methods to read in open Ticket data from `open_tickets.txt`. 

In TicketGUI, use the manager object to request all the current open Tickets.
Configure the `ticketList` JList to display this list of open Ticket objects when the GUI first opens.

Add a listener to `showAllTicketsButton` to show all of the current open tickets in `ticketList`.  The user may click this after searching for tickets. 
 
 
### Task 4: Add Ticket

Implement the add ticket feature in TicketGUI. 

Add a listener to addButton which reads data from `descriptionTextField` and `reporterTextField` and `priorityComboBox`.  Ensure that all the data needed has been entered. If so, create a new Ticket and call `manager.addTicket(newTicket)` to request that the new ticket is added to the Ticket store. 

The ticketList JList should then update to show all the open Tickets, including the new Ticket. 

If data (reporter, or description, or priority) is missing, display a message dialog to warn the user. Do not create a new Ticket. 

Use the `showMessageDialog` method in TicketGUI to show the message dialog. 
 
 
### Task 5: Search by ID

Implement a listener for the `searchIdButton`. When this button is clicked, read the text in `idSearchTextBox`. Verify data has been entered, and that it is a positive integer. 

If there is no data or the data is invalid (not an integer, or a negative integer) the `ticketList` should show an empty list, and set the `ticketListStatusDescription` JLabel to the String `INVALID_TICKET_ID`

If the ID is a positive integer then use `manager` to search for the matching ticket.

If a ticket with this ID is found, display it in `ticketList` and set the `ticketListStatusDescription` to `TICKET_MATCHING_ID`

If a ticket with this ID is not found, `ticketList` should not show any Tickets and `ticketListStatusDescription` should be set to `NO_TICKETS_FOUND`

Do not show any message dialogs. 


### Task 6: Search by Description

Implement a listener for the `searchDescriptionButton`. When this button is clicked, read the text in `descriptionSearchTextBox`. Verify data has been entered.

If there is no search data, `ticketList` should be empty, and set the `ticketListStatusDescription` JLabel to the String `NO_TICKETS_FOUND`

If there is search data, then use `manager` to search for the matching tickets.

If a tickets containing the description are found (remember the search should not be case sensitive), display all the matching Tickets in `ticketList` and set `ticketListStatusDescription` to `TICKETS_MATCHING_DESCRIPTION`. The search method you wrote in the previous version of this lab should work for this lab too. 

If no tickets matching this description are found, `ticketList` should not show any Tickets and `ticketListStatusDescription` should be set to `NO_TICKETS_FOUND`

Do not show any message dialogs.
 

### Task 7: Delete Ticket

To delete Tickets, the user will select one Ticket in `ticketList` and click the `deleteSelectedButton` button.

Add a listener to `deleteSelectedButton` that checks if a Ticket is selected. Show a message dialog with an appropriate message if no Ticket is selected in `ticketList`.

If a Ticket is selected in `ticketList`, then use TicketGUI's `showInputDialog` method to show an JOptionPane input dialog. 

The user will be able to click the Cancel button, if they don't want to delete/resolve the ticket. `showInputDialog` returns null if the user clicks cancel. In this case, you should not delete the selected Ticket. The `ticketList` should not change.

Or, the user will type in a resolution String and click the OK button. Do the following tasks:

* Use the String entered to set the resolution of the ticket. 
* Set the date it was resolved to the current date/time 
* Call manager's `resolveTicket` method to remove this Ticket from the ticket store, and add it to the resolved tickets store. 
* Display a message dialog with a confirmation message. 
* You'll need to implement TicketProgram's `resolveTicket(Ticket ticket)` method.
* The GUI should update `ticketList` so the deleted Ticket is no longer in the list. Display all the remaining open Tickets. 
* `ticketListStatusDescription` should show the String `ALL_TICKETS`. 


### Task 8: Save and Quit 

Add a listener to `saveAndQuitButton`. This should call the `manager.quitProgram()` method, which will use TicketFileIO to to save all current open and resolved Tickets to files, as implemented in the previous lab.

As before, save all program data in a directory called TicketData. Your program should use the `ticketDataDirectory` variable for the directory name, so the tests can replace this name with a test directory and avoid overwriting your data.

You should be able to use the same code written in the previous version of this program.

The TicketGUI should close itself by calling `dispose()`.  This is the only task running so will cause the program to exit.
 
You need not save tickets to file until the user quits the program.
  
Use the same filenames as Lab 7.  

### Task 9: Load Open Tickets on Relaunch

When your program opens, it should read in open Ticket information from `TicketData/open_tickets.txt`, if this file exists. Your program should provide these to the GUI so all open Tickets are shown in `ticketList` when the program opens. 

