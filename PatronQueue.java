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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class PatronQueue implements Serializable {
    private static final long serialVersionUID = 1L;

    private static class Node implements Serializable {
        private static final long serialVersionUID = 1L;
        private Patron patron;
        private Node next;

        public Node(Patron patron) {
            this.patron = patron;
            this.next = null;
        }

        public Patron getPatron() {
            return patron;
        }

        public void setPatron(Patron patron) {
            this.patron = patron;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node front, rear;

    public PatronQueue() {
        try {
            File file = new File("PatronQueue.txt");
            if (file.isFile()) {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
                front = (Node) input.readObject();
                Node current = front;
                while (current != null && current.next != null) {
                    current = current.next;
                }
                rear = current;
                input.close();
            } else {
                this.front = this.rear = null;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    // Add a patron to the queue
    public void enqueue(Patron patron) {
        Node newNode = new Node(patron);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }
        saveToFile();
    }

    // Display Queue items (Console Version)
    public void display() {
        if (isEmpty()) {
            System.out.println("No books are currently checked out");
        } else {
            System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------");
            Node temp = front;
            while (temp != null) {
                temp.getPatron().Display();
                temp = temp.getNext();
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    // Search if Patron has books to return
    public boolean search(int cardNumber) {
        if (isEmpty()) {
            return false; // No need to print here, let caller handle it
        }
        Node temp = front;
        while (temp != null) {
            if (temp.getPatron().getCardNumber() == cardNumber) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

    // Remove and return the first patron
    public Patron dequeue() {
        if (isEmpty()) return null;
        Patron patron = front.getPatron();
        front = front.getNext();
        if (front == null) rear = null; // Queue is empty
        saveToFile();
        return patron;
    }

    // Updates First Person if not all books are returned
    public void addFront(Patron patron) {
        if (isEmpty()) {
            System.out.println("Error occurred while updating Queue");
            return;
        }
        front.setPatron(patron);
        saveToFile();
    }

    // Peek at the first patron
    public Patron peek() {
        return isEmpty() ? null : front.getPatron();
    }

    // Peek at the last patron
    public Patron Rpeek() {
        return isEmpty() ? null : rear.getPatron();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Save to File
    private void saveToFile() {
        try {
            File file = new File("PatronQueue.txt");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(front);
            output.close();
        } catch (IOException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    // GUI Support: Return queue contents as a String
    public String displayString() {
        StringBuilder sb = new StringBuilder();
        if (isEmpty()) {
            sb.append("No patrons currently in checkout queue.");
        } else {
            Node temp = front;
            sb.append("Current Checkout Queue:\n");
            sb.append("----------------------------------------\n");
            while (temp != null) {
                sb.append(temp.getPatron().toString()).append("\n");
                temp = temp.getNext();
            }
            sb.append("----------------------------------------");
        }
        return sb.toString();
    }

    // Helper method to get queue contents as an array
    private Patron[] getQueueContents() {
        if (isEmpty()) {
            return new Patron[0];
        }
        // Count nodes first
        int count = 0;
        Node temp = front;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        // Create array
        Patron[] patrons = new Patron[count];
        temp = front;
        for (int i = 0; i < count; i++) {
            patrons[i] = temp.getPatron();
            temp = temp.getNext();
        }
        return patrons;
    }
}