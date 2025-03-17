/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;




@SuppressWarnings("unused")
public class CheckoutBookLinkedList implements Serializable {
	private static final long serialVersionUID = 1L;
	private BookNode head;
	
	//Default  Constructor
	public CheckoutBookLinkedList() {
			head = null; 
	}
	
	//Constructor to insert stack data 
	public void insertStack(BookStack book) {
		int count = book.countStack();
		
		if(count != 0 ) {
			BookStackElement temp = book.getTop();
			for(int i = 0; i < count; i++) {
				InsertatFront(temp.getBook());
				temp = temp.getPreviousElement();
			}
		}else {
			System.out.println("\nNo books checkedout");
		}
	}
	
	// Primary Constructor
    public CheckoutBookLinkedList(BookNode head) {
        this.head = head;
    }

    // Getters and Setters
    public BookNode getHead() {
        return head;
    }

    public void setHead(BookNode head) {
        this.head = head;
    }

    // InsertAtFront Method
    public void InsertatFront(Book dataToInsert) {
    	BookNode temp;
        temp = new BookNode();
        if (temp != null) {
            temp.setData(dataToInsert);
            temp.setNextNode(null);
            if (head == null) {
                head = temp;
            } else {
                temp.setNextNode(head);
                head = temp;
            }
            
            
        } else {
            System.err.println("Error. The list is full (out of memory). The list can not add a new node.");
        }
    }

    // InsertAtBack Method
    public void InsertAtBack(Book dataToInsert) {
    	BookNode temp1;
    	BookNode temp2;

        temp1 = new BookNode();

        if (temp1 != null) {
            temp1.setData(dataToInsert); // setting the data of the new node.
            temp1.setNextNode(null); // set the link portion of the node to point to null.

            if (head == null) { // if the list is empty.
                head = temp1; // setting head to point to the new node to be added to the list
            } else { // if the list is not empty
                temp2 = head; // initialize temp node to point to the first element in the list

                while (temp2.getNextNode() != null) { // while we are not at the last node in the list
                    temp2 = temp2.getNextNode(); // we are moving the current node temp2 to its next Node.
                }
                temp2.setNextNode(temp1); // set the link portion of the last node to point to the next node we are trying to add to the list
                
                
            }
            
        } else { // if memory was not allocated successfully
            System.err.println("Error. List is full, it can not add a new node.");
        }
    }

    // Insert at back using primary constructor 3
    public void InsertAtBack(String title, String author, String ISBN, boolean isAvailable) {
    	BookNode temp1;
    	BookNode temp2;

        temp1 = new BookNode(title,author,ISBN,isAvailable);

        if (temp1 != null) { // If data was allocated successfully
            if (head == null) { // if the list is empty.
                head = temp1; // setting head to point to the new node to be added to the list
            } else { // if the list is not empty
                temp2 = head; // initialize temp node to point to the first element in the list

                while (temp2.getNextNode() != null) { // while we are not at the last node in the list
                    temp2 = temp2.getNextNode(); // we are moving the current node temp2 to its next Node.
                }
                temp2.setNextNode(temp1); // set the link portion of the last node to point to the next node we are trying to add to the list
            }
        } else { // if memory was not allocated successfully
            System.err.println("Error. List is full, it can not add a new node.");
        }
    }

    //Change availability of all books corresponding in book linkedlist
    public void changeAllAvailability() {
    	if(!IsEmpty()) {
    		BookLinkedList list = new BookLinkedList();
    		BookNode curr = head; // initialize curr to point to the first element of the list
            while (curr != null) { // while curr is pointing to a valid node
                list.ChangeToAvailable(curr.getData().getISBN());
                curr = curr.getNextNode(); // points curr to its next node
            }
    	}
    }
    
    public int CountNode() {
        int count = 0; // initialize the counter
        BookNode curr = head; // initialize curr to point to the first element of the list
        while (curr != null) { // while curr is pointing to a valid node
            count++;
            curr = curr.getNextNode(); // points curr to its next node
        }
        return count;
    }

    public boolean SearchForANode(String title) {
        boolean isFound = false;
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getTitle().equalsIgnoreCase(title)) { // If the node has the data we are searching for
                isFound = true;
                break;
            }
            curr = curr.getNextNode(); // points curr to its next node
        }
        return isFound;
    }
    
    
    public boolean SearchForANode1 (String author) {
        boolean isFound = false;
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getAuthor().equalsIgnoreCase(author)) { // If the node has the data we are searching for
                isFound = true;
                break;
            }
            curr = curr.getNextNode(); // points curr to its next node
        }
        return isFound;
    }

    // Display list Method
    public void DisplayList() {
    	BookNode curr = head; // point curr to the first element in the list

        while (curr != null) { // while the list is not empty.
            System.out.println("Book Information is : " + curr.getData().toString2());

            curr = curr.getNextNode(); // point curr to the next node in the list
        }
        System.out.println("End of list.");
    } // End of DisplayList Method

    public boolean IsEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    public boolean IsFull() {
    	BookNode temp = new BookNode(); // attempt to reserve space for a new node by calling default constructor
        if (temp != null) {
            temp = null;
            return false;
        }
        return true;
    }

    public Book DeleteANode(String ISBN) {
        Book dataToReturn = new Book();
        if (!IsEmpty()) {
        	BookNode curr = head, prev = null;
            while (curr != null) {
                if (curr.getData().getISBN().equalsIgnoreCase(ISBN)) { // if the curr node has the data
                	BookLinkedList list = new BookLinkedList();
                	list.ChangeToAvailable(ISBN); //changes recently returned book to available
                    if (curr == head) { // if curr is pointing to the first node in the list
                        head = head.getNextNode(); // point head to its next node
                    } else {
                        prev.setNextNode(curr.getNextNode()); // setting the next node of the prev next node to the curr next node
                    }
                    dataToReturn = curr.getData(); // capture data from node to be deleted
                    curr = null;
                    break; // jump out of the loop
                }
                prev = curr;
                curr = curr.getNextNode(); // point curr to its next node
            }

        } else {
            System.err.println("Error. List full.");
        }
        return dataToReturn; 
    } // End of DeleteNode Method
    
    public Book DeleteANode1(String title) {
        Book dataToReturn = new Book();
        if (!IsEmpty()) {
        	BookNode curr = head, prev = null;
            while (curr != null) {
                if (curr.getData().getTitle().equalsIgnoreCase(title)) { // if the curr node has the data
                	BookLinkedList list = new BookLinkedList();
                	list.ChangeToAvailable(curr.getData().getISBN()); //changes recently returned book to available
                    if (curr == head) { // if curr is pointing to the first node in the list
                        head = head.getNextNode(); // point head to its next node
                    } else {
                        prev.setNextNode(curr.getNextNode()); // setting the next node of the prev next node to the curr next node
                    }
                    dataToReturn = curr.getData(); // capture data from node to be deleted
                    curr = null;
                    break; // jump out of the loop
                }
                prev = curr;
                curr = curr.getNextNode(); // point curr to its next node
            }

        } else {
            System.err.println("Error. List full.");
        }
        return dataToReturn; 
    } // End of DeleteNode Method

	public String displayListString() {
    StringBuilder sb = new StringBuilder();
    BookNode curr = head;
    if (curr == null) {
        sb.append("No books checked out.");
    } else {
        while (curr != null) {
            sb.append(curr.getData().toString2()).append("\n");
            curr = curr.getNextNode();
        }
    }
    return sb.toString();
}
    
}
