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
import java.util.ArrayList;
import java.util.List;

public class BookBST implements Serializable {
    private static final long serialVersionUID = 1L;
    private BSTNode root;

    public BookBST() {
        this.root = null;
    }

    // Insert books from linked list
    public void insertLinkedList(BookLinkedList book) {
        int count = book.CountNode();
        if (count != 0) {
            BookNode temp = book.getHead();
            for (int i = 0; i < count; i++) {
                insert(temp.getData());
                temp = temp.getNextNode();
            }
        }
    }

    // Insert a book into the BST (by title)
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    private BSTNode insertRec(BSTNode root, Book book) {
        if (root == null) {
            return new BSTNode(book);
        }

        if (book.getTitle().compareToIgnoreCase(root.book.getTitle()) < 0) {
            root.left = insertRec(root.left, book);
        } else {
            root.right = insertRec(root.right, book);
        }
        return root;
    }

    // Search for book by title
    public Book searchByTitle(String title) {
        return searchTitleRec(root, title);
    }

    private Book searchTitleRec(BSTNode root, String title) {
        if (root == null) {
            return null;
        }
        if (title.equalsIgnoreCase(root.book.getTitle())) {
            return root.book;
        }
        if (title.compareToIgnoreCase(root.book.getTitle()) < 0) {
            return searchTitleRec(root.left, title);
        } else {
            return searchTitleRec(root.right, title);
        }
    }

    // Search for books by author
    public List<Book> searchByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();
        searchAuthorRec(root, author, booksByAuthor);
        return booksByAuthor;
    }

    private void searchAuthorRec(BSTNode root, String author, List<Book> booksByAuthor) {
        if (root == null) {
            return;
        }
        searchAuthorRec(root.left, author, booksByAuthor);
        if (root.book.getAuthor().equalsIgnoreCase(author)) {
            booksByAuthor.add(root.book);
        }
        searchAuthorRec(root.right, author, booksByAuthor);
    }

    // Search for book by ISBN
    public Book searchByISBN(String ISBN) {
        return searchISBNRec(root, ISBN);
    }

    private Book searchISBNRec(BSTNode root, String ISBN) {
        if (root == null) {
            return null; // Fixed: was incorrectly returning root.book
        }
        if (ISBN.equalsIgnoreCase(root.book.getISBN())) {
            return root.book;
        }
        Book foundInLeft = searchISBNRec(root.left, ISBN);
        if (foundInLeft != null) return foundInLeft;
        return searchISBNRec(root.right, ISBN);
    }

    // Display Books in Sorted Order (console)
    public void displayInOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(BSTNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.book);
            inOrderRec(root.right);
        }
    }

    // Added for GUI: Get books in sorted order
    public List<Book> getInOrderList() {
        List<Book> books = new ArrayList<>();
        inOrderTraversal(root, books);
        return books;
    }

    private void inOrderTraversal(BSTNode root, List<Book> books) {
        if (root != null) {
            inOrderTraversal(root.left, books);
            books.add(root.book);
            inOrderTraversal(root.right, books);
        }
    }
}