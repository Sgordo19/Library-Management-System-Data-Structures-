/*
Names: 
Jade Freeman: 2300078
Shavon Gordon: 2306989
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
*/

package librarySystemsProject;

import java.io.Serializable;

public class CheckinBookLinkedList implements Serializable {
    private static final long serialVersionUID = 1L;
    private BookNode head;

    // Default Constructor
    public CheckinBookLinkedList() {
        head = null;
    }

    // Insert a book at the front of the check-in list
    public void insertAtFront(Book book) {
        BookNode newNode = new BookNode(book);
        newNode.setNextNode(head);
        head = newNode;
    }

    /* Insert a book at the back of the check-in list
    public void insertAtBack(Book book) {
        BookNode newNode = new BookNode(book);
        if (head == null) {
            head = newNode;
            return;
        }
        BookNode temp = head;
        while (temp.getNextNode() != null) {
            temp = temp.getNextNode();
        }
        temp.setNextNode(newNode);
    }*/

    // Search for a book by title
    public boolean searchByTitle(String title) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getTitle().equalsIgnoreCase(title)) { // Standardized method name
                return true;
            }
            curr = curr.getNextNode();
        }
        return false;
    }

    // Remove a checked-in book from the list by title
    public boolean removeBook(String title) {
        if (head == null) return false;

        if (head.getData().getTitle().equalsIgnoreCase(title)) { // Standardized method name
            head = head.getNextNode();
            return true;
        }

        BookNode curr = head, prev = null;
        while (curr != null && !curr.getData().getTitle().equalsIgnoreCase(title)) { // Standardized method name
            prev = curr;
            curr = curr.getNextNode();
        }

        if (curr == null) return false;
        prev.setNextNode(curr.getNextNode());
        return true;
    }

    // Display list of checked-in books
    public void displayList() {
        BookNode curr = head;
        while (curr != null) {
            System.out.println("Checked-in Book: " + curr.getData());
            curr = curr.getNextNode();
        }
        System.out.println("End of checked-in books list.");
    }

    // Check if the check-in list is empty
    public boolean isEmpty() {
        return head == null;
    }
}
