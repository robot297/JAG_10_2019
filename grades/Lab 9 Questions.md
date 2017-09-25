# Lab 9: More GUI programs:  Suport Ticket application

## Problem 1: Support Ticket GUI 


This program is a prototype to manage IT support tickets for a company. Users would call or email a helpdesk to report computer problems, and this program keeps a record of all current problems. 

The tickets are assigned a priority between 1-5.   

1 is the most urgent (e.g. all servers down); 5 is the least (e.g. missing mouse mat). 
When a problem is fixed, the ticket is deleted. 

For this problem, create a GUI for the program you built in Lab 7 

### Problem 2:

Implement a way to add a new ticket. Use a GUI component that restricts the priority entered is between 1 and 5. 

### Problem 3:

Search boxes: by description and by ID

Show matching tickets in the list


### Problem 4:

Delete: user should be able to click on one ticket in the list and click a delete button.

Use a JOptionPane show input dialog to get the resolution

remove ticket from list, save in resovled ticket store 


### Problem 5:

Modify your program so you can save information about deleted tickets.

Your Tickets should be able to store another date; `resolvedDate`, the date the ticket was closed.
And, a String that documents why the ticket was closed – the fix or the resolution for the ticket. This String should be called `resolution`

Now assume that when users delete a ticket, it has been resolved in some way. Either a technician fixed the problem, or the user has figured out how to change their own screensaver, or it’s become a non-issue in some other way.

Now, when you delete a Ticket, your program should ask the user for the resolution. It should store the resolution, plus the current date. Now, remove this Ticket from the ticketQueue list.

And, add the resolved ticket to a new data structure.

Will you create a new class to manage the list of resolved tickets? Re-use TicketStore? Or something else?

You'll need to add some new methods to TicketUI and TicketStore. Keep your classes focused on their current roles. 

### Problem 6:


Re-use the code from Lab 7. 

When the program is closed, all the data is lost.  Add the ability to save all data to file.  You can decide how to organize and structure the data in your files. 

Create a new class to manage the file input and output. Question_3_Support_Ticket_Manager will use this class when the program starts and ends.  

When the program closes, write out all the data about all open tickets to one file. 
Write all data about tickets that have been resolved in this session, to a separate file.

Resolved tickets should go into one file, per day. This file should have today’s date in the filename. Something like `resolved_tickets_as_of_february_20_2014.txt` perhaps? If you run the program twice on one day, make sure you don't overwrite existing tickets in that day's file. (Hint: you can open a file in append mode). 

Open tickets should go in another file called `open_tickets.txt`.

### Problem 7: 

When your program opens, it should look for a file called `open_tickets.txt`. If this file exists, read in this file, and create ticket objects, and store these in the TicketStore object list so the user can see all open tickets.

You don't need to read in the previous resolved tickets. 

What happens to ticket IDs when the program is closed and opened? Make sure they don't reset to 1 when the user restarts the program.

