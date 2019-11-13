# Lab 10: More GUI programs: Travel Wish List Application

This program is a different version of the command line Travel Wish List application from a previous lab.

For this lab, you will implement a GUI for the program. 

You will be working with lists of String place names. You don't need a Place class.

**IMPORTANT TEST INFO for `WishListMenuTest.java` These tests use a different testing library, AssertJ. This launches the GUI, and uses the mouse and keyboard to enter data, click buttons etc. When these tests launch, let them run to completion. Don't click anywhere outside the Wishlist program. Otherwise, the tests may get stuck or start clicking/typing in other applications running on your computer! If they get stuck, they'll time out and stop within 20 seconds.
If you want to quit these tests as they are running, press Control+Shift+A.**

You will not need to modify the GUI design, or add or remove any components from WishListGUI.form. 


## Task 1: Set up JList Models

The GUI has two JLists - one for the names of places the user wishes to go to `wishList`, and names of places the user has visited, `visitedList`.

These lists should be lists of Strings.
Create and assign a model of Strings for both lists. 


## Task 2: Add a new place 

Add an event listener to the `addButton` JButton. 

When this button is clicked, read the text entered in `newPlaceNameTextField`.

If the text is blank, then don't do anything. Don't add anything to the `wishList`.

If the text is not blank, add the new place to the wish list model, to add it to the `wishList` JList.  Clear the text in `newPlaceNameTextField`.

The program should not accept duplicate names. If the place name is already in the `wishList`, then display an alert dialog using the `showMessageDialog` method provided for you in `WishListGUI.java`.  Do not add the place name again.

For this program, you can assume two place names in different cases are the same place. So your program should NOT permit "new york" if "New York" is already in the list.  

Important! You must use the `showMessageDialog` method to show a message dialog. 
 
 
## Task 3: Marking a place visited 

Add a JPopupMenu to the `wishList`. It will have two menu items,

-Visited!  
-Delete

The JMenuItems should have the exact text "Visited!" and "Delete".

When the user right-clicks on a place and selects "Visited!" the place should be removed from the `wishList` and added to the `visitedList`.


## Task 4: Deleting a WishList Place 

You added a JPopupMenu for the `wishList` for Task 3. 

For this task, implement the Delete menu item.

When the user right-clicks and selects Delete from the menu, remove the place from the `wishList`.

Do not show any dialogs, or ask for confirmation. Just remove the place from the `wishList`.


## Task 5: Deleting a Visited Place 

Add a JPopupMenu to the `visitedList`.

This menu has one item

-Delete

Use this exact text for the JMenuItem.

When the user right-clicks and selects Delete from the menu, remove the place from the `visitedList`.

Do not show any dialogs, or ask for confirmation. Just remove the place from the `visitedList`.


## Task 6: Saving to files 

Add an event listener to `saveQuitButton` JButton. 

When the user clicks the Save and Quit button, call the Main.quit method with a list of places in the `wishList` and a list of places in the `visitedList`. You'll need to build these lists.

In Storage.java, finish the `writeListToFile` method. Write the list of String places to a file, one String per line.

Write the lines in the same order as they are displayed in the JList.

Overwrite any existing files. 

Use the try-with-resources style of exception handling.  

The Storage.java `writeListToFile` method should NOT declare it throws any exceptions. 

Finally, in your saveQuitButton event handler in the GUI, call `dispose()` to close the GUI window and end the program. 

## Task 7: Loading Saved Places 

Finish the `readListFromFile` method in Storage.java. 

When the program is launched, this method is called.

Write code to read all of the Strings from the filename, add each String to a list, and return it.

Use the try-with-resources style of exception handling.  

The Storage.java `writeListToFile` method should NOT declare it throws any exceptions. 

If the filename is not found, or if there are any other IOException thrown, return an empty list. 

In WishListGUI.java, the constructor is called with a list of `wishListPlaces`, and a list of `visitedPlaces`.  In the constructor, use these lists to add places to the `wishList` and `visitedList` JLists. Add the place names in the same order as they are found in the files.

So, when the program starts, it will read lists of places from file. It will display places read from file in the JLists.  When user saves and quits, all of the places in the `wishList` and `visitedList` will be written to file. So the next time the program starts, these places will be displayed. 