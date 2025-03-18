
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

public class BookLinkedList implements Serializable {
    private static final long serialVersionUID = 1L;
    private BookNode head;

    // Default Constructor
    public BookLinkedList() {
        try {
            File file = new File("Books.txt");
            if (file.isFile()) {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
                head = (BookNode) input.readObject();
                input.close();
            } else {
                head = null;
            }
        } catch (IOException | ClassNotFoundException e) 
        {  }
    }

    // Primary Constructor
    public BookLinkedList(BookNode head) {
        this.head = head;
    }

    // Getters and Setters
    public BookNode getHead() {
        return head;
    }

    public void setHead(BookNode head) {
        this.head = head;
        saveToFile();
    }

    // InsertAtFront
    public void InsertAtFront(Book dataToInsert) {
        BookNode temp = new BookNode(dataToInsert);
        if (temp != null) {
            temp.setNextNode(head);
            head = temp;
            saveToFile();
        }
    }

    // InsertAtBack
    public void InsertAtBack(Book dataToInsert) {
        BookNode temp = new BookNode(dataToInsert);
        if (temp != null) {
            if (head == null) {
                head = temp;
            } else {
                BookNode curr = head;
                while (curr.getNextNode() != null) {
                    curr = curr.getNextNode();
                }
                curr.setNextNode(temp);
            }
            saveToFile();
        }
    }

    // InsertAtBack with parameters
    public void InsertAtBack(String title, String author, String ISBN, boolean isAvailable) {
        BookNode temp = new BookNode(title, author, ISBN, isAvailable);
        if (temp != null) {
            if (head == null) {
                head = temp;
            } else {
                BookNode curr = head;
                while (curr.getNextNode() != null) {
                    curr = curr.getNextNode();
                }
                curr.setNextNode(temp);
            }
            saveToFile();
        }
    }

    // Count nodes
    public int CountNode() {
        int count = 0;
        BookNode curr = head;
        while (curr != null) {
            count++;
            curr = curr.getNextNode();
        }
        return count;
    }

    // Search by title
    public boolean SearchForANode(String title) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getTitle().equalsIgnoreCase(title)) {
                return true;
            }
            curr = curr.getNextNode();
        }
        return false;
    }

    // Change availability to Available
    public boolean ChangeToAvailable(String ISBN) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getISBN().equalsIgnoreCase(ISBN)) {
                curr.getData().setIsAvailable(true);
                saveToFile();
                return true;
            }
            curr = curr.getNextNode();
        }
        return false;
    }

    // Change availability to Checked Out
    public boolean ChangeToCheckedOut(String ISBN) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getISBN().equalsIgnoreCase(ISBN)) {
                curr.getData().setIsAvailable(false);
                saveToFile();
                return true;
            }
            curr = curr.getNextNode();
        }
        return false;
    }

    // Search by author
    public boolean SearchForANode1(String author) {
        BookNode curr = head;
        while (curr != null) {
            if (curr.getData().getAuthor().equalsIgnoreCase(author)) {
                return true;
            }
            curr = curr.getNextNode();
        }
        return false;
    }

    // Display list (console)
    public void DisplayList() {
        BookNode curr = head;
        if (curr == null) {
            System.out.println("No books in the list.");
        } else {
            while (curr != null) {
                System.out.println("Book Information: " + curr.getData().toString());
                curr = curr.getNextNode();
            }
            System.out.println("End of list.");
        }
    }

    // GUI-friendly display
    public String displayListString() {
        StringBuilder sb = new StringBuilder();
        BookNode curr = head;
        if (curr == null) {
            sb.append("No books in the list.");
        } else {
            while (curr != null) {
                sb.append(curr.getData().toString()).append("\n");
                curr = curr.getNextNode();
            }
        }
        return sb.toString();
    }

    // Check if empty
    public boolean IsEmpty() {
        return head == null;
    }

    // Check if full 
    public boolean IsFull() {
        return new BookNode() == null;
    }

    // Delete by title
    public Book DeleteANode(String title) {
        if (IsEmpty()) return null;
        BookNode curr = head, prev = null;
        while (curr != null) {
            if (curr.getData().getTitle().equalsIgnoreCase(title)) {
                if (curr == head) {
                    head = head.getNextNode();
                } else {
                    prev.setNextNode(curr.getNextNode());
                }
                Book deleted = curr.getData();
                saveToFile();
                return deleted;
            }
            prev = curr;
            curr = curr.getNextNode();
        }
        return null;
    }

    // Delete by author (returns first match)
    public Book DeleteANode1(String author) {
        if (IsEmpty()) return null;
        BookNode curr = head, prev = null;
        while (curr != null) {
            if (curr.getData().getAuthor().equalsIgnoreCase(author)) {
                if (curr == head) {
                    head = head.getNextNode();
                } else {
                    prev.setNextNode(curr.getNextNode());
                }
                Book deleted = curr.getData();
                saveToFile();
                return deleted;
            }
            prev = curr;
            curr = curr.getNextNode();
        }
        return null;
    }

    // Save to file
    private void saveToFile() {
        try {
            File file = new File("Books.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(head);
            output.close();
        } catch (IOException e) 
        {      }
    }
}