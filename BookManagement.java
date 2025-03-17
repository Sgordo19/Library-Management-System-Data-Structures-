/*
Names: 
Jade Freeman: 2300078
Tonique Haywood: 2301114
Anttwone Marsh: 2304211
Jordon Taylor: 2304907
Shavon Gordon: 2306989

*/

package librarySystemsProject;

import java.util.List;

public class BookManagement {
    private BookLinkedList bookList;
    private BookBST bookTree;

    public BookManagement() {
        this.bookList = new BookLinkedList();
        this.bookTree = new BookBST();
        bookTree.insertLinkedList(bookList);
    }

    // Add a book (GUI-friendly)
    public void addBook(String title, String author, String ISBN, boolean isAvailable) {
        Book newBook = new Book(title, author, ISBN, isAvailable);
        bookList.InsertAtBack(newBook);
        bookTree.insert(newBook);
    }

    // Legacy console-based method
    public void addBook(java.util.Scanner scanner) {
        System.out.print("\nEnter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("\nEnter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("\nEnter ISBN: ");
        String ISBN = scanner.nextLine();
        boolean isAvailable = true; 
   

        addBook(title, author, ISBN, isAvailable);
        System.out.println("Book added successfully!");
    }

    // Search by Title
    public Book searchByTitle(String title) {
        return bookTree.searchByTitle(title);
    }

    // Search by Author (console)
    public void searchByAuthor(String author) {
        List<Book> books = bookTree.searchByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No books in library written by " + author);
        } else {
            System.out.println("\n-------------------------------------------------------------------");
            System.out.println("\n                             BOOKS BY " + author);
            System.out.println("\n-------------------------------------------------------------------");
            for (Book book : books) {
                System.out.println(book.toString());
            }
        }
    }

    // Search by Author (GUI)
    public String searchByAuthorString(String author) {
        StringBuilder sb = new StringBuilder();
        List<Book> books = bookTree.searchByAuthor(author);
        if (books.isEmpty()) {
            sb.append("No books in library written by ").append(author);
        } else {
            sb.append("Books by ").append(author).append(":\n");
            sb.append("-------------------------------------------------------------------\n");
            for (Book book : books) {
                sb.append(book.toString()).append("\n");
            }
            sb.append("-------------------------------------------------------------------");
        }
        return sb.toString();
    }

    // Search by ISBN
    public Book searchByISBN(String ISBN) {
        return bookTree.searchByISBN(ISBN);
    }

    // Display books sorted (console)
    public void displaySortedBooks() {
        bookTree.displayInOrder();
    }

    // Display books sorted (GUI)
    public String displaySortedBooksString() {
        StringBuilder sb = new StringBuilder();
        List<Book> sortedBooks = bookTree.getInOrderList();
        if (sortedBooks.isEmpty()) {
            sb.append("No books available in the system.");
        } else {
            for (Book book : sortedBooks) {
                sb.append(book.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    // Total Books
    public int totalbooks() {
        return bookList.CountNode();
    }

    // Display list (console)
    public void displayList() {
        bookList.DisplayList();
    }
}
